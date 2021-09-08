
package com.themescoder.androidstore.models.payment_model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PaymentMethodsData {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("data")
    @Expose
    private List<PaymentMethodsInfo> data;
    @SerializedName("message")
    @Expose
    private String message;


    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<PaymentMethodsInfo> getData() {
        return data;
    }

    public void setData(List<PaymentMethodsInfo> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}

