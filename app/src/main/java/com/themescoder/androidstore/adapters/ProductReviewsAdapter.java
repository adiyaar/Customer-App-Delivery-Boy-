package com.themescoder.androidstore.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.themescoder.androidstore.R;
import com.themescoder.androidstore.customs.CircularImageView;
import com.themescoder.androidstore.models.ratings.ProductReviews;

import java.util.List;

import hyogeun.github.com.colorratingbarlib.ColorRatingBar;


/**
 * ProductReviewsAdapter is the adapter class of RecyclerView holding List of Product Reviews in Product_Description
 **/

public class ProductReviewsAdapter extends RecyclerView.Adapter<ProductReviewsAdapter.MyViewHolder> {

    Context context;
    private List<ProductReviews> reviewsList;


    public ProductReviewsAdapter(Context context, List<ProductReviews> reviewsList) {
        this.context = context;
        this.reviewsList = reviewsList;
    }



    //********** Called to Inflate a Layout from XML and then return the Holder *********//

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_reviews, parent, false);

        return new MyViewHolder(itemView);
    }



    //********** Called by RecyclerView to display the Data at the specified Position *********//

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        
        // Get the data model based on Position
        final ProductReviews review = reviewsList.get(position);
        
        holder.authore_name.setText(review.getFirst_name()+" "+review.getLast_name());
        holder.authore_message.setText(review.getReview());
        
//        String review_date = review.getDateCreated().replaceAll("[a-zA-Z]", " ");
        String[] review_date_time = review.getDateCreated().split("T");
        String review_date = review_date_time[0];
        
        holder.authore_date.setText(review_date);
        
        
        holder.authore_rating.setRating((float) review.getRating());
        
    }



    //********** Returns the total number of items in the data set *********//

    @Override
    public int getItemCount() {
        return reviewsList.size();
    }



    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/

    public class MyViewHolder extends RecyclerView.ViewHolder {
        
        CircularImageView authore_img;
        ColorRatingBar authore_rating;
        TextView authore_name, authore_date, authore_message;


        public MyViewHolder(final View itemView) {
            super(itemView);
    
            authore_name = (TextView) itemView.findViewById(R.id.author_name);
            authore_date = (TextView) itemView.findViewById(R.id.author_date);
            authore_message = (TextView) itemView.findViewById(R.id.author_message);
            authore_rating = (ColorRatingBar) itemView.findViewById(R.id.author_rating);
            authore_img = (CircularImageView) itemView.findViewById(R.id.author_image);
        }
    }

}
