package com.themescoder.driver.models;

import java.io.Serializable;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetails implements Serializable, Parcelable {

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
    @SerializedName("vendors_id")
    @Expose
    private Integer vendorsId;
    @SerializedName("image")
    @Expose
    private Object image;
    @SerializedName("manufacturer_name")
    @Expose
    private Object manufacturerName;
    @SerializedName("categories_name")
    @Expose
    private String categoriesName;
    @SerializedName("vendors_firstname")
    @Expose
    private String vendorsFirstname;
    @SerializedName("vendors_lastname")
    @Expose
    private String vendorsLastname;
    @SerializedName("shop_name")
    @Expose
    private String shopName;
    @SerializedName("vendors_address")
    @Expose
    private String vendorsAddress;
    @SerializedName("vendors_phone")
    @Expose
    private String vendorsPhone;
    @SerializedName("vendors_suburb")
    @Expose
    private String vendorsSuburb;
    @SerializedName("vendors_postcode")
    @Expose
    private String vendorsPostcode;
    @SerializedName("vendors_city")
    @Expose
    private String vendorsCity;
    @SerializedName("vendors_state")
    @Expose
    private String vendorsState;
    @SerializedName("vendors_latitude")
    @Expose
    private Object vendorsLatitude;
    @SerializedName("vendors_longitude")
    @Expose
    private Object vendorsLongitude;
    @SerializedName("vendors_countries_name")
    @Expose
    private String vendorsCountriesName;
    @SerializedName("vendors_zone_name")
    @Expose
    private String vendorsZoneName;
    @SerializedName("vendor_orders_status_id")
    @Expose
    private String vendorOrdersStatusId;
    @SerializedName("vendor_orders_status_status")
    @Expose
    private String vendorOrdersStatusStatus;
    @SerializedName("vendor_orders_status_comments")
    @Expose
    private String vendorOrdersStatusComments;
    @SerializedName("attributes")
    @Expose
    private List<Object> attributes = null;
    public final static Parcelable.Creator<OrderDetails> CREATOR = new Creator<OrderDetails>() {


        @SuppressWarnings({
                "unchecked"
        })
        public OrderDetails createFromParcel(Parcel in) {
            return new OrderDetails(in);
        }

        public OrderDetails[] newArray(int size) {
            return (new OrderDetails[size]);
        }

    };
    private final static long serialVersionUID = 8357403643912729813L;

    protected OrderDetails(Parcel in) {
        this.ordersProductsId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.ordersId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.productsId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.productsModel = ((Object) in.readValue((Object.class.getClassLoader())));
        this.productsName = ((String) in.readValue((String.class.getClassLoader())));
        this.productsPrice = ((String) in.readValue((String.class.getClassLoader())));
        this.finalPrice = ((String) in.readValue((String.class.getClassLoader())));
        this.productsTax = ((String) in.readValue((String.class.getClassLoader())));
        this.productsQuantity = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.vendorsId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.image = ((Object) in.readValue((Object.class.getClassLoader())));
        this.manufacturerName = ((Object) in.readValue((Object.class.getClassLoader())));
        this.categoriesName = ((String) in.readValue((String.class.getClassLoader())));
        this.vendorsFirstname = ((String) in.readValue((String.class.getClassLoader())));
        this.vendorsLastname = ((String) in.readValue((String.class.getClassLoader())));
        this.shopName = ((String) in.readValue((String.class.getClassLoader())));
        this.vendorsAddress = ((String) in.readValue((String.class.getClassLoader())));
        this.vendorsPhone = ((String) in.readValue((String.class.getClassLoader())));
        this.vendorsSuburb = ((String) in.readValue((String.class.getClassLoader())));
        this.vendorsPostcode = ((String) in.readValue((String.class.getClassLoader())));
        this.vendorsCity = ((String) in.readValue((String.class.getClassLoader())));
        this.vendorsState = ((String) in.readValue((String.class.getClassLoader())));
        this.vendorsLatitude = ((Object) in.readValue((Object.class.getClassLoader())));
        this.vendorsLongitude = ((Object) in.readValue((Object.class.getClassLoader())));
        this.vendorsCountriesName = ((String) in.readValue((String.class.getClassLoader())));
        this.vendorsZoneName = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.attributes, (java.lang.Object.class.getClassLoader()));
    }

    public OrderDetails() {
    }

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

    public Integer getVendorsId() {
        return vendorsId;
    }

    public void setVendorsId(Integer vendorsId) {
        this.vendorsId = vendorsId;
    }

    public Object getImage() {
        return image;
    }

    public void setImage(Object image) {
        this.image = image;
    }

    public Object getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(Object manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getCategoriesName() {
        return categoriesName;
    }

    public void setCategoriesName(String categoriesName) {
        this.categoriesName = categoriesName;
    }

    public String getVendorsFirstname() {
        return vendorsFirstname;
    }

    public void setVendorsFirstname(String vendorsFirstname) {
        this.vendorsFirstname = vendorsFirstname;
    }

    public String getVendorsLastname() {
        return vendorsLastname;
    }

    public void setVendorsLastname(String vendorsLastname) {
        this.vendorsLastname = vendorsLastname;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getVendorsAddress() {
        return vendorsAddress;
    }

    public void setVendorsAddress(String vendorsAddress) {
        this.vendorsAddress = vendorsAddress;
    }

    public String getVendorsPhone() {
        return vendorsPhone;
    }

    public void setVendorsPhone(String vendorsPhone) {
        this.vendorsPhone = vendorsPhone;
    }

    public String getVendorsSuburb() {
        return vendorsSuburb;
    }

    public void setVendorsSuburb(String vendorsSuburb) {
        this.vendorsSuburb = vendorsSuburb;
    }

    public String getVendorsPostcode() {
        return vendorsPostcode;
    }

    public void setVendorsPostcode(String vendorsPostcode) {
        this.vendorsPostcode = vendorsPostcode;
    }

    public String getVendorsCity() {
        return vendorsCity;
    }

    public void setVendorsCity(String vendorsCity) {
        this.vendorsCity = vendorsCity;
    }

    public String getVendorsState() {
        return vendorsState;
    }

    public void setVendorsState(String vendorsState) {
        this.vendorsState = vendorsState;
    }

    public Object getVendorsLatitude() {
        return vendorsLatitude;
    }

    public void setVendorsLatitude(Object vendorsLatitude) {
        this.vendorsLatitude = vendorsLatitude;
    }

    public Object getVendorsLongitude() {
        return vendorsLongitude;
    }

    public void setVendorsLongitude(Object vendorsLongitude) {
        this.vendorsLongitude = vendorsLongitude;
    }

    public String getVendorsCountriesName() {
        return vendorsCountriesName;
    }

    public void setVendorsCountriesName(String vendorsCountriesName) {
        this.vendorsCountriesName = vendorsCountriesName;
    }

    public String getVendorsZoneName() {
        return vendorsZoneName;
    }

    public void setVendorsZoneName(String vendorsZoneName) {
        this.vendorsZoneName = vendorsZoneName;
    }

    public String getVendorOrdersStatusId() {
        return vendorOrdersStatusId;
    }

    public void setVendorOrdersStatusId(String vendorOrdersStatusId) {
        this.vendorOrdersStatusId = vendorOrdersStatusId;
    }

    public String getVendorOrdersStatusStatus() {
        return vendorOrdersStatusStatus;
    }

    public void setVendorOrdersStatusStatus(String vendorOrdersStatusStatus) {
        this.vendorOrdersStatusStatus = vendorOrdersStatusStatus;
    }

    public String getVendorOrdersStatusComments() {
        return vendorOrdersStatusComments;
    }

    public void setVendorOrdersStatusComments(String vendorOrdersStatusComments) {
        this.vendorOrdersStatusComments = vendorOrdersStatusComments;
    }

    public List<Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Object> attributes) {
        this.attributes = attributes;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(ordersProductsId);
        dest.writeValue(ordersId);
        dest.writeValue(productsId);
        dest.writeValue(productsModel);
        dest.writeValue(productsName);
        dest.writeValue(productsPrice);
        dest.writeValue(finalPrice);
        dest.writeValue(productsTax);
        dest.writeValue(productsQuantity);
        dest.writeValue(vendorsId);
        dest.writeValue(image);
        dest.writeValue(manufacturerName);
        dest.writeValue(categoriesName);
        dest.writeValue(vendorsFirstname);
        dest.writeValue(vendorsLastname);
        dest.writeValue(shopName);
        dest.writeValue(vendorsAddress);
        dest.writeValue(vendorsPhone);
        dest.writeValue(vendorsSuburb);
        dest.writeValue(vendorsPostcode);
        dest.writeValue(vendorsCity);
        dest.writeValue(vendorsState);
        dest.writeValue(vendorsLatitude);
        dest.writeValue(vendorsLongitude);
        dest.writeValue(vendorsCountriesName);
        dest.writeValue(vendorsZoneName);
        dest.writeList(attributes);
    }

    public int describeContents() {
        return 0;
    }

}