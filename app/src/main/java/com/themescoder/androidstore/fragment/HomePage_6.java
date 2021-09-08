package com.themescoder.androidstore.fragment;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

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

public class HomePage_6 extends Fragment {


    StartAppRequests startAppRequests;

    List<BannerDetails> bannerImages = new ArrayList<>();
    List<CategoryDetails> allCategoriesList = new ArrayList<>();

    ImageView home6_single_banner_fragment;
    TextView allproductsTitle;

    Top_Seller topSeller;
    All_Products allProducts;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (topSeller != null && allProducts != null) {
                topSeller.invalidateProducts();
                allProducts.invalidateProducts();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.homepage_6, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(ConstantValues.APP_HEADER);

        NoInternetDialog noInternetDialog = new NoInternetDialog.Builder(getContext()).build();
        //noInternetDialog.show();

        startAppRequests = new StartAppRequests(getContext());

        bannerImages = ((App) getContext().getApplicationContext()).getBannersList();
        allCategoriesList = ((App) getContext().getApplicationContext()).getCategoriesList();


        // Binding Layout Views
        home6_single_banner_fragment = rootView.findViewById(R.id.home6_single_banner_fragment);
        allproductsTitle = rootView.findViewById(R.id.allproducts_header);

        allproductsTitle.setText(String.format("%s %s", getResources().getString(R.string.all), getResources().getString(R.string.products)));


        // Get FragmentManager
        FragmentManager fragmentManager = getFragmentManager();
        // Add Top_Seller Fragment to specified FrameLayout
        Bundle bundle = new Bundle();
        bundle.putBoolean("isHeaderVisible", true);
        bundle.putBoolean("isMenuItem", false);
        topSeller = new Top_Seller();
        topSeller.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.home6_top_seller_fragment, topSeller).commit();

        // Add all products Fragment to specified FrameLayout
        allProducts = new All_Products();
        Bundle bundleInfo = new Bundle();
        bundleInfo.putString("sortBy", "Newest");
        bundleInfo.putBoolean("hideBottomBar", true);
        allProducts.setArguments(bundleInfo);
        fragmentManager.beginTransaction().replace(R.id.home6_all_products_fragment, allProducts).commit();

        if (bannerImages.isEmpty() || allCategoriesList.isEmpty())
            new MyTask().execute();
        else continueSetup();

        return rootView;
    }

    private void continueSetup() {

        // Get BannersList from ApplicationContext
        bannerImages = ((App) getContext().getApplicationContext()).getBannersList();
        allCategoriesList = ((App) getContext().getApplicationContext()).getCategoriesList();

        // Setup BannerSlider
        if (bannerImages != null && !bannerImages.isEmpty())
            setupBannerSlider(bannerImages);

        // Initialize new Bundle for Fragment arguments
        Bundle bundle = new Bundle();
        bundle.putBoolean("isHeaderVisible", true);
        bundle.putBoolean("isMenuItem", false);

        // Get FragmentManager
        FragmentManager fragmentManager = getFragmentManager();


        Fragment categories = new Category_7();
        categories.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.home6_categories, categories).commit();


        // Add Special_Deals Fragment to specified FrameLayout
        if (bannerImages.size() > 0) {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH);
            // Set Product Image on ImageView with Glide Library
            Glide.with(getContext())
                    .setDefaultRequestOptions(options)
                    .load(ConstantValues.ECOMMERCE_URL + bannerImages.get(0).getImage())
                    .addListener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            //holder.cover_loader.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            //holder.cover_loader.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(home6_single_banner_fragment);
        } else {
            home6_single_banner_fragment.setVisibility(View.GONE);
        }

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
            fragmentManager.beginTransaction().replace(R.id.bannerFrameHome6, bannerStyle).commit();
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
