package com.themescoder.androidstore.utils;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import com.themescoder.androidstore.activities.SplashScreen;
import com.themescoder.androidstore.app.MyAppPrefsManager;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;


/**
 * NotificationHelper is used to Schedule Local Notifications
 **/

public class NotificationScheduler {

    private static final int NOTIFICATION_REQUEST_CODE = 100;
    private static long ALARM_INTERVAL = AlarmManager.INTERVAL_DAY;

    
    
    //*********** Used to initialize Scheduled Notifications ********//
    
    public static void setReminder(Context context, Class<?> cls) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 3600);
        
        
        // Enable a receiver
        ComponentName receiver = new ComponentName(context, cls);
        PackageManager pm = context.getPackageManager();
        
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);


        Intent notificationIntent = new Intent(context, cls);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        }
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_REQUEST_CODE, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);


        MyAppPrefsManager myAppPrefsManager = new MyAppPrefsManager(context);

        if (myAppPrefsManager.getLocalNotificationsDuration().equalsIgnoreCase("month")) {
            ALARM_INTERVAL = 30 * AlarmManager.INTERVAL_DAY;
        }
        else if (myAppPrefsManager.getLocalNotificationsDuration().equalsIgnoreCase("year")) {
            ALARM_INTERVAL = 365 * AlarmManager.INTERVAL_DAY;
        }
        else {
            ALARM_INTERVAL = AlarmManager.INTERVAL_DAY;
        }


        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), ALARM_INTERVAL, pendingIntent);
        
    }
    
    
    
    //*********** Used to cancel the Scheduled Notifications ********//
    
    public static void cancelReminder(Context context, Class<?> cls) {
        
        // Disable a receiver
        ComponentName receiver = new ComponentName(context, cls);
        PackageManager pm = context.getPackageManager();
        
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);

        Intent notificationIntent = new Intent(context, SplashScreen.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_REQUEST_CODE, notificationIntent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();
        
    }
    
}

