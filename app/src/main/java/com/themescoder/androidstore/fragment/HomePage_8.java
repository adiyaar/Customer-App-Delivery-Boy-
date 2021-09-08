package com.themescoder.androidstore.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

import am.appwise.components.ni.NoInternetDialog;

import com.themescoder.androidstore.R;
import com.themescoder.androidstore.app.App;
import com.themescoder.androidstore.constant.ConstantValues;
import com.themescoder.androidstore.models.banner_model.BannerDetails;
import com.themescoder.androidstore.models.category_model.CategoryDetails;
import com.themescoder.androidstore.network.StartAppRequests;
import com.themescoder.androidstore.utils.Utilities;

public class HomePage_8 extends Fragment {

    View rootView;

    StartAppRequests startAppRequests;

    List<BannerDetails> bannerImages = new ArrayList<>();
    List<CategoryDetails> allCategoriesList = new ArrayList<>();

    FragmentManager fragmentManager;

    Top_Seller topSeller;
    Most_Liked mostLiked;
    All_Products allProducts;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (topSeller != null && mostLiked != null && allProducts != null) {
                topSeller.invalidateProducts();
                mostLiked.invalidateProducts();
                allProducts.invalidateProducts();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.homepage_8, container, false);

        // Enable Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
        //MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(ConstantValues.APP_HEADER);

        NoInternetDialog noInternetDialog = new NoInternetDialog.Builder(getContext()).build();
        //noInternetDialog.show();

        startAppRequests = new StartAppRequests(getContext());

        // Get BannersList from ApplicationContext
        bannerImages = ((App) getContext().getApplicationContext()).getBannersList();
        allCategoriesList = ((App) getContext().getApplicationContext()).getCategoriesList();


        if (bannerImages.isEmpty() || allCategoriesList.isEmpty())
            new MyTask().execute();
        else
            continueSetup();

        return rootView;

    }


    public void continueSetup() {

        bannerImages = ((App) getContext().getApplicationContext()).getBannersList();
        allCategoriesList = ((App) getContext().getApplicationContext()).getCategoriesList();
        // Setup BannerSlider
        if (bannerImages != null && !bannerImages.isEmpty())
            setupBannerSlider(bannerImages);

        fragmentManager = getFragmentManager();

        Bundle bundle = new Bundle();
        bundle.putBoolean("isHeaderVisible", true);
        bundle.putBoolean("isMenuItem", false);


        Fragment categories = new Categories_8();
        categories.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.home8_categories, categories).commit();

        topSeller = new Top_Seller();
        topSeller.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.home8_top_sellers, topSeller).commit();

        mostLiked = new Most_Liked();
        mostLiked.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.home8_most_liked, mostLiked).commit();

        allProducts = new All_Products();
        Bundle bundleInfo = new Bundle();
        bundleInfo.putString("sortBy", "Newest");
        allProducts.setArguments(bundleInfo);
        fragmentManager.beginTransaction().replace(R.id.home8_products_fragment, allProducts).commit();

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
            fragmentManager.beginTransaction().replace(R.id.bannerFrameHome8, bannerStyle).commit();
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

