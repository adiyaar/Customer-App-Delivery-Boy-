package com.themescoder.driver;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.themescoder.driver.R;
import com.themescoder.driver.adapters.ExpandableListAdapter;
import com.themescoder.driver.api.RetrofitClient;
import com.themescoder.driver.api.ServerData;
import com.themescoder.driver.custom.NonScrollExpandableListView;
import com.themescoder.driver.dialogs.ChangeStatusDialog;
import com.themescoder.driver.dialogs.CommentsDialog;
import com.themescoder.driver.models.MenufactureDetails;
import com.themescoder.driver.models.OrderDetails;
import com.themescoder.driver.models.StatusResponse;
import com.themescoder.driver.utils.DialogUtils;
import com.themescoder.driver.utils.OrderStatuses;
import com.themescoder.driver.utils.Utilities;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.expandablelist)
    NonScrollExpandableListView listView;

    // Sections Layout.
    @BindView(R.id.deliveryAddressLayout)
    LinearLayout deliveryAddressLayout;
    @BindView(R.id.deliveryAddressLayoutCopy)
    LinearLayout deliveryAddressLayoutCopy;

    //    StartHeader
    @BindView(R.id.no_of_products)
    TextView no_of_products;
    @BindView(R.id.total_price)
    TextView total_price;
    @BindView(R.id.order_status)
    TextView order_status;
    @BindView(R.id.order_date)
    TextView order_date;
    @BindView(R.id.order_time)
    TextView order_time;
    @BindView(R.id.time_slot)
    TextView time_slot;

    //   Billing.
    @BindView(R.id.billingName)
    TextView billingName;
    @BindView(R.id.billingCompany)
    TextView billingCompany;
    @BindView(R.id.billingPhone)
    TextView billingPhone;
    @BindView(R.id.billingEmail)
    TextView billingEmail;
    @BindView(R.id.billingPostCode)
    TextView billingPostCode;
    @BindView(R.id.billingStreetAddress)
    TextView billingStreetAddress;
    @BindView(R.id.billingCity)
    TextView billingCity;
    @BindView(R.id.billingState)
    TextView billingState;
    @BindView(R.id.billingCountry)
    TextView billingCountry;

    //   Delivery
    @BindView(R.id.deliveryName)
    TextView deliveryName;
    @BindView(R.id.deliveryCompany)
    TextView deliveryCompany;
    @BindView(R.id.deliveryPhone)
    TextView deliveryPhone;
    @BindView(R.id.deliveryEmail)
    TextView deliveryEmail;
    @BindView(R.id.deliveryPostCode)
    TextView deliveryPostCode;
    @BindView(R.id.deliveryStreetAddress)
    TextView deliveryStreetAddress;
    @BindView(R.id.deliveryCity)
    TextView deliveryCity;
    @BindView(R.id.deliveryState)
    TextView deliveryState;
    @BindView(R.id.deliveryCountry)
    TextView deliveryCountry;

    //    ShippingMetgid
    @BindView(R.id.shippingMethod)
    TextView shippingMethod;

    //    Total
    @BindView(R.id.totalSubtotal)
    TextView totalSubtotal;
    @BindView(R.id.totalTax)
    TextView totalTax;
    @BindView(R.id.totalShipping)
    TextView totalShipping;
    @BindView(R.id.totalDiscount)
    TextView totalDiscount;
    @BindView(R.id.totalTotal)
    TextView totalTotal;

    //    PaymentMethod
    @BindView(R.id.paymentMethod)
    TextView paymentMethod;

    // Comments.
    @BindView(R.id.customerComments)
    TextView customerComments;
    @BindView(R.id.adminComments)
    TextView adminComments;

    @BindView(R.id.orderStatusStrig)
    TextView orderStatusString;

    // Buttons
    @BindView(R.id.changeStatusButton)
    AppCompatButton changeStatusButton;
    @BindView(R.id.cancelOrderButton)
    AppCompatButton cancelOrderButton;

    @BindView(R.id.contactButtonBilling)
    AppCompatButton contactButtonBilling;
    @BindView(R.id.navigateButtonBilling)
    AppCompatButton navigateButtonBilling;

    @BindView(R.id.contactButtonDelivery)
    AppCompatButton contactButtonDelivery;
    @BindView(R.id.navigateButtonDelivery)
    AppCompatButton navigateButtonDelivery;


    private ExpandableListAdapter adapter;
    private int clickedPosition;

    private List<MenufactureDetails> orderObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        ButterKnife.bind(this);

        clickedPosition = getIntent().getExtras().getInt("ITEM_CLICKED_POSITION");

        //Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        orderObj = ServerData.dashboardObjectsList;
        if (getIntent().getExtras().getBoolean("IS_REDIRECTED_FROM_HISTORY", false)) {
            findViewById(R.id.buttonLayout).setVisibility(View.GONE);
            findViewById(R.id.bottom_most_layout).setVisibility(View.GONE);
            orderObj = ServerData.historyObjectsList;
        }

        getSupportActionBar().setTitle("Order ID: " + orderObj.get(clickedPosition).getOrdersId());

        no_of_products.setText(orderObj.size() + "");
        total_price.setText(orderObj.get(clickedPosition).getCurrency() + " " + orderObj.get(clickedPosition).getOrderPrice());
        order_status.setText(orderObj.get(clickedPosition).getOrdersStatus());
        order_date.setText(orderObj.get(clickedPosition).getDatePurchased().split(" ")[0]);
        order_time.setText(orderObj.get(clickedPosition).getDatePurchased().split(" ")[1]);
        time_slot.setText(orderObj.get(clickedPosition).getDeliveryTime());
        String mBillingName, mBillingCompany, mBillingPhone, mBillingEmail, mBillingPostCode, mBillingStreetAddress, mBillingCity, mBillingState, mBillingCountry;
        String mDeliveryName, mDeliveryCompany, mDeliveryPhone, mDeliveryEmail, mDeliveryPostCode, mDeliveryStreetAddress, mDeliveryCity, mDeliveryState, mDeliveryCountry;

        mBillingName = orderObj.get(clickedPosition).getBillingName();
        if (orderObj.get(clickedPosition).getBillingCompany() != null) {
            mBillingCompany = orderObj.get(clickedPosition).getBillingCompany().toString();
        } else {
            mBillingCompany = "Null";
        }
        mBillingPhone = orderObj.get(clickedPosition).getBillingPhone();
        mBillingEmail = orderObj.get(clickedPosition).getEmail();
        mBillingPostCode = orderObj.get(clickedPosition).getBillingPostcode();
        mBillingStreetAddress = orderObj.get(clickedPosition).getBillingStreetAddress();
        mBillingCity = orderObj.get(clickedPosition).getBillingCity();
        mBillingState = orderObj.get(clickedPosition).getBillingState();
        mBillingCountry = orderObj.get(clickedPosition).getBillingCountry();

        mDeliveryName = orderObj.get(clickedPosition).getDeliveryName();
        if (orderObj.get(clickedPosition).getDeliveryCompany() != null) {
            mDeliveryCompany = orderObj.get(clickedPosition).getDeliveryCompany().toString();
        } else {
            mDeliveryCompany = "Null";
        }
        mDeliveryPhone = orderObj.get(clickedPosition).getDeliveryPhone();
        mDeliveryEmail = orderObj.get(clickedPosition).getEmail();
        mDeliveryPostCode = orderObj.get(clickedPosition).getDeliveryPostcode();
        mDeliveryStreetAddress = orderObj.get(clickedPosition).getDeliveryStreetAddress();
        mDeliveryCity = orderObj.get(clickedPosition).getDeliveryCity();
        mDeliveryState = orderObj.get(clickedPosition).getDeliveryState();
        mDeliveryCountry = orderObj.get(clickedPosition).getDeliveryCountry();

        if (mBillingName.equals(mDeliveryName)
                && mBillingCompany.equals(mDeliveryCompany)
                && mBillingPhone.equals(mDeliveryPhone)
                && mBillingEmail.equals(mDeliveryEmail)
                && mBillingPostCode.equals(mDeliveryPostCode)
                && mBillingStreetAddress.equals(mDeliveryStreetAddress)
                && mBillingCity.equals(mDeliveryCity)
                && mBillingState.equals(mDeliveryState)
                && mBillingCountry.equals(mDeliveryCountry)) {
            deliveryAddressLayout.setVisibility(View.GONE);
            deliveryAddressLayoutCopy.setVisibility(View.VISIBLE);
        }

        billingName.setText(mBillingName);
        billingCompany.setText(mBillingCompany);
        billingPhone.setText(mBillingPhone);
        billingEmail.setText(mBillingEmail);
        billingPostCode.setText(mBillingPostCode);
        billingStreetAddress.setText(mBillingStreetAddress);
        billingCity.setText(mBillingCity);
        billingState.setText(mBillingState);
        billingCountry.setText(mBillingCountry);

        deliveryName.setText(mDeliveryName);
        deliveryCompany.setText(mDeliveryCompany);
        deliveryPhone.setText(mDeliveryPhone);
        deliveryEmail.setText(mDeliveryEmail);
        deliveryPostCode.setText(mDeliveryPostCode);
        deliveryStreetAddress.setText(mDeliveryStreetAddress);
        deliveryCity.setText(mDeliveryCity);
        deliveryState.setText(mDeliveryState);
        deliveryCountry.setText(mDeliveryCountry);

        shippingMethod.setText(orderObj.get(clickedPosition).getShippingMethod());

        double temp_total = Double.parseDouble(orderObj.get(clickedPosition).getOrderPrice());
        double temp_shipping = Double.parseDouble(orderObj.get(clickedPosition).getShippingCost());
        double temp_tax = Double.parseDouble(orderObj.get(clickedPosition).getTotalTax());
        double temp_subtotal = temp_total - temp_tax - temp_shipping;
        totalTax.setText(orderObj.get(clickedPosition).getCurrency() + " " + orderObj.get(clickedPosition).getTotalTax());
        totalShipping.setText(orderObj.get(clickedPosition).getCurrency() + " " + orderObj.get(clickedPosition).getShippingCost());
        totalTotal.setText(orderObj.get(clickedPosition).getCurrency() + " " + orderObj.get(clickedPosition).getOrderPrice());
        totalSubtotal.setText(orderObj.get(clickedPosition).getCurrency() + " " + temp_subtotal);
        totalDiscount.setText(orderObj.get(clickedPosition).getCurrency() + " 0.00");

        paymentMethod.setText(orderObj.get(clickedPosition).getPaymentMethod());

        customerComments.setText(orderObj.get(clickedPosition).getCustomerComments());
        adminComments.setText(orderObj.get(clickedPosition).getAdminComments());

        if (orderObj.get(clickedPosition).getOrdersStatusId() == OrderStatuses.DISPATCHED) {
            findViewById(R.id.buttonLayout).setVisibility(View.VISIBLE);
            findViewById(R.id.bottom_most_layout).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.buttonLayout).setVisibility(View.GONE);
            findViewById(R.id.bottom_most_layout).setVisibility(View.GONE);
        }

        changeStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ChangeStatusDialog dialog3 = new ChangeStatusDialog(OrderDetailsActivity.this);
                dialog3.show();
                dialog3.submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog3.editText.getText().toString().trim().equals(ServerData.currentDriver.getData().get(0).getPassword() + "")) {
                            dialog3.dismiss();
                            final CommentsDialog dialog = new CommentsDialog(OrderDetailsActivity.this);
                            dialog.show();
                            dialog.submit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (callToChangeOrderStatus(OrderStatuses.DELIVERED + "", dialog.otpEditText.getText().toString(), true)) {  // 2 for completed.
                                        dialog.dismiss();
                                        OrderDetailsActivity.this.finish();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(OrderDetailsActivity.this, "PinCode does not match.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        cancelOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ChangeStatusDialog dialog = new ChangeStatusDialog(OrderDetailsActivity.this);
                dialog.show();
                dialog.submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog.editText.getText().toString().trim().equals(ServerData.currentDriver.getData().get(0).getPassword() + "")) {
                            dialog.dismiss();
                            if (callToChangeOrderStatus(OrderStatuses.CANCEL + "", "", true)) {  // 3 for Canceled.
                                OrderDetailsActivity.this.finish();
                            }
                        } else {
                            Toast.makeText(OrderDetailsActivity.this, "PinCode does not match.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        contactButtonBilling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilities.doMakeACall(OrderDetailsActivity.this, orderObj.get(clickedPosition).getBillingPhone());
            }
        });

        contactButtonDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilities.doMakeACall(OrderDetailsActivity.this, orderObj.get(clickedPosition).getBillingPhone());
            }
        });

        navigateButtonBilling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ServerData.settingsData.getMapType().equalsIgnoreCase("internal")) {

                    Bundle bundle = new Bundle();
                    bundle.putString("customer_latitude", orderObj.get(clickedPosition).getLatitude());
                    bundle.putString("customer_longitude", orderObj.get(clickedPosition).getLongitude());
                    bundle.putString("customer_name", orderObj.get(clickedPosition).getDeliveryName());
                    Intent intent = new Intent(OrderDetailsActivity.this, MapActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);

                } else {

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    String geoUri = "http://maps.google.com/maps?q=loc:" + orderObj.get(clickedPosition).getLatitude() + "," + orderObj.get(clickedPosition).getLongitude() + " (" + orderObj.get(clickedPosition).getDeliveryName() + ")";
                    intent.setData(Uri.parse(geoUri));
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    } else {
                        Toast.makeText(OrderDetailsActivity.this, "could not find the customer location", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        navigateButtonDelivery.setVisibility(View.GONE); // Navigation options is only avaiable for one address.

        // Dynamic Products.
        final List<String> manufactures = new ArrayList<>();
        for (int i = 0; i < orderObj.get(clickedPosition).getData().size(); i++) {
            if (orderObj.get(clickedPosition).getData().get(i).getShopName() == null) {
                manufactures.add("UnCategorise" +
                        " -- " + "EMPTY" + "EMPTY");
            } else {
                manufactures.add(orderObj.get(clickedPosition).getData().get(i).getShopName() +
                        " -- " + orderObj.get(clickedPosition).getData().get(i).getVendorsPhone() +
                        " -- " + orderObj.get(clickedPosition).getData().get(i).getVendorsLatitude() +
                        "," + orderObj.get(clickedPosition).getData().get(i).getVendorsLongitude());
            }
        }
        // Clear Rpeating Elements.
        Set<String> set = new HashSet<>(manufactures);
        manufactures.clear();
        manufactures.addAll(set);
        // Main Hash Map.
        HashMap<String, List<OrderDetails>> products = new HashMap<>();
        for (int j = 0; j < manufactures.size(); j++) {
            List<OrderDetails> order = new ArrayList<>();
            for (int i = 0; i < orderObj.get(clickedPosition).getData().size(); i++) {
                if (manufactures.get(j).split(" -- ")[0].equalsIgnoreCase(orderObj.get(clickedPosition).getData().get(i).getShopName())) {
                    order.add(orderObj.get(clickedPosition).getData().get(i));
                }
                if (manufactures.get(j).split(" -- ")[0].equals("UnCategorise")) {
                    if (orderObj.get(clickedPosition).getData().get(i).getShopName() == null) {
                        order.add(orderObj.get(clickedPosition).getData().get(i));
                    }
                }
            }
            products.put(manufactures.get(j), order);
        }


/*
        final List<String> headers = new ArrayList<>();
        headers.add("One");
        headers.add("two");
        headers.add("three");
        List<String> details = new ArrayList<>();
        details.add("one");
        details.add("two");
        HashMap<String, List<String>> productdetails = new HashMap<>();
        productdetails.put(headers.get(0), details);
        productdetails.put(headers.get(1), details);
        productdetails.put(headers.get(2), details);
*/


        adapter = new ExpandableListAdapter(OrderDetailsActivity.this, manufactures, products, orderObj.get(clickedPosition).getCurrency(), listView);
        listView.setAdapter(adapter);

        for (int i = 0; i < manufactures.size(); i++) {
            listView.expandGroup(i);
        }
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                return true;
            }
        });
        NestedScrollView scrollView = findViewById(R.id.mainScroll);
        scrollView.smoothScrollTo(0, scrollView.getTop());

    }


    private boolean callToChangeOrderStatus(String statusId, String comments, final boolean islastStep) {
        Call<StatusResponse> call = RetrofitClient.getInstance()
                .changeOrderStatus(orderObj.get(clickedPosition).getOrdersId().toString(),
                        statusId,
                        comments,
                        ServerData.currentDriver.getData().get(0).getPassword() + "");
        DialogUtils.showProgressDialog(OrderDetailsActivity.this);
        final boolean[] temp = new boolean[1];
        temp[0] = true;
        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                if (!islastStep) {
                    DialogUtils.hideProgressDialog();
                }
                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equals("1")) {
                        temp[0] = true;
                    } else {
                        Toast.makeText(OrderDetailsActivity.this, "success = 0", Toast.LENGTH_SHORT).show();
                        temp[0] = false;
                    }
                } else {
                    Toast.makeText(OrderDetailsActivity.this, "Responce is not successfull", Toast.LENGTH_SHORT).show();
                    temp[0] = true;
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                DialogUtils.hideProgressDialog();
            }
        });
        return temp[0];
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainActivity.refreshThis();
        finish();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
