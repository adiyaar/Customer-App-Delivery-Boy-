package com.themescoder.androidstore.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.themescoder.androidstore.R;
import com.themescoder.androidstore.models.order_model.LocationData;

public class Order_Tracking_Activity extends AppCompatActivity implements OnMapReadyCallback {


    private GoogleMap mGoogleMap;
    Marker deliveryBoyMarker;

    String customerLatitude = "0.0";
    String customerLongitude = "0.0";
    String deliveryBoyName = "";
    String deliveryBoyID = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_order);

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.order_tracking_map);
        assert supportMapFragment != null;
        supportMapFragment.getMapAsync(Order_Tracking_Activity.this);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            customerLatitude = b.getString("Customer_Delivery_Lat");
            customerLongitude = b.getString("Customer_Delivery_Lng");
            deliveryBoyID = b.getString("Delivery_Boy_Pincode");
            deliveryBoyName = b.getString("Delivery_Boy_Name");
        }

    }

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

        if (!deliveryBoyID.isEmpty()) {

            MarkerOptions deliveryBoyMarkerOption = new MarkerOptions();
            deliveryBoyMarkerOption.position(new LatLng(0, 0));
            deliveryBoyMarkerOption.title(deliveryBoyName);
            deliveryBoyMarkerOption.snippet("Delivery Boy");
            deliveryBoyMarkerOption.draggable(false);
            //deliveryBoyMarkerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.deliveryboymarker));
            deliveryBoyMarker = mGoogleMap.addMarker(deliveryBoyMarkerOption);

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("location").child(deliveryBoyID);

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

                        LocationData location = dataSnapshot.getValue(LocationData.class);

                        deliveryBoyMarker.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));

                        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));
                    } else {
                        Toast.makeText(Order_Tracking_Activity.this, "Delivery boy details are missing", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
        } else {
            Toast.makeText(this, "Your rider info is empty", Toast.LENGTH_SHORT).show();
        }


        MarkerOptions vendorMarkerOpiton = new MarkerOptions();
        vendorMarkerOpiton.position(
                new LatLng(
                        Double.parseDouble(customerLatitude)
                        , Double.parseDouble(customerLongitude)));
        vendorMarkerOpiton.title("Customer Location");
        vendorMarkerOpiton.snippet("Customer location");
        vendorMarkerOpiton.draggable(false);
        //vendorMarkerOpiton.icon(BitmapDescriptorFactory.fromResource(R.drawable.vendormarker));
        mGoogleMap.addMarker(vendorMarkerOpiton);

        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
                Double.parseDouble(customerLatitude)
                , Double.parseDouble(customerLongitude)), 15));
        // Zoom in, animating the camera.
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 500, null);

    }
}
