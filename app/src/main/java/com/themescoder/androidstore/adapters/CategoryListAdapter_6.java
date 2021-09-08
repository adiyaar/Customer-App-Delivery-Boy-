package com.themescoder.androidstore.adapters;


import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import com.themescoder.androidstore.R;

import java.util.ArrayList;
import java.util.List;

import com.themescoder.androidstore.app.App;
import com.themescoder.androidstore.constant.ConstantValues;
import com.themescoder.androidstore.customs.CircularImageView;
import com.themescoder.androidstore.models.category_model.CategoryDetails;

import static com.themescoder.androidstore.app.App.getContext;


/**
 * CategoryListAdapter is the adapter class of RecyclerView holding List of Categories in MainCategories
 **/

public class CategoryListAdapter_6 extends RecyclerView.Adapter<CategoryListAdapter_6.MyViewHolder> {

    boolean isSubCategory;

    Context context;
    List<CategoryDetails> categoriesList;
    List<CategoryDetails> allCategoriesList;


    public CategoryListAdapter_6(Context context, List<CategoryDetails> categoriesList, boolean isSubCategory) {
        this.context = context;
        this.isSubCategory = isSubCategory;
        this.categoriesList = categoriesList;

        allCategoriesList = ((App) getContext().getApplicationContext()).getCategoriesList();
    }



    //********** Called to Inflate a Layout from XML and then return the Holder *********//

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_categories_6, parent, false);

        return new MyViewHolder(itemView);
    }



    //********** Called by RecyclerView to display the Data at the specified Position *********//

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // Get the data model based on Position
        final CategoryDetails categoryDetails = categoriesList.get(position);

        holder.category_title.setText(categoryDetails.getName());

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);

        Glide.with(context)
                .setDefaultRequestOptions(options)
                .load(ConstantValues.ECOMMERCE_URL+categoryDetails.getImage())
                .into(holder.imageView);


        List<CategoryDetails> subCategoriesList = new ArrayList<>();

        for (int i=0;  i<allCategoriesList.size();  i++) {
            // Get Subcategories from all Categories
            if (allCategoriesList.get(i).getParentId().equalsIgnoreCase(categoryDetails.getId())) {
                subCategoriesList.add(allCategoriesList.get(i));
            }
        }

        // Initialize the SubCategoryListAdapter for RecyclerView
        SubCategoryListAdapter subCategoryListAdapter = new SubCategoryListAdapter(context, subCategoriesList);

        holder.sub_categories_list.setAdapter(subCategoryListAdapter);



    }



    //********** Returns the total number of items in the data set *********//

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }



    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ListView sub_categories_list;
        TextView category_title;
        CircularImageView imageView;


        public MyViewHolder(final View itemView) {
            super(itemView);
            category_title = (TextView) itemView.findViewById(R.id.category_title);
            sub_categories_list = (ListView) itemView.findViewById(R.id.sub_categories_list);
            imageView = itemView.findViewById(R.id.category_image);
        }

    }

}

