package com.themescoder.androidstore.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.themescoder.androidstore.Maps.MapActivity;
import com.themescoder.androidstore.activities.MainActivity;
import com.themescoder.androidstore.R;

import java.util.ArrayList;
import java.util.List;

import com.themescoder.androidstore.customs.DialogLoader;
import com.themescoder.androidstore.models.address_model.AddressData;
import com.themescoder.androidstore.models.address_model.Countries;
import com.themescoder.androidstore.models.address_model.CountryDetails;
import com.themescoder.androidstore.models.address_model.ZoneDetails;
import com.themescoder.androidstore.models.address_model.Zones;
import com.themescoder.androidstore.network.APIClient;
import com.themescoder.androidstore.utils.Utilities;
import com.themescoder.androidstore.utils.ValidateInputs;

import am.appwise.components.ni.NoInternetDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class Add_Address extends Fragment implements GoogleApiClient.OnConnectionFailedListener{

    View rootView;
    Boolean isUpdate;

    String customerID, addressID;
    String selectedZoneID, selectedCountryID;

    Button saveAddressBtn;
    LinearLayout default_shipping_layout;
    EditText input_firstname, input_lastname, input_location, input_address, input_country,
            delivery_time,input_zone, input_city, input_postcode, input_contact;
    
    ArrayAdapter<String> zoneAdapter;
    ArrayAdapter<String> countryAdapter;

    List<String> zoneNames;
    List<String> countryNames;
    List<ZoneDetails> zoneList = new ArrayList<>();
    List<CountryDetails> countryList = new ArrayList<>();

    //LocationPicker.
    /*Edited  28-Dec-18*/
    int PLACE_PICKER_REQUEST = 1463;
   // GoogleApiClient mGoogleApiClient;
    String latitude, longitude;
    DialogLoader dialogLoader;
    My_Addresses parentFrag;

    public Add_Address(My_Addresses parentFrag) {
        this.parentFrag = parentFrag;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.address, container, false);

        // Get the Bundle Arguments
        Bundle addressInfo = getArguments();
        isUpdate = addressInfo.getBoolean("isUpdate");

        // Enable Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
        //MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        ((MainActivity)getActivity()).toggleNavigaiton(false);
        NoInternetDialog noInternetDialog = new NoInternetDialog.Builder(getContext()).build();
       // noInternetDialog.show();

        dialogLoader = new DialogLoader(getContext());
        rootView.findViewById(R.id.radioGroup).setVisibility(View.GONE);
        /*Edited  28-Dec-18*/
      /*  mGoogleApiClient = new GoogleApiClient
                .Builder(getActivity())

                .enableAutoManage(getActivity(), this)
                .build();*/

        /*Edited 28-Dec-18*/


        // Set the Title of Toolbar
        if (isUpdate) {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.update_address));
        } else {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.new_address));
        }
        

        // Get the CustomersID from SharedPreferences
        customerID = getActivity().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userID", "");


        // Binding Layout Views
        input_firstname = (EditText) rootView.findViewById(R.id.firstname);
        input_lastname = (EditText) rootView.findViewById(R.id.lastname);
        input_location = (EditText) rootView.findViewById(R.id.location);
        input_address = (EditText) rootView.findViewById(R.id.address);
        input_country = (EditText) rootView.findViewById(R.id.country);
        input_zone = (EditText) rootView.findViewById(R.id.zone);
        delivery_time = (EditText) rootView.findViewById(R.id.delivery_time);
        input_city = (EditText) rootView.findViewById(R.id.city);
        input_postcode = (EditText) rootView.findViewById(R.id.postcode);
        input_contact = (EditText) rootView.findViewById(R.id.contact);
        input_contact.setVisibility(View.GONE);
        saveAddressBtn = (Button) rootView.findViewById(R.id.save_address_btn);
        default_shipping_layout = (LinearLayout) rootView.findViewById(R.id.default_shipping_layout);

       

        // Set KeyListener of some View to null
        input_country.setKeyListener(null);
        input_zone.setKeyListener(null);
    
        zoneNames = new ArrayList<>();
        countryNames = new ArrayList<>();

        // Hide the Default Checkbox Layout
        default_shipping_layout.setVisibility(View.GONE);
    
        // Hide delivery time slot
        delivery_time.setVisibility(View.GONE);
        input_location.setVisibility(View.GONE);

        // Request for Countries List
        RequestCountries();



        // Check if an existing Address is being Edited
        if (isUpdate) {
            // Set the Address details from Bundle Arguments
            addressID = addressInfo.getString("addressID");
            selectedCountryID = addressInfo.getString("addressCountryID");
            selectedZoneID = addressInfo.getString("addressZoneID");

            input_firstname.setText(addressInfo.getString("addressFirstname"));
            input_lastname.setText(addressInfo.getString("addressLastname"));
            //input_location.setText(addressInfo.getString("addressLocation"));
            input_address.setText(addressInfo.getString("addressStreet"));
            input_country.setText(addressInfo.getString("addressCountryName"));
            input_zone.setText(addressInfo.getString("addressZoneName"));
            input_city.setText(addressInfo.getString("addressCity"));
            input_postcode.setText(addressInfo.getString("addressPostCode"));
           // input_contact.setText(addressInfo.getString("addressPhone"));
    
            RequestZones(String.valueOf(selectedCountryID));
        }
        else {
            zoneNames.add("Other");
            selectedZoneID = "0";
        }

        /*Edited  28-Dec-18*/
        // Handle Touch event of input_location EditText
        input_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent autocompleteIntent = new Intent(getContext(), MapActivity.class);
                //requestCode in INT
                startActivityForResult(autocompleteIntent, PLACE_PICKER_REQUEST);
            }
        });
        /*Edited  28-Dec-18*/
        // Handle Touch event of input_country EditText

        // Handle Touch event of input_country EditText
        input_country.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP) {
    
                    countryAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1);
                    countryAdapter.addAll(countryNames);
    
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                    View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_list_search, null);
                    dialog.setView(dialogView);
                    dialog.setCancelable(false);
    
                    Button dialog_button = (Button) dialogView.findViewById(R.id.dialog_button);
                    EditText dialog_input = (EditText) dialogView.findViewById(R.id.dialog_input);
                    TextView dialog_title = (TextView) dialogView.findViewById(R.id.dialog_title);
                    ListView dialog_list = (ListView) dialogView.findViewById(R.id.dialog_list);
    
                    dialog_title.setText(getString(R.string.country));
                    dialog_list.setVerticalScrollBarEnabled(true);
                    dialog_list.setAdapter(countryAdapter);
    
                    dialog_input.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                        @Override
                        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                            // Filter CountryAdapter
                            countryAdapter.getFilter().filter(charSequence);
                        }
                        @Override
                        public void afterTextChanged(Editable s) {}
                    });
    
    
                    final AlertDialog alertDialog = dialog.create();
    
                    dialog_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });
    
                    alertDialog.show();
    
    
    
                    dialog_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            
                            alertDialog.dismiss();
                            final String selectedItem = countryAdapter.getItem(position);
            
                            int countryID = 0;
                            input_country.setText(selectedItem);
            
                            if (!selectedItem.equalsIgnoreCase("Other")) {
                
                                for (int i=0;  i<countryList.size();  i++) {
                                    if (countryList.get(i).getCountriesName().equalsIgnoreCase(selectedItem)) {
                                        // Get the ID of selected Country
                                        countryID = countryList.get(i).getCountriesId();
                                    }
                                }
                
                            }
            
                            selectedCountryID = String.valueOf(countryID);
            
                            zoneNames.clear();
                            input_zone.setText("");
            
                            // Request for all Zones in the selected Country
                            RequestZones(String.valueOf(selectedCountryID));
                        }
                    });
                }

                return false;
            }
        });

        // Handle Touch event of input_zone EditText
        input_zone.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP) {
    
                    zoneAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1);
                    zoneAdapter.addAll(zoneNames);
    
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                    View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_list_search, null);
                    dialog.setView(dialogView);
                    dialog.setCancelable(false);
    
                    Button dialog_button = (Button) dialogView.findViewById(R.id.dialog_button);
                    EditText dialog_input = (EditText) dialogView.findViewById(R.id.dialog_input);
                    TextView dialog_title = (TextView) dialogView.findViewById(R.id.dialog_title);
                    ListView dialog_list = (ListView) dialogView.findViewById(R.id.dialog_list);
    
                    dialog_title.setText(getString(R.string.zone));
                    dialog_list.setVerticalScrollBarEnabled(true);
                    dialog_list.setAdapter(zoneAdapter);
    
                    dialog_input.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                        @Override
                        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                            // Filter ZoneAdapter
                            zoneAdapter.getFilter().filter(charSequence);
                        }
                        @Override
                        public void afterTextChanged(Editable s) {}
                    });
    
    
                    final AlertDialog alertDialog = dialog.create();
    
                    dialog_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });
    
                    alertDialog.show();
    
    
    
                    dialog_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            
                            alertDialog.dismiss();
                            final String selectedItem = zoneAdapter.getItem(position);
            
                            int zoneID = 0;
                            input_zone.setText(selectedItem);
            
                            if (!zoneAdapter.getItem(position).equalsIgnoreCase("Other")) {
                
                                for (int i=0;  i<zoneList.size();  i++) {
                                    if (zoneList.get(i).getZoneName().equalsIgnoreCase(selectedItem)) {
                                        // Get the ID of selected Country
                                        zoneID = zoneList.get(i).getZoneId();
                                    }
                                }
                            }
            
                            selectedZoneID = String.valueOf(zoneID);
                        }
                    });
                }

                return false;
            }
        });


        // Handle the Click event of Save Button
        saveAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate Address Form Inputs
                boolean isValidData = validateAddressForm();

                if (isValidData) {
                    if (isUpdate) {
                        // Update the Address
                        updateUserAddress(addressID);
                    } else {
                        // Add a new Address
                        addUserAddress();
                    }
                }
            }
        });


        return rootView;
    }



    //*********** Get Countries List from the Server ********//

    private void RequestCountries() {

        Call<Countries> call = APIClient.getInstance()
                .getCountries();

        call.enqueue(new Callback<Countries>() {
            @Override
            public void onResponse(Call<Countries> call, Response<Countries> response) {
                
                if (response.isSuccessful()) {

                	// Check the Success status
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {

                        countryList = response.body().getData();

                        // Add the Country Names to the countryNames List
                        for (int i=0;  i<countryList.size();  i++) {
                            countryNames.add(countryList.get(i).getCountriesName());
                        }
    
                        countryNames.add("Other");
                        
                    }
                    else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        Snackbar.make(rootView, response.body().getMessage(), Snackbar.LENGTH_LONG).show();
    
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
            public void onFailure(Call<Countries> call, Throwable t) {
                Toast.makeText(getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }



    //*********** Get Zones List of the Country from the Server ********//

    private void RequestZones(String countryID) {

        Call<Zones> call = APIClient.getInstance()
                .getZones
                        (
                                countryID
                        );

        call.enqueue(new Callback<Zones>() {
            @Override
            public void onResponse(Call<Zones> call, Response<Zones> response) {

                if (response.isSuccessful()) {

                    if (response.body().getSuccess().equalsIgnoreCase("1")) {
    
                        zoneList = response.body().getData();
                        
                        // Add the Zone Names to the zoneNames List
                        for (int i=0;  i<zoneList.size();  i++){
                            zoneNames.add(zoneList.get(i).getZoneName());
                        }
    
                        zoneNames.add("Other");
                        
                    }
                    else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        Snackbar.make(rootView, response.body().getMessage(), Snackbar.LENGTH_LONG).show();
    
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
            public void onFailure(Call<Zones> call, Throwable t) {
                Toast.makeText(getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }



    //*********** Proceed the Request of New Address ********//

    public void addUserAddress() {

        dialogLoader.showProgressDialog();
        final String customers_default_address_id = getActivity().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userDefaultAddressID", "");


        Call<AddressData> call = APIClient.getInstance()
                .addUserAddress
                        (
                                customerID,
                                input_firstname.getText().toString().trim(),
                                input_lastname.getText().toString().trim(),
                                input_address.getText().toString().trim(),
                                input_postcode.getText().toString().trim(),
                                input_city.getText().toString().trim(),
                                selectedCountryID,
                                selectedZoneID,
                                customers_default_address_id
                        );


        call.enqueue(new Callback<AddressData>() {
            @Override
            public void onResponse(Call<AddressData> call, Response<AddressData> response) {
                
                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {
                        // Address has been added to User's Addresses
                        // Navigate to Addresses fragment
                        ((MainActivity) getContext()).getSupportFragmentManager().popBackStack();
                        parentFrag.RequestAllAddresses();
                    }
                    else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        Snackbar.make(rootView, response.body().getMessage(), Snackbar.LENGTH_LONG).show();
    
                    }
                    else {
                        // Unable to get Success status
                        Snackbar.make(rootView, getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
                dialogLoader.hideProgressDialog();
            }

            @Override
            public void onFailure(Call<AddressData> call, Throwable t) {
                Toast.makeText(getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
                dialogLoader.hideProgressDialog();
            }
        });
    }



    //*********** Proceed the Request of Update Address ********//

    public void updateUserAddress(String addressID) {
        dialogLoader.showProgressDialog();
        final String customers_default_address_id = getActivity().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userDefaultAddressID", "");


        Call<AddressData> call = APIClient.getInstance()
                .updateUserAddress
                        (
                                customerID,
                                addressID,
                                input_firstname.getText().toString().trim(),
                                input_lastname.getText().toString().trim(),
                                input_address.getText().toString().trim(),
                                input_postcode.getText().toString().trim(),
                                input_city.getText().toString().trim(),
                                selectedCountryID,
                                selectedZoneID,
                                customers_default_address_id
                        );

        call.enqueue(new Callback<AddressData>() {
            @Override
            public void onResponse(Call<AddressData> call, Response<AddressData> response) {
                
                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {
                        // Address has been Edited
                        // Navigate to Addresses fragment
                        ((MainActivity) getContext()).getSupportFragmentManager().popBackStack();
                        parentFrag.RequestAllAddresses();
                        
                    }
                    else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        // Address has not been Edited
                        // Show the Message to the User
                        Toast.makeText(getContext(), ""+response.body().toString(), Toast.LENGTH_LONG).show();
                    }
                }
                dialogLoader.hideProgressDialog();
            }

            @Override
            public void onFailure(Call<AddressData> call, Throwable t) {
                Toast.makeText(getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
                dialogLoader.hideProgressDialog();
            }
        });
    }



    //*********** Validate Address Form Inputs ********//

    private boolean validateAddressForm() {
        if (!ValidateInputs.isValidName(input_firstname.getText().toString().trim())) {
            input_firstname.setError(getString(R.string.invalid_first_name));
            return false;
        } else if (!ValidateInputs.isValidName(input_lastname.getText().toString().trim())) {
            input_lastname.setError(getString(R.string.invalid_last_name));
            return false;
        } else if (input_address.getText().toString().trim().isEmpty()) {
            input_address.setError(getString(R.string.invalid_address));
            return false;
        } else if (!ValidateInputs.isValidInput(input_country.getText().toString().trim())) {
            input_country.setError(getString(R.string.select_country));
            return false;
        } else if (!ValidateInputs.isValidInput(input_zone.getText().toString().trim())) {
            input_zone.setError(getString(R.string.select_zone));
            return false;
        } else if (!ValidateInputs.isValidInput(input_city.getText().toString().trim())) {
            input_city.setError(getString(R.string.enter_city));
            return false;
        } else if (!ValidateInputs.isValidNumber(input_postcode.getText().toString().trim())) {
            input_postcode.setError(getString(R.string.invalid_post_code));
            return false;
        }

/*
        else if(input_location.getText().toString().isEmpty()) {
            input_location.setError("Invalid Location Points");
            return false;
        }
*/

        else {
            return true;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                String lat = data.getStringExtra("lat");
                String lon = data.getStringExtra("lon");
                latitude = lat;
                longitude = lon;
                String address = Utilities.getCompleteAddressString(getContext(),Double.parseDouble(latitude),Double.parseDouble(longitude));
                input_location.setText(latitude + ", " + longitude);
                input_address.setText(address);
//                 place.getName();
//                 place.getAddress();
            }
            else {
                Log.e("ShippingAddress","Error in data fetching");
                // Toast.makeText(getActivity(),   "Error in data fetching", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getActivity(), connectionResult.getErrorMessage() + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        //mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        //mGoogleApiClient.disconnect();
    }

    @Override
    public void onPause() {
      /*  mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();*/
        super.onPause();
    }
}

