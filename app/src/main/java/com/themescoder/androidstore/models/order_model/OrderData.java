package com.themescoder.androidstore.models.order_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class OrderData {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("data")
    @Expose
    private List<OrderDetails> data = new ArrayList<OrderDetails>();
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("products_id")
    @Expose
    private String products_Id;


    /**
     * @return The success
     */
    public String getSuccess() {
        return success;
    }

    /**
     * @param success The success
     */
    public void setSuccess(String success) {
        this.success = success;
    }

    /**
     * @return The data
     */
    public List<OrderDetails> getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(List<OrderDetails> data) {
        this.data = data;
    }

    /**
     * @return The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    public String getProducts_Id() {
        return products_Id;
    }

    public void setProducts_Id(String products_Id) {
        this.products_Id = products_Id;
    }
}
