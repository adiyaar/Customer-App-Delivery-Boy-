package com.themescoder.androidstore.adapters;


import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.themescoder.androidstore.R;
import com.themescoder.androidstore.models.payment_model.PaymentMethodsInfo;
import java.util.List;


/**
 * PaymentMethodAdapter is the adapter class of the Dialog holding List of PaymentMethods
 **/

public class PaymentMethodAdapter extends BaseAdapter {

    private Context context;
    private List<PaymentMethodsInfo> paymentMethods;
    
    private LayoutInflater layoutInflater;


    public PaymentMethodAdapter(Context context, List<PaymentMethodsInfo> paymentMethods) {
        this.context = context;
        this.paymentMethods = paymentMethods;
    
        layoutInflater = LayoutInflater.from(context);
    }
    
    
    //********** Returns the total number of items in the data set represented by this Adapter *********//
    
    @Override
    public int getCount() {
        return paymentMethods.size();
    }
    
    
    //********** Returns the item associated with the specified position in the data set *********//
    
    @Override
    public PaymentMethodsInfo getItem(int position) {
        return paymentMethods.get(position);
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
        PaymentMethodsInfo paymentMethod = paymentMethods.get(position);
    
    
        final ViewHolder holder;
        
        // Check if an existing View is being Reused, otherwise Inflate the View with custom Layout
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.layout_attribute_values_dialog, parent, false);
            
            holder = new ViewHolder();
    
            holder.payment_method_name = (TextView) convertView.findViewById(R.id.attribute_value_name);
            holder.payment_method_price = (TextView) convertView.findViewById(R.id.attribute_value_price);
            holder.payment_method_prefix = (TextView) convertView.findViewById(R.id.attribute_value_prefix);
    
            holder.payment_method_price.setVisibility(View.GONE);
            holder.payment_method_prefix.setVisibility(View.GONE);
            
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        
        // Populate the data into the Template View
        holder.payment_method_name.setText(paymentMethod.getName());

        
        return convertView;
    }
    
    
    
    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/
    
    static class ViewHolder {
        private TextView payment_method_name, payment_method_prefix, payment_method_price;
        
    }
    
}

