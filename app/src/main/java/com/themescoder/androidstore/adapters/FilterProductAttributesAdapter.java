package com.themescoder.androidstore.adapters;


import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.themescoder.androidstore.R;

import java.util.ArrayList;
import java.util.List;

import com.themescoder.androidstore.models.filter_model.get_filters.FilterValue;
import com.themescoder.androidstore.models.filter_model.post_filters.FiltersAttributes;


/**
 * FilterProductAttributesAdapter is the adapter class of RecyclerView holding List of Filter_Attributes in FilterProductsAdapter
 **/

public class FilterProductAttributesAdapter extends RecyclerView.Adapter<FilterProductAttributesAdapter.MyViewHolder>{

    Context context;
    String filterName;
    List<FilterValue> filterValues;
    List<FiltersAttributes> filtersAttributesList = new ArrayList<>();


    public FilterProductAttributesAdapter(Context context, List<FilterValue> filterValues, String filterName, List<FiltersAttributes> filtersAttributesList) {
        this.context = context;
        this.filterValues = filterValues;
        this.filterName = filterName;
        this.filtersAttributesList = filtersAttributesList;
    }



    //********** Called to Inflate a Layout from XML and then return the Holder *********//

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_filters_attributes, parent, false);

        return new MyViewHolder(itemView);
    }



    //********** Called by RecyclerView to display the Data at the specified Position *********//

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        // Get the data model based on Position
        final FilterValue value = filterValues.get(position);

        // New Attribute
        final FiltersAttributes filtersAttribute = new FiltersAttributes();


        holder.filter_attribute_name.setText(value.getValue());

        
        // Add or Remove relevant Filter from selected FilterList
        holder.filter_attribute_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                // Get Filter Data
                filtersAttribute.setName(filterName);
                filtersAttribute.setValue(value.getValue());

                if (isChecked){
                    // Add to selected FiltersList
                    filtersAttributesList.add(filtersAttribute);
                } else {
                    // Remove from selected FiltersList
                    filtersAttributesList.remove(filtersAttribute);
                }
            }
        });
    }



    //********** Returns the total number of items in the data set *********//

    @Override
    public int getItemCount() {
        return filterValues.size();
    }



    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView filter_attribute_name;
        public CheckBox filter_attribute_checkbox;


        public MyViewHolder(final View itemView) {
            super(itemView);
            filter_attribute_name = (TextView) itemView.findViewById(R.id.filter_attribute_name);
            filter_attribute_checkbox = (CheckBox) itemView.findViewById(R.id.filter_attribute_checkbox);
        }
    }

}

