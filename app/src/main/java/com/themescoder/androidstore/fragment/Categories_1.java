package com.themescoder.androidstore.fragment;


import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.themescoder.androidstore.R;

import java.util.ArrayList;
import java.util.List;

import com.themescoder.androidstore.adapters.CategoryListAdapter_1;
import com.themescoder.androidstore.app.App;
import com.themescoder.androidstore.models.category_model.CategoryDetails;


public class Categories_1 extends Fragment {

    Boolean isMenuItem = true;
    Boolean isHeaderVisible = false;

    TextView emptyText, headerText;
    RecyclerView category_recycler;

    CategoryListAdapter_1 categoryListAdapter;

    List<CategoryDetails> allCategoriesList;
    List<CategoryDetails> mainCategoriesList;
    int spancount= 2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.categories, container, false);

        if (getArguments() != null) {
            if (getArguments().containsKey("isHeaderVisible")) {
                isHeaderVisible = getArguments().getBoolean("isHeaderVisible", false);
            }
        
            if (getArguments().containsKey("isMenuItem")) {
                isMenuItem = getArguments().getBoolean("isMenuItem", true);
            }

            if (getArguments().containsKey("home_9")) {
                if (getArguments().getBoolean("home_9")) {

                    spancount = 3;
                } else {

                    spancount = 2;
                }
            }


        }
        
        
        if (isMenuItem) {
            // Enable Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
            //MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.categories));
        }
        

        allCategoriesList = new ArrayList<>();

        // Get CategoriesList from ApplicationContext
        allCategoriesList = ((App) getContext().getApplicationContext()).getCategoriesList();
        
        
        // Binding Layout Views
        emptyText = (TextView) rootView.findViewById(R.id.empty_record_text);
        headerText = (TextView) rootView.findViewById(R.id.categories_header);
        category_recycler = (RecyclerView) rootView.findViewById(R.id.categories_recycler);
        NestedScrollView scroll_container = (NestedScrollView) rootView.findViewById(R.id.scroll_container);
        
        scroll_container.setNestedScrollingEnabled(true);
        category_recycler.setNestedScrollingEnabled(false);
        

        // Hide some of the Views
        emptyText.setVisibility(View.GONE);

        // Check if Header must be Invisible or not
        if (!isHeaderVisible) {
            // Hide the Header of CategoriesList
            headerText.setVisibility(View.GONE);
        } else {
            headerText.setText(getString(R.string.categories));
        }



        mainCategoriesList= new ArrayList<>();

        for (int i=0;  i<allCategoriesList.size();  i++) {
            if (allCategoriesList.get(i).getParentId().equalsIgnoreCase("0")) {
                mainCategoriesList.add(allCategoriesList.get(i));
            }
        }

        if (mainCategoriesList.isEmpty()){
            emptyText.setVisibility(View.VISIBLE);
        }

        // Initialize the CategoryListAdapter for RecyclerView
        categoryListAdapter = new CategoryListAdapter_1(getContext(), mainCategoriesList, allCategoriesList, false);

        // Set the Adapter and LayoutManager to the RecyclerView
        category_recycler.setAdapter(categoryListAdapter);
        category_recycler.setLayoutManager(new GridLayoutManager(getContext(), spancount));

        categoryListAdapter.notifyDataSetChanged();


        return rootView;
    }

}

