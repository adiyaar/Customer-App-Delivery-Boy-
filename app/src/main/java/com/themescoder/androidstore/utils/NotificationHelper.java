package com.themescoder.androidstore.utils;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.media.RingtoneManager;
import androidx.core.app.NotificationManagerCompat;

import com.themescoder.androidstore.R;
import com.themescoder.androidstore.activities.MainActivity;


/**
 * NotificationHelper is used to create new Notifications
 **/

public class NotificationHelper {
    
    
    public static final int NOTIFICATION_REQUEST_CODE = 100;
    
    
    //*********** Used to create Notifications ********//
    
    public static void showNewNotification(Context context, Intent intent, String title, String msg, Bitmap bitmap) {

        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent notificationIntent;

        if (intent != null) {
            notificationIntent = intent;
        }
        else {
            notificationIntent = new Intent(context.getApplicationContext(), MainActivity.class);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity((context), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT| PendingIntent.FLAG_ONE_SHOT);
    
    
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
    
        
        Notification.Builder builder = new Notification.Builder(context);
        if (bitmap != null)
            builder.setStyle(new Notification.BigPictureStyle().bigPicture(bitmap));
    
        
        // Create Notification
        Notification notification = builder
                .setContentTitle(title)
                .setContentText(msg)
                .setTicker(context.getString(R.string.app_name))
                .setSmallIcon(R.drawable.ic_logo)
                .setSound(notificationSound)
                .setLights(Color.RED, 3000, 3000)
                .setVibrate(new long[] { 1000, 1000 })
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();

    
        notificationManager.notify(NOTIFICATION_REQUEST_CODE, notification);
        
    }
    
    
}

