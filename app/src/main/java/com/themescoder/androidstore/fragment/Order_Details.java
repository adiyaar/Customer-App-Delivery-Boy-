package com.themescoder.androidstore.fragment;

import androidx.annotation.Nullable;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kofigyan.stateprogressbar.StateProgressBar;
import com.themescoder.androidstore.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.themescoder.androidstore.activities.MainActivity;
import com.themescoder.androidstore.activities.Order_Tracking_Activity;
import com.themescoder.androidstore.adapters.CouponsAdapter;
import com.themescoder.androidstore.adapters.OrderedProductsListAdapter;
import com.themescoder.androidstore.constant.ConstantValues;
import com.themescoder.androidstore.models.coupons_model.CouponsInfo;
import com.themescoder.androidstore.models.order_model.OrderDetails;
import com.themescoder.androidstore.models.order_model.OrderProducts;
import com.themescoder.androidstore.customs.DividerItemDecoration;

import am.appwise.components.ni.NoInternetDialog;


public class Order_Details extends Fragment {

    View rootView;

    OrderDetails orderDetails;

    CardView buyer_comments_card, seller_comments_card;
    RecyclerView checkout_items_recycler, checkout_coupons_recycler;
    TextView checkout_subtotal, checkout_tax, checkout_shipping, checkout_discount, checkout_total;
    TextView billing_name, billing_street, billing_address, shipping_name, shipping_street, shipping_address;
    TextView order_price, order_products_count, order_date, shipping_method, payment_method, buyer_comments, seller_comments, order_status;
    Button track_order;

    List<CouponsInfo> couponsList;
    List<OrderProducts> orderProductsList;

    CouponsAdapter couponsAdapter;
    OrderedProductsListAdapter orderedProductsAdapter;

    List<String> items;
    public StateProgressBar stateProgressBar;


    public Order_Details(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.order_details, container, false);

        ((MainActivity) getActivity()).toggleNavigaiton(false);

        NoInternetDialog noInternetDialog = new NoInternetDialog.Builder(getContext()).build();
        //noInternetDialog.show();

        // Set the Title of Toolbar
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.order_details));


        // Binding Layout Views
        order_price = (TextView) rootView.findViewById(R.id.order_price);
        order_products_count = (TextView) rootView.findViewById(R.id.order_products_count);
        shipping_method = (TextView) rootView.findViewById(R.id.shipping_method);
        payment_method = (TextView) rootView.findViewById(R.id.payment_method);
        order_status = (TextView) rootView.findViewById(R.id.order_status);
        order_date = (TextView) rootView.findViewById(R.id.order_date);
        checkout_subtotal = (TextView) rootView.findViewById(R.id.checkout_subtotal);
        checkout_tax = (TextView) rootView.findViewById(R.id.checkout_tax);
        checkout_shipping = (TextView) rootView.findViewById(R.id.checkout_shipping);
        checkout_discount = (TextView) rootView.findViewById(R.id.checkout_discount);
        checkout_total = (TextView) rootView.findViewById(R.id.checkout_total);
        billing_name = (TextView) rootView.findViewById(R.id.billing_name);
        billing_address = (TextView) rootView.findViewById(R.id.billing_address);
        billing_street = (TextView) rootView.findViewById(R.id.billing_street);
        shipping_name = (TextView) rootView.findViewById(R.id.shipping_name);
        shipping_address = (TextView) rootView.findViewById(R.id.shipping_address);
        shipping_street = (TextView) rootView.findViewById(R.id.shipping_street);
        buyer_comments = (TextView) rootView.findViewById(R.id.buyer_comments);
        seller_comments = (TextView) rootView.findViewById(R.id.seller_comments);
        buyer_comments_card = (CardView) rootView.findViewById(R.id.buyer_comments_card);
        seller_comments_card = (CardView) rootView.findViewById(R.id.seller_comments_card);
        track_order = rootView.findViewById(R.id.track_order_btn);
        checkout_items_recycler = (RecyclerView) rootView.findViewById(R.id.checkout_items_recycler);
        checkout_coupons_recycler = (RecyclerView) rootView.findViewById(R.id.checkout_coupons_recycler);

        checkout_items_recycler.setNestedScrollingEnabled(false);
        checkout_coupons_recycler.setNestedScrollingEnabled(false);


        // Set Order Details

        couponsList = orderDetails.getCoupons();
        orderProductsList = orderDetails.getProducts();

        double subTotal = 0;
        int noOfProducts = 0;
        for (int i = 0; i < orderProductsList.size(); i++) {
            subTotal += Double.parseDouble(orderProductsList.get(i).getFinalPrice());
            noOfProducts += orderProductsList.get(i).getProductsQuantity();
        }


        String orderPrice = new DecimalFormat("#0.00").format(Double.parseDouble(orderDetails.getOrderPrice())) + " " + orderDetails.getCurrency();
        String Tax = new DecimalFormat("#0.00").format(Double.parseDouble(orderDetails.getTotalTax())) + " " + orderDetails.getCurrency();
        String Shipping = new DecimalFormat("#0.00").format(Double.parseDouble(orderDetails.getShippingCost())) + " " + orderDetails.getCurrency();
        String Discount = new DecimalFormat("#0.00").format(Double.parseDouble(orderDetails.getCouponAmount() + "")) + " " + orderDetails.getCurrency();
        String Subtotal = new DecimalFormat("#0.00").format(subTotal) + " " + orderDetails.getCurrency();
        String Total = new DecimalFormat("#0.00").format(Double.parseDouble(orderDetails.getOrderPrice())) + " " + orderDetails.getCurrency();


        order_price.setText(orderPrice);
        order_products_count.setText(String.valueOf(noOfProducts));
        shipping_method.setText(orderDetails.getShippingMethod());
        payment_method.setText(orderDetails.getPaymentMethod());

        // calling the status progressbar
        orderStatusProgressBar();

        order_status.setText(orderDetails.getOrdersStatus());
        order_date.setText(orderDetails.getDatePurchased());

        checkout_tax.setText(Tax);
        checkout_shipping.setText(Shipping);
        checkout_discount.setText(Discount);
        checkout_subtotal.setText(Subtotal);
        checkout_total.setText(Total);

        billing_name.setText(orderDetails.getBillingName());
        billing_address.setText(orderDetails.getBillingCity());
        billing_street.setText(orderDetails.getBillingStreetAddress());
        shipping_name.setText(orderDetails.getDeliveryName());
        shipping_address.setText(orderDetails.getDeliveryCity());
        shipping_street.setText(orderDetails.getDeliveryStreetAddress());


        if (!TextUtils.isEmpty(orderDetails.getCustomerComments())) {
            buyer_comments_card.setVisibility(View.VISIBLE);
            buyer_comments.setText(orderDetails.getCustomerComments());
        } else {
            buyer_comments_card.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(orderDetails.getAdminComments())) {
            seller_comments_card.setVisibility(View.VISIBLE);
            seller_comments.setText(orderDetails.getAdminComments());
        } else {
            seller_comments_card.setVisibility(View.GONE);
        }

        track_order.setVisibility(View.GONE);

        couponsAdapter = new CouponsAdapter(getContext(), couponsList, false, null, orderDetails.getCurrency());

        checkout_coupons_recycler.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        checkout_coupons_recycler.setAdapter(couponsAdapter);


        orderedProductsAdapter = new OrderedProductsListAdapter(getContext(), orderProductsList, orderDetails.getCurrency());

        checkout_items_recycler.setAdapter(orderedProductsAdapter);
        checkout_items_recycler.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        checkout_items_recycler.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        track_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (orderDetails.getDeliveryboyInfo().size() > 0 && orderDetails.getOrdersStatusId() == 5) {
                    Intent intent = new Intent(getActivity(), Order_Tracking_Activity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("Customer_Delivery_Lat", orderDetails.getDeliveryLatitude());
                    bundle.putString("Customer_Delivery_Lng", orderDetails.getDeliveryLongitude());
                    bundle.putString("Delivery_Boy_Pincode", orderDetails.getDeliveryboyInfo().get(0).getDeliveryboyId()+"");
                    bundle.putString("Delivery_Boy_Name", orderDetails.getDeliveryboyInfo().get(0).getFirstName() + " " + orderDetails.getDeliveryboyInfo().get(0).getLastName());
                    intent.putExtras(bundle);
                    getContext().startActivity(intent);
                }
            }
        });

        if (orderDetails.getDeliveryboyInfo().size() > 0 && orderDetails.getOrdersStatusId() == 5) {
            track_order.setVisibility(View.VISIBLE);
        }


        return rootView;

    }


    private void orderStatusProgressBar() {

        // this Items will all to Order status progress bar
        items = new ArrayList<>();
        items.add("Pending");
        items.add("Confirm");
        items.add("Out For Delivery");
        items.add("Delivered");

        String[] arraystr = new String[items.size()];
        stateProgressBar = rootView.findViewById(R.id.stateProgress);
        stateProgressBar.setStateDescriptionData(items.toArray(arraystr));


        // State Progress bar.
        int statusId = Integer.parseInt(orderDetails.getOrdersStatusId() + "");

        switch (statusId) {
            case 1:   // Pending.
                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
                break;
            case 8:   // Confirmed.
                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                break;
            case 5:   // Out for delivery.
                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                break;
            case 2:   // completed.
                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);
                break;
            case 3:   // Canceled.
                stateProgressBar.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

}



