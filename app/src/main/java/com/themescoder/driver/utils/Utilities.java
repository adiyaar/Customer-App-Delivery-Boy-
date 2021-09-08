package com.themescoder.driver.utils;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.themescoder.driver.models.DeviceInfo;

public class Utilities {

    public static DeviceInfo getDeviceInfo(Context context) {

        double lat = 0;
        double lng = 0;
        String IMEI = "";
        String NETWORK = "";
        String PROCESSORS = "";


        String UNIQUE_ID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        PROCESSORS = String.valueOf(Runtime.getRuntime().availableProcessors());

        ActivityManager actManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
        actManager.getMemoryInfo(memInfo);
        double totalRAM = Math.round( ((memInfo.totalMem /1024.0) /1024.0)  /1024.0 );


        DeviceInfo device = new DeviceInfo();

        device.setDeviceID(UNIQUE_ID);
        device.setDeviceType("Android");
        device.setDeviceUser(Build.USER);
        device.setDeviceModel(Build.BRAND +" "+Build.MODEL);
        device.setDeviceBrand(Build.BRAND);
        device.setDeviceSerial(Build.SERIAL);
        device.setDeviceSystemOS(System.getProperty("os.name"));
        device.setDeviceAndroidOS("Android "+ Build.VERSION.RELEASE);
        device.setDeviceManufacturer(Build.MANUFACTURER);
        device.setDeviceIMEI(IMEI);
        device.setDeviceRAM(totalRAM +"GB");
        device.setDeviceCPU(Build.UNKNOWN);
        device.setDeviceStorage(Build.UNKNOWN);
        device.setDeviceProcessors(PROCESSORS);
        device.setDeviceIP(Build.UNKNOWN);
        device.setDeviceMAC(Build.UNKNOWN);
        device.setDeviceNetwork(NETWORK);
        device.setDeviceLocation(lat +", "+ lng);
        device.setDeviceBatteryLevel(Build.UNKNOWN);
        device.setDeviceBatteryStatus(Build.UNKNOWN);

        return device;
    }

    public static void doMakeACall(Activity activity, String phoneNumber){
        String[] permissions = {Manifest.permission.CALL_PHONE};

        if (ContextCompat.checkSelfPermission(activity.getApplicationContext(),
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + phoneNumber));
            activity.startActivity(callIntent);
            // do your work.
        } else {
            ActivityCompat.requestPermissions(activity,
                    permissions,
                    123);
        }
    }
}
