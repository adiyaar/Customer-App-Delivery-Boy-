package com.themescoder.androidstore.adapters;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.themescoder.androidstore.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.themescoder.androidstore.activities.MainActivity;
import com.themescoder.androidstore.constant.ConstantValues;
import com.themescoder.androidstore.fragment.Product_Description;
import com.themescoder.androidstore.models.order_model.OrderProducts;
import com.themescoder.androidstore.models.order_model.PostProductsAttributes;
import com.themescoder.androidstore.models.product_model.Value;


/**
 * OrderedProductsListAdapter is the adapter class of RecyclerView holding List of Ordered Products in Order_Details
 **/

public class OrderedProductsListAdapter extends RecyclerView.Adapter<OrderedProductsListAdapter.MyViewHolder> {

    private Context context;
    private List<OrderProducts> orderProductsList;

    private ProductAttributeValuesAdapter attributesAdapter;
    private String currency = "";

    public OrderedProductsListAdapter(Context context, List<OrderProducts> orderProductsList, String currency) {
        this.context = context;
        this.orderProductsList = orderProductsList;
        this.currency = currency;
    }


    //********** Called to Inflate a Layout from XML and then return the Holder *********//

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_checkout_items, parent, false);

        return new MyViewHolder(itemView);
    }


    //********** Called by RecyclerView to display the Data at the specified Position *********//

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        // Get the data model based on Position
        final OrderProducts product = orderProductsList.get(position);

        // Set Ordered Product Image on ImageView with Glide Library
        Glide
                .with(context)
                .load(ConstantValues.ECOMMERCE_URL + product.getImage())
                .into(holder.checkout_item_cover);


        holder.checkout_item_title.setText(product.getProductsName());
        holder.checkout_item_quantity.setText(String.valueOf(product.getProductsQuantity()));
        holder.checkout_item_price.setText(new DecimalFormat("#0.00").format(Double.valueOf(product.getProductsPrice())) + " " + currency);
        holder.checkout_item_price_final.setText(new DecimalFormat("#0.00").format(Double.valueOf(product.getFinalPrice())) + " " + currency);
        holder.checkout_item_category.setText(product.getCategories().get(0).getCategoriesName());


        List<Value> selectedAttributeValues = new ArrayList<>();
        List<PostProductsAttributes> productAttributes = new ArrayList<>();

        productAttributes = product.getAttributes();

        for (int i = 0; i < productAttributes.size(); i++) {
            Value value = new Value();
            value.setValue(productAttributes.get(i).getProductsOptionsValues());
            value.setPrice(productAttributes.get(i).getOptionsValuesPrice());
            value.setPricePrefix(productAttributes.get(i).getPricePrefix());

            selectedAttributeValues.add(value);
        }


        // Initialize the ProductAttributeValuesAdapter for RecyclerView
        attributesAdapter = new ProductAttributeValuesAdapter(context, selectedAttributeValues);

        // Set the Adapter and LayoutManager to the RecyclerView
        holder.attributes_recycler.setAdapter(attributesAdapter);
        holder.attributes_recycler.setLayoutManager(new LinearLayoutManager(context));

        attributesAdapter.notifyDataSetChanged();


    }


    //********** Returns the total number of items in the data set *********//

    @Override
    public int getItemCount() {
        return orderProductsList.size();
    }


    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private LinearLayout checkout_item;
        private ImageView checkout_item_cover;
        private RecyclerView attributes_recycler;
        private TextView checkout_item_title, checkout_item_quantity, checkout_item_price, checkout_item_price_final, checkout_item_category;


        public MyViewHolder(final View itemView) {
            super(itemView);

            checkout_item = (LinearLayout) itemView.findViewById(R.id.checkout_item);
            checkout_item_cover = (ImageView) itemView.findViewById(R.id.checkout_item_cover);
            checkout_item_title = (TextView) itemView.findViewById(R.id.checkout_item_title);
            checkout_item_quantity = (TextView) itemView.findViewById(R.id.checkout_item_quantity);
            checkout_item_price = (TextView) itemView.findViewById(R.id.checkout_item_price);
            checkout_item_price_final = (TextView) itemView.findViewById(R.id.checkout_item_price_final);
            checkout_item_category = (TextView) itemView.findViewById(R.id.checkout_item_category);

            attributes_recycler = (RecyclerView) itemView.findViewById(R.id.order_item_attributes_recycler);


            checkout_item.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

            Bundle categoryInfo = new Bundle();
            categoryInfo.putInt("itemID", Integer.parseInt(orderProductsList.get(getAdapterPosition()).getProductsId() + ""));

            Fragment fragment = new Product_Description();
            fragment.setArguments(categoryInfo);
            FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.main_fragment, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(null).commit();
        }


    }


}

