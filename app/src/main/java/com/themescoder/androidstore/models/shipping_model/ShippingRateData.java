
package com.themescoder.androidstore.models.shipping_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShippingRateData {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("data")
    @Expose
    private ShippingRateDetails data;
    @SerializedName("message")
    @Expose
    private String message;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public ShippingRateDetails getData() {
        return data;
    }

    public void setData(ShippingRateDetails data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
