package com.themescoder.androidstore.constant;


import android.text.format.Formatter;
import android.util.Log;

import com.themescoder.androidstore.R;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * ConstantValues contains some constant variables, used all over the App.
 **/


public class ConstantValues {

    //*********** API Base URL ********//

    public static final String ECOMMERCE_URL = "YOUR_ECOMMERCE_URL";

    public static final String ECOMMERCE_CONSUMER_KEY = "YOUR_CONSUMER_KEY";
    public static final String ECOMMERCE_CONSUMER_SECRET = "YOUR_CONSUMER_SECRET_KEY";

    private static String TAG = "Consumer_IP";

    public static final String CODE_VERSION = "4.0.14";

    public static final boolean IS_CLIENT_ACTIVE = true;                               // "false" if compiling the project for Demo, "true" otherwise

    public static final String DEFAULT_NOTIFICATION = "onesignal";                      // "fcm" for FCM_Notifications, "onesignal" for OneSignal
    public static String NAVIGATION_STYLE = "side";                             // "bottom" for bottom navigation. "side" for side navigation.
    public static String DAYNIGHT_MODE = "day";                                 // "day" for light mode "night" for dark mode.

    public static int THEME_ID = R.style.AppTheme;

    public static String APP_HEADER;

    public static String MAINTENANCE_MODE;
    public static String MAINTENANCE_TEXT;

    public static String DEFAULT_HOME_STYLE;
    public static String DEFAULT_CATEGORY_STYLE;
    public static int DEFAULT_PRODUCT_CARD_STYLE;
    public static int DEFAULT_BANNER_STYLE;

    public static int LANGUAGE_ID;
    public static String LANGUAGE_CODE;
    public static String CURRENCY_SYMBOL;
    public static String CURRENCY_CODE;
    public static String PACKING_CHARGE;
    public static long NEW_PRODUCT_DURATION;

    public static boolean IS_GOOGLE_LOGIN_ENABLED;
    public static boolean IS_FACEBOOK_LOGIN_ENABLED;
    public static boolean IS_PHONE_LOGIN_ENABLED;
    public static boolean IS_EMAIL_LOGIN_ENABLED;
    public static boolean IS_ADD_TO_CART_BUTTON_ENABLED;
    public static boolean IS_INVENTORY_ENABLED;

    public static boolean IS_ADMOBE_ENABLED;
    public static String ADMOBE_ID;
    public static String AD_UNIT_ID_BANNER;
    public static String AD_UNIT_ID_INTERSTITIAL;

    public static boolean IS_RESTART = false;

    public static String ABOUT_US;
    public static String TERMS_SERVICES;
    public static String PRIVACY_POLICY;
    public static String REFUND_POLICY;
    public static String A_Z;

    public static boolean IS_USER_LOGGED_IN;
    public static boolean IS_PUSH_NOTIFICATIONS_ENABLED;
    public static boolean IS_LOCAL_NOTIFICATIONS_ENABLED;

    public static String PKG_NAME;
    public static String SHA1;

    public static final String PHONE_PATTERN = "^[987]\\d{9}$";

    public static final String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        String ip = Formatter.formatIpAddress(inetAddress.hashCode());
                        Log.i(TAG, "***** IP = " + ip);
                        return ip;
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e(TAG, ex.toString());
        }
        return null;
    }


}
