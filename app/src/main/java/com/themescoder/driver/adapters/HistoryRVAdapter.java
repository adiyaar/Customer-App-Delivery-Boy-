package com.themescoder.driver.adapters;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.themescoder.driver.api.ServerData;
import com.themescoder.driver.Listeners.HistoryRVListener;
import com.themescoder.driver.OrderDetailsActivity;
import com.themescoder.driver.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HistoryRVAdapter extends RecyclerView.Adapter<HistoryRVAdapter.MyViewHolder> {

    private Context context;
    private HistoryRVListener listener;
    private List<String> items;

    public HistoryRVAdapter(Context context, List<String> items, HistoryRVListener listener) {
        this.context = context;
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
                intent.putExtra("IS_REDIRECTED_FROM_HISTORY", true);
                intent.putExtra("ITEM_CLICKED_POSITION", myViewHolder.getAdapterPosition());
                context.startActivity(intent);
            }
        });

        // Bind with all items.
        myViewHolder.customerName.setText(ServerData.historyObjectsList.get(i).getCustomersName());
        myViewHolder.orderStatus.setText(ServerData.historyObjectsList.get(i).getOrdersStatus());
        myViewHolder.orderDate.setText(ServerData.historyObjectsList.get(i).getDatePurchased().split(" ")[0]);
        myViewHolder.orderID.setText("#"+ ServerData.historyObjectsList.get(i).getOrdersId());
        myViewHolder.totalPrice.setText(ServerData.historyObjectsList.get(i).getCurrency()+""+ServerData.historyObjectsList.get(i).getOrderPrice());
        myViewHolder.orderMethod.setText((ServerData.historyObjectsList.get(i).getPaymentMethod().equalsIgnoreCase("cash on delivery") ? "COD" : ServerData.historyObjectsList.get(i).getPaymentMethod()));
        myViewHolder.address.setText(ServerData.historyObjectsList.get(i).getCustomersStreetAddress());

        myViewHolder.callToCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call to customer

            }
        });
        myViewHolder.navigateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Address.

            }
        });

    }

    @Override
    public int getItemCount() {
        return ServerData.historyObjectsList.size();
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
             return new SimpleDateFormat("K:mm:ss").format(dateObj);

        } catch (final ParseException e) {
            return "";
        }
    }
}
