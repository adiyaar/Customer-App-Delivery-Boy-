package com.themescoder.androidstore.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.themescoder.androidstore.activities.MainActivity;
import com.themescoder.androidstore.R;

import java.util.ArrayList;
import java.util.List;

import com.themescoder.androidstore.adapters.AddressListAdapter;
import com.themescoder.androidstore.customs.DialogLoader;
import com.themescoder.androidstore.models.address_model.AddressData;
import com.themescoder.androidstore.models.address_model.AddressDetails;
import com.themescoder.androidstore.network.APIClient;

import am.appwise.components.ni.NoInternetDialog;
import retrofit2.Call;
import retrofit2.Callback;


public class My_Addresses extends Fragment {

    View rootView;
    String customerID;
    String defaultAddressID;

    RecyclerView addresses_recycler;
    FloatingActionButton add_address_fab;
    LinearLayout emptyRecordLayout;
    AppCompatButton addAddressButton;

    DialogLoader dialogLoader;
    static AddressListAdapter addressListAdapter;

    List<AddressDetails> addressesList = new ArrayList<>();

    private int defaultAddressPosition = -1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.my_addresses, container, false);

        // Enable Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
        //MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.actionAddresses));

        NoInternetDialog noInternetDialog = new NoInternetDialog.Builder(getContext()).build();
       // noInternetDialog.show();

        // Get the CustomerID and DefaultAddressID from SharedPreferences
        customerID = this.getContext().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userID", "");
        defaultAddressID = this.getContext().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userDefaultAddressID", "");


        // Binding Layout Views
        addresses_recycler = (RecyclerView) rootView.findViewById(R.id.addresses_recycler);
        add_address_fab = (FloatingActionButton) rootView.findViewById(R.id.add_address_fab);
        emptyRecordLayout = rootView.findViewById(R.id.empty_record);
        addAddressButton = rootView.findViewById(R.id.continue_shopping_btn);

        dialogLoader = new DialogLoader(getContext());

        // Request for User's Addresses
        RequestAllAddresses();


        addAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Add_Address Fragment with arguments
                Fragment fragment = new Add_Address(My_Addresses.this);
                Bundle args = new Bundle();
                args.putBoolean("isUpdate", false);
                fragment.setArguments(args);

                FragmentManager fragmentManager = ((MainActivity) getContext()).getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .hide(((MainActivity)getActivity()).currentFragment)
                        .add(R.id.main_fragment, fragment)
                        .addToBackStack(null).commit();
            }
        });

        // Handle Click event of add_address_fab FAB
        add_address_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Navigate to Add_Address Fragment with arguments
                Fragment fragment = new Add_Address(My_Addresses.this);
                Bundle args = new Bundle();
                args.putBoolean("isUpdate", false);
                fragment.setArguments(args);

                FragmentManager fragmentManager = ((MainActivity) getContext()).getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .hide(((MainActivity)getActivity()).currentFragment)
                        .add(R.id.main_fragment, fragment)
                        .addToBackStack(null).commit();
            }
        });


        return rootView;
    }



    //*********** Adds Addresses returned from the Server to the AddressesList ********//

    private void addAddresses(AddressData addressData) {

        // Add Addresses to addressesList from the List of AddressData
        addressesList = addressData.getData();

        if (addressesList.isEmpty()){
            emptyRecordLayout.setVisibility(View.VISIBLE);
        } else {
            emptyRecordLayout.setVisibility(View.GONE);
        }

        for (int i=0;  i<addressesList.size();  i++) {
            if (addressesList.get(i).getAddressId() == addressesList.get(i).getDefaultAddress()) {
                defaultAddressPosition = i;
            }
        }

        if (addressesList.size() == 1) {
            MakeAddressDefault(customerID, String.valueOf(addressesList.get(0).getAddressId()), getContext(), rootView);
        }
        

        // Initialize the AddressListAdapter for RecyclerView
        addressListAdapter = new AddressListAdapter(My_Addresses.this, getContext(), customerID, defaultAddressPosition, addressesList, My_Addresses.this);

        // Set the Adapter and LayoutManager to the RecyclerView
        addresses_recycler.setAdapter(addressListAdapter);
        addresses_recycler.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        addressListAdapter.notifyDataSetChanged();

    }



    //*********** Request User's all Addresses from the Server ********//

    public void RequestAllAddresses() {

        dialogLoader.showProgressDialog();

        Call<AddressData> call = APIClient.getInstance()
                .getAllAddress
                        (
                                customerID
                        );

        call.enqueue(new Callback<AddressData>() {
            @Override
            public void onResponse(Call<AddressData> call, retrofit2.Response<AddressData> response) {

                dialogLoader.hideProgressDialog();

                // Check if the Response is successful
                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {

                        // Addresses have been returned. Add Addresses to the addressesList
                        addAddresses(response.body());

                    }
                    else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        // Addresses haven't been returned. Show the Message to the User
                        Snackbar.make(rootView, response.body().getMessage(), Snackbar.LENGTH_SHORT).show();

                    }
                    else {
                        // Unable to get Success status
                        Snackbar.make(rootView, getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddressData> call, Throwable t) {
                Toast.makeText(getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }



    //*********** Request to Delete the given User's Address ********//

    public void DeleteAddress(final String customerID, final String addressID, final Context context, final View view) {

        Call<AddressData> call = APIClient.getInstance()
                .deleteUserAddress
                        (
                                customerID,
                                addressID
                        );

        call.enqueue(new Callback<AddressData>() {
            @Override
            public void onResponse(Call<AddressData> call, retrofit2.Response<AddressData> response) {
                
                if (response.isSuccessful()) {

                    if (response.body().getSuccess().equalsIgnoreCase("1")) {

                        // Address has been Deleted. Show the Message to the User
                        Snackbar.make(view, response.body().getMessage(), Snackbar.LENGTH_SHORT).show();
                        RequestAllAddresses();
                    }
                    else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        Snackbar.make(view, response.body().getMessage(), Snackbar.LENGTH_LONG).show();
    
                    }
                    else {
                        // Unable to get Success status
                        Snackbar.make(view, context.getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddressData> call, Throwable t) {
                Toast.makeText(context, "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }



    //*********** Request for Changing the Address to User's Default Address ********//

    public void MakeAddressDefault(final String customerID, final String addressID, final Context context, final View view) {
        
        final DialogLoader dialogLoader = new DialogLoader(context);
        dialogLoader.showProgressDialog();
        
        Call<AddressData> call = APIClient.getInstance()
                .updateDefaultAddress
                        (
                                customerID,
                                addressID
                        );

        call.enqueue(new Callback<AddressData>() {
            @Override
            public void onResponse(Call<AddressData> call, retrofit2.Response<AddressData> response) {
    
                dialogLoader.hideProgressDialog();
                
                // Check if the Response is successful
                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {

                        // Notify that items from specified position have changed.

                        // Change the value of userDefaultAddressID in the SharedPreferences
                        SharedPreferences.Editor editor = context.getSharedPreferences("UserInfo", context.MODE_PRIVATE).edit();
                        editor.putString("userDefaultAddressID", addressID);
                        editor.apply();

                    }
                    else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        Snackbar.make(view, response.body().getMessage(), Snackbar.LENGTH_LONG).show();
    
                    }
                    else {
                        // Unable to get Success status
                        Snackbar.make(view, context.getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddressData> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(context, "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
        
    }

}

