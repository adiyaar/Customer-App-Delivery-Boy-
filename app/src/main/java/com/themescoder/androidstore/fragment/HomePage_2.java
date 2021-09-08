package com.themescoder.androidstore.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.tabs.TabLayout;
import com.themescoder.androidstore.R;
import com.themescoder.androidstore.adapters.ViewPagerSimpleAdapter;
import com.themescoder.androidstore.app.App;
import com.themescoder.androidstore.constant.ConstantValues;
import com.themescoder.androidstore.models.banner_model.BannerDetails;
import com.themescoder.androidstore.models.category_model.CategoryDetails;
import com.themescoder.androidstore.network.StartAppRequests;
import com.themescoder.androidstore.utils.Utilities;

import java.util.ArrayList;
import java.util.List;

import am.appwise.components.ni.NoInternetDialog;


public class HomePage_2 extends Fragment {

    ViewPager viewPager;
    TabLayout tabLayout;

    StartAppRequests startAppRequests;

    List<BannerDetails> bannerImages = new ArrayList<>();
    List<CategoryDetails> allCategoriesList = new ArrayList<>();
    List<CategoryDetails> allSubCategoriesList = new ArrayList<>();
    ViewPagerSimpleAdapter viewPagerAdapter;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (viewPagerAdapter != null)
                viewPagerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.homepage_2, container, false);

        // Enable Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
        //MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(ConstantValues.APP_HEADER);

        NoInternetDialog noInternetDialog = new NoInternetDialog.Builder(getContext()).build();
        // noInternetDialog.show();

        startAppRequests = new StartAppRequests(getContext());

        // Get BannersList from ApplicationContext
        bannerImages = ((App) getContext().getApplicationContext()).getBannersList();

        // Get CategoriesList from AppContext
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

    private void continueSetup() {
        // Get BannersList from ApplicationContext
        bannerImages = ((App) getContext().getApplicationContext()).getBannersList();

        // Get CategoriesList from AppContext
        allCategoriesList = ((App) getContext().getApplicationContext()).getCategoriesList();

        allSubCategoriesList = new ArrayList<>();

        // Get SubCategoriesList from AllCategoriesList
        for (int i = 0; i < allCategoriesList.size(); i++) {
            if (!allCategoriesList.get(i).getParentId().equalsIgnoreCase("0")) {
                allSubCategoriesList.add(allCategoriesList.get(i));
            }
        }

        if (allSubCategoriesList.isEmpty())
            allSubCategoriesList = allCategoriesList;

        // Setup BannerSlider
        if (bannerImages != null && !bannerImages.isEmpty())
        setupBannerSlider(bannerImages);


        // Setup ViewPagers
        setupViewPager(viewPager);

        // Add corresponding ViewPagers to TabLayouts
        tabLayout.setupWithViewPager(viewPager);


        // Setup CustomTabs for all the Categories
        setupCustomTabs();


    }


    //*********** Setup the given ViewPager ********//

    private void setupCustomTabs() {

        // Initialize New View for custom Tab
        View tabOne = (View) LayoutInflater.from(getContext()).inflate(R.layout.layout_tabs_custom, null);

        // Set Text of custom Tab
        TextView tabText1 = (TextView) tabOne.findViewById(R.id.myTabs_text);
        tabText1.setText(getString(R.string.all));

        // Set Icon of custom Tab
        ImageView tabIcon1 = (ImageView) tabOne.findViewById(R.id.myTabs_icon);
        tabIcon1.setImageResource(R.drawable.ic_list);

        // Add tabOne to TabLayout at index 0
        tabLayout.getTabAt(0).setCustomView(tabOne);


        for (int i = 0; i < allSubCategoriesList.size(); i++) {

            // Initialize New View for custom Tab
            View tabNew = (View) LayoutInflater.from(getContext()).inflate(R.layout.layout_tabs_custom, null);

            // Set Text of custom Tab
            TextView tabText2 = (TextView) tabNew.findViewById(R.id.myTabs_text);
            tabText2.setText(allSubCategoriesList.get(i).getName());

            // Set Icon of custom Tab
            ImageView tabIcon2 = (ImageView) tabNew.findViewById(R.id.myTabs_icon);
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH);
            Glide.with(getActivity())
                    .setDefaultRequestOptions(options)
                    .asBitmap()
                    .load(ConstantValues.ECOMMERCE_URL + allSubCategoriesList.get(i).getIcon())
                    .into(tabIcon2);


            // Add tabTwo to TabLayout at specified index
            tabLayout.getTabAt(i + 1).setCustomView(tabNew);
        }
    }


    //*********** Setup the given ViewPager ********//

    private void setupViewPager(ViewPager viewPager) {

        // Initialize ViewPagerAdapter with ChildFragmentManager for ViewPager
        viewPagerAdapter = new ViewPagerSimpleAdapter(getChildFragmentManager());

        // Add the Fragments to the ViewPagerAdapter with TabHeader
        viewPagerAdapter.addFragment(new All_Products(), getString(R.string.all));


        for (int i = 0; i < allSubCategoriesList.size(); i++) {

            // Add CategoryID to new Bundle for Fragment arguments
            Bundle categoryInfo = new Bundle();
            categoryInfo.putInt("CategoryID", Integer.parseInt(allSubCategoriesList.get(i).getId()));

            // Initialize Category_Products Fragment with specified arguments
            Fragment fragment = new Category_Products();
            fragment.setArguments(categoryInfo);

            // Add the Fragments to the ViewPagerAdapter with TabHeader
            viewPagerAdapter.addFragment(fragment, allSubCategoriesList.get(i).getName());
        }

        // Set the number of pages that should be retained to either side of the current page
        viewPager.setOffscreenPageLimit(0);
        // Attach the ViewPagerAdapter to given ViewPager
        viewPager.setAdapter(viewPagerAdapter);
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
            fragmentManager.beginTransaction().replace(R.id.bannerFrameHome2, bannerStyle).commit();
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

