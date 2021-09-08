package com.themescoder.androidstore.models.order_model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PostProductsAttributes implements Parcelable {
    
    @SerializedName("orders_products_attributes_id")
    @Expose
    private String ordersProductsAttributesId;
    @SerializedName("orders_id")
    @Expose
    private String ordersId;
    @SerializedName("orders_products_id")
    @Expose
    private String ordersProductsId;
    @SerializedName("products_id")
    @Expose
    private String productsId;
    @SerializedName("products_options_id")
    @Expose
    private String productsOptionsId;
    @SerializedName("products_options")
    @Expose
    private String productsOptions;
    @SerializedName("products_options_values_id")
    @Expose
    private String productsOptionsValuesId;
    @SerializedName("products_options_values")
    @Expose
    private String productsOptionsValues;
    @SerializedName("options_values_price")
    @Expose
    private String optionsValuesPrice;
    @SerializedName("price_prefix")
    @Expose
    private String pricePrefix;
    @SerializedName("name")
    @Expose
    private String attributeName;
    
    
    public PostProductsAttributes() {
    }
    
    
    
    public String getOrdersProductsAttributesId() {
        return ordersProductsAttributesId;
    }
    
    public void setOrdersProductsAttributesId(String ordersProductsAttributesId) {
        this.ordersProductsAttributesId = ordersProductsAttributesId;
    }
    
    public String getOrdersId() {
        return ordersId;
    }
    
    public void setOrdersId(String ordersId) {
        this.ordersId = ordersId;
    }
    
    public String getOrdersProductsId() {
        return ordersProductsId;
    }
    
    public void setOrdersProductsId(String ordersProductsId) {
        this.ordersProductsId = ordersProductsId;
    }
    
    public String getProductsId() {
        return productsId;
    }
    
    public void setProductsId(String productsId) {
        this.productsId = productsId;
    }
    
    public String getProductsOptionsId() {
        return productsOptionsId;
    }
    
    public void setProductsOptionsId(String productsOptionsId) {
        this.productsOptionsId = productsOptionsId;
    }
    
    public String getProductsOptions() {
        return productsOptions;
    }
    
    public void setProductsOptions(String productsOptions) {
        this.productsOptions = productsOptions;
    }
    
    public String getProductsOptionsValuesId() {
        return productsOptionsValuesId;
    }
    
    public void setProductsOptionsValuesId(String productsOptionsValuesId) {
        this.productsOptionsValuesId = productsOptionsValuesId;
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
    
    public String getAttributeName() {
        return attributeName;
    }
    
    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }
    
    
    
    
    //********** Describes the kinds of Special Objects contained in this Parcelable Instance's marshaled representation *********//
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    
    
    //********** Writes the values to the Parcel *********//
    
    public void writeToParcel(Parcel parcel_out, int flags) {
        parcel_out.writeValue(ordersProductsAttributesId);
        parcel_out.writeValue(ordersId);
        parcel_out.writeValue(ordersProductsId);
        parcel_out.writeValue(productsId);
        parcel_out.writeValue(productsOptionsId);
        parcel_out.writeValue(productsOptions);
        parcel_out.writeValue(productsOptionsValuesId);
        parcel_out.writeValue(productsOptionsValues);
        parcel_out.writeValue(optionsValuesPrice);
        parcel_out.writeValue(pricePrefix);
        parcel_out.writeValue(attributeName);
    }
    
    
    
    //********** Generates Instances of Parcelable class from a Parcel *********//
    
    public static final Creator<PostProductsAttributes> CREATOR = new Creator<PostProductsAttributes>() {
        
        // Creates a new Instance of the Parcelable class, Instantiating it from the given Parcel
        @Override
        public PostProductsAttributes createFromParcel(Parcel parcel_in) {
            return new PostProductsAttributes(parcel_in);
        }
        
        // Creates a new array of the Parcelable class
        @Override
        public PostProductsAttributes[] newArray(int size) {
            return new PostProductsAttributes[size];
        }
    };
    
    
    
    //********** Retrieves the values from the Parcel *********//
    
    protected PostProductsAttributes(Parcel parcel_in) {
        this.ordersProductsAttributesId = parcel_in.readString();
        this.ordersId = parcel_in.readString();
        this.ordersProductsId = parcel_in.readString();
        this.productsId = parcel_in.readString();
        this.productsOptionsId = parcel_in.readString();
        this.productsOptions = parcel_in.readString();
        this.productsOptionsValuesId = parcel_in.readString();
        this.productsOptionsValues = parcel_in.readString();
        this.optionsValuesPrice = parcel_in.readString();
        this.pricePrefix = parcel_in.readString();
        this.attributeName = parcel_in.readString();
    }
    
}
