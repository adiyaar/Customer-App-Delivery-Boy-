package com.themescoder.driver.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SettingsData {

    @SerializedName("auth_domain")
    @Expose
    private Object authDomain;
    @SerializedName("currency_symbol")
    @Expose
    private String currencySymbol;
    @SerializedName("database_URL")
    @Expose
    private Object databaseURL;
    @SerializedName("default_latitude")
    @Expose
    private String defaultLatitude;
    @SerializedName("default_longitude")
    @Expose
    private String defaultLongitude;
    @SerializedName("google_map_api")
    @Expose
    private String googleMapApi;
    @SerializedName("is_enable_location")
    @Expose
    private String isEnableLocation;
    @SerializedName("maptype")
    @Expose
    private String mapType;
    @SerializedName("messaging_senderid")
    @Expose
    private Object messagingSenderid;
    @SerializedName("onesignal_app_id")
    @Expose
    private String onesignalAppId;
    @SerializedName("onesignal_sender_id")
    @Expose
    private String onesignalSenderId;
    @SerializedName("projectId")
    @Expose
    private Object projectId;
    @SerializedName("storage_bucket")
    @Expose
    private Object storageBucket;

    public Object getAuthDomain() {
        return authDomain;
    }

    public void setAuthDomain(Object authDomain) {
        this.authDomain = authDomain;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public Object getDatabaseURL() {
        return databaseURL;
    }

    public void setDatabaseURL(Object databaseURL) {
        this.databaseURL = databaseURL;
    }

    public String getDefaultLatitude() {
        return defaultLatitude;
    }

    public void setDefaultLatitude(String defaultLatitude) {
        this.defaultLatitude = defaultLatitude;
    }

    public String getDefaultLongitude() {
        return defaultLongitude;
    }

    public void setDefaultLongitude(String defaultLongitude) {
        this.defaultLongitude = defaultLongitude;
    }

    public String getGoogleMapApi() {
        return googleMapApi;
    }

    public void setGoogleMapApi(String googleMapApi) {
        this.googleMapApi = googleMapApi;
    }

    public String getIsEnableLocation() {
        return isEnableLocation;
    }

    public void setIsEnableLocation(String isEnableLocation) {
        this.isEnableLocation = isEnableLocation;
    }

    public String getMapType() {
        return mapType;
    }

    public void setMapType(String mapType) {
        this.mapType = mapType;
    }

    public Object getMessagingSenderid() {
        return messagingSenderid;
    }

    public void setMessagingSenderid(Object messagingSenderid) {
        this.messagingSenderid = messagingSenderid;
    }

    public String getOnesignalAppId() {
        return onesignalAppId;
    }

    public void setOnesignalAppId(String onesignalAppId) {
        this.onesignalAppId = onesignalAppId;
    }

    public String getOnesignalSenderId() {
        return onesignalSenderId;
    }

    public void setOnesignalSenderId(String onesignalSenderId) {
        this.onesignalSenderId = onesignalSenderId;
    }

    public Object getProjectId() {
        return projectId;
    }

    public void setProjectId(Object projectId) {
        this.projectId = projectId;
    }

    public Object getStorageBucket() {
        return storageBucket;
    }

    public void setStorageBucket(Object storageBucket) {
        this.storageBucket = storageBucket;
    }

}