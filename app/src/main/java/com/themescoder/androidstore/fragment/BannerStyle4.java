package com.themescoder.androidstore.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.themescoder.androidstore.R;
import com.themescoder.androidstore.adapters.BannerStyle2Adapter;
import com.themescoder.androidstore.models.banner_model.BannerDetails;
import com.themescoder.androidstore.models.category_model.CategoryDetails;

import java.util.ArrayList;
import java.util.List;

public class BannerStyle4 extends Fragment {


    View rootView;
    ViewPager slidingBannerViewPager;
    BannerStyle2Adapter adapter;
    ProgressBar progressBar;

    FragmentManager fragmentManager;

    List<BannerDetails> bannerImages = new ArrayList<>();
    List<CategoryDetails> allCategoriesList = new ArrayList<>();
    public static final int DELAY = 5000;
    int currentPage = 0;
    private Handler handler = new Handler();
    private Runnable runnableCode = null;
    private int progressincrement = 0;

    public BannerStyle4(List<BannerDetails> bannerImages, List<CategoryDetails> allCategoriesList) {
        this.bannerImages = bannerImages;
        this.allCategoriesList = allCategoriesList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.banner_style_4, container, false);


        slidingBannerViewPager = rootView.findViewById(R.id.slidingBanner);
        progressBar = rootView.findViewById(R.id.progressBar);


        setupSlidingBanner();
        if (bannerImages.size() > 0)
        addFragments(bannerImages);
        return rootView;
    }


    private void setupSlidingBanner() {

        ViewGroup.LayoutParams layoutParams = slidingBannerViewPager.getLayoutParams();
        layoutParams.height = Math.round((((float) ((Resources.getSystem().getDisplayMetrics().widthPixels)) * 1.0f) / 2.0f) + 250);
        slidingBannerViewPager.setLayoutParams(layoutParams);

        adapter = new BannerStyle2Adapter(getChildFragmentManager());

        slidingBannerViewPager.setAdapter(adapter);

    }

    private void addFragments(List<BannerDetails> banners) {
        adapter.clear();
        for (int i = 0; i < banners.size(); i++) {
            BannerDetails banner = banners.get(i);
            BannerItemFragment fragment = new BannerItemFragment(banner.getImage());
            adapter.addFrag(fragment, banner.getTitle());
        }

        startAutoSlider(banners.size());
    }


    private void startAutoSlider(final int i) {
        progressincrement = 100 / bannerImages.size();
        progressBar.setProgress(progressincrement);
        this.runnableCode = new Runnable() {
            public void run() {
                int currentItem = slidingBannerViewPager.getCurrentItem() + 1;
                if (currentItem >= i) {
                    currentItem = 0;
                }
                slidingBannerViewPager.setCurrentItem(currentItem, true);
                progressBar.setProgress(progressincrement * (currentItem+1));
                handler.postDelayed(runnableCode, DELAY);
            }
        };
        this.handler.postDelayed(this.runnableCode, DELAY);
    }

}
