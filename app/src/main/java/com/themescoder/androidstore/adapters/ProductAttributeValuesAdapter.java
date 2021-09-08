package com.themescoder.androidstore.adapters;


import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.themescoder.androidstore.R;
import com.themescoder.androidstore.constant.ConstantValues;
import com.themescoder.androidstore.models.product_model.Value;

import java.util.List;


/**
 * ProductAttributeValuesAdapter is the adapter class of RecyclerView holding List of Product Attribute Values in CartItemsAdapter
 **/

public class ProductAttributeValuesAdapter extends RecyclerView.Adapter<ProductAttributeValuesAdapter.MyViewHolder> {

    Context context;
    private List<Value> attributeValues;


    public ProductAttributeValuesAdapter(Context context, List<Value> attributeValues) {
        this.context = context;
        this.attributeValues = attributeValues;
    }



    //********** Called to Inflate a Layout from XML and then return the Holder *********//

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_attribute_values, parent, false);

        return new MyViewHolder(itemView);
    }



    //********** Called by RecyclerView to display the Data at the specified Position *********//

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // Get the data model based on Position
        Value value = attributeValues.get(position);
    
        holder.attribute_value_name.setText(value.getValue());
        holder.attribute_value_prefix.setText(value.getPricePrefix());
        holder.attribute_value_price.setText(ConstantValues.CURRENCY_SYMBOL + value.getPrice());
    }



    //********** Returns the total number of items in the data set *********//

    @Override
    public int getItemCount() {
        return attributeValues.size();
    }



    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView attribute_value_name;
        TextView attribute_value_prefix;
        TextView attribute_value_price;


        public MyViewHolder(final View itemView) {
            super(itemView);
            attribute_value_name = (TextView) itemView.findViewById(R.id.attribute_value_name);
            attribute_value_prefix = (TextView) itemView.findViewById(R.id.attribute_value_prefix);
            attribute_value_price = (TextView) itemView.findViewById(R.id.attribute_value_price);
        }
    }

}

