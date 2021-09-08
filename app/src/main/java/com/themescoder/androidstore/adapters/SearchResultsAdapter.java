package com.themescoder.androidstore.adapters;


import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.themescoder.androidstore.activities.MainActivity;
import com.themescoder.androidstore.R;

import java.util.List;

import com.themescoder.androidstore.constant.ConstantValues;
import com.themescoder.androidstore.fragment.Product_Description;
import com.themescoder.androidstore.fragment.Products;
import com.themescoder.androidstore.models.search_model.SearchResults;


/**
 * SearchResultsAdapter is the adapter class of RecyclerView holding List of Search Results in SearchFragment
 **/

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.MyViewHolder> {

    Context context;
    List<SearchResults> searchResultsList;


    public SearchResultsAdapter(Context context, List<SearchResults> searchResultsList) {
        this.context = context;
        this.searchResultsList = searchResultsList;
    }



    //********** Called to Inflate a Layout from XML and then return the Holder *********//

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_search_results, parent, false);

        return new MyViewHolder(itemView);
    }



    //********** Called by RecyclerView to display the Data at the specified Position *********//

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // Get the data model based on Position
        final SearchResults searchResult = searchResultsList.get(position);

        // Get the Type of SearchItem (i.e Product, SubCategory)
        final String parent = searchResult.getParent();


        holder.search_result_title.setText(searchResult.getName());
        
        // Set Item Image on ImageView with Glide Library
        Glide
            .with(context)
            .load(ConstantValues.ECOMMERCE_URL+searchResult.getImage())
            .into(holder.search_result_cover);


        // Handle the SearchItem Click Event
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Check the Type of SearchItem (i.e Product, Categories) to navigate to relevant Fragment
                if (parent.equalsIgnoreCase("Categories")) {
                    // Get SubCategory Info
                    Bundle categoryInfo = new Bundle();
                    categoryInfo.putInt("CategoryID", searchResult.getId());
                    categoryInfo.putString("CategoryName", searchResult.getName());

                    // Navigate to Products of selected SubCategory
                    Fragment fragment = new Products();
                    fragment.setArguments(categoryInfo);
                    FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .add(R.id.main_fragment, fragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(null).commit();


                } else if (parent.equalsIgnoreCase("Products")) {
                    // Get Product Info
                    Bundle itemInfo = new Bundle();
                    itemInfo.putInt("itemID", searchResult.getId());

                    // Navigate to Product_Description of selected Product
                    Fragment fragment = new Product_Description();
                    fragment.setArguments(itemInfo);
                    FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .add(R.id.main_fragment, fragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(null).commit();
                }
            }
        });

    }



    //********** Returns the total number of items in the data set *********//

    @Override
    public int getItemCount() {
        return searchResultsList.size();
    }



    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView search_result_title;
        ImageView search_result_cover;


        public MyViewHolder(final View itemView) {
            super(itemView);

            search_result_title = (TextView) itemView.findViewById(R.id.search_result_title);
            search_result_cover = (ImageView) itemView.findViewById(R.id.search_result_cover);
        }
    }
}

