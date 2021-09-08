package com.themescoder.androidstore.models.device_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AppSettingsData {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("data")
    @Expose
    private AppSettingsDetails appDetails = null;
    @SerializedName("message")
    @Expose
    private String message;

    
    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
    
    public AppSettingsDetails getAppDetails() {
        return appDetails;
    }
    
    public void setAppDetails(AppSettingsDetails appDetails) {
        this.appDetails = appDetails;
    }
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
