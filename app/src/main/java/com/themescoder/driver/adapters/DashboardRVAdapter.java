package com.themescoder.driver.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.themescoder.driver.MapActivity;
import com.themescoder.driver.api.ServerData;
import com.themescoder.driver.Listeners.DashboardRVListener;
import com.themescoder.driver.OrderDetailsActivity;
import com.themescoder.driver.R;
import com.themescoder.driver.utils.Utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DashboardRVAdapter extends RecyclerView.Adapter<DashboardRVAdapter.MyViewHolder> {

    private Activity activity;
    private DashboardRVListener listener;
    private List<String> items;
    private Context context;

    public DashboardRVAdapter(Activity activity, List<String> items, DashboardRVListener listener) {
        this.activity = activity;
        this.items = items;
        this.listener = listener;
    }

    /*
    * 3 OverRidden Methods
    * */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        final View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_order, viewGroup, false);
        final MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        myViewHolder.detailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderDetailsActivity.class);
                intent.putExtra("IS_REDIRECTED_FROM_HISTORY", false);
                intent.putExtra("ITEM_CLICKED_POSITION", myViewHolder.getAdapterPosition());
                context.startActivity(intent);
            }
        });

        // Bind with all items.
        myViewHolder.customerName.setText(ServerData.dashboardObjectsList.get(i).getCustomersName());
        myViewHolder.orderStatus.setText(ServerData.dashboardObjectsList.get(i).getOrdersStatus());
        myViewHolder.orderDate.setText(ServerData.dashboardObjectsList.get(i).getDatePurchased().split(" ")[0]);
        myViewHolder.orderID.setText("#"+ ServerData.dashboardObjectsList.get(i).getOrdersId());
        myViewHolder.totalPrice.setText(ServerData.dashboardObjectsList.get(i).getCurrency()+""+ServerData.dashboardObjectsList.get(i).getOrderPrice());
        myViewHolder.orderMethod.setText((ServerData.dashboardObjectsList.get(i).getPaymentMethod().equalsIgnoreCase("cash on delivery") ? "COD" : ServerData.dashboardObjectsList.get(i).getPaymentMethod()));
        myViewHolder.address.setText(ServerData.dashboardObjectsList.get(i).getCustomersStreetAddress());

        myViewHolder.callToCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call to customer
                Utilities.doMakeACall(activity, ServerData.dashboardObjectsList.get(i).getBillingPhone());
            }
        });
        myViewHolder.navigateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Address.
                if (ServerData.settingsData.getMapType().equalsIgnoreCase("internal")) {

                    Bundle bundle = new Bundle();
                    bundle.putString("customer_latitude", ServerData.dashboardObjectsList.get(i).getLatitude());
                    bundle.putString("customer_longitude", ServerData.dashboardObjectsList.get(i).getLongitude());
                    bundle.putString("customer_name", ServerData.dashboardObjectsList.get(i).getDeliveryName());
                    Intent intent = new Intent(context, MapActivity.class);
                    intent.putExtras(bundle);
                    context.startActivity(intent);

                } else {

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    String geoUri = "http://maps.google.com/maps?q=loc:" + ServerData.dashboardObjectsList.get(i).getLatitude() + "," + ServerData.dashboardObjectsList.get(i).getLongitude() + " (" + ServerData.dashboardObjectsList.get(i).getDeliveryName() + ")";
                    intent.setData(Uri.parse(geoUri));
                    if (intent.resolveActivity(context.getPackageManager()) != null) {
                        context.startActivity(intent);
                    } else {
                        Toast.makeText(context, "could not find the customer location", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        // State Progress bar.
/*
        int statusId = ServerData.dashboardObjectsList.get(i).getOrdersStatusId();

        switch (statusId){
            case 7:   // Pending.
                myViewHolder.stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
                break;
            case 17:   // Confirmed.
                myViewHolder.stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                break;
            case 5:   // Out for delivery.
                myViewHolder.stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                break;
            case 6:   // Delivered.
                myViewHolder.stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);
                break;
            case 2:   // Completed.
                myViewHolder.stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);
                break;
            case 3:   // Canceled.
                myViewHolder.stateProgressBar.setVisibility(View.GONE);
                break;
            default:
                break;
        }
*/

        //
    }

    @Override
    public int getItemCount() {
        return ServerData.dashboardObjectsList.size();
    }



    /*
    * Custom View Holder
    * */
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public AppCompatButton detailsButton;

//        Elemetns;
        public TextView customerName;
        public TextView orderStatus;
        public TextView callToCustomer;
        public TextView orderDate;
        public TextView orderID;
        public TextView totalPrice;
        public TextView orderMethod;
        public TextView address;
        public ImageView navigateBtn;

        public MyViewHolder(View itemView) {
            super(itemView);
/*
            String[] arraystr = new String[items.size()];
            stateProgressBar = itemView.findViewById(R.id.stateProgress);
            stateProgressBar.setStateDescriptionData(items.toArray(arraystr));
*/

            // Elements.
            customerName = itemView.findViewById(R.id.customerName);
            orderStatus = itemView.findViewById(R.id.status);
            callToCustomer = itemView.findViewById(R.id.callToCustomer);
            orderDate = itemView.findViewById(R.id.placedOn);
            orderID = itemView.findViewById(R.id.orderId);
            totalPrice = itemView.findViewById(R.id.orderPrice);
            orderMethod = itemView.findViewById(R.id.orderMethod);
            address = itemView.findViewById(R.id.detailedAddress);
            navigateBtn = itemView.findViewById(R.id.navigationBtn);
            detailsButton = itemView.findViewById(R.id.detailsButton);


        }
    }


    private String ConvertTime(String time) {

        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("H:mm:ss");
            final Date dateObj = sdf.parse(time);
            return new SimpleDateFormat("H:mm:ss").format(dateObj);

        } catch (final ParseException e) {
            return "";
        }
    }
}
