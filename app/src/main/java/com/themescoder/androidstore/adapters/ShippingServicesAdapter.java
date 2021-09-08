package com.themescoder.androidstore.adapters;


import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.themescoder.androidstore.R;

import java.util.List;

import com.themescoder.androidstore.constant.ConstantValues;
import com.themescoder.androidstore.fragment.Shipping_Methods;
import com.themescoder.androidstore.models.shipping_model.ShippingService;


/**
 * ShippingServicesAdapter is the adapter class of RecyclerView holding List of ShippingServices in Shipping_Methods
 **/

public class ShippingServicesAdapter extends RecyclerView.Adapter<ShippingServicesAdapter.MyViewHolder> {

    Context context;
    private Shipping_Methods shipping_methods_fragment;
    private List<ShippingService> shippingServicesList;

    
    public ShippingServicesAdapter(Context context, List<ShippingService> shippingServicesList, Shipping_Methods shipping_methods_fragment) {
        this.context = context;
        this.shippingServicesList = shippingServicesList;
        this.shipping_methods_fragment = shipping_methods_fragment;
    }
    
    
    
    //********** Called to Inflate a Layout from XML and then return the Holder *********//
    
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_shipping_methods, parent, false);
    
        return new MyViewHolder(itemView);
    }
    
    
    
    //********** Called by RecyclerView to display the Data at the specified Position *********//
    
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
    
        ShippingService shippingService = shippingServicesList.get(position);
        
        holder.shippingMethodName.setText(shippingService.getName());
        holder.shippingMethodCost.setText(ConstantValues.CURRENCY_SYMBOL + shippingService.getRate());
    
    
        if (shippingService.getName().equalsIgnoreCase(shipping_methods_fragment.getSelectedShippingService().getName())) {
            
            holder.shippingMethodSelector.setChecked(true);
            shipping_methods_fragment.setLastChecked_RB(holder.shippingMethodSelector);
        
        } else {
            holder.shippingMethodSelector.setChecked(false);
        }
        
    }
    
    
    
    //********** Returns the total number of items in the data set *********//
    
    @Override
    public int getItemCount() {
        return shippingServicesList.size();
    }
    
    
    
    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/
    
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        
        LinearLayout shipping_method;
        RadioButton shippingMethodSelector;
        TextView shippingMethodName, shippingMethodCost;
        
        
        public MyViewHolder(final View itemView) {
            super(itemView);
            shipping_method = (LinearLayout) itemView.findViewById(R.id.shipping_method);
            shippingMethodName = (TextView) itemView.findViewById(R.id.shipping_method_name);
            shippingMethodCost = (TextView) itemView.findViewById(R.id.shipping_method_cost);
            shippingMethodSelector = (RadioButton) itemView.findViewById(R.id.shipping_method_selector);
    
            shipping_method.setOnClickListener(this);
        }
        
        
        // Handle Click Listener on ShippingMethod
        @Override
        public void onClick(View view) {
            
            RadioButton currentChecked_RB = (RadioButton) view.findViewById(R.id.shipping_method_selector);
            ShippingService selectedShippingMethod = shippingServicesList.get(getAdapterPosition());
    
    
            // UnCheck last Checked CheckBox
            if (shipping_methods_fragment.getLastChecked_RB() != null) {
                shipping_methods_fragment.getLastChecked_RB().setChecked(false);
            }
    
            currentChecked_RB.setChecked(true);
            shipping_methods_fragment.setLastChecked_RB(currentChecked_RB);
    
    
            shipping_methods_fragment.setSelectedShippingService(selectedShippingMethod);
        }
        
    }
    
    
}

