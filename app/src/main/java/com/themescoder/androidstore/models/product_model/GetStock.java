package com.themescoder.androidstore.models.product_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class GetStock {
    
    @SerializedName("products_id")
    @Expose
    private String productsId;
    @SerializedName("attributes")
    @Expose
    private List<String> attributes = null;
    
    
    public String getProductsId() {
        return productsId;
    }
    
    public void setProductsId(String productsId) {
        this.productsId = productsId;
    }
    
    public List<String> getAttributes() {
        return attributes;
    }
    
    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }
    
}