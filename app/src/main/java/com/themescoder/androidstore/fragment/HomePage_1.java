package com.themescoder.androidstore.fragment;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.themescoder.androidstore.R;
import com.themescoder.androidstore.adapters.ViewPagerCustomAdapter;
import com.themescoder.androidstore.app.App;
import com.themescoder.androidstore.constant.ConstantValues;
import com.themescoder.androidstore.models.banner_model.BannerDetails;
import com.themescoder.androidstore.models.category_model.CategoryDetails;

import java.util.ArrayList;
import java.util.List;

import am.appwise.components.ni.NoInternetDialog;

import com.themescoder.androidstore.network.StartAppRequests;
import com.themescoder.androidstore.utils.Utilities;

public class HomePage_1 extends Fragment {

    View rootView;

    ViewPager viewPager;
    TabLayout tabLayout;

    StartAppRequests startAppRequests;

    List<BannerDetails> bannerImages = new ArrayList<>();
    List<CategoryDetails> allCategoriesList = new ArrayList<>();

    ShimmerFrameLayout shimmerFrame;
    FragmentManager fragmentManager;
    RecentlyViewed recentlyViewed;
    Products productsFragment;
    FlashSale flashSale;

    Top_Seller topSeller;
    Special_Deals specialDeals;
    Most_Liked mostLiked;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (topSeller != null && specialDeals != null && mostLiked != null && flashSale != null && recentlyViewed != null) {
                topSeller.invalidateProducts();
                specialDeals.invalidateProducts();
                mostLiked.invalidateProducts();
                flashSale.invalidateProducts();
                recentlyViewed.invalidateProducts();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.homepage_1, container, false);

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
        shimmerFrame = rootView.findViewById(R.id.shimmerFrame);
        fragmentManager = getFragmentManager();

        // Setup ViewPagers
        setupViewPagerOne(viewPager);

        // Add RecentlyViewed Fragment to specified FrameLayout
        recentlyViewed = new RecentlyViewed();
        fragmentManager.beginTransaction().replace(R.id.recently_viewed_fragment, recentlyViewed).commit();

        // Add FlashSale Fragment to specific FrameLayout.
        Bundle flashBundle = new Bundle();
        flashSale = new FlashSale();
        flashBundle.putBoolean("isHeaderVisible", true);
        flashSale.setArguments(flashBundle);
        fragmentManager.beginTransaction().replace(R.id.flash_sale_fragment, flashSale).commit();

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

        // Add corresponding ViewPagers to TabLayouts
        tabLayout.setupWithViewPager(viewPager);

        // Add RecentlyViewed Fragment to specified FrameLayout
        productsFragment = new Products();
        Bundle bundle = new Bundle();

        bundle.putBoolean("isSubFragment", true);
        productsFragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.home1_products_fragment, productsFragment).commit();


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
            fragmentManager.beginTransaction().replace(R.id.bannerFrameHome1, bannerStyle).commit();
    }


    //*********** Handle the Click Listener on BannerImages of Slider ********//


    private class MyTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            shimmerFrame.setVisibility(View.VISIBLE);
            shimmerFrame.startShimmer();
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

            shimmerFrame.stopShimmer();
            shimmerFrame.setVisibility(View.GONE);
        }

    }
}

