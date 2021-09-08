package com.themescoder.androidstore.models.product_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ProductStock {


    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("stock")
    @Expose
    private String stock;
    @SerializedName("message")
    @Expose
    private String message;


    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
    
    public String getStock() {
        return stock;
    }
    
    public void setStock(String stock) {
        this.stock = stock;
    }
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
