package com.themescoder.androidstore.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/*
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
*/
import com.themescoder.androidstore.activities.MainActivity;
import com.themescoder.androidstore.R;


import java.util.ArrayList;
import java.util.List;

import com.themescoder.androidstore.constant.ConstantValues;
import com.themescoder.androidstore.models.category_model.CategoryDetails;
import com.themescoder.androidstore.customs.DividerItemDecoration;
import com.themescoder.androidstore.adapters.SearchResultsAdapter;
import com.themescoder.androidstore.app.App;
import com.themescoder.androidstore.models.search_model.SearchData;
import com.themescoder.androidstore.models.search_model.SearchDetails;
import com.themescoder.androidstore.models.search_model.SearchResults;
import com.themescoder.androidstore.network.APIClient;

import am.appwise.components.ni.NoInternetDialog;
import retrofit2.Call;
import retrofit2.Callback;


public class SearchFragment extends Fragment {

    View rootView;
    
    //AdView mAdView;
    EditText search_editText;
    FrameLayout banner_adView;
    RecyclerView categories_recycler, products_recycler;
    ProgressBar progressBar;

    List<SearchResults> resultsList;
    List<CategoryDetails> allCategoriesList = new ArrayList<>();
    List<CategoryDetails> subCategoriesList = new ArrayList<>();
    List<CategoryDetails> finalCategories = new ArrayList<>();

    SearchResultsAdapter searchProductsAdapter;
    SearchResultsAdapter searchCategoriesAdapter;

    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.search_fragment, container, false);

        setHasOptionsMenu(true);

        //MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        ((MainActivity)getActivity()).toggleNavigaiton(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.actionSearch));

        NoInternetDialog noInternetDialog = new NoInternetDialog.Builder(getContext()).build();
        //noInternetDialog.show();

        
        // Binding Layout Views
        banner_adView = (FrameLayout) rootView.findViewById(R.id.banner_adView);
        search_editText = (EditText) rootView.findViewById(R.id.search_editText);
        products_recycler = (RecyclerView) rootView.findViewById(R.id.products_recycler);
        categories_recycler = (RecyclerView) rootView.findViewById(R.id.categories_recycler);
        progressBar = rootView.findViewById(R.id.progressBar);
    
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

        
        products_recycler.setNestedScrollingEnabled(false);
        categories_recycler.setNestedScrollingEnabled(false);
        
        // Hide some of the Views
        products_recycler.setVisibility(View.GONE);
        categories_recycler.setVisibility(View.GONE);
        
        
        resultsList = new ArrayList<>();
        subCategoriesList = new ArrayList<>();
        
        
        // Get All CategoriesList from ApplicationContext
        allCategoriesList = ((App) getContext().getApplicationContext()).getCategoriesList();
        
        for (int i=0;  i<allCategoriesList.size();  i++) {
            if (!allCategoriesList.get(i).getParentId().equalsIgnoreCase("0")) {
                subCategoriesList.add(allCategoriesList.get(i));
            }
        }

        finalCategories.addAll(allCategoriesList);
        finalCategories.addAll(subCategoriesList);
    
        // Initialize the SearchResultsAdapter for RecyclerView
        searchCategoriesAdapter = new SearchResultsAdapter(getContext(), resultsList);
    
        // Set the Adapter, LayoutManager and ItemDecoration to the RecyclerView
        categories_recycler.setAdapter(searchCategoriesAdapter);
        categories_recycler.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        categories_recycler.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
    
    
        // Initialize the SearchResultsAdapter for RecyclerView
        searchProductsAdapter = new SearchResultsAdapter(getContext(), resultsList);
    
        // Set the Adapter, LayoutManager and ItemDecoration to the RecyclerView
        products_recycler.setAdapter(searchProductsAdapter);
        products_recycler.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        products_recycler.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        

        // Show Categories
        showCategories();
    
    
        search_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(search_editText.getText().toString())) {
                    // Show Categories
                    showCategories();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        
        
        // Set listener to be called when an action is performed on the search_editText
        search_editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!TextUtils.isEmpty(search_editText.getText().toString())) {
                        hideKeyboardFrom(getContext());
                        RequestSearchData(search_editText.getText().toString());
                        return true;
                    }
                }
                return false;
            }
        });


        return rootView;
    }


    public void hideKeyboardFrom(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(search_editText.getWindowToken(), 0);
    }


    //*********** Show Main Categories in the SearchList ********//

    private void showCategories() {

        // Make CategoriesList Visible
        categories_recycler.setVisibility(View.VISIBLE);
        products_recycler.setVisibility(View.GONE);

        resultsList.clear();
        searchCategoriesAdapter.notifyDataSetChanged();

        for (int i=0;  i<finalCategories.size();  i++) {
            // Add the current OrderProductCategory Info to SearchResults
            SearchResults searchResult = new SearchResults();
            searchResult.setId(Integer.parseInt(finalCategories.get(i).getId()));
            searchResult.setName(finalCategories.get(i).getName());
            searchResult.setImage(finalCategories.get(i).getImage());
            searchResult.setParent("Categories");

            // Add SearchResults to results List
            resultsList.add(searchResult);
        }
        

        // Notify the Adapter
        searchCategoriesAdapter.notifyDataSetChanged();

    }



    //*********** Adds SearchResults returned from the Server to the resultsList ********//

    private void addResults(SearchData searchData) {

        // Get the model SearchDetails from SearchData
        SearchDetails searchResults = searchData.getProductData();
    
        
        if (searchResults.getProducts().size() > 0) {
    
            // Make CategoriesList Visible
            categories_recycler.setVisibility(View.GONE);
            products_recycler.setVisibility(View.VISIBLE);
    
            resultsList.clear();
            searchCategoriesAdapter.notifyDataSetChanged();
            
    
            for (int i=0;  i<searchResults.getProducts().size();  i++) {
                // Add the current Product Info to SearchResults
                SearchResults searchResult = new SearchResults();
                searchResult.setId(searchResults.getProducts().get(i).getProductsId());
                searchResult.setName(searchResults.getProducts().get(i).getProductsName());
                searchResult.setImage(searchResults.getProducts().get(i).getProductsImage());
                searchResult.setParent("Products");
        
                // Add SearchResults to results List
                resultsList.add(searchResult);
            }
            
            searchProductsAdapter.notifyDataSetChanged();
            
        }
        else {
            showCategories();
        }
        
    }



    //*********** Request Search Results from the Server based on the given Query ********//

    public void RequestSearchData(String searchValue) {

        progressBar.setVisibility(View.VISIBLE);

        Call<SearchData> call = APIClient.getInstance()
                .getSearchData
                        (
                                searchValue,
                                ConstantValues.LANGUAGE_ID,
                                ConstantValues.CURRENCY_CODE
                        );

        call.enqueue(new Callback<SearchData>() {
            @Override
            public void onResponse(Call<SearchData> call, retrofit2.Response<SearchData> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {

                        // Search Results have been returned. Add Results to the resultsList
                        addResults(response.body());

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
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SearchData> call, Throwable t) {
                Toast.makeText(getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Hide Search Icon in the Toolbar
        MenuItem cartItem = menu.findItem(R.id.toolbar_ic_cart);
        MenuItem searchItem = menu.findItem(R.id.toolbar_ic_search);
        cartItem.setVisible(true);
        searchItem.setVisible(false);
        progressBar.setVisibility(View.GONE);
    }

}



