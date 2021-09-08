package com.themescoder.androidstore.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import com.themescoder.androidstore.R;
import com.themescoder.androidstore.activities.MainActivity;
import com.themescoder.androidstore.adapters.ViewPagerCustomAdapter;
import com.themescoder.androidstore.app.App;
import com.themescoder.androidstore.models.category_model.CategoryDetails;


public class Products extends Fragment {

    String sortBy = "Newest";
    boolean isMenuItem = false;
    boolean isSubFragment = false;

    int selectedTabIndex = 0;
    String selectedTabText = "";

    TabLayout product_tabs;
    ViewPager product_viewpager;

    ViewPagerCustomAdapter viewPagerCustomAdapter;

    List<CategoryDetails> allCategoriesList = new ArrayList<>();
    List<CategoryDetails> allSubCategoriesList = new ArrayList<>();
    List<CategoryDetails> finalCategoriesList = new ArrayList<>();

    public void invalidateProducts(){
        viewPagerCustomAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get CategoriesList from AppContext
        allCategoriesList = ((App) getContext().getApplicationContext()).getCategoriesList();

        allSubCategoriesList = new ArrayList<>();

        // Get SubCategoriesList from AllCategoriesList
        for (int i=0;  i<allCategoriesList.size();  i++) {
            if (!allCategoriesList.get(i).getParentId().equalsIgnoreCase("0")) {
                allSubCategoriesList.add(allCategoriesList.get(i));
            }
        }

        finalCategoriesList = new ArrayList<>();
        finalCategoriesList.addAll(allCategoriesList);
        //finalCategoriesList.addAll(allSubCategoriesList);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.products, container, false);

        if (getArguments() != null) {
            if (getArguments().containsKey("sortBy")) {
                sortBy = getArguments().getString("sortBy", "Newest");
            }

            if (getArguments().containsKey("isMenuItem")) {
                isMenuItem = getArguments().getBoolean("isMenuItem", false);
            }

            if (getArguments().containsKey("isSubFragment")) {
                isSubFragment = getArguments().getBoolean("isSubFragment", false);
            }

            if (getArguments().containsKey("CategoryName")) {
                selectedTabText = getArguments().getString("CategoryName", "Category");
            }
        }


        // Toggle Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
        if (!isSubFragment) {
            if (isMenuItem) {
                //MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
                ((MainActivity)getActivity()).toggleNavigaiton(true);
            } else {
                //MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
                ((MainActivity)getActivity()).toggleNavigaiton(false);
            }
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.actionShop));
        }


        // Binding Layout Views
        product_tabs = (TabLayout) rootView.findViewById(R.id.product_tabs);
        product_viewpager = (ViewPager) rootView.findViewById(R.id.product_viewpager);


        // Setup ViewPagerAdapter
        setupViewPagerAdapter();


        product_viewpager.setOffscreenPageLimit(0);
        product_viewpager.setAdapter(viewPagerCustomAdapter);

        // Add corresponding ViewPagers to TabLayouts
        product_tabs.setupWithViewPager(product_viewpager);


        try {
            product_tabs.getTabAt(selectedTabIndex).select();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }



        return rootView;

    }



    //*********** Setup the ViewPagerAdapter ********//

    private void setupViewPagerAdapter() {

        // Initialize ViewPagerAdapter with ChildFragmentManager for ViewPager
        viewPagerCustomAdapter = new ViewPagerCustomAdapter(getChildFragmentManager());

        // Initialize All_Products Fragment with specified arguments
        Fragment allProducts = new All_Products();
        Bundle bundleInfo = new Bundle();
        bundleInfo.putString("sortBy", sortBy);
        allProducts.setArguments(bundleInfo);

        // Add the Fragments to the ViewPagerAdapter with TabHeader
        viewPagerCustomAdapter.addFragment(allProducts, getContext().getString(R.string.all));


        for (int i=0;  i < finalCategoriesList.size();  i++) {

            // Initialize Category_Products Fragment with specified arguments
            Fragment categoryProducts = new Category_Products();
            Bundle categoryInfo = new Bundle();
            categoryInfo.putString("sortBy", sortBy);
            categoryInfo.putInt("CategoryID", Integer.parseInt(finalCategoriesList.get(i).getId()));
            categoryProducts.setArguments(categoryInfo);

            // Add the Fragments to the ViewPagerAdapter with TabHeader
            viewPagerCustomAdapter.addFragment(categoryProducts, finalCategoriesList.get(i).getName());


            if (selectedTabText.equalsIgnoreCase(finalCategoriesList.get(i).getName())) {
                selectedTabIndex = i + 1;
            }
        }

    }

}

