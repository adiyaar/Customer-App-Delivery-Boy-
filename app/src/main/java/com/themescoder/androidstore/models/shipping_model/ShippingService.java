
package com.themescoder.androidstore.models.shipping_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShippingService {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("rate")
    @Expose
    private String rate;
    @SerializedName("currencyCode")
    @Expose
    private String currencyCode;
    @SerializedName("shipping_method")
    @Expose
    private String shippingMethod;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

}
