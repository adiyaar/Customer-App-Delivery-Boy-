package com.themescoder.androidstore.app;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * MyAppPrefsManager handles some Prefs of AndroidShopApp Application
 **/


public class MyAppPrefsManager {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor prefsEditor;
    
    private int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "AndroidShopApp_Prefs";


    private static final String USER_LANGUAGE_ID  = "language_ID";
    private static final String USER_LANGUAGE_CODE  = "language_Code";
    private static final String CURRENCY_CODE = "currency_code";
    private static final String APPLICATION_VERSION = "application_version";
    private static final String IS_USER_LOGGED_IN = "isLogged_in";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String IS_PUSH_NOTIFICATIONS_ENABLED = "isPushNotificationsEnabled";
    private static final String IS_LOCAL_NOTIFICATIONS_ENABLED = "isLocalNotificationsEnabled";

    private static final String LOCAL_NOTIFICATIONS_TITLE = "localNotificationsTitle";
    private static final String LOCAL_NOTIFICATIONS_DURATION = "localNotificationsDuration";
    private static final String LOCAL_NOTIFICATIONS_DESCRIPTION = "localNotificationsDescription";

    private static final String Skip_For_Again = "skipMessage";

    public MyAppPrefsManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        prefsEditor = sharedPreferences.edit();
    }
    
    public void setUserLanguageId(int langID) {
        prefsEditor.putInt(USER_LANGUAGE_ID, langID);
        prefsEditor.commit();
    }
    
    public Integer getUserLanguageId() {
        return sharedPreferences.getInt(USER_LANGUAGE_ID, 1);
    }
    
    public void setUserLanguageCode(String langCode) {
        prefsEditor.putString(USER_LANGUAGE_CODE, langCode);
        prefsEditor.commit();
    }

    public String getUserLanguageCode() {
        return sharedPreferences.getString(USER_LANGUAGE_CODE, "en");
    }

    public  void setCurrencyCode(String currencyCode){
        prefsEditor.putString(CURRENCY_CODE,currencyCode);
        prefsEditor.commit();
    }

    public String getCurrencyCode(){
        return sharedPreferences.getString(CURRENCY_CODE, "USD");
    }

    public String getApplicationVersion() {
        return sharedPreferences.getString(APPLICATION_VERSION, "");
    }

    public void setApplicationVersion(String applicationVersion) {
        prefsEditor.putString(APPLICATION_VERSION, applicationVersion);
        prefsEditor.commit();
    }

    public void setUserLoggedIn(boolean isUserLoggedIn) {
        prefsEditor.putBoolean(IS_USER_LOGGED_IN, isUserLoggedIn);
        prefsEditor.commit();
    }

    public boolean isUserLoggedIn() {
        return sharedPreferences.getBoolean(IS_USER_LOGGED_IN, false);
    }

    public void setFirstTimeLaunch(boolean isFirstTimeLaunch) {
        prefsEditor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTimeLaunch);
        prefsEditor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return sharedPreferences.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setPushNotificationsEnabled(boolean isPushNotificationsEnabled) {
        prefsEditor.putBoolean(IS_PUSH_NOTIFICATIONS_ENABLED, isPushNotificationsEnabled);
        prefsEditor.commit();
    }

    public boolean isPushNotificationsEnabled() {
        return sharedPreferences.getBoolean(IS_PUSH_NOTIFICATIONS_ENABLED, true);
    }

    public void setLocalNotificationsEnabled(boolean isLocalNotificationsEnabled) {
        prefsEditor.putBoolean(IS_LOCAL_NOTIFICATIONS_ENABLED, isLocalNotificationsEnabled);
        prefsEditor.commit();
    }

    public boolean isLocalNotificationsEnabled() {
        return sharedPreferences.getBoolean(IS_LOCAL_NOTIFICATIONS_ENABLED, true);
    }

    public void setLocalNotificationsTitle(String localNotificationsTitle) {
        prefsEditor.putString(LOCAL_NOTIFICATIONS_TITLE, localNotificationsTitle);
        prefsEditor.commit();
    }

    public String getLocalNotificationsTitle() {
        return sharedPreferences.getString(LOCAL_NOTIFICATIONS_TITLE, "CdmKart");
    }

    public void setLocalNotificationsDuration(String localNotificationsDuration) {
        prefsEditor.putString(LOCAL_NOTIFICATIONS_DURATION, localNotificationsDuration);
        prefsEditor.commit();
    }

    public String getLocalNotificationsDuration() {
        return sharedPreferences.getString(LOCAL_NOTIFICATIONS_DURATION, "day");
    }

    public void setLocalNotificationsDescription(String localNotificationsDescription) {
        prefsEditor.putString(LOCAL_NOTIFICATIONS_DESCRIPTION, localNotificationsDescription);
        prefsEditor.commit();
    }

    public String getLocalNotificationsDescription() {
        return sharedPreferences.getString(LOCAL_NOTIFICATIONS_DESCRIPTION, "Check bundle of New Stores");
    }

    public void setSkip_For_Again(boolean isChecked) {
        prefsEditor.putBoolean(Skip_For_Again, isChecked);
        prefsEditor.commit();
    }

    public boolean getSkip_For_Again() {
        return sharedPreferences.getBoolean(Skip_For_Again,false);
    }
}
