
package com.themescoder.androidstore.models.googleMap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GoogleAPIResponse {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("message")
    @Expose
    private String message;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
