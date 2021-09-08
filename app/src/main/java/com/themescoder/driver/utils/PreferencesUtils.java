package com.themescoder.driver.utils;

import android.content.Context;
import android.content.SharedPreferences.Editor;


public class PreferencesUtils {

    private static String PREFERENCE_NAME = "VectorCoder_DeliveryBoy_pref";
    public static String LOGED_IN_USER = "deliveryboy_logedin_user";
    public static String ONLNE_STATUS = "deliveryboy_online_status";
    public static String DONT_SHOW_AGAIN_DIALOG = "deliveryboy_dontshowagain_dialog";

    public static String CURRUNT_DRIVER_PIN_CODE = "currunt_driver_pin_code";

    private PreferencesUtils() {
        throw new AssertionError();
    }

    public static void putString(Context context, String key, String value) {
        Editor editor = context.getSharedPreferences(PREFERENCE_NAME, 0).edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(Context context, String key) {
        return getString(context, key, null);
    }

    public static String getString(Context context, String key, String defaultValue) {
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).getString(key, defaultValue);
    }

    public static void putInt(Context context, String key, int value) {
        Editor editor = context.getSharedPreferences(PREFERENCE_NAME, 0).edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int getInt(Context context, String key) {
        return getInt(context, key, -1);
    }

    public static int getInt(Context context, String key, int defaultValue) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getInt(key, defaultValue);
    }

    public static void putLong(Context context, String key, long value) {
        Editor editor = context.getSharedPreferences(PREFERENCE_NAME, 0).edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static long getLong(Context context, String key) {
        return getLong(context, key, -1);
    }

    public static long getLong(Context context, String key, long defaultValue) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getLong(key, defaultValue);
    }

    public static void putFloat(Context context, String key, float value) {
        Editor editor = context.getSharedPreferences(PREFERENCE_NAME, 0).edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public static float getFloat(Context context, String key) {
        return getFloat(context, key, -1.0f);
    }

    public static float getFloat(Context context, String key, float defaultValue) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getFloat(key, defaultValue);
    }

    public static void putBoolean(Context context, String key, boolean value) {
        Editor editor = context.getSharedPreferences(PREFERENCE_NAME, 0).edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getBoolean(Context context, String key) {
        return getBoolean(context, key, false);
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getBoolean(key, defaultValue);
    }

}
