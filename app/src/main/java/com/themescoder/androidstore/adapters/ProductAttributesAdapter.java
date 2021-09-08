package com.themescoder.androidstore.adapters;


import android.content.Context;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.themescoder.androidstore.R;

import java.util.ArrayList;
import java.util.List;

import com.themescoder.androidstore.constant.ConstantValues;
import com.themescoder.androidstore.fragment.Product_Description;
import com.themescoder.androidstore.models.cart_model.CartProductAttributes;
import com.themescoder.androidstore.models.product_model.Attribute;
import com.themescoder.androidstore.models.product_model.Option;
import com.themescoder.androidstore.models.product_model.Value;


/**
 * ProductAttributesAdapter is the adapter class of RecyclerView holding List of Attributes in Product_Description
 **/

public class ProductAttributesAdapter extends RecyclerView.Adapter<ProductAttributesAdapter.MyViewHolder> {
    
    private Context context;
    private String[] attributeIDs;
    private String[] attributePrices;
    private List<Value> attributeValuesList;
    
    private Product_Description product_description;
    private List<Attribute> productAttributesList;
    private List<CartProductAttributes> selectedAttributesList;
    
    
    public ProductAttributesAdapter(Context context, Product_Description product_description, List<Attribute> productAttributesList, List<CartProductAttributes> selectedAttributesList) {
        this.context = context;
        this.product_description = product_description;
        this.productAttributesList = productAttributesList;
        this.selectedAttributesList = selectedAttributesList;
        
        // Set the size of AttributePrices Array
        attributePrices = new String[this.productAttributesList.size()];
        
        // Set 0 value at every index of AttributePrices Array
        for (int i=0;  i<attributePrices.length;  i++) {
            attributePrices[i] = "0";
        }
        
        // Set the size of AttributePrices Array
        attributeIDs = new String[this.productAttributesList.size()];
        
        // Set 0 value at every index of AttributePrices Array
        for (int i=0;  i<attributeIDs.length;  i++) {
            attributeIDs[i] = String.valueOf(productAttributesList.get(i).getValues().get(0).getProducts_attributes_id());
        }
        
    }
    
    //********** Called to Inflate a Layout from XML and then return the Holder *********//
    
    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_product_attributes, parent, false);
        
        return new MyViewHolder(itemView);
    }
    
    
    //********** Called by RecyclerView to display the Data at the specified Position *********//
    
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // Get the data model based on Position
        final Attribute productsAttribute = productAttributesList.get(position);
        
        // Get Name of Attribute
        final Option option = productsAttribute.getOption();
        // ValuesList of Attribute
        attributeValuesList = new ArrayList<>();
        attributeValuesList = productsAttribute.getValues();
        
        // String Array of Attribute Values
        List<String> attributeValueNames = new ArrayList<>();
        
        // Add Attribute Values to AttributeValueNames Array as String
        for (int i=0;  i<attributeValuesList.size();  i++) {
            attributeValueNames.add(attributeValuesList.get(i).getValue()
                    + " ("
                    + attributeValuesList.get(i).getPricePrefix()
                    + ConstantValues.CURRENCY_SYMBOL
                    + attributeValuesList.get(i).getPrice()
                    +")");
        }
        
        
        holder.attribute_name.setText(option.getName());
        holder.attribute_value.setText(attributeValueNames.get(0));
        
    }
    
    
    //********** Returns the total number of items in the data set *********//
    
    @Override
    public int getItemCount() {
        return productAttributesList.size();
    }
    
    
    
    //********** Returns the Array of Attribute Prices *********//
    
    public String[] getAttributePrices() {
        return attributePrices;
    }
    
    
    //********** Returns the Array of Attribute Prices *********//
    
    public List<String> getAttributeIDs() {
        
        List<String> attributes = new ArrayList<>();
        
        for (int i=0;  i<attributeIDs.length;  i++) {
            attributes.add(attributeIDs[i]);
        }
        
        return attributes;
    }
    
    
    
    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/
    
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        
        RelativeLayout attribute;
        TextView attribute_name, attribute_value;
        
        
        public MyViewHolder(final View itemView) {
            super(itemView);
            
            attribute = (RelativeLayout) itemView.findViewById(R.id.attribute);
            attribute_name = (TextView) itemView.findViewById(R.id.attribute_name);
            attribute_value = (TextView) itemView.findViewById(R.id.attribute_value);
            
            attribute.setOnClickListener(this);
        }
        
        
        @Override
        public void onClick(View v) {
            
            
            Attribute productsAttribute = productAttributesList.get(getAdapterPosition());
            
            final Option option = productsAttribute.getOption();
            final List<Value> attributeValuesList = productsAttribute.getValues();
            
            
            final ProductAttributesDialogAdapter attrValuesAdapter = new ProductAttributesDialogAdapter(context, attributeValuesList);
            
            
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            View dialogView = product_description.getActivity().getLayoutInflater().inflate(R.layout.dialog_list, null);
            dialog.setView(dialogView);
            dialog.setCancelable(true);
            
            Button dialog_button = (Button) dialogView.findViewById(R.id.dialog_button);
            TextView dialog_title = (TextView) dialogView.findViewById(R.id.dialog_title);
            ListView dialog_list = (ListView) dialogView.findViewById(R.id.dialog_list);
            
            dialog_button.setVisibility(View.GONE);
            
            dialog_title.setText(option.getName());
            dialog_list.setAdapter(attrValuesAdapter);
            
            
            final AlertDialog alertDialog = dialog.create();
            alertDialog.show();
            
            
            
            dialog_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    
                    Value selectedValue = attributeValuesList.get(position);
                    String valueID = String.valueOf(selectedValue.getProducts_attributes_id());
                    String valueName = selectedValue.getValue();
                    String valuePrice = selectedValue.getPrice();
                    String valuePrefix = selectedValue.getPricePrefix();
                    
                    attribute_value.setText(valueName
                            + " ("
                            + valuePrefix
                            + ConstantValues.CURRENCY_SYMBOL
                            + valuePrice
                            +")" );
                    
                    
                    // List of selected Attribute Values
                    List<Value> values = new ArrayList<Value>();
                    Value value = new Value();
                    
                    value.setValue(valueName);
                    value.setPrice(valuePrice);
                    value.setPricePrefix(valuePrefix);
                    value.setProducts_attributes_id(Integer.parseInt(valueID));
                    
                    // Add Value to ValuesList
                    values.add(value);
                    
                    
                    // Create new Attribute
                    CartProductAttributes cartProductAttributes = new CartProductAttributes();
                    
                    // Set the Name and Value of Attribute
                    cartProductAttributes.setOption(option);
                    cartProductAttributes.setValues(values);
                    
                    
                    
                    // Check if the Attribute is already exists in the SelectedAttributesList
                    if (getAdapterPosition() < selectedAttributesList.size()) {
                        // Remove the Attribute from SelectedAttributesList
                        selectedAttributesList.remove(getAdapterPosition());
                    }
                    
                    // Add the Attribute to the SelectedAttributesList
                    selectedAttributesList.add(getAdapterPosition(), cartProductAttributes);
                    
                    
                    // Add the Attribute's Price to the AttributePrices
                    attributePrices[getAdapterPosition()] = valuePrefix + valuePrice;
                    
                    // Add the Attribute's ID to the AttributeIDs
                    attributeIDs[getAdapterPosition()] = valueID;
                    
                    // Update Product's final Price in Product_Description Fragment from the method of Product_Description
                    product_description.updateProductPrice();
                    
                    
                    alertDialog.dismiss();
                    
                }
            });
            
        }
        
    }
    
}

