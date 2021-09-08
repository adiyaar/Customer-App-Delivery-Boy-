package com.themescoder.driver.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterDeviceResponce {

@SerializedName("success")
@Expose
private String success;
@SerializedName("data")
@Expose
private List<Object> data = null;
@SerializedName("message")
@Expose
private String message;

public String getSuccess() {
return success;
}

public void setSuccess(String success) {
this.success = success;
}

public List<Object> getData() {
return data;
}

public void setData(List<Object> data) {
this.data = data;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

}