package com.themescoder.androidstore.models.order_model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderProducts {

    @SerializedName("orders_products_id")
    @Expose
    private Integer ordersProductsId;
    @SerializedName("orders_id")
    @Expose
    private Integer ordersId;
    @SerializedName("products_id")
    @Expose
    private Integer productsId;
    @SerializedName("products_model")
    @Expose
    private Object productsModel;
    @SerializedName("products_name")
    @Expose
    private String productsName;
    @SerializedName("products_price")
    @Expose
    private String productsPrice;
    @SerializedName("final_price")
    @Expose
    private String finalPrice;
    @SerializedName("products_tax")
    @Expose
    private String productsTax;
    @SerializedName("products_quantity")
    @Expose
    private Integer productsQuantity;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("categories")
    @Expose
    private List<OrderProductCategory> categories = null;
    @SerializedName("attributes")
    @Expose
    private List<PostProductsAttributes> attributes = null;

    public Integer getOrdersProductsId() {
        return ordersProductsId;
    }

    public void setOrdersProductsId(Integer ordersProductsId) {
        this.ordersProductsId = ordersProductsId;
    }

    public Integer getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(Integer ordersId) {
        this.ordersId = ordersId;
    }

    public Integer getProductsId() {
        return productsId;
    }

    public void setProductsId(Integer productsId) {
        this.productsId = productsId;
    }

    public Object getProductsModel() {
        return productsModel;
    }

    public void setProductsModel(Object productsModel) {
        this.productsModel = productsModel;
    }

    public String getProductsName() {
        return productsName;
    }

    public void setProductsName(String productsName) {
        this.productsName = productsName;
    }

    public String getProductsPrice() {
        return productsPrice;
    }

    public void setProductsPrice(String productsPrice) {
        this.productsPrice = productsPrice;
    }

    public String getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(String finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getProductsTax() {
        return productsTax;
    }

    public void setProductsTax(String productsTax) {
        this.productsTax = productsTax;
    }

    public Integer getProductsQuantity() {
        return productsQuantity;
    }

    public void setProductsQuantity(Integer productsQuantity) {
        this.productsQuantity = productsQuantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<OrderProductCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<OrderProductCategory> categories) {
        this.categories = categories;
    }

    public List<PostProductsAttributes> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<PostProductsAttributes> attributes) {
        this.attributes = attributes;
    }
}
