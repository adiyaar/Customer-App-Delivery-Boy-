package com.themescoder.androidstore.customs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;

import com.themescoder.androidstore.R;

import java.util.ArrayList;
import java.util.List;

import com.themescoder.androidstore.adapters.FilterProductsAdapter;
import com.themescoder.androidstore.models.filter_model.get_filters.FilterDetails;
import com.themescoder.androidstore.models.filter_model.post_filters.FiltersAttributes;
import com.themescoder.androidstore.models.filter_model.post_filters.FiltersPrice;
import com.themescoder.androidstore.models.filter_model.post_filters.PostFilterData;


/**
 * FilterDialog will be used to implement Price and Attribute Filters on Products in different categories
 **/

public abstract class FilterDialog extends Dialog {
    
    private String categoryID;
    private double maxPrice;

    private LinearLayout filterDialogAttributes;
    private CrystalRangeSeekbar filter_price_slider;
    private TextView filter_min_price, filter_max_price;
    private Button filter_cancel_btn, filter_clear_btn, filter_apply_btn;

    private RecyclerView filter_attributes_recycler;
    private FilterProductsAdapter filterProductsAdapter;

    private List<FilterDetails> filtersList;
    private List<FiltersAttributes> filtersAttributesList = new ArrayList<>();



    public FilterDialog(Context context, String categoryID, List<FilterDetails> filtersList, double maxPrice) {
        super(context);
        this.categoryID = categoryID;
        this.maxPrice = maxPrice;
        this.filtersList = filtersList;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Make the Window Full Screen
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setContentView(R.layout.filter_dialog);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        filter_min_price = (TextView) findViewById(R.id.filter_min_price);
        filter_max_price = (TextView) findViewById(R.id.filter_max_price);
        filter_cancel_btn = (Button) findViewById(R.id.filter_cancel_btn);
        filter_clear_btn = (Button) findViewById(R.id.filter_clear_btn);
        filter_apply_btn = (Button) findViewById(R.id.filter_apply_btn);
        filter_price_slider = (CrystalRangeSeekbar) findViewById(R.id.filter_price_slider);
        filter_attributes_recycler = (RecyclerView) findViewById(R.id.filters_recycler);
        filterDialogAttributes = (LinearLayout) findViewById(R.id.filter_dialog_attributes);


        if (filtersList.size() > 0) {
            filterDialogAttributes.setVisibility(View.VISIBLE);
        } else {
            filterDialogAttributes.setVisibility(View.GONE);
        }
    
    
        // Initialize the FilterProductsAdapter for RecyclerView
        filterProductsAdapter = new FilterProductsAdapter(getContext(), filtersList, filtersAttributesList);

        filter_attributes_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        filter_attributes_recycler.setAdapter(filterProductsAdapter);

        filterProductsAdapter.notifyDataSetChanged();


        if (maxPrice > 0) {
            filter_max_price.setText(String.valueOf(maxPrice));
            filter_price_slider.setMaxValue(Float.parseFloat(String.valueOf(maxPrice)));
        } else {
            filter_max_price.setText(String.valueOf(1000));
            filter_price_slider.setMaxValue(Float.parseFloat(String.valueOf(1000)));
        }

        // Get the Price RangeBar Values
        filter_price_slider.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                // Set the Minimum and Maximum Price Values
                filter_min_price.setText(String.valueOf(minValue));
                filter_max_price.setText(String.valueOf(maxValue));
            }
        });


        // Dismiss the FilterDialog
        filter_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


        // Clear Selected Filters
        filter_clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clear Filters
                clearFilters();
                dismiss();
            }
        });


        // Apply Selected Filters
        filter_apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FiltersPrice filtersPrice = new FiltersPrice();
                filtersPrice.setMinPrice(Integer.parseInt(filter_min_price.getText().toString()));
                filtersPrice.setMaxPrice(Integer.parseInt(filter_max_price.getText().toString()));

                PostFilterData filtersData = new PostFilterData();

                filtersData.setPrice(filtersPrice);
                filtersData.setFilters(filtersAttributesList);

                // Apply Filters
                applyFilters(filtersData);

                dismiss();
            }
        });
    }



    //*********** Apply Selected Filters on the Products of a OrderProductCategory ********//

    public abstract void applyFilters(PostFilterData postFilterData);



    //*********** Clear All Filters on the Products of a OrderProductCategory ********//

    public abstract void clearFilters();
    
}

