package com.themescoder.androidstore.fragment;


import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.themescoder.androidstore.R;
import com.themescoder.androidstore.adapters.ProductAdapter;
import com.themescoder.androidstore.constant.ConstantValues;
import com.themescoder.androidstore.models.product_model.GetAllProducts;
import com.themescoder.androidstore.models.product_model.ProductData;
import com.themescoder.androidstore.models.product_model.ProductDetails;
import com.themescoder.androidstore.network.APIClient;

import java.util.ArrayList;
import java.util.List;

import am.appwise.components.ni.NoInternetDialog;
import retrofit2.Call;
import retrofit2.Callback;


public class FlashSale extends Fragment {
    
    String customerID;
    
    TextView emptyRecord, headerText, shopMore;
    RecyclerView recents_recycler;
    List<ProductDetails> flashList;
    ShimmerFrameLayout shimmerFrameLayout;
    ProductAdapter productAdapter;

    boolean IsEmptyRecordVisible,isHeaderVisible;

    ProgressBar progressBar;

    public void invalidateProducts(){
        productAdapter.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.f_products_horizontal, container, false);

        NoInternetDialog noInternetDialog = new NoInternetDialog.Builder(getContext()).build();
        //noInternetDialog.show();

        // Get the CustomerID from SharedPreferences
        customerID = this.getContext().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userID", "");
        flashList = new ArrayList<>();
        
        // Binding Layout Views
        emptyRecord = (TextView) rootView.findViewById(R.id.empty_record_text);
        headerText = (TextView) rootView.findViewById(R.id.products_horizontal_header);
        shopMore = rootView.findViewById(R.id.shop_more_text);
        recents_recycler = (RecyclerView) rootView.findViewById(R.id.products_horizontal_recycler);
        progressBar = rootView.findViewById(R.id.progressBar);
        shimmerFrameLayout = rootView.findViewById(R.id.shimmerFrame);
        
        // Hide some of the Views
        emptyRecord.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        


        // get aguement data passing from previous frament

            if(getArguments()!=null){

                if(getArguments().containsKey("isHeaderVisible")){
                    isHeaderVisible=getArguments().getBoolean("isHeaderVisible");

                }
                if(getArguments().getBoolean("home_6")){

                    IsEmptyRecordVisible =true;
                }
                else {

                    IsEmptyRecordVisible =false;
                }

            }

        shopMore.setVisibility(View.GONE);
        // Set text of Header
        headerText.setText(getString(R.string.flashSale));
        if(!isHeaderVisible) {
            headerText.setVisibility(View.GONE);
        }
        else {
            headerText.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                headerText.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_sale, 0, 0, 0);
            } else {
                headerText.setCompoundDrawables(getResources().getDrawable(R.drawable.ic_sale), null, null, null);
            }
        }
        
        
        // Initialize the ProductAdapterRemovable and LayoutManager for RecyclerView
        productAdapter = new ProductAdapter(getContext(), flashList, true, true);
        recents_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        
        // Set the Adapter and LayoutManager to the RecyclerView
        
        recents_recycler.setAdapter(productAdapter);
        
        RequestProductDetails();
        

        return rootView;
    }
    
    
    //*********** Adds Products returned from the Server to the RecentViewedList ********//
    
    private void addFlashProducts(ProductData productData) {
        
        // Add Products to recentViewedList
        if (productData.getProductData().size() > 0) {
            flashList.addAll(productData.getProductData());
        } else {
            headerText.setVisibility(View.GONE);
        }
        
        // Notify the Adapter
        productAdapter.notifyDataSetChanged();
    }
    
    
    //*********** Request the Product's Details from the Server based on Product's ID ********//
    
    public void RequestProductDetails() {
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmer();

        GetAllProducts getAllProducts = new GetAllProducts();
        getAllProducts.setLanguageId(ConstantValues.LANGUAGE_ID);
        getAllProducts.setCustomersId(customerID);
        getAllProducts.setType("flashsale");
        getAllProducts.setCurrencyCode(ConstantValues.CURRENCY_CODE);
        
        String str = new Gson().toJson(getAllProducts);
        
        Call<ProductData> call = APIClient.getInstance()
                .getAllProducts
                        (
                                getAllProducts
                        );
        
        call.enqueue(new Callback<ProductData>() {
            @Override
            public void onResponse(Call<ProductData> call, retrofit2.Response<ProductData> response) {
                
                if (response.isSuccessful()) {
                    
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {
                        // Product's Details has been returned.
                        // Add Product to the recentViewedList
                        addFlashProducts(response.body());
                        
                        // Check if the recents List isn't empty
                        if (flashList.size() < 1) {
                            emptyRecord.setVisibility(View.VISIBLE);
                        }
                        
                    } else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        // Product's Details haven't been returned.
                        // Call the method to process some implementations
                        addFlashProducts(response.body());
                        if(IsEmptyRecordVisible){
                            emptyRecord.setVisibility(View.VISIBLE);

                        }
                        else {
                            emptyRecord.setVisibility(View.GONE);
                        }

                    }
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                }
            }
            
            @Override
            public void onFailure(Call<ProductData> call, Throwable t) {
                Toast.makeText(getContext(), "NetworkCallFailure : " + t, Toast.LENGTH_LONG).show();
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
            }
        });
        
    }
    
}

