package com.themescoder.androidstore.fragment;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/*
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
*/

import com.themescoder.androidstore.R;

import java.util.ArrayList;
import java.util.List;

import com.themescoder.androidstore.app.App;
import com.themescoder.androidstore.adapters.CategoryListAdapter_2;
import com.themescoder.androidstore.constant.ConstantValues;
import com.themescoder.androidstore.models.category_model.CategoryDetails;

import am.appwise.components.ni.NoInternetDialog;
import com.themescoder.androidstore.network.StartAppRequests;
import com.themescoder.androidstore.utils.Utilities;


public class HomePage_5 extends Fragment {

    RecyclerView category_recycler;

    CategoryListAdapter_2 categoryListAdapter;

    StartAppRequests startAppRequests;
    List<CategoryDetails> allCategoriesList;
    List<CategoryDetails> mainCategoriesList;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.homepage_5, container, false);

        // Enable Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
        //MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(ConstantValues.APP_HEADER);

        NoInternetDialog noInternetDialog = new NoInternetDialog.Builder(getContext()).build();
        //noInternetDialog.show();

        startAppRequests = new StartAppRequests(getContext());
        allCategoriesList = new ArrayList<>();

        // Get CategoriesList from ApplicationContext
        allCategoriesList = ((App) getContext().getApplicationContext()).getCategoriesList();


        category_recycler = (RecyclerView) rootView.findViewById(R.id.categories_recycler);

        if (allCategoriesList.isEmpty())
            new MyTask().execute();
        else continueSetup();

        return rootView;

    }

    private void continueSetup() {

/*
        if (ConstantValues.IS_ADMOBE_ENABLED) {
            // Initialize InterstitialAd
            final InterstitialAd mInterstitialAd = new InterstitialAd(getActivity());
            mInterstitialAd.setAdUnitId(ConstantValues.AD_UNIT_ID_INTERSTITIAL);
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    mInterstitialAd.show();
                }
            });
        }
*/


        mainCategoriesList = new ArrayList<>();

        for (int i = 0; i < allCategoriesList.size(); i++) {
            if (allCategoriesList.get(i).getParentId().equalsIgnoreCase("0")) {
                mainCategoriesList.add(allCategoriesList.get(i));
            }
        }


        // Initialize the CategoryListAdapter for RecyclerView
        categoryListAdapter = new CategoryListAdapter_2(getContext(), mainCategoriesList, allCategoriesList, false);

        // Set the Adapter and LayoutManager to the RecyclerView
        category_recycler.setAdapter(categoryListAdapter);
        category_recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        categoryListAdapter.notifyDataSetChanged();

    }

    private class MyTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            // Check for Internet Connection from the static method of Helper class
            if (Utilities.hasActiveInternetConnection(getContext())) {

                // Call the method of StartAppRequests class to process App Startup Requests
                startAppRequests.RequestAllCategories();

                return "1";
            } else {

                return "0";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result.equalsIgnoreCase("1")) {
                continueSetup();
            }
        }

    }
}

