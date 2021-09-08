package com.themescoder.androidstore.models.cart_model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.themescoder.androidstore.models.product_model.ProductDetails;


public class CartProduct {


    @SerializedName("customers_id")
    @Expose
    private int customersId;
    @SerializedName("customers_basket_id")
    @Expose
    private int customersBasketId;
    @SerializedName("customers_basket_date_added")
    @Expose
    private String customersBasketDateAdded;
    @SerializedName("customers_basket_product")
    @Expose
    private ProductDetails customersBasketProduct;
    @SerializedName("customers_basket_product_attributes")
    @Expose
    private List<CartProductAttributes> customersBasketProductAttributes = new ArrayList<CartProductAttributes>();



    public int getCustomersId() {
        return customersId;
    }

    public void setCustomersId(int customersId) {
        this.customersId = customersId;
    }

    public int getCustomersBasketId() {
        return customersBasketId;
    }

    public void setCustomersBasketId(int customersBasketId) {
        this.customersBasketId = customersBasketId;
    }

    public String getCustomersBasketDateAdded() {
        return customersBasketDateAdded;
    }

    public void setCustomersBasketDateAdded(String customersBasketDateAdded) {
        this.customersBasketDateAdded = customersBasketDateAdded;
    }

    public ProductDetails getCustomersBasketProduct() {
        return customersBasketProduct;
    }

    public void setCustomersBasketProduct(ProductDetails customersBasketProduct) {
        this.customersBasketProduct = customersBasketProduct;
    }

    public List<CartProductAttributes> getCustomersBasketProductAttributes() {
        return customersBasketProductAttributes;
    }

    public void setCustomersBasketProductAttributes(List<CartProductAttributes> customersBasketProductAttributes) {
        this.customersBasketProductAttributes = customersBasketProductAttributes;
    }

}
