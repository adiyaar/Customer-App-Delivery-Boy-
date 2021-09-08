package com.themescoder.androidstore.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import androidx.annotation.Nullable;
import android.os.AsyncTask;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


import com.google.gson.Gson;
import com.themescoder.androidstore.R;

import java.util.ArrayList;

import java.util.List;

import com.themescoder.androidstore.app.App;
import com.themescoder.androidstore.constant.ConstantValues;
import com.themescoder.androidstore.customs.DialogLoader;
import com.themescoder.androidstore.models.filter_model.get_filters.FilterData;
import com.themescoder.androidstore.models.filter_model.get_filters.FilterDetails;
import com.themescoder.androidstore.models.product_model.GetAllProducts;
import com.themescoder.androidstore.customs.EndlessRecyclerViewScroll;
import com.themescoder.androidstore.adapters.ProductAdapter;
import com.themescoder.androidstore.customs.FilterDialog;
import com.themescoder.androidstore.models.filter_model.post_filters.PostFilterData;
import com.themescoder.androidstore.models.product_model.ProductData;
import com.themescoder.androidstore.models.product_model.ProductDetails;
import com.themescoder.androidstore.network.APIClient;

import am.appwise.components.ni.NoInternetDialog;
import retrofit2.Call;
import retrofit2.Callback;


public class Category_Products extends Fragment {

    View rootView;
    
    int pageNo = 0;
    double maxPrice = 0;
    boolean isVisible;
    boolean isGridView;
    boolean isFilterApplied;
    
    int categoryID;
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
    RecyclerView category_products_recycler;
    
    LoadMoreTask loadMoreTask;
    FilterDialog filterDialog;
    PostFilterData filters = null;

    ProductAdapter productAdapter;
    List<ProductDetails> categoryProductsList;
    List<FilterDetails> filtersList = new ArrayList<>();

    GridLayoutManager gridLayoutManager;
    LinearLayoutManager linearLayoutManager;
    DialogLoader dialogLoader;

    Call<FilterData> filterCAll;
    Call<ProductData> productsCall;
    
    
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    
        isVisible = isVisibleToUser;
    }
    
    
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.f_products_vertical, container, false);


        NoInternetDialog noInternetDialog = new NoInternetDialog.Builder(getContext()).build();
        //noInternetDialog.show();

        // Get CategoryID from bundle arguments
        categoryID = getArguments().getInt("CategoryID");

        // Get sortBy from bundle arguments
        if (getArguments().containsKey("sortBy")) {
            sortBy = getArguments().getString("sortBy");
        }
        

        // Get the Customer's ID from SharedPreferences
        customerID = getActivity().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userID", "");


        // Binding Layout Views
        bottomBar = (LinearLayout) rootView.findViewById(R.id.bottomBar);
        sortList = (LinearLayout) rootView.findViewById(R.id.sort_list);
        sortListText = (TextView) rootView.findViewById(R.id.sort_text);
        emptyRecord = (TextView) rootView.findViewById(R.id.empty_record);
        progressBar =  rootView.findViewById(R.id.loading_bar);
        resetFiltersBtn = (Button) rootView.findViewById(R.id.resetFiltersBtn);
        filterButton = (ToggleButton) rootView.findViewById(R.id.filterBtn);
        toggleLayoutView = (ToggleButton) rootView.findViewById(R.id.layout_toggleBtn);
        category_products_recycler = (RecyclerView) rootView.findViewById(R.id.products_recycler);
        mainProgress = rootView.findViewById(R.id.progressBar);

        // Hide some of the Views
        progressBar.setVisibility(View.GONE);
        emptyRecord.setVisibility(View.GONE);
        resetFiltersBtn.setVisibility(View.GONE);
        
        
        isGridView = true;
        isFilterApplied = false;
        filterButton.setChecked(isFilterApplied);
        toggleLayoutView.setChecked(isGridView);
    
    
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


        // Initialize CategoryProductsList
        categoryProductsList = new ArrayList<>();
    
    
        // Request for Products of given OrderProductCategory based on PageNo.
        RequestCategoryProducts(pageNo, sortBy);
    
        // Request for Filters of given OrderProductCategory
        RequestFilters(String.valueOf(categoryID));


        // Initialize GridLayoutManager and LinearLayoutManager
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        linearLayoutManager = new LinearLayoutManager(getContext());


        // Initialize the ProductAdapter for RecyclerView
        productAdapter = new ProductAdapter(getContext(), categoryProductsList, false,false);

        
        setRecyclerViewLayoutManager(isGridView);
        category_products_recycler.setAdapter(productAdapter);



        // Handle the Scroll event of Product's RecyclerView
        category_products_recycler.addOnScrollListener(new EndlessRecyclerViewScroll(bottomBar) {
            @Override
            public void onLoadMore(final int current_page) {
                
                progressBar.setVisibility(View.VISIBLE);

                if (isFilterApplied) {
                    // Initialize LoadMoreTask to Load More Products from Server against some Filters
                    loadMoreTask = new LoadMoreTask(current_page, filters);
                } else {
                    // Initialize LoadMoreTask to Load More Products from Server without Filters
                    loadMoreTask = new LoadMoreTask(current_page, filters);
                }

                // Execute AsyncTask LoadMoreTask to Load More Products from Server
                loadMoreTask.execute();
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
        filterDialog = new FilterDialog(getContext(), String.valueOf(categoryID), filtersList, maxPrice) {
            @Override
            public void clearFilters() {
                // Clear Filters
                isFilterApplied = false;
                filterButton.setChecked(false);
                filters = null;
                categoryProductsList.clear();
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
                categoryProductsList.clear();
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
                    filterDialog = new FilterDialog(getContext(), String.valueOf(categoryID), filtersList, maxPrice) {
                        @Override
                        public void clearFilters() {
                            isFilterApplied = false;
                            filterButton.setChecked(false);
                            filters = null;
                            categoryProductsList.clear();
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
                            categoryProductsList.clear();
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

                final String[] sortArray = getResources().getStringArray(R.array.sortBy_array);

                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setCancelable(true);
                
                dialog.setItems(sortArray, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        
                        String selectedText = sortArray[which];
                        sortListText.setText(selectedText);
    
                        
                        if (selectedText.equalsIgnoreCase(sortArray[0])) {
                            sortBy = "Newest";
                        }
                        else if (selectedText.equalsIgnoreCase(sortArray[1])) {
                            sortBy = "a to z";
                        }
                        else if (selectedText.equalsIgnoreCase(sortArray[2])) {
                            sortBy = "z to a";
                        }
                        else if (selectedText.equalsIgnoreCase(sortArray[3])) {
                            sortBy = "high to low";
                        }
                        else if (selectedText.equalsIgnoreCase(sortArray[4])) {
                            sortBy = "low to high";
                        }
                        else if (selectedText.equalsIgnoreCase(sortArray[5])) {
                            sortBy = "top seller";
                        }
                        else if (selectedText.equalsIgnoreCase(sortArray[6])) {
                            sortBy = "special";
                        }
                        else if (selectedText.equalsIgnoreCase(sortArray[7])) {
                            sortBy = "most liked";
                        }
                        else {
                            sortBy = "Newest";
                        }
                        

                        categoryProductsList.clear();
                        productAdapter.notifyDataSetChanged();
                        mainProgress.setVisibility(View.VISIBLE);

                        if(isFilterApplied){
                            // Initialize LoadMoreTask to Load More Products from Server against some Filters
                            RequestFilteredProducts(pageNo, sortBy, filters);
                        }else {
                            // Initialize LoadMoreTask to Load More Products from Server without Filters
                            RequestCategoryProducts(pageNo, sortBy);
                        }
                        dialog.dismiss();


                        // Handle the Scroll event of Product's RecyclerView
                        category_products_recycler.addOnScrollListener(new EndlessRecyclerViewScroll(bottomBar) {
                            @Override
                            public void onLoadMore(final int current_page) {
                                
                                progressBar.setVisibility(View.VISIBLE);

                                if(isFilterApplied){
                                    // Initialize LoadMoreTask to Load More Products from Server against some Filters
                                    loadMoreTask = new LoadMoreTask(current_page, filters);
                                }else {
                                    // Initialize LoadMoreTask to Load More Products from Server without Filters
                                    loadMoreTask = new LoadMoreTask(current_page, filters);
                                }

                                // Execute AsyncTask LoadMoreTask to Load More Products from Server
                                loadMoreTask.execute();
                            }
                        });

                    }
                });
                dialog.show();
            }
        });


        resetFiltersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFilterApplied = false;
                filterButton.setChecked(false);
                filters = null;
                categoryProductsList.clear();
                productAdapter.notifyDataSetChanged();
                emptyRecord.setVisibility(View.GONE);
                resetFiltersBtn.setVisibility(View.GONE);
                mainProgress.setVisibility(View.VISIBLE);

                new LoadMoreTask(pageNo, filters).execute();
            }
        });


        return rootView;
    }
    
    
    
    //*********** Switch RecyclerView's LayoutManager ********//
    
    public void setRecyclerViewLayoutManager(Boolean isGridView) {
        int scrollPosition = 0;
        
        // If a LayoutManager has already been set, get current Scroll Position
        if (category_products_recycler.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) category_products_recycler.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        }
    
        productAdapter.toggleLayout(isGridView);
        
        category_products_recycler.setLayoutManager(isGridView ? gridLayoutManager : linearLayoutManager);
        category_products_recycler.setAdapter(productAdapter);
        
        category_products_recycler.scrollToPosition(scrollPosition);
    }



    //*********** Adds Products returned from the Server to the CategoryProductsList ********//

    private void addCategoryProducts(ProductData productData) {

        // Add Products to CategoryProductsList from the List of ProductData
        for (int i = 0; i < productData.getProductData().size(); i++) {
            ProductDetails productDetails = productData.getProductData().get(i);
            categoryProductsList.add(productDetails);
        }

        productAdapter.notifyDataSetChanged();


        // Change the Visibility of emptyRecord Text based on CategoryProductsList's Size
        if (productAdapter.getItemCount() == 0) {
            if (isFilterApplied) {
                resetFiltersBtn.setVisibility(View.VISIBLE);
            }
            emptyRecord.setVisibility(View.VISIBLE);

        } else {
            emptyRecord.setVisibility(View.GONE);
            resetFiltersBtn.setVisibility(View.GONE);
        }
    }



    //*********** Request Products of given OrderProductCategory from the Server based on PageNo. ********//

    public void RequestCategoryProducts(int pageNumber, String sortBy) {

        GetAllProducts getAllProducts = new GetAllProducts();
        getAllProducts.setPageNumber(pageNumber);
        getAllProducts.setLanguageId(ConstantValues.LANGUAGE_ID);
        getAllProducts.setCustomersId(customerID);
        getAllProducts.setCategoriesId(String.valueOf(categoryID));
        getAllProducts.setCurrencyCode(ConstantValues.CURRENCY_CODE);
        getAllProducts.setType(sortBy);
        getAllProducts.setCurrencyCode(ConstantValues.CURRENCY_CODE);

        productsCall = APIClient.getInstance()
                .getAllProducts
                        (
                                getAllProducts
                        );

        productsCall.enqueue(new Callback<ProductData>() {
            @Override
            public void onResponse(Call<ProductData> call, retrofit2.Response<ProductData> response) {
                
                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {

                        // Products have been returned. Add Products to the ProductsList
                        addCategoryProducts(response.body());

                    }
                    else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        // Products haven't been returned. Call the method to process some implementations
                        addCategoryProducts(response.body());
                        
                        // Show the Message to the User
                        if (isVisible)
                            Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                    else {
                        // Unable to get Success status
                        if (isVisible)
                            Toast.makeText(getContext(), getString(R.string.unexpected_response), Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    if (isVisible)
                        Toast.makeText(App.getContext(), ""+response.message(), Toast.LENGTH_SHORT).show();
                }

                // Hide the ProgressBar
                progressBar.setVisibility(View.GONE);
                mainProgress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<ProductData> call, Throwable t) {
                if (isVisible) {
                    Toast.makeText(App.getContext(), "NetworkCallFailure : " + t, Toast.LENGTH_LONG).show();
                    // Hide the ProgressBar
                    progressBar.setVisibility(View.GONE);
                    mainProgress.setVisibility(View.GONE);

                }
            }
        });
    }



    //*********** Request Products of given OrderProductCategory from the Server based on PageNo. against some Filters ********//

    public void RequestFilteredProducts(int pageNumber, String sortBy, PostFilterData postFilterData) {


        GetAllProducts getAllProducts = new GetAllProducts();
        getAllProducts.setPageNumber(pageNumber);
        getAllProducts.setLanguageId(ConstantValues.LANGUAGE_ID);
        getAllProducts.setCustomersId(customerID);
        getAllProducts.setCategoriesId(categoryID+"");
        getAllProducts.setType(sortBy);
        getAllProducts.setPrice(postFilterData.getPrice());
        getAllProducts.setFilters(postFilterData.getFilters());
        getAllProducts.setCurrencyCode(ConstantValues.CURRENCY_CODE);

        String data = new Gson().toJson(getAllProducts);

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
                        addCategoryProducts(response.body());

                    }
                    else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        // Products haven't been returned. Call the method to process some implementations
                        addCategoryProducts(response.body());
                        
                        // Show the Message to the User
                        Snackbar.make(rootView, response.body().getMessage(), Snackbar.LENGTH_SHORT).show();

                    }
                    else {
                        // Unable to get Success status
                        Snackbar.make(rootView, getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                    }

                }
                else {
                    Toast.makeText(App.getContext(), ""+response.message(), Toast.LENGTH_SHORT).show();
                }

                // Hide the ProgressBar
                progressBar.setVisibility(View.GONE);
                mainProgress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<ProductData> call, Throwable t) {
                Toast.makeText(App.getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();

            }
        });
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

                    }
                    else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        if (isVisible)
                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (isVisible)
                            Toast.makeText(getActivity(), getString(R.string.unexpected_response), Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    if (isVisible)
                        Toast.makeText(getActivity(), ""+response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FilterData> call, Throwable t) {
                /*if (isVisible)
                    Toast.makeText(getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();*/
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
        }


        //*********** Performs some Processes on Background Thread and Returns a specified Result  ********//

        @Override
        protected String doInBackground(String... params) {

            // Check if any of the Filter is applied
            if (isFilterApplied) {
                // Request for Products against specified Filters, based on PageNo.
                RequestFilteredProducts(page_number, sortBy, postFilters);
            }
            else {
                // Request for Products of given OrderProductCategory, based on PageNo.
                RequestCategoryProducts(page_number, sortBy);
            }

            return "All Done!";
        }


        //*********** Runs on the UI thread after #doInBackground() ********//

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (productsCall.isExecuted())
            productsCall.cancel();
        if (filterCAll.isExecuted())
            filterCAll.cancel();
    }


}