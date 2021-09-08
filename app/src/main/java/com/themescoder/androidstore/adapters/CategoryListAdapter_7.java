package com.themescoder.androidstore.adapters;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import com.themescoder.androidstore.R;
import com.themescoder.androidstore.activities.MainActivity;
import com.themescoder.androidstore.constant.ConstantValues;
import com.themescoder.androidstore.fragment.Products;
import com.themescoder.androidstore.fragment.SubCategories_1;
import com.themescoder.androidstore.fragment.SubCategories_5;
import com.themescoder.androidstore.models.category_model.CategoryDetails;


/**
 * CategoryListAdapter is the adapter class of RecyclerView holding List of Categories in MainCategories
 **/

public class CategoryListAdapter_7 extends RecyclerView.Adapter<CategoryListAdapter_7.MyViewHolder> {

    boolean isSubCategory;

    Context context;
    List<CategoryDetails> categoriesList;
    List<CategoryDetails> allCategoriesList;


    public CategoryListAdapter_7(Context context, List<CategoryDetails> categoriesList, List<CategoryDetails> allCategoriesList, boolean isSubCategory) {
        this.context = context;
        this.isSubCategory = isSubCategory;
        this.categoriesList = categoriesList;
        this.allCategoriesList = allCategoriesList;
    }



    //********** Called to Inflate a Layout from XML and then return the Holder *********//

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_categories_7, parent, false);

        return new MyViewHolder(itemView);
    }



    //********** Called by RecyclerView to display the Data at the specified Position *********//

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // Get the data model based on Position
        final CategoryDetails categoryDetails = categoriesList.get(position);

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);
        // Set OrderProductCategory Image on ImageView with Glide Library
        Glide.with(context)
                .setDefaultRequestOptions(options)
                .load(ConstantValues.ECOMMERCE_URL+categoryDetails.getImage())
                .into(holder.category_image);


        holder.category_title.setText(categoryDetails.getName());
        holder.category_products.setText(categoryDetails.getTotalProducts() + " "+ context.getString(R.string.products));
    }



    //********** Returns the total number of items in the data set *********//

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }



    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    
        ImageView category_image;
        LinearLayout category_card;
        TextView category_title, category_products;


        public MyViewHolder(final View itemView) {
            super(itemView);
            category_card = (LinearLayout) itemView.findViewById(R.id.category_card);
            category_image = itemView.findViewById(R.id.category_image);
            category_title = (TextView) itemView.findViewById(R.id.category_title);
            category_products = (TextView) itemView.findViewById(R.id.category_products);

            category_card.setOnClickListener(this);
        }


        // Handle Click Listener on OrderProductCategory item
        @Override
        public void onClick(View view) {
            // Get OrderProductCategory Info
            Bundle categoryInfo = new Bundle();
            categoryInfo.putInt("CategoryID", Integer.parseInt(categoriesList.get(getAdapterPosition()).getId()));
            categoryInfo.putString("CategoryName", categoriesList.get(getAdapterPosition()).getName());

            Fragment fragment;

            if (isSubCategory) {
                // Navigate to Products Fragment
                fragment = new Products();
            } else {
                if (allCategoriesList != null){
                    boolean haveCategorisies = false;
                    for (int i=0;  i<allCategoriesList.size();  i++) {
                        if (allCategoriesList.get(i).getParentId().equalsIgnoreCase(categoriesList.get(getAdapterPosition()).getId())) {
                            haveCategorisies = true;
                            break;
                        }
                    }

                    if (!haveCategorisies){
                        fragment = new Products();
                    } else {
                        // Navigate to SubCategories Fragment
                        fragment = new SubCategories_5();
                    }

                } else {
                    // Navigate to SubCategories Fragment
                    fragment = new SubCategories_5();
                }
            }

            fragment.setArguments(categoryInfo);
            FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.main_fragment, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(null).commit();
        }
    }

}

