package com.themescoder.androidstore.models.shipping_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import com.themescoder.androidstore.models.product_model.ProductDetails;


public class PostTaxAndShippingData {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("street_address")
    @Expose
    private String streetAddress;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("postcode")
    @Expose
    private String postcode;
    @SerializedName("zone")
    @Expose
    private String zone;
    @SerializedName("tax_zone_id")
    @Expose
    private int taxZoneId;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("country_id")
    @Expose
    private int countryID;
    @SerializedName("telephone")
    @Expose
    private String telephone;
    @SerializedName("products_weight")
    @Expose
    private double productsWeight;
    @SerializedName("products_weight_unit")
    @Expose
    private String productsWeightUnit;
    @SerializedName("products")
    @Expose
    private List<ShippingProductDetails> products = null;
    
    @SerializedName("language_id")
    @Expose
    private int language_id;
    @SerializedName("currency_code")
    @Expose
    private String currencyCode;


    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public int getTaxZoneId() {
        return taxZoneId;
    }

    public void setTaxZoneId(int taxZoneId) {
        this.taxZoneId = taxZoneId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    
    public int getCountryID() {
        return countryID;
    }
    
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }
    
    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public double getProductsWeight() {
        return productsWeight;
    }

    public void setProductsWeight(double productsWeight) {
        this.productsWeight = productsWeight;
    }

    public String getProductsWeightUnit() {
        return productsWeightUnit;
    }

    public void setProductsWeightUnit(String productsWeightUnit) {
        this.productsWeightUnit = productsWeightUnit;
    }
    
    public int getLanguage_id() {
        return language_id;
    }
    
    public void setLanguage_id(int language_id) {
        this.language_id = language_id;
    }
    
    public List<ShippingProductDetails> getProducts() {
        return products;
    }

    public void setProducts(List<ShippingProductDetails> products) {
        this.products = products;
    }

}
