package com.themescoder.driver;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.themescoder.driver.R;
import com.themescoder.driver.api.RetrofitClient;
import com.themescoder.driver.api.ServerData;
import com.themescoder.driver.fragments.Dashboard;
import com.themescoder.driver.fragments.History;
import com.themescoder.driver.fragments.PlaceHolder;
import com.themescoder.driver.fragments.Settings;
import com.themescoder.driver.models.DeviceInfo;
import com.themescoder.driver.models.OrdersResponse;
import com.themescoder.driver.models.RegisterDeviceResponce;
import com.themescoder.driver.models.StatusResponse;
import com.themescoder.driver.services.TrackingService;
import com.themescoder.driver.utils.DialogUtils;
import com.themescoder.driver.utils.OrderStatuses;
import com.themescoder.driver.utils.PreferencesUtils;
import com.themescoder.driver.utils.Utilities;
import com.onesignal.OneSignal;

import java.util.ArrayList;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {


    private Context context;

    private boolean isExit = false;
    private static final int PERMISSIONS_REQUEST = 100;

    public static BottomNavigationView navigationView;

    private TextView title;
    private SwitchCompat switchCompat;
    private TextView myStatus;
    public static LinearLayout statusLayout;
    public static ImageView refreshbutton;

    public static boolean isRedirectFromNext = false;

    @Override
    protected void onStart() {
        super.onStart();
        context = MainActivity.this;
        RegisterDeviceForFCM(MainActivity.this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        context = MainActivity.this;

        // Toolbar
        title = findViewById(R.id.toolbar_title);
        statusLayout = findViewById(R.id.switchlayout);
        switchCompat = findViewById(R.id.myswitch);
        myStatus = findViewById(R.id.mystatus);
        refreshbutton = findViewById(R.id.refreshButton);

        // Bottom Navigation.
        navigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        initEverything();


        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
                    if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        DialogUtils.showProgressDialog(MainActivity.this);
                        changeStatus(isChecked);
                    } else {
                        switchCompat.setChecked(false);
                        Toast.makeText(context, "Please make sure your GSP is enabled", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    changeStatus(isChecked);
                }

            }
        });

        refreshbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshThis();
            }
        });

    }

    private void initLocationService() {
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission == PackageManager.PERMISSION_GRANTED) {
            startTrackerService();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST);
        }
    }
    private void startTrackerService() {
        startService(new Intent(this, TrackingService.class));
        Toast.makeText(this, getString(R.string.gps_enabled), Toast.LENGTH_SHORT).show();
    }

    public static void refreshThis() {
        switch (navigationView.getSelectedItemId()) {
            case R.id.navigation_dashboard:
                refreshDashboard();
                break;
            case R.id.navigation_history:
                refreshHistory();
                break;
        }
    }

    private static void refreshDashboard() {
        Dashboard.recyclerView.setVisibility(View.GONE);
        Dashboard.progressBar.setVisibility(View.VISIBLE);
        Call<OrdersResponse> call = RetrofitClient.getInstance().gatDeliveryOrders("1", ServerData.currentDriver.getData().get(0).getPassword() + "");
        call.enqueue(new Callback<OrdersResponse>() {
            @Override
            public void onResponse(Call<OrdersResponse> call, Response<OrdersResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equals("1")) {

                        ServerData.ordersResponse = response.body();
                        ServerData.dashboardObjectsList = new ArrayList<>();
                        ServerData.historyObjectsList = new ArrayList<>();

                        for (int i = 0; i < ServerData.ordersResponse.getData().size(); i++) {
                            if (ServerData.ordersResponse.getData().get(i).getOrdersStatusId() == OrderStatuses.COMPLETED
                                    || ServerData.ordersResponse.getData().get(i).getOrdersStatusId() == OrderStatuses.CANCEL) {
                                ServerData.historyObjectsList.add(ServerData.ordersResponse.getData().get(i));
                            } else {
                                ServerData.dashboardObjectsList.add(ServerData.ordersResponse.getData().get(i));
                            }
                        }

                        Dashboard.recyclerView.setVisibility(View.VISIBLE);
                        Dashboard.progressBar.setVisibility(View.GONE);
                        Dashboard.adapter.notifyDataSetChanged();

                    } else {
                        Dashboard.progressBar.setVisibility(View.GONE);
                    }
                } else {
                    Dashboard.progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<OrdersResponse> call, Throwable t) {
                Dashboard.progressBar.setVisibility(View.GONE);
            }
        });
    }

    private static void refreshHistory() {
        History.recyclerView.setVisibility(View.GONE);
        History.progressBar.setVisibility(View.VISIBLE);
        Call<OrdersResponse> call = RetrofitClient.getInstance().gatDeliveryOrders("1", ServerData.currentDriver.getData().get(0).getPassword() + "");
        call.enqueue(new Callback<OrdersResponse>() {
            @Override
            public void onResponse(Call<OrdersResponse> call, Response<OrdersResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equals("1")) {
                        ServerData.ordersResponse = response.body();
                        ServerData.dashboardObjectsList = new ArrayList<>();
                        ServerData.historyObjectsList = new ArrayList<>();

                        for (int i = 0; i < ServerData.ordersResponse.getData().size(); i++) {
                            if (ServerData.ordersResponse.getData().get(i).getOrdersStatusId() == OrderStatuses.COMPLETED
                                    || ServerData.ordersResponse.getData().get(i).getOrdersStatusId() == OrderStatuses.CANCEL) {
                                ServerData.historyObjectsList.add(ServerData.ordersResponse.getData().get(i));
                            } else {
                                ServerData.dashboardObjectsList.add(ServerData.ordersResponse.getData().get(i));
                            }
                        }

                        History.recyclerView.setVisibility(View.VISIBLE);
                        History.progressBar.setVisibility(View.GONE);
                        History.adapter.notifyDataSetChanged();

                    } else {
                        History.progressBar.setVisibility(View.GONE);
                    }
                } else {
                    History.progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<OrdersResponse> call, Throwable t) {
                History.progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void initEverything() {

        // Getting Preferences.
        String str = PreferencesUtils.getString(context, PreferencesUtils.ONLNE_STATUS, getString(R.string.offline));

        final long mystatus = ServerData.currentDriver.getData().get(0).getAvailabilityStatus();
        if (mystatus == 11) {
            str = getString(R.string.offline);
        } else if (mystatus == 8) {
            str = getString(R.string.online);
        }
        if (str.equals(getString(R.string.online))) {
            myStatus.setText(getString(R.string.online));
            switchCompat.setChecked(true);
            inflateFragment(Dashboard.class);
            title.setText(getString(R.string.title_dashboard));
            LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
            if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                if (ServerData.settingsData.getIsEnableLocation().equalsIgnoreCase("1"))
                    initLocationService();
            } else {
                Toast.makeText(context, getString(R.string.gps_warning), Toast.LENGTH_SHORT).show();
            }
        } else if (str.equals(getString(R.string.offline))) {
            title.setText(getString(R.string.title_settings));
            myStatus.setText(getString(R.string.offline));
            switchCompat.setChecked(false);
            inflateFragment(Settings.class);
            navigationView.setSelectedItemId(R.id.navigation_settings);
            DialogUtils.showStatusDialog(MainActivity.this);
            if (ServerData.settingsData.getIsEnableLocation().equalsIgnoreCase("1"))
                stopService(new Intent(MainActivity.this, TrackingService.class));
        } else {
            title.setText(getString(R.string.title_settings));
            myStatus.setText(getString(R.string.offline));
            switchCompat.setChecked(false);
            inflateFragment(Settings.class);
            navigationView.setSelectedItemId(R.id.navigation_settings);
            DialogUtils.showStatusDialog(MainActivity.this);
        }

    }


    private void changeStatus(final boolean isChecked) {
        String status = "";
        if (isChecked) {
            status = "8";
        } else {
            status = "11";
        }
        Call<StatusResponse> call = RetrofitClient.getInstance().changeStatus(status, ServerData.currentDriver.getData().get(0).getPassword() + "");
        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                if (response.isSuccessful()) {
                    if (!response.body().getSuccess().equals("1")) {
                        return;
                    }
                    DialogUtils.hideProgressDialog();
                    if (isChecked) {
                        myStatus.setText(getString(R.string.online));
                        inflateFragment(Dashboard.class);
                        PreferencesUtils.putString(context, PreferencesUtils.ONLNE_STATUS, getString(R.string.online));
                        navigationView.setSelectedItemId(R.id.navigation_dashboard);
                        if (ServerData.settingsData.getIsEnableLocation().equalsIgnoreCase("1"))
                            initLocationService();
                    } else {
                        myStatus.setText(getString(R.string.offline));
                        PreferencesUtils.putString(context, PreferencesUtils.ONLNE_STATUS, getString(R.string.offline));
                        if (ServerData.settingsData.getIsEnableLocation().equalsIgnoreCase("1"))
                            stopService(new Intent(MainActivity.this, TrackingService.class));
                    }
                } else {
                    DialogUtils.hideProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                DialogUtils.hideProgressDialog();
            }
        });
    }

    private void inflateFragment(Class fclas) {
        Fragment f = null;
        try {
            f = (Fragment) fclas.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, f).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            String str = PreferencesUtils.getString(context, PreferencesUtils.ONLNE_STATUS, getString(R.string.offline));

            switch (item.getItemId()) {
                case R.id.navigation_dashboard:
                    if (str.equals(getString(R.string.online))) {
                        inflateFragment(Dashboard.class);
                        title.setText(getString(R.string.title_dashboard));
                    } else {
                        inflateFragment(PlaceHolder.class);
                    }
                    return true;
                case R.id.navigation_history:
                    if (str.equals(getString(R.string.online))) {
                        inflateFragment(History.class);
                        title.setText(getString(R.string.title_history));
                    } else {
                        inflateFragment(PlaceHolder.class);
                    }
                    return true;
                case R.id.navigation_settings:
                    inflateFragment(Settings.class);
                    title.setText(getString(R.string.title_settings));
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onBackPressed() {
        if (this.isExit) {
            super.onBackPressed();
        } else {
            this.isExit = true;
            Toast.makeText(getApplicationContext(), getString(R.string.click_again_to_close), Toast.LENGTH_SHORT).show();
            new Handler(getMainLooper()).postDelayed(new Runnable() {
                public void run() {
                    isExit = false;
                }
            }, 1000);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[]
            grantResults) {

        if (requestCode == PERMISSIONS_REQUEST && grantResults.length == 1
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            startTrackerService();
        } else {

            Toast.makeText(this, getString(R.string.gps_warning), Toast.LENGTH_SHORT).show();
        }
    }


    public static void RegisterDeviceForFCM(final Context context) {


        DeviceInfo device = Utilities.getDeviceInfo(context);
        String userPin = PreferencesUtils.getString(context, PreferencesUtils.CURRUNT_DRIVER_PIN_CODE, "");
        String deviceID = OneSignal.getPermissionSubscriptionState().getSubscriptionStatus().getUserId();


        Call<RegisterDeviceResponce> call = RetrofitClient.getInstance()
                .registerDevice
                        (
                                deviceID,
                                device.getDeviceType(),
                                device.getDeviceRAM(),
                                device.getDeviceProcessors(),
                                device.getDeviceAndroidOS(),
                                device.getDeviceLocation(),
                                device.getDeviceModel(),
                                device.getDeviceManufacturer(),
                                userPin
                        );

        call.enqueue(new Callback<RegisterDeviceResponce>() {
            @Override
            public void onResponse(Call<RegisterDeviceResponce> call, Response<RegisterDeviceResponce> response) {

                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {

                        Log.i("notification", response.body().getMessage());
                        //Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();

                    } else {

                        Log.i("notification", response.body().getMessage());
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Log.i("notification", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<RegisterDeviceResponce> call, Throwable throwable) {
                Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}
