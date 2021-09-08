
package com.themescoder.androidstore.models.shipping_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShippingRateDetails {

    @SerializedName("tax")
    @Expose
    private String tax;
    @SerializedName("shippingMethods")
    @Expose
    private ShippingMethods shippingMethods;


    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public ShippingMethods getShippingMethods() {
        return shippingMethods;
    }

    public void setShippingMethods(ShippingMethods shippingMethods) {
        this.shippingMethods = shippingMethods;
    }

}
