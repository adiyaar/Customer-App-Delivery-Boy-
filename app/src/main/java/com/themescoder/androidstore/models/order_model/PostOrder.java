package com.themescoder.androidstore.models.order_model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.themescoder.androidstore.models.coupons_model.CouponsInfo;

public class PostOrder implements Serializable
{

    @SerializedName("billing_city")
    @Expose
    private String billingCity;
    @SerializedName("billing_country")
    @Expose
    private String billingCountry;
    @SerializedName("billing_country_id")
    @Expose
    private String billingCountryId;
    @SerializedName("billing_firstname")
    @Expose
    private String billingFirstname;
    @SerializedName("billing_lastname")
    @Expose
    private String billingLastname;
    @SerializedName("billing_phone")
    @Expose
    private String billingPhone;
    @SerializedName("billing_postcode")
    @Expose
    private String billingPostcode;
    @SerializedName("billing_state")
    @Expose
    private String billingState;
    @SerializedName("billing_street_address")
    @Expose
    private String billingStreetAddress;
    @SerializedName("billing_suburb")
    @Expose
    private String billingSuburb;
    @SerializedName("billing_zone")
    @Expose
    private String billingZone;
    @SerializedName("billing_zone_id")
    @Expose
    private String billingZoneId;
    @SerializedName("comments")
    @Expose
    private String comments;
    @SerializedName("coupon_amount")
    @Expose
    private Double couponAmount;
    @SerializedName("coupons")
    @Expose
    private List<CouponsInfo> coupons = null;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("customers_id")
    @Expose
    private Integer customersId;
    @SerializedName("customers_name")
    @Expose
    private String customersName;
    @SerializedName("customers_telephone")
    @Expose
    private String customersTelephone;
    @SerializedName("delivery_city")
    @Expose
    private String deliveryCity;
    @SerializedName("delivery_country")
    @Expose
    private String deliveryCountry;
    @SerializedName("delivery_country_id")
    @Expose
    private String deliveryCountryId;
    @SerializedName("delivery_firstname")
    @Expose
    private String deliveryFirstname;
    @SerializedName("delivery_lastname")
    @Expose
    private String deliveryLastname;
    @SerializedName("delivery_phone")
    @Expose
    private String deliveryPhone;
    @SerializedName("delivery_postcode")
    @Expose
    private String deliveryPostcode;
    @SerializedName("delivery_state")
    @Expose
    private String deliveryState;
    @SerializedName("delivery_street_address")
    @Expose
    private String deliveryStreetAddress;
    @SerializedName("delivery_suburb")
    @Expose
    private String deliverySuburb;
    @SerializedName("delivery_zone")
    @Expose
    private String deliveryZone;
    @SerializedName("delivery_zone_id")
    @Expose
    private String deliveryZoneId;
    @SerializedName("delivery_cost")
    @Expose
    private String deliveryCost;
    @SerializedName("delivery_time")
    @Expose
    private String deliveryTime;
    @SerializedName("is_coupon_applied")
    @Expose
    private Integer isCouponApplied;
    @SerializedName("language_id")
    @Expose
    private Integer languageId;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("nonce")
    @Expose
    private String nonce;
    @SerializedName("packing_charge_tax")
    @Expose
    private String packingChargeTax;
    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
    @SerializedName("currency_code")
    @Expose
    private String currency;
    @SerializedName("products")
    @Expose
    private List<PostProducts> products = null;
    @SerializedName("productsTotal")
    @Expose
    private Double productsTotal;
    @SerializedName("shipping_cost")
    @Expose
    private Double shippingCost;
    @SerializedName("shipping_method")
    @Expose
    private String shippingMethod;
    @SerializedName("tax_zone_id")
    @Expose
    private Integer taxZoneId;
    @SerializedName("totalPrice")
    @Expose
    private Double totalPrice;
    @SerializedName("total_tax")
    @Expose
    private Double totalTax;


    private final static long serialVersionUID = -2721986212560631035L;

    @SerializedName("transaction_id")
    @Expose
    private String order_payment_id;


    public String getOrder_payment_id() {
        return order_payment_id;
    }

    public void setOrder_payment_id(String order_payment_id) {
        this.order_payment_id = order_payment_id;
    }

    public String getBillingCity() {
        return billingCity;
    }

    public void setBillingCity(String billingCity) {
        this.billingCity = billingCity;
    }

    public String getBillingCountry() {
        return billingCountry;
    }

    public void setBillingCountry(String billingCountry) {
        this.billingCountry = billingCountry;
    }

    public String getBillingCountryId() {
        return billingCountryId;
    }

    public void setBillingCountryId(String billingCountryId) {
        this.billingCountryId = billingCountryId;
    }

    public String getBillingFirstname() {
        return billingFirstname;
    }

    public void setBillingFirstname(String billingFirstname) {
        this.billingFirstname = billingFirstname;
    }

    public String getBillingLastname() {
        return billingLastname;
    }

    public void setBillingLastname(String billingLastname) {
        this.billingLastname = billingLastname;
    }

    public String getBillingPhone() {
        return billingPhone;
    }

    public void setBillingPhone(String billingPhone) {
        this.billingPhone = billingPhone;
    }

    public String getBillingPostcode() {
        return billingPostcode;
    }

    public void setBillingPostcode(String billingPostcode) {
        this.billingPostcode = billingPostcode;
    }

    public String getBillingState() {
        return billingState;
    }

    public void setBillingState(String billingState) {
        this.billingState = billingState;
    }

    public String getBillingStreetAddress() {
        return billingStreetAddress;
    }

    public void setBillingStreetAddress(String billingStreetAddress) {
        this.billingStreetAddress = billingStreetAddress;
    }

    public String getBillingSuburb() {
        return billingSuburb;
    }

    public void setBillingSuburb(String billingSuburb) {
        this.billingSuburb = billingSuburb;
    }

    public String getBillingZone() {
        return billingZone;
    }

    public void setBillingZone(String billingZone) {
        this.billingZone = billingZone;
    }

    public String getBillingZoneId() {
        return billingZoneId;
    }

    public void setBillingZoneId(String billingZoneId) {
        this.billingZoneId = billingZoneId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Double getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(Double couponAmount) {
        this.couponAmount = couponAmount;
    }

    public List<CouponsInfo> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<CouponsInfo> coupons) {
        this.coupons = coupons;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getCustomersId() {
        return customersId;
    }

    public void setCustomersId(Integer customersId) {
        this.customersId = customersId;
    }

    public String getCustomersName() {
        return customersName;
    }

    public void setCustomersName(String customersName) {
        this.customersName = customersName;
    }

    public String getCustomersTelephone() {
        return customersTelephone;
    }

    public void setCustomersTelephone(String customersTelephone) {
        this.customersTelephone = customersTelephone;
    }

    public String getDeliveryCity() {
        return deliveryCity;
    }

    public void setDeliveryCity(String deliveryCity) {
        this.deliveryCity = deliveryCity;
    }

    public String getDeliveryCountry() {
        return deliveryCountry;
    }

    public void setDeliveryCountry(String deliveryCountry) {
        this.deliveryCountry = deliveryCountry;
    }

    public String getDeliveryCountryId() {
        return deliveryCountryId;
    }

    public void setDeliveryCountryId(String deliveryCountryId) {
        this.deliveryCountryId = deliveryCountryId;
    }

    public String getDeliveryFirstname() {
        return deliveryFirstname;
    }

    public void setDeliveryFirstname(String deliveryFirstname) {
        this.deliveryFirstname = deliveryFirstname;
    }

    public String getDeliveryLastname() {
        return deliveryLastname;
    }

    public void setDeliveryLastname(String deliveryLastname) {
        this.deliveryLastname = deliveryLastname;
    }

    public String getDeliveryPhone() {
        return deliveryPhone;
    }

    public void setDeliveryPhone(String deliveryPhone) {
        this.deliveryPhone = deliveryPhone;
    }

    public String getDeliveryPostcode() {
        return deliveryPostcode;
    }

    public void setDeliveryPostcode(String deliveryPostcode) {
        this.deliveryPostcode = deliveryPostcode;
    }

    public String getDeliveryState() {
        return deliveryState;
    }

    public void setDeliveryState(String deliveryState) {
        this.deliveryState = deliveryState;
    }

    public String getDeliveryStreetAddress() {
        return deliveryStreetAddress;
    }

    public void setDeliveryStreetAddress(String deliveryStreetAddress) {
        this.deliveryStreetAddress = deliveryStreetAddress;
    }

    public String getDeliverySuburb() {
        return deliverySuburb;
    }

    public void setDeliverySuburb(String deliverySuburb) {
        this.deliverySuburb = deliverySuburb;
    }

    public String getDeliveryZone() {
        return deliveryZone;
    }

    public void setDeliveryZone(String deliveryZone) {
        this.deliveryZone = deliveryZone;
    }

    public String getDeliveryZoneId() {
        return deliveryZoneId;
    }

    public void setDeliveryZoneId(String deliveryZoneId) {
        this.deliveryZoneId = deliveryZoneId;
    }

    public String getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(String deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Integer getIsCouponApplied() {
        return isCouponApplied;
    }

    public void setIsCouponApplied(Integer isCouponApplied) {
        this.isCouponApplied = isCouponApplied;
    }

    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getPackingChargeTax() {
        return packingChargeTax;
    }

    public void setPackingChargeTax(String packingChargeTax) {
        this.packingChargeTax = packingChargeTax;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<PostProducts> getProducts() {
        return products;
    }

    public void setProducts(List<PostProducts> products) {
        this.products = products;
    }

    public Double getProductsTotal() {
        return productsTotal;
    }

    public void setProductsTotal(Double productsTotal) {
        this.productsTotal = productsTotal;
    }

    public Double getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(Double shippingCost) {
        this.shippingCost = shippingCost;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public Integer getTaxZoneId() {
        return taxZoneId;
    }

    public void setTaxZoneId(Integer taxZoneId) {
        this.taxZoneId = taxZoneId;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(Double totalTax) {
        this.totalTax = totalTax;
    }

}