package com.themescoder.androidstore.fragment;


import androidx.annotation.Nullable;

import android.os.Build;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;

import com.themescoder.androidstore.R;

import java.util.ArrayList;
import java.util.List;

import com.themescoder.androidstore.adapters.ProductAdapter;
import com.themescoder.androidstore.app.App;
import com.themescoder.androidstore.constant.ConstantValues;
import com.themescoder.androidstore.models.product_model.GetAllProducts;
import com.themescoder.androidstore.models.product_model.ProductData;
import com.themescoder.androidstore.models.product_model.ProductDetails;
import com.themescoder.androidstore.network.APIClient;

import am.appwise.components.ni.NoInternetDialog;
import retrofit2.Call;
import retrofit2.Callback;


public class Top_Seller extends Fragment {

    String customerID;
    Boolean isHeaderVisible;
    Call<ProductData> networkCall;

    ShimmerFrameLayout shimmerFrameLayout;
    ProgressBar progressBar;
    TextView emptyRecord, headerText, shopMore;
    RecyclerView top_seller_recycler;

    ProductAdapter productAdapter;

    List<ProductDetails> topSellerProductsList;

    public void invalidateProducts(){
        productAdapter.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.f_products_horizontal, container, false);

        // Get the Boolean from Bundle Arguments
        isHeaderVisible = getArguments().getBoolean("isHeaderVisible");

        // Get the CustomerID from SharedPreferences
        customerID = this.getContext().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userID", "");

        NoInternetDialog noInternetDialog = new NoInternetDialog.Builder(getContext()).build();
        //noInternetDialog.show();



        // Binding Layout Views
        emptyRecord = (TextView) rootView.findViewById(R.id.empty_record_text);
        headerText = (TextView) rootView.findViewById(R.id.products_horizontal_header);
        shopMore = rootView.findViewById(R.id.shop_more_text);
        top_seller_recycler = (RecyclerView) rootView.findViewById(R.id.products_horizontal_recycler);
        shimmerFrameLayout = rootView.findViewById(R.id.shimmerFrame);
        progressBar = rootView.findViewById(R.id.progressBar);

        // Hide some of the Views
        emptyRecord.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    
        // Check if Header must be Invisible or not
        if (!isHeaderVisible) {
            headerText.setVisibility(View.GONE);
            shopMore.setVisibility(View.GONE);
        } else {
            headerText.setText(getString(R.string.top_seller));
            shopMore.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                headerText.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_sale, 0, 0, 0);
            } else {
                headerText.setCompoundDrawables(getResources().getDrawable(R.drawable.ic_sale), null, null, null);
            }
        }


        shopMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("sortBy", "top seller");
                bundle.putBoolean("isMenuItem", false);
                Products topSellers = new Products();
                topSellers.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .add(R.id.main_fragment, topSellers)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();

            }
        });
    
        topSellerProductsList = new ArrayList<>();
        

        // RecyclerView has fixed Size
        top_seller_recycler.setHasFixedSize(true);
        top_seller_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    
        // Initialize the ProductAdapter for RecyclerView
        productAdapter = new ProductAdapter(getContext(), topSellerProductsList, true,false);
    
        // Set the Adapter and LayoutManager to the RecyclerView
        top_seller_recycler.setAdapter(productAdapter);
        

        // Request for Most Sold Products
        RequestTopSellerProducts();


        return rootView;
    }


    //*********** Adds Products returned from the Server to the topSellerProductsList ********//

    private void addProducts(ProductData productData) {

        // Add Products to topSellerProductsList
        if (productData.getProductData().size() > 0) {
            topSellerProductsList.addAll(productData.getProductData());
        } else {
            headerText.setVisibility(View.GONE);
        }
        productAdapter.notifyDataSetChanged();
    }


    //*********** Request all the Products from the Server based on the Sales of Products ********//

    public void RequestTopSellerProducts() {

        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmer();
        GetAllProducts getAllProducts = new GetAllProducts();
        getAllProducts.setPageNumber(0);
        getAllProducts.setLanguageId(ConstantValues.LANGUAGE_ID);
        getAllProducts.setCustomersId(customerID);
        getAllProducts.setType("top seller");
        getAllProducts.setCurrencyCode(ConstantValues.CURRENCY_CODE);

        networkCall = APIClient.getInstance()
                .getAllProducts
                        (
                                getAllProducts
                        );

        networkCall.enqueue(new Callback<ProductData>() {
            @Override
            public void onResponse(Call<ProductData> call, retrofit2.Response<ProductData> response) {
                
                if (response.isSuccessful()) {

                    // Check the Success status
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {
                        // Products have been returned. Add Products to the topSellerProductsList
                        emptyRecord.setVisibility(View.GONE);
                        addProducts(response.body());

                    }
                    else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        // Products haven't been returned
                        emptyRecord.setVisibility(View.VISIBLE);
                        if (isHeaderVisible){
                            emptyRecord.setVisibility(View.GONE);
                            headerText.setVisibility(View.GONE);
                        }
                    }
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ProductData> call, Throwable t) {
                if (!networkCall.isCanceled()) {
                    Toast.makeText(App.getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onPause() {

        // Check if NetworkCall is being executed
        if (networkCall.isExecuted()){
            // Cancel the NetworkCall
            networkCall.cancel();
        }

        super.onPause();
    }
}

