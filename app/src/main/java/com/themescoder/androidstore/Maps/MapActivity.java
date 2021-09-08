package com.themescoder.androidstore.Maps;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;

import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.internal.impl.net.pablo.PlaceResult;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.themescoder.androidstore.R;
import com.themescoder.androidstore.adapters.AutoCompleteAdapter;
import com.themescoder.androidstore.utils.Utilities;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener, AutoCompleteAdapter.ClickListener {

    private GoogleMap mMap;
    AppCompatButton submitBtn;
    Marker myMarker;
    PlaceResult.Geometry.Location mLocation;
    private FusedLocationProviderClient fusedLocationClient;
    AppCompatImageView closeBtn;

    private final int REQUEST_CHECK_SETTINGS = 100;
    String TAG = "MapActivity";



    AutoCompleteTextView autoCompleteTextView;
    AutoCompleteAdapter adapter;
   // TextView responseView;
    PlacesClient placesClient;
    String cityName;
    private AutoCompleteAdapter mAutoCompleteAdapter;
    private RecyclerView recyclerView;
    EditText place_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        displayLocationSettingsRequest(MapActivity.this);
        submitBtn = findViewById(R.id.submitBtn);
        closeBtn = findViewById(R.id.closeBtn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Add a marker in Sydney and move the camera
                if(myMarker!=null) {
                    LatLng position = myMarker.getPosition(); //
                    Intent resultIntent = new Intent();
                    // TODO Add extras or a data URI to this intent as appropriate.
                    resultIntent.putExtra("lat", "" + position.latitude);
                    resultIntent.putExtra("lon", "" + position.longitude);
                    setResult(Activity.RESULT_OK, resultIntent);
                }
                finish();
            }
        });

        //responseView = findViewById(R.id.response);

        String apiKey = getString(R.string.place_picker_id);
        if(apiKey.isEmpty()){
           Toast.makeText(MapActivity.this,getString(R.string.error),Toast.LENGTH_SHORT).show();
            return;
        }

        // Setup Places Client
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }

        placesClient = Places.createClient(this);


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationClickListener(this);

        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                getLastKnownLocation();
                return false;
            }
        });

        if(isGpsEnable()){
            getLastKnownLocation();
        }



    }



    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        mMap.clear();

    }

    private void getLastKnownLocation(){
        mMap.clear();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {

                            LatLng myLocation = new LatLng(location.getLatitude(),location.getLongitude());

                            Log.e("cityName", Utilities.getCityCountry(MapActivity.this,location.getLatitude(),location.getLongitude()));
                            cityName = Utilities.getCityCountry(MapActivity.this,location.getLatitude(),location.getLongitude());
                            // Logic to handle location object
                            myMarker=mMap.addMarker(new MarkerOptions().position(myLocation)
                                    .title(Utilities.getCityCountry(MapActivity.this,location.getLatitude(),location.getLongitude())));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                            myMarker.showInfoWindow();

                            initAutoCompleteTextView(cityName);
                           // Log.d("location", "Latitude:" + location.getLatitude() + "  " + "Longitude:" + location.getLongitude());
                        }
                    }
                });
    }

    private void displayLocationSettingsRequest(Context context) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);


        Task<LocationSettingsResponse> task  = LocationServices.getSettingsClient(MapActivity.this).checkLocationSettings(builder.build());

        task.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(Task<LocationSettingsResponse> task) {
                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    // All location settings are satisfied. The client can initialize location
                    // requests here.
                } catch (ApiException exception) {
                    switch (exception.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied. But could be fixed by showing the
                            // user a dialog.
                            try {
                                // Cast to a resolvable exception.
                                ResolvableApiException resolvable = (ResolvableApiException) exception;
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                resolvable.startResolutionForResult(
                                        MapActivity.this,
                                        REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            } catch (ClassCastException e) {
                                // Ignore, should be an impossible error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have no way to fix the
                            // settings so we won't show the dialog.
                            //Log.e(TAG,states.toString());
                            break;
                    }
                }
            }
        });

    }

    private boolean isGpsEnable(){
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        if ( manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // All required changes were successfully made
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getLastKnownLocation();
                            }
                        },5000);

                       // Log.e(TAG,states.toString());
                        break;
                    case Activity.RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to

                        Log.e(TAG,states.toString());
                        break;
                    default:
                        break;
                }
                break;
        }
    }


    private void initAutoCompleteTextView(String cityName) {

        recyclerView = (RecyclerView) findViewById(R.id.places_recycler_view);
        place_search =  findViewById(R.id.place_search);
        place_search.addTextChangedListener(filterTextWatcher);

        mAutoCompleteAdapter = new AutoCompleteAdapter(this,cityName);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAutoCompleteAdapter.setClickListener(this);
        recyclerView.setAdapter(mAutoCompleteAdapter);
        mAutoCompleteAdapter.notifyDataSetChanged();

       /* autoCompleteTextView = findViewById(R.id.auto);
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setOnItemClickListener(autocompleteClickListener);
        adapter = new AutoCompleteAdapter(this,cityName);
        autoCompleteTextView.setAdapter(adapter);*/
    }

    private TextWatcher filterTextWatcher = new TextWatcher() {
        public void afterTextChanged(Editable s) {
            if (!s.toString().equals("")) {
                mAutoCompleteAdapter.getFilter().filter(s.toString());
                if (recyclerView.getVisibility() == View.GONE) {recyclerView.setVisibility(View.VISIBLE);}
            } else {
                if (recyclerView.getVisibility() == View.VISIBLE) {recyclerView.setVisibility(View.GONE);}
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
        public void onTextChanged(CharSequence s, int start, int before, int count) { }
    };


    @Override
    public void click(Place place) {
        // Toast.makeText(this, place.getAddress()+", "+place.getLatLng().latitude+place.getLatLng().longitude, Toast.LENGTH_SHORT).show();
        mMap.clear();
        double lat,lng;
        lat = place.getLatLng().latitude;
        lng = place.getLatLng().longitude;
        LatLng myLocation = new LatLng(lat,lng);

        Log.e("cityName",Utilities.getCityCountry(MapActivity.this,lat,lng));
        // Logic to handle location object
        myMarker=mMap.addMarker(new MarkerOptions().position(myLocation)
                .title(Utilities.getCityCountry(MapActivity.this,lat,lng)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        myMarker.showInfoWindow();
        place_search.setText("");
    }

 /*   private AdapterView.OnItemClickListener autocompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            try {
                final AutocompletePrediction item = adapter.getItem(i);
                String placeID = null;
                if (item != null) {
                    placeID = item.getPlaceId();
                }

//                To specify which data types to return, pass an array of Place.Fields in your FetchPlaceRequest
//                Use only those fields which are required.

                List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS
                        , Place.Field.LAT_LNG);

                FetchPlaceRequest request = null;
                if (placeID != null) {
                    request = FetchPlaceRequest.builder(placeID, placeFields)
                            .build();
                }

                if (request != null) {
                    placesClient.fetchPlace(request).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onSuccess(FetchPlaceResponse task) {

                            LatLng myLocation = task.getPlace().getLatLng();
                            // Logic to handle location object
                            myMarker=mMap.addMarker(new MarkerOptions().position(myLocation)
                                    .title(Utilities.getCityCountry(MapActivity.this,myLocation.latitude,myLocation.longitude)));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                            myMarker.showInfoWindow();
                            Log.d("location", "Latitude:" + myLocation.latitude + "\n" + "Longitude:" + myLocation.longitude);

                            //responseView.setText(task.getPlace().getName() + "\n" + task.getPlace().getAddress());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                            //responseView.setText(e.getMessage());
                            Log.d("SearchPlace",e.getMessage());
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };*/

}
