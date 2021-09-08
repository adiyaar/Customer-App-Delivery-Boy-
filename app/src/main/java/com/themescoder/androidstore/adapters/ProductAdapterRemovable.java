package com.themescoder.androidstore.adapters;


import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.facebook.shimmer.ShimmerFrameLayout;

import com.themescoder.androidstore.activities.MainActivity;
import com.themescoder.androidstore.R;

import java.text.DecimalFormat;
import java.util.List;

import com.themescoder.androidstore.fragment.WishList;
import com.themescoder.androidstore.utils.Utilities;
import com.themescoder.androidstore.constant.ConstantValues;
import com.themescoder.androidstore.databases.User_Recents_DB;
import com.themescoder.androidstore.fragment.Product_Description;
import com.themescoder.androidstore.models.product_model.ProductDetails;


/**
 * ProductAdapterRemovable is the adapter class of RecyclerView holding List of Products in RecentlyViewed and WishList
 **/

public class ProductAdapterRemovable extends RecyclerView.Adapter<ProductAdapterRemovable.MyViewHolder> {

    private Context context;

    private String customerID;
    private TextView emptyText;

    private boolean isRecents;
    private boolean isHorizontal;

    private List<ProductDetails> productList;

    private User_Recents_DB recents_db;
    int defaultSmLayoutId;
    int defaultLgLayoutId;
    WishList wishList;

    public ProductAdapterRemovable(Context context, List<ProductDetails> productList, boolean isHorizontal, boolean isRecents, TextView emptyText, WishList wishList) {
        this.context = context;
        this.productList = productList;
        this.isHorizontal = isHorizontal;
        this.isRecents = isRecents;
        this.emptyText = emptyText;
        this.wishList = wishList;

        recents_db = new User_Recents_DB();
        customerID = this.context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE).getString("userID", "");
        setupDefaultLayoutId();
    }



    //********** Called to Inflate a Layout from XML and then return the Holder *********//

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView = null;

        if (isHorizontal) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(defaultSmLayoutId, parent, false);
        } else {
            itemView = LayoutInflater.from(parent.getContext()).inflate(defaultLgLayoutId, parent, false);
        }

        return new MyViewHolder(itemView);
    }



    //********** Called by RecyclerView to display the Data at the specified Position *********//

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        // Get the data model based on Position
        final ProductDetails product = productList.get(position);

        holder.product_checked.setVisibility(View.GONE);

        RequestOptions options = new RequestOptions()
                .centerInside()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);

        // Set Product Image on ImageView with Glide Library
        Glide.with(context)
                .setDefaultRequestOptions(options)
                .load(ConstantValues.ECOMMERCE_URL + (product.getImages().size() > 0 ? product.getImages().get(0).getImage() : product.getProductsImage()))
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.shimmerProgress.setVisibility(View.GONE);
                        holder.shimmerProgress.stopShimmer();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.shimmerProgress.setVisibility(View.GONE);
                        holder.shimmerProgress.stopShimmer();
                        return false;
                    }
                })
                .into(holder.product_thumbnail);

        holder.product_title.setText(product.getProductsName());
        holder.product_price_old.setPaintFlags(holder.product_price_old.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);

        holder.product_category.setText(product.getCategoryNames());

        // Calculate the Discount on Product with static method of Helper class
        String discount = Utilities.checkDiscount(product.getProductsPrice(), product.getDiscountPrice());

        if (discount != null) {
            // Set Product's Price
            holder.product_price_old.setVisibility(View.VISIBLE);
            holder.product_price_old.setText(ConstantValues.CURRENCY_SYMBOL + new DecimalFormat("#0.00").format(Double.valueOf(product.getProductsPrice())));
            holder.product_price_new.setText(ConstantValues.CURRENCY_SYMBOL + new DecimalFormat("#0.00").format(Double.valueOf(product.getDiscountPrice())));

            holder.product_tag_new.setVisibility(View.GONE);

            // Set Discount Tag and its Text
            holder.layoutSale.setVisibility(View.VISIBLE);
            holder.product_tag_discount_text.setText(discount);

        } else {

            // Check if the Product is Newly Added with the help of static method of Helper class
            if (Utilities.checkNewProduct(product.getProductsDateAdded())) {
                // Set New Tag and its Text
                holder.product_tag_new.setVisibility(View.VISIBLE);
            } else {
                holder.product_tag_new.setVisibility(View.GONE);
            }

            // Hide Discount Text and Set Product's Price
            holder.layoutSale.setVisibility(View.GONE);
            holder.product_price_old.setVisibility(View.GONE);
            holder.product_price_new.setText(ConstantValues.CURRENCY_SYMBOL + new DecimalFormat("#0.00").format(Double.valueOf(product.getProductsPrice())));
        }
        
        

        // Handle the Click event of product_thumbnail ImageView
        holder.product_thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get Product Info
                Bundle itemInfo = new Bundle();
                itemInfo.putParcelable("productDetails", product);

                // Navigate to Product_Description of selected Product
                Fragment fragment = new Product_Description(wishList);
                fragment.setArguments(itemInfo);
                //MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
                FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.main_fragment, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null).commit();


                // Add the Product to User's Recently Viewed Products
                if (!recents_db.getUserRecents().contains(product.getProductsId())) {
                    recents_db.insertRecentItem(product.getProductsId());
                }
            }
        });
    
    
        holder.product_remove_btn.setVisibility(View.VISIBLE);
    
        // Handle Click event of product_remove_btn Button
        holder.product_remove_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            
                // Check if Product is in User's Recents
                if (isRecents) {
                    // Delete Product from User_Recents_DB Local Database
                    recents_db.deleteRecentItem(product.getProductsId());
                
                } else {
                    // Unlike the Product for the User with the static method of Product_Description
                    Product_Description.UnlikeProduct(product.getProductsId(), customerID, context, view);
                }
            
                // Remove Product from productList List
                productList.remove(holder.getAdapterPosition());
            
                notifyItemRemoved(holder.getAdapterPosition());
            
                // Update View
                updateView(isRecents);
            }
        });

    }



    //********** Returns the total number of items in the RecyclerView *********//

    @Override
    public int getItemCount() {
        return productList.size();
    }



    //********** Change the Layout View based on the total number of items in the RecyclerView *********//

    public void updateView(boolean isRecentProducts) {


        // Check if Product is in User's Recents
        if (isRecentProducts) {

            // Check if RecyclerView has some Items
            if (getItemCount() != 0) {
                emptyText.setVisibility(View.VISIBLE);
            } else {
                emptyText.setVisibility(View.GONE);
            }

        } else {

            // Check if RecyclerView has some Items
            if (getItemCount() != 0) {
                emptyText.setVisibility(View.GONE);
            } else {
                emptyText.setVisibility(View.VISIBLE);
            }
        }
        
        notifyDataSetChanged();

    }



    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/

    public class MyViewHolder extends RecyclerView.ViewHolder {


        ImageView product_checked;
        Button product_remove_btn;
        ImageButton product_card_img_btn;
        ToggleButton product_like_btn;
        ImageView product_thumbnail, product_tag_new;
        TextView product_title, product_category, product_price_old, product_price_new, product_tag_discount_text;
        LinearLayout layoutSale;
        ShimmerFrameLayout shimmerProgress;

        public MyViewHolder(final View itemView) {
            super(itemView);

            product_checked = (ImageView) itemView.findViewById(R.id.product_checked);

            product_remove_btn = (Button) itemView.findViewById(R.id.product_card_Btn);
            product_card_img_btn = itemView.findViewById(R.id.product_card_img_btn);
            product_like_btn = (ToggleButton) itemView.findViewById(R.id.product_like_btn);
            product_title = (TextView) itemView.findViewById(R.id.product_title);
            product_category = (TextView) itemView.findViewById(R.id.product_category);
            product_price_old = (TextView) itemView.findViewById(R.id.product_price_old);
            product_price_new = (TextView) itemView.findViewById(R.id.product_price_new);
            product_thumbnail = (ImageView) itemView.findViewById(R.id.product_cover);
            product_tag_new = (ImageView) itemView.findViewById(R.id.product_item_tag_new);
            product_tag_discount_text = (TextView) itemView.findViewById(R.id.productItemTagOff);
            layoutSale = itemView.findViewById(R.id.saleLayout);
            shimmerProgress = itemView.findViewById(R.id.shimmerFrame);

            product_card_img_btn.setVisibility(View.GONE);
            product_like_btn.setVisibility(View.GONE);
            product_remove_btn.setText(context.getString(R.string.removeProduct));
            product_remove_btn.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_corners_button_red));
        }
    }

    private void setupDefaultLayoutId() {
        switch (ConstantValues.DEFAULT_PRODUCT_CARD_STYLE){
            case 0:
                defaultSmLayoutId = R.layout.layout_product_0_grid_sm;
                defaultLgLayoutId = R.layout.layout_product_0_grid_lg;
                break;
            case 1:
                defaultSmLayoutId = R.layout.layout_product_1_grid_sm;
                defaultLgLayoutId = R.layout.layout_product_1_grid_lg;
                break;
            case 2:
                defaultSmLayoutId = R.layout.layout_product_2_grid_sm;
                defaultLgLayoutId = R.layout.layout_product_2_grid_lg;
                break;
            case 3:
                defaultSmLayoutId = R.layout.layout_product_3_grid_sm;
                defaultLgLayoutId = R.layout.layout_product_3_grid_lg;
                break;
            case 4:
                defaultSmLayoutId = R.layout.layout_product_4_grid_sm;
                defaultLgLayoutId = R.layout.layout_product_4_grid_lg;
                break;
            case 5:
                defaultSmLayoutId = R.layout.layout_product_5_grid_sm;
                defaultLgLayoutId = R.layout.layout_product_5_grid_lg;
                break;
            case 6:
                defaultSmLayoutId = R.layout.layout_product_6_grid_sm;
                defaultLgLayoutId = R.layout.layout_product_6_grid_lg;
                break;
            case 7:
                defaultSmLayoutId = R.layout.layout_product_0_grid_sm;
                defaultLgLayoutId = R.layout.layout_product_0_grid_lg;
                break;
            case 8:
                defaultSmLayoutId = R.layout.layout_product_8_grid_sm;
                defaultLgLayoutId = R.layout.layout_product_8_grid_lg;
                break;
            case 9:
                defaultSmLayoutId = R.layout.layout_product_9_grid_sm;
                defaultLgLayoutId = R.layout.layout_product_9_grid_lg;
                break;
            case 10:
                defaultSmLayoutId = R.layout.layout_product_10_grid_sm;
                defaultLgLayoutId = R.layout.layout_product_10_grid_lg;
                break;
            case 11:
                defaultSmLayoutId = R.layout.layout_product_11_grid_sm;
                defaultLgLayoutId = R.layout.layout_product_11_grid_lg;
                break;
            case 12:
                defaultSmLayoutId = R.layout.layout_product_12_grid_sm;
                defaultLgLayoutId = R.layout.layout_product_12_grid_lg;
                break;
            case 13:
                defaultSmLayoutId = R.layout.layout_product_13_grid_sm;
                defaultLgLayoutId = R.layout.layout_product_13_grid_lg;
                break;
            case 14:
                defaultSmLayoutId = R.layout.layout_product_14_grid_sm;
                defaultLgLayoutId = R.layout.layout_product_14_grid_lg;
                break;
            case 15:
                defaultSmLayoutId = R.layout.layout_product_15_grid_sm;
                defaultLgLayoutId = R.layout.layout_product_15_grid_lg;
                break;
            case 16:
                defaultSmLayoutId = R.layout.layout_product_16_grid_sm;
                defaultLgLayoutId = R.layout.layout_product_16_grid_lg;
                break;
            case 17:
                defaultSmLayoutId = R.layout.layout_product_17_grid_sm;
                defaultLgLayoutId = R.layout.layout_product_17_grid_lg;
                break;
            case 18:
                defaultSmLayoutId = R.layout.layout_product_18_grid_sm;
                defaultLgLayoutId = R.layout.layout_product_18_grid_lg;
                break;
            case 19:
                defaultSmLayoutId = R.layout.layout_product_19_grid_sm;
                defaultLgLayoutId = R.layout.layout_product_19_grid_lg;
                break;
            case 20:
                defaultSmLayoutId = R.layout.layout_product_20_grid_sm;
                defaultLgLayoutId = R.layout.layout_product_20_grid_lg;
                break;
            case 21:
                defaultSmLayoutId = R.layout.layout_product_21_grid_sm;
                defaultLgLayoutId = R.layout.layout_product_21_grid_lg;
                break;
            case 22:
                defaultSmLayoutId = R.layout.layout_product_22_grid_sm;
                defaultLgLayoutId = R.layout.layout_product_22_grid_lg;
                break;
        }
    }
}

