package com.themescoder.driver.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SettingsResponse {

@SerializedName("success")
@Expose
private String success;
@SerializedName("data")
@Expose
private SettingsData data;
@SerializedName("message")
@Expose
private String message;

public String getSuccess() {
return success;
}

public void setSuccess(String success) {
this.success = success;
}

public SettingsData getData() {
return data;
}

public void setData(SettingsData data) {
this.data = data;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

}