package com.themescoder.driver;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.themescoder.driver.R;
import com.themescoder.driver.api.RetrofitClient;
import com.themescoder.driver.api.ServerData;
import com.themescoder.driver.dialogs.ConnectionFailedDialog;
import com.themescoder.driver.models.DriverDetails;
import com.themescoder.driver.models.SettingsResponse;
import com.themescoder.driver.utils.PreferencesUtils;
import com.onesignal.OneSignal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
* Splash Screen is the very first screen showing when the app
* is open. An alert dialog will be shown if the network is not
* available. user have to wait on this screen until the data is being
* loaded from the web server.
*
* */

public class SplashActivity extends AppCompatActivity {

    /*
    * SPLASH_DISPLAY_LENGTH = 700 mili seconds.
    * this variable will be used in the case if user opens the app very
    * first time. when you dont have to
    * */
    private final int SPLASH_DISPLAY_LENGTH = 0;

    /*
    * this variable contains the Currunt deriver specific pincode.
    * details behind this specific pincode will get in this Activity.
    * when all the data will be downloaded user will jumped to next screen.
    * */
    private String driverPinCode;


    /*
    * this is the main function.
    * and called before anything starting.
    * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        checking for the intertet connection.
        if (!isNetworkAvailable()){
            // Network Available
            showAlert();
//          return if network isnot availabel and do not execute the line after this return.
            return;
        }

//      Proceed if the network is availabel.
        requestSettings();


    }

    private void requestSettings() {
        RetrofitClient.getInstance().getSettings()
                .enqueue(new Callback<SettingsResponse>() {
                    @Override
                    public void onResponse(Call<SettingsResponse> call, Response<SettingsResponse> response) {
                        if (response.isSuccessful()){
                            if (response.body().getSuccess().equalsIgnoreCase("1")) {
                                ServerData.settingsData = response.body().getData();
                                proceed();
                            } else {
                                Toast.makeText(SplashActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SplashActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SettingsResponse> call, Throwable t) {
                        Toast.makeText(SplashActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }


    /*
    * this method will show the alert dialog.
    * */
    private void showAlert(){
        final ConnectionFailedDialog dialog = new ConnectionFailedDialog(SplashActivity.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        dialog.tryagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (isNetworkAvailable()) {
                    requestSettings();
                } else {
                    showAlert();
                }
            }
        });
    }

    /*
    * this method will check the preferrences if any user is logged in last time.
    * if no user is logged in last time. this means the app is opening very first time.
    * and if app is opening very first time. the screen will be changed after some milli seconds.
    * else if any user wass logged in previously then the data associated will that user will be
    * downloaded while this progress bar is showing.
    * */
    private void proceed(){
        driverPinCode = PreferencesUtils.getString(SplashActivity.this, PreferencesUtils.CURRUNT_DRIVER_PIN_CODE, "");
        if (driverPinCode.trim().isEmpty()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    jumpToNext();
                }
            }, SPLASH_DISPLAY_LENGTH);
        } else {
            OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
                @Override
                public void idsAvailable(String userId, String registrationId) {
                    /*
                     * Making a call to the Server for getting the data behind the last logged in user pin code.
                     * */
                    Call<DriverDetails> call = RetrofitClient.getInstance().getUser(driverPinCode, userId);
                    call.enqueue(new Callback<DriverDetails>() {
                        @Override
                        public void onResponse(Call<DriverDetails> call, Response<DriverDetails> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getSuccess().equals("1")) {
                                    // Case Successful response.
                                    // Data is saved in the ServerData class for the later use.
                                    ServerData.currentDriver = response.body();
                                    // After saving the data jumo to next screen,
                                    jumpToNext();
                                } else {
                                    // Case when the call is executed but could not got response.
                                    Toast.makeText(SplashActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // Case when there is some error while getting the response.
                                Toast.makeText(SplashActivity.this, "Response is unsuccessful", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<DriverDetails> call, Throwable t) {
                            // Case when the call could not be executed.
                            Toast.makeText(SplashActivity.this, "Call is Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }



    /*
    * this method will take you to the new screen.
    * */
    private void jumpToNext() {
        Intent intent = new Intent();
        intent.setClass(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }


    /*
    * this method will check if network is available or not.
    * */
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
