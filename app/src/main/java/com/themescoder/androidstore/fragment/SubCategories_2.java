package com.themescoder.androidstore.fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.themescoder.androidstore.activities.MainActivity;
import com.themescoder.androidstore.R;

import java.util.ArrayList;
import java.util.List;

import com.themescoder.androidstore.adapters.CategoryListAdapter_2;
import com.themescoder.androidstore.app.App;
import com.themescoder.androidstore.models.category_model.CategoryDetails;

import am.appwise.components.ni.NoInternetDialog;


public class SubCategories_2 extends Fragment {

    int parentCategoryID;
    Boolean isHeaderVisible;

    TextView emptyText, headerText;
    RecyclerView category_recycler;

    CategoryListAdapter_2 categoryListAdapter;

    List<CategoryDetails> allCategoriesList;
    List<CategoryDetails> subCategoriesList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.categories, container, false);

        // Get CategoryID from Bundle arguments
        parentCategoryID = getArguments().getInt("CategoryID");

        NoInternetDialog noInternetDialog = new NoInternetDialog.Builder(getContext()).build();
        // noInternetDialog.show();

        // Enable Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
        //MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        ((MainActivity)getActivity()).toggleNavigaiton(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getArguments().getString("CategoryName", getString(R.string.actionCategory)));


        // Get CategoriesList from ApplicationContext
        allCategoriesList = new ArrayList<>();
        allCategoriesList = ((App) getContext().getApplicationContext()).getCategoriesList();


        // Binding Layout Views
        emptyText = (TextView) rootView.findViewById(R.id.empty_record_text);
        headerText = (TextView) rootView.findViewById(R.id.categories_header);
        category_recycler = (RecyclerView) rootView.findViewById(R.id.categories_recycler);


        // Hide some of the Views
        headerText.setVisibility(View.GONE);
        emptyText.setVisibility(View.GONE);


        subCategoriesList = new ArrayList<>();

        for (int i = 0; i < allCategoriesList.size(); i++) {
            if (allCategoriesList.get(i).getParentId().equalsIgnoreCase(String.valueOf(parentCategoryID))) {
                subCategoriesList.add(allCategoriesList.get(i));
            }
        }


        // Initialize the CategoryListAdapter and LayoutManager for RecyclerView
        categoryListAdapter = new CategoryListAdapter_2(getContext(), subCategoriesList, null, true);
        category_recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        // Set the Adapter to the RecyclerView
        category_recycler.setAdapter(categoryListAdapter);

        categoryListAdapter.notifyDataSetChanged();


        return rootView;
    }

}

