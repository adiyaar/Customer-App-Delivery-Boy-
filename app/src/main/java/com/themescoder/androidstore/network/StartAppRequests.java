package com.themescoder.androidstore.network;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.themescoder.androidstore.R;
import com.google.firebase.iid.FirebaseInstanceId;
import com.onesignal.OneSignal;

import java.io.IOException;

import com.themescoder.androidstore.app.App;
import com.themescoder.androidstore.utils.Utilities;
import com.themescoder.androidstore.constant.ConstantValues;
import com.themescoder.androidstore.models.user_model.UserData;
import com.themescoder.androidstore.models.banner_model.BannerData;
import com.themescoder.androidstore.models.category_model.CategoryData;
import com.themescoder.androidstore.models.device_model.AppSettingsData;
import com.themescoder.androidstore.models.device_model.DeviceInfo;
import com.themescoder.androidstore.models.pages_model.PagesDetails;
import com.themescoder.androidstore.models.pages_model.PagesData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


/**
 * StartAppRequests contains some Methods and API Requests, that are Executed on Application Startup
 **/

public class StartAppRequests {

    private App app = new App();


    public StartAppRequests(Context context) {
        app = ((App) context.getApplicationContext());
    }



    //*********** Contains all methods to Execute on Startup ********//

    public void StartRequests(){

        //RequestBanners();
        //RequestAllCategories();
        RequestAppSetting();
        RequestStaticPagesData();
        
    }
    
    
    
    //*********** Register Device to Admin Panel with the Device's Info ********//
    
    public static void RegisterDeviceForFCM(final Context context) {
    
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserInfo", MODE_PRIVATE);
    
        String deviceID = "";
        DeviceInfo device = Utilities.getDeviceInfo(context);
        String customer_ID = sharedPreferences.getString("userID", "");
        
        
        if (ConstantValues.DEFAULT_NOTIFICATION.equalsIgnoreCase("onesignal")) {
            deviceID = OneSignal.getPermissionSubscriptionState ().getSubscriptionStatus().getUserId();
        }
        else if (ConstantValues.DEFAULT_NOTIFICATION.equalsIgnoreCase("fcm")) {
            deviceID = FirebaseInstanceId.getInstance().getToken();
        }
        
        
        
        Call<UserData> call = APIClient.getInstance()
                .registerDeviceToFCM
                        (
                                deviceID,
                                device.getDeviceType(),
                                device.getDeviceRAM(),
                                device.getDeviceProcessors(),
                                device.getDeviceAndroidOS(),
                                device.getDeviceLocation(),
                                device.getDeviceModel(),
                                device.getDeviceManufacturer(),
                                customer_ID
                        );
        
        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, retrofit2.Response<UserData> response) {
                
                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {
                        
                        Log.i("notification", response.body().getMessage());
//                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    
                    }
                    else {
                        
                        Log.i("notification", response.body().getMessage());
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Log.i("notification", response.errorBody().toString());
                }
            }
            
            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
//                Toast.makeText(context, "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
        
    }



    //*********** API Request Method to Fetch App Banners ********//

    public void RequestBanners() {
    
        Call<BannerData> call = APIClient.getInstance()
                .getBanners(ConstantValues.LANGUAGE_ID);
    
        try {
            Response<BannerData> response = call.execute();
    
            BannerData bannerData = new BannerData();
        
            if (response.isSuccessful()) {
    
                bannerData = response.body();
    
                if (!TextUtils.isEmpty(bannerData.getSuccess()))
                    app.setBannersList(bannerData.getData());
            
            }
        
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }


    //*********** API Request Method to Fetch All Categories ********//
    
    public void RequestAllCategories() {
    
        Call<CategoryData> call = APIClient.getInstance()
                .getAllCategories
                        (
                                ConstantValues.LANGUAGE_ID
                        );
        
        try {
            Response<CategoryData> response = call.execute();

            CategoryData categoryData = new CategoryData();
            
            if (response.isSuccessful()) {

                String json= new Gson().toJson(response.body());
                categoryData = response.body();

                if (!TextUtils.isEmpty(categoryData.getSuccess()))
                    app.setCategoriesList(categoryData.getData());
            
            }
        
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }



    //*********** Request App Settings from the Server ********//
    
    private void RequestAppSetting() {
    
        Call<AppSettingsData> call = APIClient.getInstance()
                .getAppSetting();
    
        try {
            Response<AppSettingsData> response = call.execute();
        
            if (response.isSuccessful()) {
    

                AppSettingsData appSettingsData = null;
    
                appSettingsData = response.body();
                String strJson = new Gson().toJson(appSettingsData);
                if (!TextUtils.isEmpty(appSettingsData.getSuccess()))
                    app.setAppSettingsDetails(appSettingsData.getAppDetails());

            }
           else {
               
               Log.e("Appsettings","Response is not successful");
            }
        
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    
    //*********** Request Static Pages Data from the Server ********//
    
    private void RequestStaticPagesData() {
    
        ConstantValues.ABOUT_US = app.getString(R.string.lorem_ipsum);
        ConstantValues.TERMS_SERVICES = app.getString(R.string.lorem_ipsum);
        ConstantValues.PRIVACY_POLICY = app.getString(R.string.lorem_ipsum);
        ConstantValues.REFUND_POLICY = app.getString(R.string.lorem_ipsum);
        ConstantValues.A_Z = app.getString(R.string.lorem_ipsum);
    
    
        Call<PagesData> call = APIClient.getInstance()
                .getStaticPages
                        (
                                ConstantValues.LANGUAGE_ID
                        );
    
        try {
            Response<PagesData> response = call.execute();
    
            PagesData pages = new PagesData();
            
            if (response.isSuccessful()) {
                
                pages = response.body();
    
                if (pages.getSuccess().equalsIgnoreCase("1")) {
        
                    app.setStaticPagesDetails(pages.getPagesData());
        
                    for (int i=0;  i<pages.getPagesData().size();  i++) {
                        PagesDetails page = pages.getPagesData().get(i);
            
                        if (page.getSlug().equalsIgnoreCase("about-us")) {
                            ConstantValues.ABOUT_US = page.getDescription();
                        }
                        else if (page.getSlug().equalsIgnoreCase("term-services")) {
                            ConstantValues.TERMS_SERVICES = page.getDescription();
                        }
                        else if (page.getSlug().equalsIgnoreCase("privacy-policy")) {
                            ConstantValues.PRIVACY_POLICY = page.getDescription();
                        }
                        else if (page.getSlug().equalsIgnoreCase("refund-policy")) {
                            ConstantValues.REFUND_POLICY = page.getDescription();
                        }
                        else if(page.getSlug().equalsIgnoreCase("A-Z")){
                            ConstantValues.A_Z = page.getDescription();
                        }
                    }
                }
            }
        
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
}
