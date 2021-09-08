package com.themescoder.androidstore.adapters;


import androidx.annotation.NonNull;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.themescoder.androidstore.R;

import java.util.List;

import com.themescoder.androidstore.constant.ConstantValues;
import com.themescoder.androidstore.models.product_model.Value;


/**
 * ProductAttributesDialogAdapter is the adapter class of the Dialog holding List of Attributes in ProductAttributesAdapter
 **/

public class ProductAttributesDialogAdapter extends BaseAdapter {

    private Context context;
    private List<Value> attributeValues;
    
    private LayoutInflater layoutInflater;


    public ProductAttributesDialogAdapter(Context context, List<Value> attributeValues) {
        this.context = context;
        this.attributeValues = attributeValues;
    
        layoutInflater = LayoutInflater.from(context);
    }
    
    
    //********** Returns the total number of items in the data set represented by this Adapter *********//
    
    @Override
    public int getCount() {
        return attributeValues.size();
    }
    
    
    //********** Returns the item associated with the specified position in the data set *********//
    
    @Override
    public Object getItem(int position) {
        return attributeValues.get(position);
    }
    
    
    //********** Returns the item id associated with the specified position in the list *********//
    
    @Override
    public long getItemId(int position) {
        return position;
    }
    
    
    
    //********** Returns a View that displays the data at the specified position in the data set *********//

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data model based on Position
        Value value = attributeValues.get(position);
    
    
        final ViewHolder holder;
        
        // Check if an existing View is being Reused, otherwise Inflate the View with custom Layout
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.layout_attribute_values_dialog, parent, false);
            
            holder = new ViewHolder();
    
            holder.attribute_value_name = (TextView) convertView.findViewById(R.id.attribute_value_name);
            holder.attribute_value_prefix = (TextView) convertView.findViewById(R.id.attribute_value_prefix);
            holder.attribute_value_price = (TextView) convertView.findViewById(R.id.attribute_value_price);
            
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        
        // Populate the data into the Template View
        holder.attribute_value_name.setText(value.getValue());
        holder.attribute_value_prefix.setText(value.getPricePrefix());
        holder.attribute_value_price.setText(ConstantValues.CURRENCY_SYMBOL + value.getPrice());

        
        return convertView;
    }
    
    
    
    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/
    
    static class ViewHolder {
        private TextView attribute_value_name, attribute_value_prefix, attribute_value_price;
        
    }
    
}

