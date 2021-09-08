package com.themescoder.androidstore.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

import com.themescoder.androidstore.app.App;
import com.themescoder.androidstore.models.device_model.AppSettingsDetails;
import com.themescoder.androidstore.app.MyAppPrefsManager;

import com.themescoder.androidstore.R;

import com.themescoder.androidstore.constant.ConstantValues;
import com.themescoder.androidstore.utils.Utilities;
import com.themescoder.androidstore.network.StartAppRequests;

import am.appwise.components.ni.NoInternetDialog;


/**
 * SplashScreen activity, appears on App Startup
 **/

public class SplashScreen extends AppCompatActivity {

    View rootView;
    ProgressBar progressBar;

    MyTask myTask;
    StartAppRequests startAppRequests;
    MyAppPrefsManager myAppPrefsManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(ConstantValues.THEME_ID);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash);


        Log.i("VC_Shop", "AndroidEcommerce_Version = " + ConstantValues.CODE_VERSION);

        progressBar = findViewById(R.id.progressBar);
        rootView = (View) findViewById(R.id.mainView);
        // Intializing No internet Dialogue
        new NoInternetDialog.Builder(SplashScreen.this).build();
        //noInternetDialog.show();
        // Bind Layout Views

        // Initializing StartAppRequests and PreferencesManager
        startAppRequests = new StartAppRequests(this);
        myAppPrefsManager = new MyAppPrefsManager(this);


//      ConstantValues.IS_ADMOBE_ENABLED = true;
        ConstantValues.LANGUAGE_ID = myAppPrefsManager.getUserLanguageId();
        ConstantValues.LANGUAGE_CODE = myAppPrefsManager.getUserLanguageCode();
        ConstantValues.IS_USER_LOGGED_IN = myAppPrefsManager.isUserLoggedIn();
        ConstantValues.IS_PUSH_NOTIFICATIONS_ENABLED = myAppPrefsManager.isPushNotificationsEnabled();
        ConstantValues.IS_LOCAL_NOTIFICATIONS_ENABLED = myAppPrefsManager.isLocalNotificationsEnabled();


        // Start MyTask after 3 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                myTask = new MyTask();
                myTask.execute();
            }
        }, 3000);

    }

    //*********** Sets App configuration ********//

    private void setAppConfig() {

        AppSettingsDetails appSettingsDetails = ((App) getApplicationContext()).getAppSettingsDetails();

        if (appSettingsDetails != null) {

            ConstantValues.DEFAULT_HOME_STYLE = getString(R.string.actionHome) + " " + appSettingsDetails.getHomeStyle();
            ConstantValues.DEFAULT_CATEGORY_STYLE = getString(R.string.actionCategory) + " " + appSettingsDetails.getCategoryStyle();
            //ConstantValues.DEFAULT_PRODUCT_CARD_STYLE = 0;
            ConstantValues.DEFAULT_PRODUCT_CARD_STYLE = (appSettingsDetails.getCardStyle() == null || appSettingsDetails.getCardStyle().isEmpty() ? 0 : Integer.parseInt(appSettingsDetails.getCardStyle()));
            ConstantValues.DEFAULT_BANNER_STYLE = (appSettingsDetails.getBannerStyle() == null || appSettingsDetails.getBannerStyle().isEmpty() ? 0 : Integer.parseInt(appSettingsDetails.getBannerStyle()));
            ConstantValues.MAINTENANCE_MODE = appSettingsDetails.getApp_web_environment();
            if (ConstantValues.MAINTENANCE_MODE == null)
                ConstantValues.MAINTENANCE_MODE = appSettingsDetails.getApp_web_environmentt();

            ConstantValues.CURRENCY_CODE = myAppPrefsManager.getCurrencyCode();
            ConstantValues.CURRENCY_SYMBOL = Utilities.getCurrencySymbol(ConstantValues.CURRENCY_CODE).replace("US", "");

            if (appSettingsDetails.getAppName() != null && !TextUtils.isEmpty(appSettingsDetails.getAppName())) {
//                ConstantValues.APP_HEADER = appSettingsDetails.getAppName();
                ConstantValues.APP_HEADER = getString(R.string.app_name);
            } else {
                ConstantValues.APP_HEADER = getString(R.string.app_name);
            }

            if (appSettingsDetails.getMaintenance_text() != null && !TextUtils.isEmpty(appSettingsDetails.getMaintenance_text())) {

                ConstantValues.MAINTENANCE_TEXT = appSettingsDetails.getMaintenance_text();
            }
            
            /*
            if (appSettingsDetails.getCurrencySymbol() != null  &&  !TextUtils.isEmpty(appSettingsDetails.getCurrencySymbol())) {
                ConstantValues.CURRENCY_SYMBOL = Utilities.getCurrencySymbol(appSettingsDetails.getCurrencySymbol());
            } else {
                ConstantValues.CURRENCY_SYMBOL = "₹";
            }
            */

            if (appSettingsDetails.getPacking_charge_tax() != null && !TextUtils.isEmpty(appSettingsDetails.getCurrencySymbol())) {
                ConstantValues.PACKING_CHARGE = appSettingsDetails.getPacking_charge_tax();
            } else {
                ConstantValues.PACKING_CHARGE = "0.0";
            }

            if (appSettingsDetails.getNewProductDuration() != null && !TextUtils.isEmpty(appSettingsDetails.getNewProductDuration())) {
                ConstantValues.NEW_PRODUCT_DURATION = Long.parseLong(appSettingsDetails.getNewProductDuration());
            } else {
                ConstantValues.NEW_PRODUCT_DURATION = 30;
            }


            ConstantValues.IS_GOOGLE_LOGIN_ENABLED = (appSettingsDetails.getGoogleLogin().equalsIgnoreCase("1"));
            ConstantValues.IS_FACEBOOK_LOGIN_ENABLED = (appSettingsDetails.getFacebookLogin().equalsIgnoreCase("1"));
            ConstantValues.IS_PHONE_LOGIN_ENABLED = (appSettingsDetails.getPhoneLogin().equalsIgnoreCase("1"));
            ConstantValues.IS_EMAIL_LOGIN_ENABLED = (appSettingsDetails.getEmailLogin().equalsIgnoreCase("1"));
            ConstantValues.IS_ADD_TO_CART_BUTTON_ENABLED = (appSettingsDetails.getCartButton().equalsIgnoreCase("1"));
            ConstantValues.IS_INVENTORY_ENABLED = (appSettingsDetails.getInventory().equalsIgnoreCase("1"));

            ConstantValues.IS_ADMOBE_ENABLED = (appSettingsDetails.getAdmob().equalsIgnoreCase("1"));
            ConstantValues.ADMOBE_ID = appSettingsDetails.getAdmobId();
            ConstantValues.AD_UNIT_ID_BANNER = appSettingsDetails.getAdUnitIdBanner();
            ConstantValues.AD_UNIT_ID_INTERSTITIAL = appSettingsDetails.getAdUnitIdInterstitial();


            myAppPrefsManager.setLocalNotificationsTitle(appSettingsDetails.getNotificationTitle());
            myAppPrefsManager.setLocalNotificationsDuration(appSettingsDetails.getNotificationDuration());
            myAppPrefsManager.setLocalNotificationsDescription(appSettingsDetails.getNotificationText());

        } else {
//            ConstantValues.APP_HEADER = getString(R.string.app_name);
            ConstantValues.APP_HEADER = getResources().getString(R.string.app_name);

            ConstantValues.CURRENCY_SYMBOL = "$";
            ConstantValues.NEW_PRODUCT_DURATION = 30;
            ConstantValues.IS_ADMOBE_ENABLED = false;

            ConstantValues.IS_GOOGLE_LOGIN_ENABLED = true;
            ConstantValues.IS_FACEBOOK_LOGIN_ENABLED = true;
            ConstantValues.IS_PHONE_LOGIN_ENABLED = true;
            ConstantValues.IS_EMAIL_LOGIN_ENABLED = true;
            ConstantValues.IS_ADD_TO_CART_BUTTON_ENABLED = true;
            ConstantValues.IS_INVENTORY_ENABLED = true;

            ConstantValues.DEFAULT_HOME_STYLE = getString(R.string.actionHome) + " " + 1;
            ConstantValues.DEFAULT_CATEGORY_STYLE = getString(R.string.actionCategory) + " " + 1;
            ConstantValues.DEFAULT_PRODUCT_CARD_STYLE = 0;
            ConstantValues.DEFAULT_BANNER_STYLE = 1;
        }

    }


    /************* MyTask is Inner Class, that handles StartAppRequests on Background Thread *************/

    private class MyTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            // Check for Internet Connection from the static method of Helper class
            if (Utilities.hasActiveInternetConnection(SplashScreen.this)) {

                // Call the method of StartAppRequests class to process App Startup Requests
                startAppRequests.StartRequests();

                return "1";
            } else {

                return "0";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result.equalsIgnoreCase("0")) {

                progressBar.setVisibility(View.GONE);

                // No Internet Connection
                Snackbar.make(rootView, getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
                        .setAction(getString(R.string.retry), new View.OnClickListener() {

                            // Handle the Retry Button Click
                            @Override
                            public void onClick(View v) {

                                progressBar.setVisibility(View.VISIBLE);

                                // Restart MyTask after 3 seconds
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        myTask = new MyTask();
                                        myTask.execute();
                                    }
                                }, 3000);
                            }
                        })
                        .show();

            } else {
                setAppConfig();

                if (myAppPrefsManager.isFirstTimeLaunch()) {
                    // Navigate to IntroScreen
                    startActivity(new Intent(getBaseContext(), IntroScreen.class));
                    finish();
                } else {
                    // Navigate to MainActivity
                    startActivity(new Intent(getBaseContext(), MainActivity.class));
                    finish();
                }
            }
        }

    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

}


