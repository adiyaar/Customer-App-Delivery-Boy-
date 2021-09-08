package com.themescoder.androidstore.fragment;


import androidx.annotation.Nullable;

import android.os.Bundle;
import android.os.AsyncTask;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.google.android.material.snackbar.Snackbar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.LinearLayout;
import android.widget.CompoundButton;

import com.google.gson.Gson;
import com.themescoder.androidstore.R;

import java.util.ArrayList;
import java.util.List;

import com.themescoder.androidstore.app.App;
import com.themescoder.androidstore.constant.ConstantValues;
import com.themescoder.androidstore.adapters.ProductAdapter;
import com.themescoder.androidstore.customs.DialogLoader;
import com.themescoder.androidstore.customs.EndlessRecyclerViewScroll;
import com.themescoder.androidstore.customs.FilterDialog;
import com.themescoder.androidstore.models.filter_model.get_filters.FilterData;
import com.themescoder.androidstore.models.filter_model.get_filters.FilterDetails;
import com.themescoder.androidstore.models.filter_model.post_filters.PostFilterData;
import com.themescoder.androidstore.models.product_model.ProductData;
import com.themescoder.androidstore.models.product_model.ProductDetails;
import com.themescoder.androidstore.models.product_model.GetAllProducts;
import com.themescoder.androidstore.network.APIClient;

import am.appwise.components.ni.NoInternetDialog;
import retrofit2.Call;
import retrofit2.Callback;


public class All_Products extends Fragment {

    View rootView;

    int pageNo = 0;
    double maxPrice = 0;
    boolean isVisible;
    boolean isGridView;
    boolean isFilterApplied;
    boolean isHideBottomBar;

    String customerID;
    String sortBy = "Newest";

    LinearLayout bottomBar;
    LinearLayout sortList;
    TextView emptyRecord;
    TextView sortListText;
    Button resetFiltersBtn;
    ProgressBar progressBar, mainProgress;
    ToggleButton filterButton;
    ToggleButton toggleLayoutView;
    RecyclerView all_products_recycler;

    FilterDialog filterDialog;
    PostFilterData filters = null;

    ProductAdapter productAdapter;
    List<ProductDetails> productsList;
    List<FilterDetails> filtersList = new ArrayList<>();

    GridLayoutManager gridLayoutManager;
    LinearLayoutManager linearLayoutManager;
    DialogLoader dialogLoader;

    Call<FilterData> filterCAll;
    Call<ProductData> call;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        isVisible = isVisibleToUser;
    }


    public void invalidateProducts() {
        productAdapter.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.f_products_vertical, container, false);

        dialogLoader = new DialogLoader(getContext());

        if (getArguments() != null) {
            if (getArguments().containsKey("sortBy")) {
                sortBy = getArguments().getString("sortBy");
            }
            if (getArguments().containsKey("hideBottomBar")) {
                isHideBottomBar = getArguments().getBoolean("hideBottomBar");
            }
        }

        NoInternetDialog noInternetDialog = new NoInternetDialog.Builder(getContext()).build();
        //noInternetDialog.show();

        // Get the Customer's ID from SharedPreferences
        customerID = getActivity().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userID", "");

        // Binding Layout Views
        bottomBar = (LinearLayout) rootView.findViewById(R.id.bottomBar);
        sortList = (LinearLayout) rootView.findViewById(R.id.sort_list);
        sortListText = (TextView) rootView.findViewById(R.id.sort_text);
        emptyRecord = (TextView) rootView.findViewById(R.id.empty_record);
        progressBar = rootView.findViewById(R.id.loading_bar);
        resetFiltersBtn = (Button) rootView.findViewById(R.id.resetFiltersBtn);
        filterButton = (ToggleButton) rootView.findViewById(R.id.filterBtn);
        toggleLayoutView = (ToggleButton) rootView.findViewById(R.id.layout_toggleBtn);
        all_products_recycler = (RecyclerView) rootView.findViewById(R.id.products_recycler);
        mainProgress = rootView.findViewById(R.id.progressBar);

        // Hide some of the Views
        emptyRecord.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        isGridView = true;
        toggleLayoutView.setChecked(isGridView);

        bottomBar.setVisibility(isHideBottomBar ? View.GONE : View.VISIBLE);

        // Set sortListText text
        if (sortBy.equalsIgnoreCase("top seller")) {
            sortListText.setText(getString(R.string.top_seller));
        } else if (sortBy.equalsIgnoreCase("special")) {
            sortListText.setText(getString(R.string.super_deals));
        } else if (sortBy.equalsIgnoreCase("most liked")) {
            sortListText.setText(getString(R.string.most_liked));
        } else {
            sortListText.setText(getString(R.string.newest));
        }


        productsList = new ArrayList<>();

        // Request for Products based on PageNo.
        RequestAllProducts(pageNo, sortBy);
        RequestFilters("");
        // Initialize GridLayoutManager and LinearLayoutManager
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        linearLayoutManager = new LinearLayoutManager(getContext());


        // Initialize the ProductAdapter for RecyclerView
        productAdapter = new ProductAdapter(getContext(), productsList, false, false);

        setRecyclerViewLayoutManager(isGridView);
        all_products_recycler.setAdapter(productAdapter);


        // Handle the Scroll event of Product's RecyclerView
        all_products_recycler.addOnScrollListener(new EndlessRecyclerViewScroll(bottomBar) {
            // Override abstract method onLoadMore() of EndlessRecyclerViewScroll class
            @Override
            public void onLoadMore(int current_page) {
                progressBar.setVisibility(View.VISIBLE);
                // Execute AsyncTask LoadMoreTask to Load More Products from Server
                new LoadMoreTask(current_page, filters).execute();
            }
        });

        productAdapter.notifyDataSetChanged();


        // Toggle RecyclerView's LayoutManager
        toggleLayoutView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isGridView = isChecked;
                setRecyclerViewLayoutManager(isGridView);
            }
        });

        // Initialize FilterDialog and Override its abstract methods
        filterDialog = new FilterDialog(getContext(), "", filtersList, maxPrice) {
            @Override
            public void clearFilters() {
                // Clear Filters
                isFilterApplied = false;
                filterButton.setChecked(false);
                filters = null;
                productsList.clear();
                productAdapter.notifyDataSetChanged();
                mainProgress.setVisibility(View.VISIBLE);
                emptyRecord.setVisibility(View.GONE);
                new LoadMoreTask(pageNo, filters).execute();
            }

            @Override
            public void applyFilters(PostFilterData postFilterData) {
                // Apply Filters
                isFilterApplied = true;
                filterButton.setChecked(true);
                filters = postFilterData;
                productsList.clear();
                productAdapter.notifyDataSetChanged();
                mainProgress.setVisibility(View.VISIBLE);
                emptyRecord.setVisibility(View.GONE);
                new LoadMoreTask(pageNo, filters).execute();
            }
        };

        // Handle the Click event of Filter Button
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isFilterApplied) {
                    filterButton.setChecked(true);
                    filterDialog.show();

                } else {
                    filterButton.setChecked(false);
                    filterDialog = new FilterDialog(getContext(), "", filtersList, maxPrice) {
                        @Override
                        public void clearFilters() {
                            isFilterApplied = false;
                            filterButton.setChecked(false);
                            filters = null;
                            productsList.clear();
                            productAdapter.notifyDataSetChanged();
                            mainProgress.setVisibility(View.VISIBLE);
                            emptyRecord.setVisibility(View.GONE);
                            new LoadMoreTask(pageNo, filters).execute();
                        }

                        @Override
                        public void applyFilters(PostFilterData postFilterData) {
                            isFilterApplied = true;
                            filterButton.setChecked(true);
                            filters = postFilterData;
                            productsList.clear();
                            productAdapter.notifyDataSetChanged();
                            mainProgress.setVisibility(View.VISIBLE);
                            emptyRecord.setVisibility(View.GONE);
                            new LoadMoreTask(pageNo, filters).execute();
                        }
                    };
                    filterDialog.show();
                }
            }
        });


        sortList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get sortBy_array from String_Resources
                final String[] sortArray = getResources().getStringArray(R.array.sortBy_array);

                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setCancelable(true);

                dialog.setItems(sortArray, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        String selectedText = sortArray[which];
                        sortListText.setText(selectedText);


                        if (selectedText.equalsIgnoreCase(sortArray[0])) {
                            sortBy = "Newest";
                        } else if (selectedText.equalsIgnoreCase(sortArray[1])) {
                            sortBy = "a to z";
                        } else if (selectedText.equalsIgnoreCase(sortArray[2])) {
                            sortBy = "z to a";
                        } else if (selectedText.equalsIgnoreCase(sortArray[3])) {
                            sortBy = "high to low";
                        } else if (selectedText.equalsIgnoreCase(sortArray[4])) {
                            sortBy = "low to high";
                        } else if (selectedText.equalsIgnoreCase(sortArray[5])) {
                            sortBy = "top seller";
                        } else if (selectedText.equalsIgnoreCase(sortArray[6])) {
                            sortBy = "special";
                        } else if (selectedText.equalsIgnoreCase(sortArray[7])) {
                            sortBy = "most liked";
                        } else {
                            sortBy = "Newest";
                        }


                        productsList.clear();
                        productAdapter.notifyDataSetChanged();
                        mainProgress.setVisibility(View.VISIBLE);
                        // Request for Products based on sortBy
                        if (isFilterApplied){
                            RequestFilteredProducts(pageNo, sortBy, filters);
                        } else {
                            RequestAllProducts(pageNo, sortBy);
                        }
                        dialog.dismiss();


                        // Handle the Scroll event of Product's RecyclerView
                        all_products_recycler.addOnScrollListener(new EndlessRecyclerViewScroll(bottomBar) {
                            // Override abstract method onLoadMore() of EndlessRecyclerViewScroll class
                            @Override
                            public void onLoadMore(int current_page) {
//                                progressBar.setVisibility(View.VISIBLE);
                                // Execute AsyncTask LoadMoreTask to Load More Products from Server
                                new LoadMoreTask(current_page, filters).execute();
                            }
                        });

                    }
                });
                dialog.show();
            }
        });

        return rootView;

    }

    //*********** Switch RecyclerView's LayoutManager ********//

    public void setRecyclerViewLayoutManager(Boolean isGridView) {
        int scrollPosition = 0;

        // If a LayoutManager has already been set, get current Scroll Position
        if (all_products_recycler.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) all_products_recycler.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        }

        productAdapter.toggleLayout(isGridView);

        all_products_recycler.setLayoutManager(isGridView ? gridLayoutManager : linearLayoutManager);
        all_products_recycler.setAdapter(productAdapter);

        all_products_recycler.scrollToPosition(scrollPosition);
    }

    //*********** Adds Products returned from the Server to the ProductsList ********//

    private void addProducts(ProductData productData) {

        // Add Products to ProductsList from the List of ProductData
        for (int i = 0; i < productData.getProductData().size(); i++) {
            ProductDetails productDetails = productData.getProductData().get(i);
            productsList.add(productDetails);
        }

        productAdapter.notifyDataSetChanged();

        // Change the Visibility of emptyRecord Text based on ProductList's Size
        if (productAdapter.getItemCount() == 0) {
            emptyRecord.setVisibility(View.VISIBLE);
        } else {
            emptyRecord.setVisibility(View.GONE);
        }

    }

    //*********** Request Filters of the given OrderProductCategory ********//

    private void RequestFilters(String categories_id) {

        filterCAll = APIClient.getInstance()
                .getFilters
                        (
                                categories_id,
                                ConstantValues.LANGUAGE_ID
                        );

        filterCAll.enqueue(new Callback<FilterData>() {
            @Override
            public void onResponse(Call<FilterData> call, retrofit2.Response<FilterData> response) {

                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {

                        filtersList = response.body().getFilters();
                        maxPrice = Double.parseDouble((response.body().getMaxPrice().isEmpty()) ? "0.0" : response.body().getMaxPrice());

                    } else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        if (isVisible)
                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        if (isVisible)
                            Toast.makeText(getActivity(), getString(R.string.unexpected_response), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (isVisible)
                        Toast.makeText(getActivity(), "" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FilterData> call, Throwable t) {
                /*if (isVisible)
                    Toast.makeText(getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();*/
            }
        });
    }

    //*********** Request Products from the Server based on PageNo. ********//

    public void RequestAllProducts(int pageNumber, String sortBy) {

        GetAllProducts getAllProducts = new GetAllProducts();
        getAllProducts.setPageNumber(pageNumber);
        getAllProducts.setLanguageId(ConstantValues.LANGUAGE_ID);
        getAllProducts.setCustomersId(customerID);
        getAllProducts.setType(sortBy);
        getAllProducts.setCurrencyCode(ConstantValues.CURRENCY_CODE);

        String str = new Gson().toJson(getAllProducts);
        call = APIClient.getInstance()
                .getAllProducts
                        (
                                getAllProducts
                        );

        call.enqueue(new Callback<ProductData>() {
            @Override
            public void onResponse(Call<ProductData> call, retrofit2.Response<ProductData> response) {

                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {
                        // Products have been returned. Add Products to the ProductsList
                        addProducts(response.body());

                    } else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        // Products haven't been returned. Call the method to process some implementations
                        addProducts(response.body());
                        //Snackbar.make(rootView, response.body().getMessage(), Snackbar.LENGTH_SHORT).show();
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        // Unable to get Success status
                        //Snackbar.make(rootView, getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                        Toast.makeText(getContext(), getString(R.string.unexpected_response), Toast.LENGTH_SHORT).show();
                    }

                    // Hide the ProgressBar
                    progressBar.setVisibility(View.GONE);
                    mainProgress.setVisibility(View.GONE);
                } else {
                    Toast.makeText(getContext(), "" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductData> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                mainProgress.setVisibility(View.GONE);
                // Toast.makeText(App.getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }

    //*********** Request Products of given OrderProductCategory from the Server based on PageNo. against some Filters ********//

    public void RequestFilteredProducts(int pageNumber, String sortBy, PostFilterData postFilterData) {


        GetAllProducts getAllProducts = new GetAllProducts();
        getAllProducts.setPageNumber(pageNumber);
        getAllProducts.setLanguageId(ConstantValues.LANGUAGE_ID);
        getAllProducts.setCustomersId(customerID);
        getAllProducts.setCategoriesId("");
        getAllProducts.setType(sortBy);
        getAllProducts.setPrice(postFilterData.getPrice());
        getAllProducts.setFilters(postFilterData.getFilters());
        getAllProducts.setCurrencyCode(ConstantValues.CURRENCY_CODE);

        //String data = new Gson().toJson(getAllProducts);

        Call<ProductData> call = APIClient.getInstance()
                .getAllProducts
                        (
                                (GetAllProducts) getAllProducts
                        );

        call.enqueue(new Callback<ProductData>() {
            @Override
            public void onResponse(Call<ProductData> call, retrofit2.Response<ProductData> response) {

                //String newdata = new Gson().toJson(response);

                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {

                        // Products have been returned. Add Products to the ProductsList
                        addProducts(response.body());

                    } else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        // Products haven't been returned. Call the method to process some implementations
                        addProducts(response.body());

                        // Show the Message to the User
                        Snackbar.make(rootView, response.body().getMessage(), Snackbar.LENGTH_SHORT).show();

                    } else {
                        // Unable to get Success status
                        Snackbar.make(rootView, getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(App.getContext(), "" + response.message(), Toast.LENGTH_SHORT).show();
                }

                // Hide the ProgressBar
                progressBar.setVisibility(View.GONE);
                mainProgress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<ProductData> call, Throwable t) {
                Toast.makeText(App.getContext(), "NetworkCallFailure : " + t, Toast.LENGTH_LONG).show();

            }
        });
    }


    /*********** LoadMoreTask Used to Load more Products from the Server in the Background Thread using AsyncTask ********/

    private class LoadMoreTask extends AsyncTask<String, Void, String> {

        int page_number;
        PostFilterData postFilters;

        private LoadMoreTask(int page_number, PostFilterData postFilterData) {
            this.page_number = page_number;
            this.postFilters = postFilterData;
        }


        //*********** Runs on the UI thread before #doInBackground() ********//

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogLoader.showProgressDialog();

        }


        //*********** Performs some Processes on Background Thread and Returns a specified Result  ********//

        @Override
        protected String doInBackground(String... params) {

            if (isFilterApplied) {
                RequestFilteredProducts(page_number, sortBy, postFilters);
            } else {
                // Request for Products based on PageNo.
                RequestAllProducts(page_number, sortBy);
            }
            return "All Done!";
        }


        //*********** Runs on the UI thread after #doInBackground() ********//

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dialogLoader.hideProgressDialog();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (call != null && call.isExecuted()) {
            call.cancel();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (call.isExecuted())
            call.cancel();
        if (filterCAll.isExecuted())
            filterCAll.cancel();
    }


}