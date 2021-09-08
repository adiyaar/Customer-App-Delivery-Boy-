package com.themescoder.androidstore.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.bumptech.glide.request.RequestOptions;
import com.themescoder.androidstore.R;

import com.themescoder.androidstore.activities.MainActivity;
import com.themescoder.androidstore.constant.ConstantValues;
import com.themescoder.androidstore.models.news_model.all_news.NewsDetails;

import am.appwise.components.ni.NoInternetDialog;


public class NewsDescription extends Fragment {

    View rootView;
    
    ImageView news_cover;
    TextView news_title, news_date;
    WebView news_description_webView;

    NewsDetails newsDetails;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.news_description, container, false);
        

        // Get NewsDetails from bundle arguments
        newsDetails = getArguments().getParcelable("NewsDetails");

        NoInternetDialog noInternetDialog = new NoInternetDialog.Builder(getContext()).build();
        //noInternetDialog.show();

    
    
        // Enable Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
        //MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        ((MainActivity)getActivity()).toggleNavigaiton(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.news_description));


        // Binding Layout Views
        news_cover = (ImageView) rootView.findViewById(R.id.news_cover);
        news_title = (TextView) rootView.findViewById(R.id.news_title);
        news_date = (TextView) rootView.findViewById(R.id.news_date);
        news_description_webView = (WebView) rootView.findViewById(R.id.news_description_webView);


        // Set News Details
        news_title.setText(newsDetails.getNewsName());
        news_date.setText(String.valueOf(newsDetails.getNewsDateAdded()));
    
        
        String description = newsDetails.getNewsDescription();
        String styleSheet = "<style> " +
                                "body{background:#eeeeee; margin:0; padding:0} " +
                                "p{color:#666666;} " +
                                "img{display:inline; height:auto; max-width:100%;}" +
                            "</style>";
    
        news_description_webView.setHorizontalScrollBarEnabled(false);
        news_description_webView.loadDataWithBaseURL(null, styleSheet+description, "text/html", "utf-8", null);
        

        Glide
            .with(getContext())
                .asBitmap()
            .load(ConstantValues.ECOMMERCE_URL+newsDetails.getNewsImage())

                .apply(new RequestOptions()
                        .placeholder(R.mipmap.ic_launcher)
                        .fitCenter())
                .load(ConstantValues.ECOMMERCE_URL+newsDetails.getNewsImage())
                .into(news_cover);
        


        return rootView;

    }

}

