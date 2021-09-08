package com.themescoder.androidstore.fragment;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.themescoder.androidstore.R;

import java.util.ArrayList;
import java.util.List;

import com.themescoder.androidstore.app.App;
import com.themescoder.androidstore.constant.ConstantValues;
import com.themescoder.androidstore.models.banner_model.BannerDetails;
import com.themescoder.androidstore.models.category_model.CategoryDetails;

import am.appwise.components.ni.NoInternetDialog;

import com.themescoder.androidstore.network.StartAppRequests;
import com.themescoder.androidstore.utils.Utilities;

public class HomePage_4 extends Fragment {


    StartAppRequests startAppRequests;
    List<BannerDetails> bannerImages = new ArrayList<>();
    List<CategoryDetails> allCategoriesList = new ArrayList<>();

    FragmentManager fragmentManager;

    Top_Seller topSeller;
    Special_Deals specialDeals;
    Most_Liked mostLiked;
    RecentlyViewed recentlyViewed;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (topSeller != null && specialDeals != null && mostLiked != null && recentlyViewed != null) {
                topSeller.invalidateProducts();
                specialDeals.invalidateProducts();
                mostLiked.invalidateProducts();
                recentlyViewed.invalidateProducts();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.homepage_4, container, false);

        // Enable Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
        //MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(ConstantValues.APP_HEADER);

        NoInternetDialog noInternetDialog = new NoInternetDialog.Builder(getContext()).build();
        // noInternetDialog.show();

        startAppRequests = new StartAppRequests(getContext());
        // Get BannersList from ApplicationContext
        bannerImages = ((App) getContext().getApplicationContext()).getBannersList();
        allCategoriesList = ((App) getContext().getApplicationContext()).getCategoriesList();

        // Initialize new Bundle for Fragment arguments
        Bundle bundle = new Bundle();
        bundle.putBoolean("isHeaderVisible", true);
        bundle.putBoolean("isMenuItem", false);

        // Get FragmentManager
        fragmentManager = getFragmentManager();


        // Add Top_Seller Fragment to specified FrameLayout
        topSeller = new Top_Seller();
        topSeller.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.home4_top_seller_fragment, topSeller).commit();

        // Add Special_Deals Fragment to specified FrameLayout
        specialDeals = new Special_Deals();
        specialDeals.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.home4_super_deals_fragment, specialDeals).commit();

        // Add Most_Liked Fragment to specified FrameLayout
        mostLiked = new Most_Liked();
        mostLiked.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.home4_most_liked_fragment, mostLiked).commit();

        // Add Recently Viewes products to specified FrameLayout
        RecentlyViewed recentlyViewed = new RecentlyViewed();
        fragmentManager.beginTransaction().replace(R.id.home4_recentProducts, recentlyViewed).commit();

        if (bannerImages.isEmpty() || allCategoriesList.isEmpty())
            new MyTask().execute();
        else
            continueSetup();


        return rootView;

    }

    private void continueSetup() {
        // Setup BannerSlider
        if (bannerImages != null && !bannerImages.isEmpty())
            setupBannerSlider(bannerImages);


        // Add MainCategories Fragment to specified FrameLayout
        Bundle categoryBundle = new Bundle();
        categoryBundle.putBoolean("isHeaderVisible", false);
        categoryBundle.putBoolean("isMenuItem", false);
        Fragment categories = new Categories_1();
        categories.setArguments(categoryBundle);
        fragmentManager.beginTransaction().replace(R.id.home4_all_categories_fragment, categories).commit();

    }

    //*********** Setup the BannerSlider with the given List of BannerImages ********//

    private void setupBannerSlider(final List<BannerDetails> bannerImages) {
        FragmentManager fragmentManager = getFragmentManager();
        Fragment bannerStyle = null;

        switch (ConstantValues.DEFAULT_BANNER_STYLE) {
            case 0:
                bannerStyle = new BannerStyle1(bannerImages, allCategoriesList);
                break;
            case 1:
                bannerStyle = new BannerStyle1(bannerImages, allCategoriesList);
                break;
            case 2:
                bannerStyle = new BannerStyle2(bannerImages, allCategoriesList);
                break;
            case 3:
                bannerStyle = new BannerStyle3(bannerImages, allCategoriesList);
                break;
            case 4:
                bannerStyle = new BannerStyle4(bannerImages, allCategoriesList);
                break;
            case 5:
                bannerStyle = new BannerStyle5(bannerImages, allCategoriesList);
                break;
            case 6:
                bannerStyle = new BannerStyle6(bannerImages, allCategoriesList);
                break;
        }

        if (bannerStyle != null)
            fragmentManager.beginTransaction().replace(R.id.bannerFrameHome4, bannerStyle).commit();
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
                startAppRequests.RequestBanners();
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

