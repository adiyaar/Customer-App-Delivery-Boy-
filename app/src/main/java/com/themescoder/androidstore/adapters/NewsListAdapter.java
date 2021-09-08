package com.themescoder.androidstore.adapters;


import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.themescoder.androidstore.activities.MainActivity;
import com.themescoder.androidstore.R;

import java.util.List;

import com.themescoder.androidstore.constant.ConstantValues;
import com.themescoder.androidstore.fragment.NewsDescription;
import com.themescoder.androidstore.models.news_model.all_news.NewsDetails;


/**
 * NewsListAdapter is the adapter class of RecyclerView holding List of News in News_All
 **/

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.MyViewHolder> {

    Context context;
    private List<NewsDetails> newsList;


    public NewsListAdapter(Context context, List<NewsDetails> newsList) {
        this.context = context;
        this.newsList = newsList;
    }



    //********** Called to Inflate a Layout from XML and then return the Holder *********//

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_news_all, parent, false);

        return new MyViewHolder(itemView);
    }



    //********** Called by RecyclerView to display the Data at the specified Position *********//

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        
        // Get the data model based on Position
        final NewsDetails newsDetails = newsList.get(position);
    
        holder.news_title.setText(newsDetails.getNewsName());
        holder.news_date.setText(newsDetails.getNewsDateAdded());
        holder.news_description.setText(stripHtml(newsDetails.getNewsDescription()));


        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);
    
        // Set News Image on ImageView with Glide Library
        Glide
            .with(context)
            .load(ConstantValues.ECOMMERCE_URL+newsDetails.getNewsImage())
            .apply(options)
            .into(holder.news_image);
        
    }



    //********** Returns the total number of items in the data set *********//

    @Override
    public int getItemCount() {
        return newsList.size();
    }
    
    
    
    //********** Returns displayable plain string from the provided HTML string *********//
    
    @SuppressWarnings("deprecation")
    private String stripHtml(String html) {
        
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString();
        }
        else {
            return Html.fromHtml(html).toString();
        }
    }



    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        RelativeLayout news_item;
        ImageView news_image;
        TextView news_title, news_date, news_description;


        public MyViewHolder(final View itemView) {
            super(itemView);
            news_item = (RelativeLayout) itemView.findViewById(R.id.news_item);
            news_image = (ImageView) itemView.findViewById(R.id.news_image);
            news_title = (TextView) itemView.findViewById(R.id.news_title);
            news_date = (TextView) itemView.findViewById(R.id.news_date);
            news_description = (TextView) itemView.findViewById(R.id.news_description);

            news_item.setOnClickListener(this);
        }


        // Handle Click Listener on News item
        @Override
        public void onClick(View view) {
            // Get News Info
            Bundle newsInfo = new Bundle();
            newsInfo.putParcelable("NewsDetails", newsList.get(getAdapterPosition()));

            Fragment newsDescription = new NewsDescription();
            newsDescription.setArguments(newsInfo);
            FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.main_fragment, newsDescription)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(null).commit();
        }
    }

}

