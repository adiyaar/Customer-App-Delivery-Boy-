package com.themescoder.androidstore.services;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.themescoder.androidstore.activities.MainActivity;
import com.themescoder.androidstore.constant.ConstantValues;
import com.themescoder.androidstore.network.StartAppRequests;
import com.themescoder.androidstore.utils.NotificationHelper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * FirebaseInstanceIdService Gets FCM instance ID token from Firebase Cloud Messaging Server
 */

public class MyFirebaseInstanceIDService extends FirebaseMessagingService {
    
    
    //*********** Called whenever the Token is Generated or Refreshed ********//

    //*********** Called when the Notification is Received ********//
    @Override
    public void onNewToken(String s) {
        Log.e("NEW_TOKEN", s);
        if (ConstantValues.DEFAULT_NOTIFICATION.equalsIgnoreCase("fcm")) {

            StartAppRequests.RegisterDeviceForFCM(getApplicationContext());

        }
    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Bitmap notificationBitmap = null;
        String notification_title, notification_message, notification_image = "";


        if (remoteMessage.getData().size() > 0) {
            notification_title = remoteMessage.getData().get("title");
            notification_message = remoteMessage.getData().get("message");
            notification_image = remoteMessage.getData().get("image");
        } else {
            notification_title = remoteMessage.getNotification().getTitle();
            notification_message = remoteMessage.getNotification().getBody();
        }


        notificationBitmap = getBitmapFromUrl(notification_image);


        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        NotificationHelper.showNewNotification
                (
                        getApplicationContext(),
                        notificationIntent,
                        notification_title,
                        notification_message,
                        notificationBitmap
                );

    }


    public Bitmap getBitmapFromUrl(String imageUrl) {
        if ("".equalsIgnoreCase(imageUrl)) {
            return null;
        }
        else {
            try {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                return BitmapFactory.decodeStream(input);

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
    
}
