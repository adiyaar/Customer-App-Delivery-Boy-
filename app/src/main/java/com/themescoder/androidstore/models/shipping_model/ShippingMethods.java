
package com.themescoder.androidstore.models.shipping_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShippingMethods {

    @SerializedName("upsShipping")
    @Expose
    private UpsShipping upsShipping;
    @SerializedName("freeShipping")
    @Expose
    private FreeShipping freeShipping;
    @SerializedName("localPickup")
    @Expose
    private LocalPickup localPickup;
    @SerializedName("flateRate")
    @Expose
    private FlateRate flateRate;
    @SerializedName("shippingprice")
    @Expose
    private ShippingPrice shippingprice;


    public UpsShipping getUpsShipping() {
        return upsShipping;
    }

    public void setUpsShipping(UpsShipping upsShipping) {
        this.upsShipping = upsShipping;
    }

    public FreeShipping getFreeShipping() {
        return freeShipping;
    }

    public void setFreeShipping(FreeShipping freeShipping) {
        this.freeShipping = freeShipping;
    }

    public LocalPickup getLocalPickup() {
        return localPickup;
    }

    public void setLocalPickup(LocalPickup localPickup) {
        this.localPickup = localPickup;
    }

    public FlateRate getFlateRate() {
        return flateRate;
    }

    public void setFlateRate(FlateRate flateRate) {
        this.flateRate = flateRate;
    }

    public ShippingPrice getShippingprice() {
        return shippingprice;
    }

    public void setShippingprice(ShippingPrice shippingprice) {
        this.shippingprice = shippingprice;
    }
}
