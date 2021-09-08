package com.themescoder.androidstore.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.LatLng;

import com.themescoder.androidstore.R;
import com.themescoder.androidstore.app.App;
import com.themescoder.androidstore.constant.ConstantValues;
import com.themescoder.androidstore.customs.CustomScrollMapFragment;
import com.themescoder.androidstore.customs.DialogLoader;
import com.themescoder.androidstore.models.contact_model.ContactUsData;
import com.themescoder.androidstore.models.device_model.AppSettingsDetails;
import com.themescoder.androidstore.network.APIClient;
import com.themescoder.androidstore.utils.ValidateInputs;

import am.appwise.components.ni.NoInternetDialog;
import retrofit2.Call;
import retrofit2.Callback;


public class ContactUs extends Fragment implements OnMapReadyCallback {

    View rootView;

    DialogLoader dialogLoader;

    Button btn_contact_us;
    EditText ed_name, ed_email, ed_message;
    TextView tv_address, tv_email, tv_telephone;
    CoordinatorLayout coordinator_container;

    private GoogleMap mGoogleMap;
    private AppSettingsDetails appSettings;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.contact_us, container, false);

        // Enable Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
        //MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.actionContactUs));

        NoInternetDialog noInternetDialog = new NoInternetDialog.Builder(getContext()).build();
        //noInternetDialog.show();


        // Get AppSettingsDetails from ApplicationContext
        appSettings = ((App) getContext().getApplicationContext()).getAppSettingsDetails();


        // Binding Layout Views
        btn_contact_us = (Button) rootView.findViewById(R.id.btn_contact_us);
        ed_name = (EditText) rootView.findViewById(R.id.ed_name);
        ed_email = (EditText) rootView.findViewById(R.id.ed_email);
        ed_message = (EditText) rootView.findViewById(R.id.ed_message);
        tv_address = (TextView) rootView.findViewById(R.id.tv_address);
        tv_email = (TextView) rootView.findViewById(R.id.tv_email);
        tv_telephone = (TextView) rootView.findViewById(R.id.tv_telephone);
        coordinator_container = (CoordinatorLayout) rootView.findViewById(R.id.coordinator_container);

        /*SupportMapFragment mapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.user_location_map));
        mapFragment.getMapAsync(this);*/

        dialogLoader = new DialogLoader(getContext());


        tv_address.setText(appSettings.getAddress());
        tv_email.setText(appSettings.getContactUsEmail());
        tv_telephone.setText(appSettings.getPhoneNo());


        btn_contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ValidateInputs.isValidName(ed_name.getText().toString())) {
                    if (ValidateInputs.isValidEmail(ed_email.getText().toString())) {
                        if (!"".equalsIgnoreCase(ed_message.getText().toString())) {

                            hideKeyboardFrom(getContext());

                            ContactWithUs();

                        } else {
                            ed_message.setError(getString(R.string.enter_message));
                        }
                    } else {
                        ed_email.setError(getString(R.string.invalid_email));
                    }
                } else {
                    ed_name.setError(getString(R.string.enter_name));
                }

            }
        });


        return rootView;
    }


    //*********** Called after onCreateView() has returned, but before any saved state has been restored in to the view ********//

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CustomScrollMapFragment mapFragment = ((CustomScrollMapFragment) getChildFragmentManager().findFragmentByTag("mapFragment"));
        if (mapFragment == null) {
            mapFragment = new CustomScrollMapFragment();
            getChildFragmentManager().beginTransaction()
                    .add(R.id.user_location_map, mapFragment, "mapFragment")
                    .commit();
            getChildFragmentManager().executePendingTransactions();
        }
        mapFragment.getMapAsync(this);

        mapFragment.setListener(new CustomScrollMapFragment.OnTouchListener() {
            @Override
            public void onTouch() {
                coordinator_container.requestDisallowInterceptTouchEvent(true);
            }
        });
    }


    //*********** Triggered when the Map is ready to be used ********//

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //googleMap.setMyLocationEnabled(true);
        mGoogleMap.setTrafficEnabled(false);
        mGoogleMap.setIndoorEnabled(false);
        mGoogleMap.setBuildingsEnabled(true);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.getUiSettings().setZoomGesturesEnabled(true);

        double latitude = 0.00;
        double longitude = 0.00;
        try {
            latitude = Double.parseDouble(appSettings.getLatitude());
            longitude = Double.parseDouble(appSettings.getLongitude());
        } catch (Exception e) {
            //
        }

        drawMarker(latitude, longitude);
    }


    //*********** Draws location marker on given location ********//

    private void drawMarker(double latitude, double longitude) {
        mGoogleMap.clear();

        MarkerOptions markerOptions = new MarkerOptions();

        markerOptions.position(new LatLng(latitude, longitude));
        markerOptions.title(ConstantValues.APP_HEADER);
        markerOptions.snippet("Lat:" + latitude + ", Lng:" + longitude);

        markerOptions.draggable(false);

        mGoogleMap.addMarker(markerOptions);

        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15));
    }

    /*********** Send Feedback to the Server ********/

    public void ContactWithUs() {

        dialogLoader.showProgressDialog();

        Call<ContactUsData> call = APIClient.getInstance()
                .contactUs
                        (
                                ed_name.getText().toString().trim(),
                                ed_email.getText().toString().trim(),
                                ed_message.getText().toString().trim()
                        );

        call.enqueue(new Callback<ContactUsData>() {
            @Override
            public void onResponse(Call<ContactUsData> call, retrofit2.Response<ContactUsData> response) {

                dialogLoader.hideProgressDialog();

                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {

                        Snackbar.make(rootView, response.body().getMessage(), Snackbar.LENGTH_SHORT).show();

                    } else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        Snackbar.make(rootView, response.body().getMessage(), Snackbar.LENGTH_LONG).show();

                    } else {
                        // Unable to get Success status
                        Snackbar.make(rootView, getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ContactUsData> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(getContext(), "NetworkCallFailure : " + t, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void hideKeyboardFrom(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(ed_name.getWindowToken(), 0);
    }


}

