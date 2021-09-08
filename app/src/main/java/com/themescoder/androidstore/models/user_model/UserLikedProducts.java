package com.themescoder.androidstore.models.user_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class UserLikedProducts {

    @SerializedName("products_id")
    @Expose
    private Integer productsId;


    public Integer getProductsId() {
        return productsId;
    }

    public void setProductsId(Integer productsId) {
        this.productsId = productsId;
    }

}
