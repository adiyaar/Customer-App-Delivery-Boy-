package com.themescoder.androidstore.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.themescoder.androidstore.R;
import com.themescoder.androidstore.activities.MainActivity;
import com.themescoder.androidstore.adapters.ShippingServicesAdapter;
import com.themescoder.androidstore.app.App;
import com.themescoder.androidstore.constant.ConstantValues;
import com.themescoder.androidstore.customs.DialogLoader;
import com.themescoder.androidstore.customs.DividerItemDecoration;
import com.themescoder.androidstore.databases.User_Cart_DB;
import com.themescoder.androidstore.models.address_model.AddressDetails;
import com.themescoder.androidstore.models.cart_model.CartProduct;
import com.themescoder.androidstore.models.shipping_model.PostTaxAndShippingData;
import com.themescoder.androidstore.models.shipping_model.ShippingProductDetails;
import com.themescoder.androidstore.models.shipping_model.ShippingRateData;
import com.themescoder.androidstore.models.shipping_model.ShippingRateDetails;
import com.themescoder.androidstore.models.shipping_model.ShippingService;
import com.themescoder.androidstore.network.APIClient;

import java.util.ArrayList;
import java.util.List;

import am.appwise.components.ni.NoInternetDialog;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;

/*
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
*/


public class Shipping_Methods extends Fragment {

    View rootView;
    Boolean isUpdate = false;

    String tax;

    DialogLoader dialogLoader;
    User_Cart_DB user_cart_db;

    Button saveShippingMethodBtn;
    TextView free_shipping_title, local_pickup_title, flat_rate_title, ups_shipping_title, shipping_price_title;
    LinearLayout free_shipping_layout, local_pickup_layout, flat_rate_layout, ups_shipping_layout, shipping_price_layout;
    RecyclerView free_shipping_services, local_pickup_services, flat_rate_services, ups_shipping_services, shipping_price_services;

    List<CartProduct> checkoutItemsList;
    List<ShippingService> freeShippingServicesList;
    List<ShippingService> localPickupServicesList;
    List<ShippingService> flatRateServicesList;
    List<ShippingService> upsShippingServicesList;
    List<ShippingService> shippingpriceServicesList;

    AddressDetails shippingAddress;
    ShippingServicesAdapter freeShippingServicesAdapter;
    ShippingServicesAdapter localPickupServicesAdapter;
    ShippingServicesAdapter flatRateServicesAdapter;
    ShippingServicesAdapter upsShippingServicesAdapter;
    ShippingServicesAdapter shippingPriceServicesAdapter;

    private ShippingService shippingService;

    // To keep track of Checked Radio Button
    private RadioButton lastChecked_RB = null;
    My_Cart my_cart;

    public Shipping_Methods(My_Cart my_cart) {
        this.my_cart = my_cart;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.shipping_methods, container, false);

        NoInternetDialog noInternetDialog = new NoInternetDialog.Builder(getContext()).build();
        //noInternetDialog.show();

        if (getArguments() != null) {
            if (getArguments().containsKey("isUpdate")) {
                isUpdate = getArguments().getBoolean("isUpdate", false);
            }
        }

        // Set the Title of Toolbar
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.shipping_method));


        // Get the Shipping Method and Tax from AppContext
        tax = ((App) getContext().getApplicationContext()).getTax();
        //shippingService = ((App) getContext().getApplicationContext()).getShippingService();
        shippingAddress = ((App) getContext().getApplicationContext()).getShippingAddress();


        // Binding Layout Views
        saveShippingMethodBtn = (Button) rootView.findViewById(R.id.save_shipping_method_btn);
        free_shipping_title = (TextView) rootView.findViewById(R.id.free_shipping_title);
        local_pickup_title = (TextView) rootView.findViewById(R.id.local_pickup_title);
        flat_rate_title = (TextView) rootView.findViewById(R.id.flat_rate_title);
        ups_shipping_title = (TextView) rootView.findViewById(R.id.ups_shipping_title);
        shipping_price_title = (TextView) rootView.findViewById(R.id.shipping_price_title);
        free_shipping_services = (RecyclerView) rootView.findViewById(R.id.free_shipping_services_list);
        local_pickup_services = (RecyclerView) rootView.findViewById(R.id.local_pickup_services_list);
        flat_rate_services = (RecyclerView) rootView.findViewById(R.id.flat_rate_services_list);
        ups_shipping_services = (RecyclerView) rootView.findViewById(R.id.ups_shipping_services_list);
        shipping_price_services = (RecyclerView) rootView.findViewById(R.id.shipping_price_list);
        free_shipping_layout = (LinearLayout) rootView.findViewById(R.id.free_shipping_layout);
        local_pickup_layout = (LinearLayout) rootView.findViewById(R.id.local_pickup_layout);
        flat_rate_layout = (LinearLayout) rootView.findViewById(R.id.flat_rate_layout);
        ups_shipping_layout = (LinearLayout) rootView.findViewById(R.id.ups_shipping_layout);
        shipping_price_layout = (LinearLayout) rootView.findViewById(R.id.shipping_price_layout);


        free_shipping_services.setNestedScrollingEnabled(false);
        local_pickup_services.setNestedScrollingEnabled(false);
        flat_rate_services.setNestedScrollingEnabled(false);
        ups_shipping_services.setNestedScrollingEnabled(false);

        // Hide some Views
        free_shipping_layout.setVisibility(View.GONE);
        local_pickup_layout.setVisibility(View.GONE);
        flat_rate_layout.setVisibility(View.GONE);
        ups_shipping_layout.setVisibility(View.GONE);
        shipping_price_layout.setVisibility(View.GONE);


     /*
             if (ConstantValues.IS_ADMOBE_ENABLED) {
                 // Initialize InterstitialAd
                 final InterstitialAd mInterstitialAd = new InterstitialAd(getActivity());
                 mInterstitialAd.setAdUnitId(ConstantValues.AD_UNIT_ID_INTERSTITIAL);
                 mInterstitialAd.loadAd(new AdRequest.Builder().build());
                 mInterstitialAd.setAdListener(new AdListener(){
                     @Override
                     public void onAdLoaded() {
                         mInterstitialAd.show();
                     }
                 });
             }
     */


        dialogLoader = new DialogLoader(getContext());
        user_cart_db = new User_Cart_DB();


        // Get checkoutItems from Local Databases User_Cart_DB
        checkoutItemsList = user_cart_db.getCartItems();

        freeShippingServicesList = new ArrayList<>();
        localPickupServicesList = new ArrayList<>();
        flatRateServicesList = new ArrayList<>();
        upsShippingServicesList = new ArrayList<>();
        shippingpriceServicesList = new ArrayList<>();

        // Request Shipping Rates
        RequestShippingRates();


        saveShippingMethodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (shippingService != null) {

                    // Save the AddressDetails
                    ((App) getContext().getApplicationContext()).setTax(tax);
                    ((App) getContext().getApplicationContext()).setShippingService(shippingService);


                    // Check if an Address is being Edited
                    if (isUpdate) {
                        // Navigate to CheckoutFinal Fragment
                        ((MainActivity) getContext()).getSupportFragmentManager().popBackStack();
                    } else {
                        // Navigate to CheckoutFinal Fragment
                        Fragment fragment = new CheckoutFinal(my_cart, shippingService);
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().add(R.id.main_fragment, fragment, getString(R.string.checkout))
                                .addToBackStack(null).commit();
                    }

                }
            }
        });


        return rootView;
    }


    //*********** Handle ShippingMethods returned from the Server ********//

    private void addShippingMethods(ShippingRateDetails shippingRateDetails) {

        tax = shippingRateDetails.getTax();


        if (shippingRateDetails.getShippingMethods().getFreeShipping() != null) {
            free_shipping_layout.setVisibility(View.VISIBLE);
            free_shipping_title.setText(shippingRateDetails.getShippingMethods().getFreeShipping().getName());

            freeShippingServicesList.addAll(shippingRateDetails.getShippingMethods().getFreeShipping().getServices());
            freeShippingServicesAdapter = new ShippingServicesAdapter(getContext(), freeShippingServicesList, this);

            free_shipping_services.setAdapter(freeShippingServicesAdapter);
            free_shipping_services.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        } else {
            free_shipping_layout.setVisibility(View.GONE);
        }


        if (shippingRateDetails.getShippingMethods().getLocalPickup() != null) {
            local_pickup_layout.setVisibility(View.VISIBLE);
            local_pickup_title.setText(shippingRateDetails.getShippingMethods().getLocalPickup().getName());

            localPickupServicesList.addAll(shippingRateDetails.getShippingMethods().getLocalPickup().getServices());
            localPickupServicesAdapter = new ShippingServicesAdapter(getContext(), localPickupServicesList, this);

            local_pickup_services.setAdapter(localPickupServicesAdapter);
            local_pickup_services.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        } else {
            local_pickup_layout.setVisibility(View.GONE);
        }


        if (shippingRateDetails.getShippingMethods().getFlateRate() != null) {
            flat_rate_layout.setVisibility(View.VISIBLE);
            flat_rate_title.setText(shippingRateDetails.getShippingMethods().getFlateRate().getName());

            flatRateServicesList.addAll(shippingRateDetails.getShippingMethods().getFlateRate().getServices());
            flatRateServicesAdapter = new ShippingServicesAdapter(getContext(), flatRateServicesList, this);

            flat_rate_services.setAdapter(flatRateServicesAdapter);
            flat_rate_services.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        } else {
            flat_rate_layout.setVisibility(View.GONE);
        }


        if (shippingRateDetails.getShippingMethods().getUpsShipping() != null) {

            if (shippingRateDetails.getShippingMethods().getUpsShipping().getSuccess().equalsIgnoreCase("1")) {
                ups_shipping_layout.setVisibility(View.VISIBLE);
                ups_shipping_title.setText(shippingRateDetails.getShippingMethods().getUpsShipping().getName());

                upsShippingServicesList.addAll(shippingRateDetails.getShippingMethods().getUpsShipping().getServices());
                upsShippingServicesAdapter = new ShippingServicesAdapter(getContext(), upsShippingServicesList, this);

                ups_shipping_services.setAdapter(upsShippingServicesAdapter);
                ups_shipping_services.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                ups_shipping_services.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

            } else {
                ups_shipping_layout.setVisibility(View.GONE);

                final Snackbar snackbar = Snackbar.make(rootView,
                        shippingRateDetails.getShippingMethods().getUpsShipping().getMessage(),
                        Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction(getString(R.string.ok), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                    }
                });
                snackbar.show();
            }

        } else {
            ups_shipping_layout.setVisibility(View.GONE);
        }

        if (shippingRateDetails.getShippingMethods().getShippingprice() != null) {

            if (shippingRateDetails.getShippingMethods().getShippingprice().getSuccess().equalsIgnoreCase("1")) {
                shipping_price_layout.setVisibility(View.VISIBLE);
                shipping_price_title.setText(shippingRateDetails.getShippingMethods().getShippingprice().getName());

                shippingpriceServicesList.addAll(shippingRateDetails.getShippingMethods().getShippingprice().getServices());
                shippingPriceServicesAdapter = new ShippingServicesAdapter(getContext(), shippingpriceServicesList, this);

                shipping_price_services.setAdapter(shippingPriceServicesAdapter);
                shipping_price_services.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                shipping_price_services.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

            } else {
                shipping_price_layout.setVisibility(View.GONE);

                final Snackbar snackbar = Snackbar.make(rootView,
                        shippingRateDetails.getShippingMethods().getShippingprice().getMessage(),
                        Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction(getString(R.string.ok), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                    }
                });
                snackbar.show();
            }

        } else {
            shipping_price_layout.setVisibility(View.GONE);
        }


        if (shippingService == null) {
            if (shippingRateDetails.getShippingMethods().getFreeShipping() != null
                    && shippingRateDetails.getShippingMethods().getFreeShipping().getServices().size() > 0) {

                shippingService = shippingRateDetails.getShippingMethods().getFreeShipping().getServices().get(0);

            } else if (shippingRateDetails.getShippingMethods().getLocalPickup() != null
                    && shippingRateDetails.getShippingMethods().getLocalPickup().getServices().size() > 0) {

                shippingService = shippingRateDetails.getShippingMethods().getLocalPickup().getServices().get(0);

            } else if (shippingRateDetails.getShippingMethods().getFlateRate() != null
                    && shippingRateDetails.getShippingMethods().getFlateRate().getServices().size() > 0) {

                shippingService = shippingRateDetails.getShippingMethods().getFlateRate().getServices().get(0);

            } else if (shippingRateDetails.getShippingMethods().getUpsShipping() != null
                    && shippingRateDetails.getShippingMethods().getUpsShipping().getSuccess().equalsIgnoreCase("1")
                    && shippingRateDetails.getShippingMethods().getUpsShipping().getServices().size() > 0) {

                shippingService = shippingRateDetails.getShippingMethods().getUpsShipping().getServices().get(0);
            }
        }

    }


    //*********** Request the Server to Calculate Tax and Shipping Rates ********//

    private void RequestShippingRates() {

        dialogLoader.showProgressDialog();

        PostTaxAndShippingData postTaxAndShippingData = new PostTaxAndShippingData();

        double productWeight = 0;
        double totalWeight = 0;
        String productWeightUnit = "g";
        List<ShippingProductDetails> productsList = new ArrayList<>();


        // Get ProductWeight, WeightUnit and ProductsList
        for (int i = 0; i < checkoutItemsList.size(); i++) {
            if (checkoutItemsList.get(i).getCustomersBasketProduct().getProductsWeight() != null)
                productWeight = Double.parseDouble(checkoutItemsList.get(i).getCustomersBasketProduct().getProductsWeight());
            if (checkoutItemsList.get(i).getCustomersBasketProduct().getProductsWeightUnit() != null)
                if (checkoutItemsList.get(i).getCustomersBasketProduct().getProductsWeightUnit().equalsIgnoreCase("kg")) {
                    productWeight = Double.parseDouble(checkoutItemsList.get(i).getCustomersBasketProduct().getProductsWeight()) * 1000;
                }
            //productWeightUnit = checkoutItemsList.get(i).getCustomersBasketProduct().getProductsWeightUnit();
            totalWeight = totalWeight + productWeight * checkoutItemsList.get(i).getCustomersBasketProduct().getCustomersBasketQuantity();
            ShippingProductDetails p = new ShippingProductDetails();
            p.setCustomersBasketQuantity(checkoutItemsList.get(i).getCustomersBasketProduct().getCustomersBasketQuantity());
            p.setProductsFinalPrice((checkoutItemsList.get(i).getCustomersBasketProduct().getProductsFinalPrice() == null || checkoutItemsList.get(i).getCustomersBasketProduct().getProductsFinalPrice().isEmpty())
                    ? 0.0 : Double.parseDouble(checkoutItemsList.get(i).getCustomersBasketProduct().getProductsFinalPrice()));
            p.setProductsPrice((checkoutItemsList.get(i).getCustomersBasketProduct().getProductsPrice() == null || checkoutItemsList.get(i).getCustomersBasketProduct().getProductsPrice().isEmpty())
                    ? 0.0 : Double.parseDouble(checkoutItemsList.get(i).getCustomersBasketProduct().getProductsPrice()));
            p.setProductsId(checkoutItemsList.get(i).getCustomersBasketProduct().getProductsId());
            p.setTotalPrice((checkoutItemsList.get(i).getCustomersBasketProduct().getTotalPrice() == null || checkoutItemsList.get(i).getCustomersBasketProduct().getTotalPrice().isEmpty())
                    ? 0.0 : Double.parseDouble(checkoutItemsList.get(i).getCustomersBasketProduct().getTotalPrice()));
            p.setProductsWeightUnit(checkoutItemsList.get(i).getCustomersBasketProduct().getProductsWeightUnit());
            p.setProductsWeight(checkoutItemsList.get(i).getCustomersBasketProduct().getProductsWeight());
            productsList.add(p);
        }

        postTaxAndShippingData.setTitle(shippingAddress.getFirstname());
        postTaxAndShippingData.setStreetAddress(shippingAddress.getStreet());
        postTaxAndShippingData.setCity(shippingAddress.getCity());
        postTaxAndShippingData.setState(shippingAddress.getState());
        postTaxAndShippingData.setZone(shippingAddress.getZoneName());
        postTaxAndShippingData.setTaxZoneId(shippingAddress.getZoneId());
        postTaxAndShippingData.setPostcode(shippingAddress.getPostcode());
        postTaxAndShippingData.setCountry(shippingAddress.getCountryName());
        postTaxAndShippingData.setCountryID(shippingAddress.getCountriesId());

        postTaxAndShippingData.setProductsWeight(totalWeight);
        postTaxAndShippingData.setProductsWeightUnit(productWeightUnit);

        postTaxAndShippingData.setLanguage_id(ConstantValues.LANGUAGE_ID);

        postTaxAndShippingData.setProducts(productsList);
        postTaxAndShippingData.setCurrencyCode(ConstantValues.CURRENCY_CODE);

        String str = new Gson().toJson(postTaxAndShippingData);

        // Proceed API Call to get Tax and Shipping Rates
        Call<ShippingRateData> call = APIClient.getInstance()
                .getShippingMethodsAndTax
                        (
                                postTaxAndShippingData
                        );

        call.enqueue(new Callback<ShippingRateData>() {
            @Override
            public void onResponse(Call<ShippingRateData> call, retrofit2.Response<ShippingRateData> response) {

                dialogLoader.hideProgressDialog();

                // Check if the Response is successful
                if (response.isSuccessful()) {

                    if (response.body().getSuccess().equalsIgnoreCase("1")) {

                        addShippingMethods(response.body().getData());

                    } else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        Snackbar.make(rootView, response.body().getMessage(), Snackbar.LENGTH_LONG).show();

                    } else {
                        // Unable to get Success status
                        Snackbar.make(rootView, getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(App.getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ShippingRateData> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(App.getContext(), "NetworkCallFailure : " + t, Toast.LENGTH_LONG).show();
            }
        });
    }


    public ShippingService getSelectedShippingService() {
        return shippingService;
    }


    public void setSelectedShippingService(ShippingService shippingService) {
        this.shippingService = shippingService;
    }


    public RadioButton getLastChecked_RB() {
        return lastChecked_RB;
    }

    public void setLastChecked_RB(RadioButton lastChecked_RB) {
        this.lastChecked_RB = lastChecked_RB;
    }


}


