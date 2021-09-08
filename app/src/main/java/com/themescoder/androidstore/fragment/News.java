package com.themescoder.androidstore.fragment;


import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Transformers.BaseTransformer;

/*
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
*/

import com.themescoder.androidstore.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.themescoder.androidstore.adapters.ViewPagerSimpleAdapter;
import com.themescoder.androidstore.app.App;
import com.themescoder.androidstore.constant.ConstantValues;
import com.themescoder.androidstore.customs.DialogLoader;
import com.themescoder.androidstore.models.news_model.all_news.NewsData;
import com.themescoder.androidstore.models.news_model.all_news.NewsDetails;
import com.themescoder.androidstore.network.APIClient;

import am.appwise.components.ni.NoInternetDialog;
import retrofit2.Call;
import retrofit2.Callback;


public class News extends Fragment implements BaseSliderView.OnSliderClickListener {

    View rootView;

    //AdView mAdView;
    ViewPager viewPager;
    TabLayout tabLayout;
    FrameLayout banner_adView;

    SliderLayout sliderLayout;
    PagerIndicator pagerIndicator;

    List<NewsDetails> newsList = new ArrayList<>();

    DialogLoader dialogLoader;
    CheckBox dontShowAgain;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.news, container, false);

        // Enable Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
        //MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.actionNews));

        NoInternetDialog noInternetDialog = new NoInternetDialog.Builder(getContext()).build();
        //noInternetDialog.show();

        dialogLoader = new DialogLoader(getContext());

        // Binding Layout Views
        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        viewPager = (ViewPager) rootView.findViewById(R.id.myViewPager);
        banner_adView = (FrameLayout) rootView.findViewById(R.id.banner_adView);
        sliderLayout = (SliderLayout) rootView.findViewById(R.id.banner_slider);
        pagerIndicator = (PagerIndicator) rootView.findViewById(R.id.banner_slider_indicator);

/*
        if (ConstantValues.IS_ADMOBE_ENABLED) {
            // Initialize Admobe
            mAdView = new AdView(getContext());
            mAdView.setAdSize(AdSize.BANNER);
            mAdView.setAdUnitId(ConstantValues.AD_UNIT_ID_BANNER);
            AdRequest adRequest = new AdRequest.Builder().build();
            banner_adView.addView(mAdView);
            mAdView.loadAd(adRequest);
        }
*/

      /*  // Upside to banner Call the Flash news
        int arraySize = msgArray.length;
        for(int i = 0; i < arraySize; i++) {
            text_flash_news.append(""+msgArray[i]);

        }*/


        // Request Banners
        RequestNewsBanners(0);

        // Setup ViewPagers
        setupViewPager(viewPager);

        // Add corresponding ViewPagers to TabLayouts
        tabLayout.setupWithViewPager(viewPager);

        return rootView;

    }

    @Override
    public void onResume() {

        super.onResume();
    }

    //*********** Setup the given ViewPager ********//

    private void setupViewPager(ViewPager viewPager) {

        // Initialize ViewPagerAdapter with ChildFragmentManager for ViewPager
        ViewPagerSimpleAdapter viewPagerAdapter = new ViewPagerSimpleAdapter(getChildFragmentManager());

        // Add the Fragments to the ViewPagerAdapter with TabHeader
        viewPagerAdapter.addFragment(new News_All(), getString(R.string.newest));
        viewPagerAdapter.addFragment(new NewsCategories(), getString(R.string.news_categories));

        // Attach the ViewPagerAdapter to given ViewPager
        viewPager.setAdapter(viewPagerAdapter);
    }



    //*********** Setup the BannerSlider with the given List of BannerImages ********//

    private void setupBannerSlider(List<NewsDetails> newsList) {

        // Initialize new LinkedHashMap<ImageName, ImagePath>
        final LinkedHashMap<String, String> slider_covers = new LinkedHashMap<>();


        for (int i=0;  i< newsList.size();  i++) {
            // Get bannerDetails at given Position from bannerImages List
            NewsDetails news =  newsList.get(i);

            // Put Image's Name and URL to the HashMap slider_covers
            slider_covers.put
                    (
                            news.getNewsName(),
                            ConstantValues.ECOMMERCE_URL+news.getNewsImage()
                    );
        }


        for(String name : slider_covers.keySet()) {
            // Initialize DefaultSliderView
            DefaultSliderView defaultSliderView = new DefaultSliderView(getContext());

            // Set Attributes(Name, Image, Type etc) to DefaultSliderView
            defaultSliderView
                    .description(name)
                    .image(slider_covers.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            // Add DefaultSliderView to the SliderLayout
            sliderLayout.addSlider(defaultSliderView);
        }

        // Set PresetTransformer type of the SliderLayout
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);


        // Check if the size of Images in the Slider is less than 2
        if (slider_covers.size() < 2) {
            // Disable PagerTransformer
            sliderLayout.setPagerTransformer(false, new BaseTransformer() {
                @Override
                protected void onTransform(View view, float v) {
                }
            });

            // Hide Slider PagerIndicator
            sliderLayout.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);

        } else {
            // Set custom PagerIndicator to the SliderLayout
            sliderLayout.setCustomIndicator(pagerIndicator);
            // Make PagerIndicator Visible
            sliderLayout.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Visible);
        }
    }
    
    
    
    //*********** Handle the Click Listener on BannerImages of Slider ********//
    
    @Override
    public void onSliderClick(BaseSliderView slider) {
        int position = sliderLayout.getCurrentPosition();
        
        NewsDetails newsDetails = newsList.get(position);
    
        // Get Product Info
        Bundle bundle = new Bundle();
        bundle.putParcelable("NewsDetails", newsDetails);
    
        // Navigate to NewsDescription of selected News
        Fragment fragment = new NewsDescription();
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.main_fragment, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(getString(R.string.actionHome)).commit();
        
    }
    
    
    
    //*********** Request Featured News from the Server based on PageNo. ********//
    
    public void RequestNewsBanners(int pageNumber) {

        dialogLoader.showProgressDialog();
        Call<NewsData> call = APIClient.getInstance()
                .getAllNews
                        (
                                ConstantValues.LANGUAGE_ID,
                                pageNumber,
                                1,
                                ""
                        );
        
        call.enqueue(new Callback<NewsData>() {
            @Override
            public void onResponse(Call<NewsData> call, retrofit2.Response<NewsData> response) {
                
                if (response.isSuccessful()) {

                    if (response.body().getSuccess().equalsIgnoreCase("1")) {
                        
                        // Products have been returned. Add Products to the ProductsList
                        newsList.addAll(response.body().getNewsData());
                        
                        setupBannerSlider(newsList);
                        
                    }
                    else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        Snackbar.make(rootView, response.body().getMessage(), Snackbar.LENGTH_LONG).show();

                    }
                    else {
                        // Unable to get Success status
                        Snackbar.make(rootView, getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(App.getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
                dialogLoader.hideProgressDialog();
            }
            
            @Override
            public void onFailure(Call<NewsData> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(App.getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }
    
}

