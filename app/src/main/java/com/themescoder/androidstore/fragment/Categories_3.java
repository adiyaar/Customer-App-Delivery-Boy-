package com.themescoder.androidstore.fragment;


import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/*
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
*/

import com.themescoder.androidstore.R;

import java.util.ArrayList;
import java.util.List;

import com.themescoder.androidstore.adapters.CategoryListAdapter_3;
import com.themescoder.androidstore.app.App;
import com.themescoder.androidstore.constant.ConstantValues;
import com.themescoder.androidstore.models.category_model.CategoryDetails;

import am.appwise.components.ni.NoInternetDialog;


public class Categories_3 extends Fragment {
    
    Boolean isMenuItem = true;
    Boolean isHeaderVisible = false;

    TextView emptyText, headerText;
    RecyclerView category_recycler;

    CategoryListAdapter_3 categoryListAdapter;

    List<CategoryDetails> allCategoriesList;
    List<CategoryDetails> mainCategoriesList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.categories, container, false);

        NoInternetDialog noInternetDialog = new NoInternetDialog.Builder(getContext()).build();
        //noInternetDialog.show();

        if (getArguments() != null) {
            if (getArguments().containsKey("isHeaderVisible")) {
                isHeaderVisible = getArguments().getBoolean("isHeaderVisible");
            }
        
            if (getArguments().containsKey("isMenuItem")) {
                isMenuItem = getArguments().getBoolean("isMenuItem", true);
            }
        }
    
    
        if (isMenuItem) {
            // Enable Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
            //MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.categories));
        }


        allCategoriesList = new ArrayList<>();

        // Get CategoriesList from ApplicationContext
        allCategoriesList = ((App) getContext().getApplicationContext()).getCategoriesList();
        
        
        // Binding Layout Views
        emptyText = (TextView) rootView.findViewById(R.id.empty_record_text);
        headerText = (TextView) rootView.findViewById(R.id.categories_header);
        category_recycler = (RecyclerView) rootView.findViewById(R.id.categories_recycler);
        NestedScrollView scroll_container = (NestedScrollView) rootView.findViewById(R.id.scroll_container);
        scroll_container.setNestedScrollingEnabled(true);
        category_recycler.setNestedScrollingEnabled(false);
    
    
/*        if (ConstantValues.IS_ADMOBE_ENABLED) {
            // Initialize InterstitialAd
            final InterstitialAd mInterstitialAd = new InterstitialAd(getActivity());
            mInterstitialAd.setAdUnitId(ConstantValues.AD_UNIT_ID_INTERSTITIAL);
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
            mInterstitialAd.setAdListener(new AdListener(){
                @Override
                public void onAdLoaded() {
                    mInterstitialAd.show();
                }
            });
        }
        */

        // Hide some of the Views
        emptyText.setVisibility(View.GONE);

        // Check if Header must be Invisible or not
        if (!isHeaderVisible) {
            // Hide the Header of CategoriesList
            headerText.setVisibility(View.GONE);
        } else {
            headerText.setText(getString(R.string.categories));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                headerText.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_categories, 0, 0, 0);
            } else {
                headerText.setCompoundDrawables(getResources().getDrawable(R.drawable.ic_categories), null, null, null);
            }
        }



        mainCategoriesList= new ArrayList<>();

        for (int i=0;  i<allCategoriesList.size();  i++) {
            if (allCategoriesList.get(i).getParentId().equalsIgnoreCase("0")) {
                mainCategoriesList.add(allCategoriesList.get(i));
            }
        }

        if (mainCategoriesList.isEmpty()){
            emptyText.setVisibility(View.VISIBLE);
        }

        // Initialize the CategoryListAdapter for RecyclerView
        categoryListAdapter = new CategoryListAdapter_3(getContext(), mainCategoriesList, allCategoriesList, false);

        // Set the Adapter and LayoutManager to the RecyclerView
        category_recycler.setAdapter(categoryListAdapter);
        category_recycler.setLayoutManager(new GridLayoutManager(getContext(), 2));

        categoryListAdapter.notifyDataSetChanged();


        return rootView;
    }

}

