
package com.themescoder.androidstore.models.currency_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CurrencyModel implements Serializable
{

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("data")
    @Expose
    private List<CurrencyList> data = null;
    @SerializedName("message")
    @Expose
    private String message;
    private final static long serialVersionUID = -6527580727135920297L;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<CurrencyList> getData() {
        return data;
    }

    public void setData(List<CurrencyList> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}