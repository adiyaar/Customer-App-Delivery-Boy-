package com.themescoder.androidstore.adapters;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.themescoder.androidstore.activities.MainActivity;
import com.themescoder.androidstore.R;

import java.text.DecimalFormat;
import java.util.List;

import com.themescoder.androidstore.constant.ConstantValues;
import com.themescoder.androidstore.fragment.My_Orders;
import com.themescoder.androidstore.fragment.Order_Details;
import com.themescoder.androidstore.models.order_model.OrderDetails;


/**
 * OrdersListAdapter is the adapter class of RecyclerView holding List of Orders in My_Orders
 **/

public class OrdersListAdapter extends RecyclerView.Adapter<OrdersListAdapter.MyViewHolder> {

    Context context;
    String customerID;
    List<OrderDetails> ordersList;
    My_Orders fragment;
    
    
    //declare interface
    private OnItemClicked onClick;
    
    //make interface like this
    public interface OnItemClicked {
        void onItemClick(int position);
    }
    
    
    
    public OrdersListAdapter(Context context, String customerID, List<OrderDetails> ordersList, My_Orders fragment) {
        this.context = context;
        this.customerID = customerID;
        this.ordersList = ordersList;
        this.fragment = fragment;
    }



    //********** Called to Inflate a Layout from XML and then return the Holder *********//

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_card_orders, parent, false);

        return new MyViewHolder(itemView);
    }



    //********** Called by RecyclerView to display the Data at the specified Position *********//

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        
        // Get the data model based on Position
        final OrderDetails orderDetails = ordersList.get(position);
    
        int noOfProducts = 0;
        for (int i=0;  i<orderDetails.getProducts().size();  i++) {
            // Count no of Products
            noOfProducts += orderDetails.getProducts().get(i).getProductsQuantity();
        }

        holder.order_id.setText(String.valueOf(orderDetails.getOrdersId()));
        holder.order_status.setText(orderDetails.getOrdersStatus());
        holder.order_price.setText(new DecimalFormat("#0.00").format(Double.valueOf(orderDetails.getOrderPrice())) + " " + orderDetails.getCurrency());
        holder.order_date.setText(orderDetails.getDatePurchased());
        holder.order_product_count.setText(String.valueOf(noOfProducts));
    

        // Check Order's status
/*
        if (orderDetails.getOrdersStatus().equalsIgnoreCase("Pending")) {
            holder.order_status.setTextColor(ContextCompat.getColor(context, R.color.colorAccentBlue));
            holder.cancle_order_btn.setVisibility(View.VISIBLE);
        } else if (orderDetails.getOrdersStatus().equalsIgnoreCase("Completed")) {
            holder.order_status.setTextColor(ContextCompat.getColor(context, R.color.colorAccentGreen));
            holder.cancle_order_btn.setVisibility(View.GONE);
        }else if (orderDetails.getOrdersStatus().equalsIgnoreCase("Cancelled")) {
            holder.order_status.setTextColor(ContextCompat.getColor(context, R.color.colorAccentRed));
            holder.cancle_order_btn.setVisibility(View.GONE);
        } else {
            holder.order_status.setTextColor(ContextCompat.getColor(context, R.color.colorAccentRed));
            holder.cancle_order_btn.setVisibility(View.VISIBLE);
        }
*/


/*
        holder.cancle_order_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(context)
                        .setTitle(context.getString(R.string.order_dialog_prompt))
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                
                                dialogInterface.dismiss();
                                
                            }})
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                fragment.RequestMyOrdersCancel(Integer.parseInt(orderDetails.getOrdersId()+""));
                                // notifyItemChanged(fragment);
                                dialogInterface.dismiss();
                            }
    
                           
                        })
                        .show();
            }
        });
*/

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Order Info

                Fragment fragment = new Order_Details(orderDetails);
                FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.main_fragment, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null).commit();
            }
        });

    }


    //********** Returns the total number of items in the data set *********//

    @Override
    public int getItemCount() {
        return ordersList.size();
    }



    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView order_id, order_product_count, order_status, order_price, order_date;


        public MyViewHolder(final View itemView) {
            super(itemView);

            order_id = (TextView) itemView.findViewById(R.id.order_id);
            order_product_count = (TextView) itemView.findViewById(R.id.order_products_count);
            order_status = (TextView) itemView.findViewById(R.id.order_status);
            order_price = (TextView) itemView.findViewById(R.id.order_price);
            order_date = (TextView) itemView.findViewById(R.id.order_date);

        }
    }
    
    
    public void setOnClick(OnItemClicked onClick)
    {
        this.onClick=onClick;
    }
    
    private void notifyItemChanged(My_Orders fragment) {
        
        int currPosition = ordersList.indexOf(fragment);
        notifyItemChanged(currPosition);
    }
   
}

