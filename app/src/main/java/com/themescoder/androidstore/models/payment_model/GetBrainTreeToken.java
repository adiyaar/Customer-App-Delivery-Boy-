
package com.themescoder.androidstore.models.payment_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class GetBrainTreeToken {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("message")
    @Expose
    private String message;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
