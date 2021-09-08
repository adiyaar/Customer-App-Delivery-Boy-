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
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import am.appwise.components.ni.NoInternetDialog;

import com.themescoder.androidstore.R;
import com.themescoder.androidstore.adapters.ViewPagerCustomAdapter;
import com.themescoder.androidstore.app.App;
import com.themescoder.androidstore.constant.ConstantValues;
import com.themescoder.androidstore.models.banner_model.BannerDetails;
import com.themescoder.androidstore.models.category_model.CategoryDetails;
import com.themescoder.androidstore.network.StartAppRequests;
import com.themescoder.androidstore.utils.Utilities;

public class HomePage_9 extends Fragment {

    View rootView;

    ViewPager viewPager;
    TabLayout tabLayout;

    StartAppRequests startAppRequests;

    List<BannerDetails> bannerImages = new ArrayList<>();
    List<CategoryDetails> allCategoriesList = new ArrayList<>();

    FragmentManager fragmentManager;

    Top_Seller topSeller;
    Special_Deals specialDeals;
    Most_Liked mostLiked;
    All_Products allProducts;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (topSeller != null && specialDeals != null && mostLiked != null && allProducts != null) {
                topSeller.invalidateProducts();
                specialDeals.invalidateProducts();
                mostLiked.invalidateProducts();
                allProducts.invalidateProducts();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.homepage_9, container, false);

        // Enable Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
        //MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(ConstantValues.APP_HEADER);

        NoInternetDialog noInternetDialog = new NoInternetDialog.Builder(getContext()).build();
        //noInternetDialog.show();

        startAppRequests = new StartAppRequests(getContext());

        // Get BannersList from ApplicationContext
        bannerImages = ((App) getContext().getApplicationContext()).getBannersList();
        allCategoriesList = ((App) getContext().getApplicationContext()).getCategoriesList();

        // Binding Layout Views
        viewPager = (ViewPager) rootView.findViewById(R.id.myViewPager);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);

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

        // Setup ViewPagers
        setupViewPagerOne(viewPager);

        // Add corresponding ViewPagers to TabLayouts
        tabLayout.setupWithViewPager(viewPager);

        fragmentManager = getFragmentManager();

        Bundle categoryBundle = new Bundle();
        categoryBundle.putBoolean("isHeaderVisible", false);
        categoryBundle.putBoolean("isMenuItem", false);
        categoryBundle.putBoolean("home_9", true);
        Fragment categories = new Categories_1();
        categories.setArguments(categoryBundle);
        fragmentManager.beginTransaction().replace(R.id.home9_categories, categories).commit();

        allProducts = new All_Products();
        Bundle bundleInfo = new Bundle();
        bundleInfo.putString("sortBy", "Newest");
        allProducts.setArguments(bundleInfo);
        fragmentManager.beginTransaction().replace(R.id.home9_products_fragment, allProducts).commit();

    }


    //*********** Setup the given ViewPager ********//

    private void setupViewPagerOne(ViewPager viewPager) {

        // Initialize new Bundle for Fragment arguments
        Bundle bundle = new Bundle();
        bundle.putBoolean("isHeaderVisible", false);

        // Initialize Fragments
        topSeller = new Top_Seller();
        specialDeals = new Special_Deals();
        mostLiked = new Most_Liked();

        topSeller.setArguments(bundle);
        specialDeals.setArguments(bundle);
        mostLiked.setArguments(bundle);


        // Initialize ViewPagerAdapter with ChildFragmentManager for ViewPager
        ViewPagerCustomAdapter viewPagerCustomAdapter = new ViewPagerCustomAdapter(getChildFragmentManager());

        // Add the Fragments to the ViewPagerAdapter with TabHeader
        viewPagerCustomAdapter.addFragment(topSeller, getString(R.string.topSeller));
        viewPagerCustomAdapter.addFragment(specialDeals, getString(R.string.super_deals));
        viewPagerCustomAdapter.addFragment(mostLiked, getString(R.string.most_liked));


        viewPager.setOffscreenPageLimit(2);

        // Attach the ViewPagerAdapter to given ViewPager
        viewPager.setAdapter(viewPagerCustomAdapter);


         /*Configuration config = getResources().getConfiguration();
         if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
             //use the RTL trick here
             viewPager.setRotationY(180);
             viewPager.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
         }*/

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
            fragmentManager.beginTransaction().replace(R.id.bannerFrameHome9, bannerStyle).commit();
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

