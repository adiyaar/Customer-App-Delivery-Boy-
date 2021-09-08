
package com.themescoder.driver.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderAttribute {

    @SerializedName("orders_products_attributes_id")
    @Expose
    private Integer ordersProductsAttributesId;
    @SerializedName("orders_id")
    @Expose
    private Integer ordersId;
    @SerializedName("orders_products_id")
    @Expose
    private Integer ordersProductsId;
    @SerializedName("products_id")
    @Expose
    private Integer productsId;
    @SerializedName("products_options")
    @Expose
    private String productsOptions;
    @SerializedName("products_options_values")
    @Expose
    private String productsOptionsValues;
    @SerializedName("options_values_price")
    @Expose
    private String optionsValuesPrice;
    @SerializedName("price_prefix")
    @Expose
    private String pricePrefix;

    public Integer getOrdersProductsAttributesId() {
        return ordersProductsAttributesId;
    }

    public void setOrdersProductsAttributesId(Integer ordersProductsAttributesId) {
        this.ordersProductsAttributesId = ordersProductsAttributesId;
    }

    public Integer getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(Integer ordersId) {
        this.ordersId = ordersId;
    }

    public Integer getOrdersProductsId() {
        return ordersProductsId;
    }

    public void setOrdersProductsId(Integer ordersProductsId) {
        this.ordersProductsId = ordersProductsId;
    }

    public Integer getProductsId() {
        return productsId;
    }

    public void setProductsId(Integer productsId) {
        this.productsId = productsId;
    }

    public String getProductsOptions() {
        return productsOptions;
    }

    public void setProductsOptions(String productsOptions) {
        this.productsOptions = productsOptions;
    }

    public String getProductsOptionsValues() {
        return productsOptionsValues;
    }

    public void setProductsOptionsValues(String productsOptionsValues) {
        this.productsOptionsValues = productsOptionsValues;
    }

    public String getOptionsValuesPrice() {
        return optionsValuesPrice;
    }

    public void setOptionsValuesPrice(String optionsValuesPrice) {
        this.optionsValuesPrice = optionsValuesPrice;
    }

    public String getPricePrefix() {
        return pricePrefix;
    }

    public void setPricePrefix(String pricePrefix) {
        this.pricePrefix = pricePrefix;
    }

}
