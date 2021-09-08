package com.themescoder.androidstore.models.order_model;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.themescoder.androidstore.models.coupons_model.CouponsInfo;

public class OrderDetails implements Parcelable
{

    @SerializedName("orders_id")
    @Expose
    private long ordersId;
    @SerializedName("total_tax")
    @Expose
    private String totalTax;
    @SerializedName("customers_id")
    @Expose
    private long customersId;
    @SerializedName("customers_name")
    @Expose
    private String customersName;
    @SerializedName("customers_company")
    @Expose
    private Object customersCompany;
    @SerializedName("customers_street_address")
    @Expose
    private String customersStreetAddress;
    @SerializedName("customers_suburb")
    @Expose
    private String customersSuburb;
    @SerializedName("customers_city")
    @Expose
    private String customersCity;
    @SerializedName("customers_postcode")
    @Expose
    private String customersPostcode;
    @SerializedName("customers_state")
    @Expose
    private String customersState;
    @SerializedName("customers_country")
    @Expose
    private String customersCountry;
    @SerializedName("customers_telephone")
    @Expose
    private String customersTelephone;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("customers_address_format_id")
    @Expose
    private Object customersAddressFormatId;
    @SerializedName("delivery_name")
    @Expose
    private String deliveryName;
    @SerializedName("delivery_company")
    @Expose
    private Object deliveryCompany;
    @SerializedName("delivery_street_address")
    @Expose
    private String deliveryStreetAddress;
    @SerializedName("delivery_suburb")
    @Expose
    private String deliverySuburb;
    @SerializedName("delivery_city")
    @Expose
    private String deliveryCity;
    @SerializedName("delivery_postcode")
    @Expose
    private String deliveryPostcode;
    @SerializedName("delivery_state")
    @Expose
    private String deliveryState;
    @SerializedName("delivery_country")
    @Expose
    private String deliveryCountry;
    @SerializedName("delivery_address_format_id")
    @Expose
    private Object deliveryAddressFormatId;
    @SerializedName("delivery_latitude")
    @Expose
    private String deliveryLatitude;
    @SerializedName("delivery_longitude")
    @Expose
    private String deliveryLongitude;
    @SerializedName("billing_name")
    @Expose
    private String billingName;
    @SerializedName("billing_company")
    @Expose
    private Object billingCompany;
    @SerializedName("billing_street_address")
    @Expose
    private String billingStreetAddress;
    @SerializedName("billing_suburb")
    @Expose
    private String billingSuburb;
    @SerializedName("billing_city")
    @Expose
    private String billingCity;
    @SerializedName("billing_postcode")
    @Expose
    private String billingPostcode;
    @SerializedName("billing_state")
    @Expose
    private String billingState;
    @SerializedName("billing_country")
    @Expose
    private String billingCountry;
    @SerializedName("billing_address_format_id")
    @Expose
    private long billingAddressFormatId;
    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
    @SerializedName("cc_type")
    @Expose
    private Object ccType;
    @SerializedName("cc_owner")
    @Expose
    private Object ccOwner;
    @SerializedName("cc_number")
    @Expose
    private Object ccNumber;
    @SerializedName("cc_expires")
    @Expose
    private Object ccExpires;
    @SerializedName("last_modified")
    @Expose
    private String lastModified;
    @SerializedName("date_purchased")
    @Expose
    private String datePurchased;
    @SerializedName("orders_date_finished")
    @Expose
    private Object ordersDateFinished;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("currency_value")
    @Expose
    private String currencyValue;
    @SerializedName("order_price")
    @Expose
    private String orderPrice;
    @SerializedName("shipping_cost")
    @Expose
    private String shippingCost;
    @SerializedName("shipping_method")
    @Expose
    private String shippingMethod;
    @SerializedName("shipping_duration")
    @Expose
    private Object shippingDuration;
    @SerializedName("order_information")
    @Expose
    private String orderInformation;
    @SerializedName("is_seen")
    @Expose
    private long isSeen;
    @SerializedName("coupon_amount")
    @Expose
    private String couponAmount;
    @SerializedName("exclude_product_ids")
    @Expose
    private List<Object> excludeProductIds = null;
    @SerializedName("product_categories")
    @Expose
    private List<Object> productCategories = null;
    @SerializedName("excluded_product_categories")
    @Expose
    private List<Object> excludedProductCategories = null;
    @SerializedName("free_shipping")
    @Expose
    private long freeShipping;
    @SerializedName("product_ids")
    @Expose
    private List<Object> productIds = null;
    @SerializedName("ordered_source")
    @Expose
    private long orderedSource;
    @SerializedName("delivery_phone")
    @Expose
    private String deliveryPhone;
    @SerializedName("billing_phone")
    @Expose
    private String billingPhone;
    @SerializedName("transaction_id")
    @Expose
    private Object transactionId;
    @SerializedName("created_at")
    @Expose
    private Object createdAt;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;
    @SerializedName("deliveryboy_info")
    @Expose
    private List<DeliveryboyInfo> deliveryboyInfo = null;
    @SerializedName("vendors_id")
    @Expose
    private long vendorsId;
    @SerializedName("discount_type")
    @Expose
    private Object discountType = null;
    @SerializedName("amount")
    @Expose
    private List<Object> amount = null;
    @SerializedName("usage_limit")
    @Expose
    private List<Object> usageLimit = null;
    @SerializedName("coupons")
    @Expose
    private List<CouponsInfo> coupons = null;
    @SerializedName("orders_status_id")
    @Expose
    private long ordersStatusId;
    @SerializedName("orders_status")
    @Expose
    private String ordersStatus;
    @SerializedName("customer_comments")
    @Expose
    private String customerComments;
    @SerializedName("admin_comments")
    @Expose
    private String adminComments;
    @SerializedName("data")
    @Expose
    private List<OrderProducts> data = null;
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

    protected OrderDetails(Parcel in) {
        this.ordersId = ((long) in.readValue((long.class.getClassLoader())));
        this.totalTax = ((String) in.readValue((String.class.getClassLoader())));
        this.customersId = ((long) in.readValue((long.class.getClassLoader())));
        this.customersName = ((String) in.readValue((String.class.getClassLoader())));
        this.customersCompany = ((Object) in.readValue((Object.class.getClassLoader())));
        this.customersStreetAddress = ((String) in.readValue((String.class.getClassLoader())));
        this.customersSuburb = ((String) in.readValue((String.class.getClassLoader())));
        this.customersCity = ((String) in.readValue((String.class.getClassLoader())));
        this.customersPostcode = ((String) in.readValue((String.class.getClassLoader())));
        this.customersState = ((String) in.readValue((String.class.getClassLoader())));
        this.customersCountry = ((String) in.readValue((String.class.getClassLoader())));
        this.customersTelephone = ((String) in.readValue((String.class.getClassLoader())));
        this.email = ((String) in.readValue((String.class.getClassLoader())));
        this.customersAddressFormatId = ((Object) in.readValue((Object.class.getClassLoader())));
        this.deliveryName = ((String) in.readValue((String.class.getClassLoader())));
        this.deliveryCompany = ((Object) in.readValue((Object.class.getClassLoader())));
        this.deliveryStreetAddress = ((String) in.readValue((String.class.getClassLoader())));
        this.deliverySuburb = ((String) in.readValue((String.class.getClassLoader())));
        this.deliveryCity = ((String) in.readValue((String.class.getClassLoader())));
        this.deliveryPostcode = ((String) in.readValue((String.class.getClassLoader())));
        this.deliveryState = ((String) in.readValue((String.class.getClassLoader())));
        this.deliveryCountry = ((String) in.readValue((String.class.getClassLoader())));
        this.deliveryAddressFormatId = ((Object) in.readValue((Object.class.getClassLoader())));
        this.billingName = ((String) in.readValue((String.class.getClassLoader())));
        this.billingCompany = ((Object) in.readValue((Object.class.getClassLoader())));
        this.billingStreetAddress = ((String) in.readValue((String.class.getClassLoader())));
        this.billingSuburb = ((String) in.readValue((String.class.getClassLoader())));
        this.billingCity = ((String) in.readValue((String.class.getClassLoader())));
        this.billingPostcode = ((String) in.readValue((String.class.getClassLoader())));
        this.billingState = ((String) in.readValue((String.class.getClassLoader())));
        this.billingCountry = ((String) in.readValue((String.class.getClassLoader())));
        this.billingAddressFormatId = ((long) in.readValue((long.class.getClassLoader())));
        this.paymentMethod = ((String) in.readValue((String.class.getClassLoader())));
        this.ccType = ((Object) in.readValue((Object.class.getClassLoader())));
        this.ccOwner = ((Object) in.readValue((Object.class.getClassLoader())));
        this.ccNumber = ((Object) in.readValue((Object.class.getClassLoader())));
        this.ccExpires = ((Object) in.readValue((Object.class.getClassLoader())));
        this.lastModified = ((String) in.readValue((String.class.getClassLoader())));
        this.datePurchased = ((String) in.readValue((String.class.getClassLoader())));
        this.ordersDateFinished = ((Object) in.readValue((Object.class.getClassLoader())));
        this.currency = ((String) in.readValue((String.class.getClassLoader())));
        this.currencyValue = ((String) in.readValue((String.class.getClassLoader())));
        this.orderPrice = ((String) in.readValue((String.class.getClassLoader())));
        this.shippingCost = ((String) in.readValue((String.class.getClassLoader())));
        this.shippingMethod = ((String) in.readValue((String.class.getClassLoader())));
        this.shippingDuration = ((Object) in.readValue((Object.class.getClassLoader())));
        this.orderInformation = ((String) in.readValue((String.class.getClassLoader())));
        this.isSeen = ((long) in.readValue((long.class.getClassLoader())));
        this.couponAmount = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.excludeProductIds, (java.lang.Object.class.getClassLoader()));
        in.readList(this.productCategories, (java.lang.Object.class.getClassLoader()));
        in.readList(this.excludedProductCategories, (java.lang.Object.class.getClassLoader()));
        this.freeShipping = ((long) in.readValue((long.class.getClassLoader())));
        in.readList(this.productIds, (java.lang.Object.class.getClassLoader()));
        this.orderedSource = ((long) in.readValue((long.class.getClassLoader())));
        this.deliveryPhone = ((String) in.readValue((String.class.getClassLoader())));
        this.billingPhone = ((String) in.readValue((String.class.getClassLoader())));
        this.transactionId = ((Object) in.readValue((Object.class.getClassLoader())));
        this.createdAt = ((Object) in.readValue((Object.class.getClassLoader())));
        this.updatedAt = ((Object) in.readValue((Object.class.getClassLoader())));
        this.vendorsId = ((long) in.readValue((long.class.getClassLoader())));
        this.discountType = ((Object) in.readValue((String.class.getClassLoader())));
        in.readList(this.amount, (java.lang.Object.class.getClassLoader()));
        in.readList(this.usageLimit, (java.lang.Object.class.getClassLoader()));
        in.readList(this.coupons, (java.lang.Object.class.getClassLoader()));
        this.ordersStatusId = ((long) in.readValue((long.class.getClassLoader())));
        this.ordersStatus = ((String) in.readValue((String.class.getClassLoader())));
        this.customerComments = ((String) in.readValue((String.class.getClassLoader())));
        this.adminComments = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.data, (OrderProducts.class.getClassLoader()));
    }

    public OrderDetails() {
    }

    public long getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(long ordersId) {
        this.ordersId = ordersId;
    }

    public OrderDetails withOrdersId(long ordersId) {
        this.ordersId = ordersId;
        return this;
    }

    public String getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(String totalTax) {
        this.totalTax = totalTax;
    }

    public OrderDetails withTotalTax(String totalTax) {
        this.totalTax = totalTax;
        return this;
    }

    public long getCustomersId() {
        return customersId;
    }

    public void setCustomersId(long customersId) {
        this.customersId = customersId;
    }

    public OrderDetails withCustomersId(long customersId) {
        this.customersId = customersId;
        return this;
    }

    public String getCustomersName() {
        return customersName;
    }

    public void setCustomersName(String customersName) {
        this.customersName = customersName;
    }

    public OrderDetails withCustomersName(String customersName) {
        this.customersName = customersName;
        return this;
    }

    public Object getCustomersCompany() {
        return customersCompany;
    }

    public void setCustomersCompany(Object customersCompany) {
        this.customersCompany = customersCompany;
    }

    public OrderDetails withCustomersCompany(Object customersCompany) {
        this.customersCompany = customersCompany;
        return this;
    }

    public String getCustomersStreetAddress() {
        return customersStreetAddress;
    }

    public void setCustomersStreetAddress(String customersStreetAddress) {
        this.customersStreetAddress = customersStreetAddress;
    }

    public OrderDetails withCustomersStreetAddress(String customersStreetAddress) {
        this.customersStreetAddress = customersStreetAddress;
        return this;
    }

    public String getCustomersSuburb() {
        return customersSuburb;
    }

    public void setCustomersSuburb(String customersSuburb) {
        this.customersSuburb = customersSuburb;
    }

    public OrderDetails withCustomersSuburb(String customersSuburb) {
        this.customersSuburb = customersSuburb;
        return this;
    }

    public String getCustomersCity() {
        return customersCity;
    }

    public void setCustomersCity(String customersCity) {
        this.customersCity = customersCity;
    }

    public OrderDetails withCustomersCity(String customersCity) {
        this.customersCity = customersCity;
        return this;
    }

    public String getCustomersPostcode() {
        return customersPostcode;
    }

    public void setCustomersPostcode(String customersPostcode) {
        this.customersPostcode = customersPostcode;
    }

    public OrderDetails withCustomersPostcode(String customersPostcode) {
        this.customersPostcode = customersPostcode;
        return this;
    }

    public String getCustomersState() {
        return customersState;
    }

    public void setCustomersState(String customersState) {
        this.customersState = customersState;
    }

    public OrderDetails withCustomersState(String customersState) {
        this.customersState = customersState;
        return this;
    }

    public String getCustomersCountry() {
        return customersCountry;
    }

    public void setCustomersCountry(String customersCountry) {
        this.customersCountry = customersCountry;
    }

    public OrderDetails withCustomersCountry(String customersCountry) {
        this.customersCountry = customersCountry;
        return this;
    }

    public String getCustomersTelephone() {
        return customersTelephone;
    }

    public void setCustomersTelephone(String customersTelephone) {
        this.customersTelephone = customersTelephone;
    }

    public OrderDetails withCustomersTelephone(String customersTelephone) {
        this.customersTelephone = customersTelephone;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public OrderDetails withEmail(String email) {
        this.email = email;
        return this;
    }

    public Object getCustomersAddressFormatId() {
        return customersAddressFormatId;
    }

    public void setCustomersAddressFormatId(Object customersAddressFormatId) {
        this.customersAddressFormatId = customersAddressFormatId;
    }

    public OrderDetails withCustomersAddressFormatId(Object customersAddressFormatId) {
        this.customersAddressFormatId = customersAddressFormatId;
        return this;
    }

    public String getDeliveryName() {
        return deliveryName;
    }

    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    public OrderDetails withDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
        return this;
    }

    public Object getDeliveryCompany() {
        return deliveryCompany;
    }

    public void setDeliveryCompany(Object deliveryCompany) {
        this.deliveryCompany = deliveryCompany;
    }

    public OrderDetails withDeliveryCompany(Object deliveryCompany) {
        this.deliveryCompany = deliveryCompany;
        return this;
    }

    public String getDeliveryStreetAddress() {
        return deliveryStreetAddress;
    }

    public void setDeliveryStreetAddress(String deliveryStreetAddress) {
        this.deliveryStreetAddress = deliveryStreetAddress;
    }

    public OrderDetails withDeliveryStreetAddress(String deliveryStreetAddress) {
        this.deliveryStreetAddress = deliveryStreetAddress;
        return this;
    }

    public String getDeliverySuburb() {
        return deliverySuburb;
    }

    public void setDeliverySuburb(String deliverySuburb) {
        this.deliverySuburb = deliverySuburb;
    }

    public OrderDetails withDeliverySuburb(String deliverySuburb) {
        this.deliverySuburb = deliverySuburb;
        return this;
    }

    public String getDeliveryCity() {
        return deliveryCity;
    }

    public void setDeliveryCity(String deliveryCity) {
        this.deliveryCity = deliveryCity;
    }

    public OrderDetails withDeliveryCity(String deliveryCity) {
        this.deliveryCity = deliveryCity;
        return this;
    }

    public String getDeliveryPostcode() {
        return deliveryPostcode;
    }

    public void setDeliveryPostcode(String deliveryPostcode) {
        this.deliveryPostcode = deliveryPostcode;
    }

    public OrderDetails withDeliveryPostcode(String deliveryPostcode) {
        this.deliveryPostcode = deliveryPostcode;
        return this;
    }

    public String getDeliveryState() {
        return deliveryState;
    }

    public void setDeliveryState(String deliveryState) {
        this.deliveryState = deliveryState;
    }

    public OrderDetails withDeliveryState(String deliveryState) {
        this.deliveryState = deliveryState;
        return this;
    }

    public String getDeliveryCountry() {
        return deliveryCountry;
    }

    public void setDeliveryCountry(String deliveryCountry) {
        this.deliveryCountry = deliveryCountry;
    }

    public OrderDetails withDeliveryCountry(String deliveryCountry) {
        this.deliveryCountry = deliveryCountry;
        return this;
    }

    public Object getDeliveryAddressFormatId() {
        return deliveryAddressFormatId;
    }

    public void setDeliveryAddressFormatId(Object deliveryAddressFormatId) {
        this.deliveryAddressFormatId = deliveryAddressFormatId;
    }

    public OrderDetails withDeliveryAddressFormatId(Object deliveryAddressFormatId) {
        this.deliveryAddressFormatId = deliveryAddressFormatId;
        return this;
    }

    public String getDeliveryLatitude() {
        return deliveryLatitude;
    }

    public void setDeliveryLatitude(String deliveryLatitude) {
        this.deliveryLatitude = deliveryLatitude;
    }

    public String getDeliveryLongitude() {
        return deliveryLongitude;
    }

    public void setDeliveryLongitude(String deliveryLongitude) {
        this.deliveryLongitude = deliveryLongitude;
    }

    public String getBillingName() {
        return billingName;
    }

    public void setBillingName(String billingName) {
        this.billingName = billingName;
    }

    public OrderDetails withBillingName(String billingName) {
        this.billingName = billingName;
        return this;
    }

    public Object getBillingCompany() {
        return billingCompany;
    }

    public void setBillingCompany(Object billingCompany) {
        this.billingCompany = billingCompany;
    }

    public OrderDetails withBillingCompany(Object billingCompany) {
        this.billingCompany = billingCompany;
        return this;
    }

    public String getBillingStreetAddress() {
        return billingStreetAddress;
    }

    public void setBillingStreetAddress(String billingStreetAddress) {
        this.billingStreetAddress = billingStreetAddress;
    }

    public OrderDetails withBillingStreetAddress(String billingStreetAddress) {
        this.billingStreetAddress = billingStreetAddress;
        return this;
    }

    public String getBillingSuburb() {
        return billingSuburb;
    }

    public void setBillingSuburb(String billingSuburb) {
        this.billingSuburb = billingSuburb;
    }

    public OrderDetails withBillingSuburb(String billingSuburb) {
        this.billingSuburb = billingSuburb;
        return this;
    }

    public String getBillingCity() {
        return billingCity;
    }

    public void setBillingCity(String billingCity) {
        this.billingCity = billingCity;
    }

    public OrderDetails withBillingCity(String billingCity) {
        this.billingCity = billingCity;
        return this;
    }

    public String getBillingPostcode() {
        return billingPostcode;
    }

    public void setBillingPostcode(String billingPostcode) {
        this.billingPostcode = billingPostcode;
    }

    public OrderDetails withBillingPostcode(String billingPostcode) {
        this.billingPostcode = billingPostcode;
        return this;
    }

    public String getBillingState() {
        return billingState;
    }

    public void setBillingState(String billingState) {
        this.billingState = billingState;
    }

    public OrderDetails withBillingState(String billingState) {
        this.billingState = billingState;
        return this;
    }

    public String getBillingCountry() {
        return billingCountry;
    }

    public void setBillingCountry(String billingCountry) {
        this.billingCountry = billingCountry;
    }

    public OrderDetails withBillingCountry(String billingCountry) {
        this.billingCountry = billingCountry;
        return this;
    }

    public long getBillingAddressFormatId() {
        return billingAddressFormatId;
    }

    public void setBillingAddressFormatId(long billingAddressFormatId) {
        this.billingAddressFormatId = billingAddressFormatId;
    }

    public OrderDetails withBillingAddressFormatId(long billingAddressFormatId) {
        this.billingAddressFormatId = billingAddressFormatId;
        return this;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public OrderDetails withPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public Object getCcType() {
        return ccType;
    }

    public void setCcType(Object ccType) {
        this.ccType = ccType;
    }

    public OrderDetails withCcType(Object ccType) {
        this.ccType = ccType;
        return this;
    }

    public Object getCcOwner() {
        return ccOwner;
    }

    public void setCcOwner(Object ccOwner) {
        this.ccOwner = ccOwner;
    }

    public OrderDetails withCcOwner(Object ccOwner) {
        this.ccOwner = ccOwner;
        return this;
    }

    public Object getCcNumber() {
        return ccNumber;
    }

    public void setCcNumber(Object ccNumber) {
        this.ccNumber = ccNumber;
    }

    public OrderDetails withCcNumber(Object ccNumber) {
        this.ccNumber = ccNumber;
        return this;
    }

    public Object getCcExpires() {
        return ccExpires;
    }

    public void setCcExpires(Object ccExpires) {
        this.ccExpires = ccExpires;
    }

    public OrderDetails withCcExpires(Object ccExpires) {
        this.ccExpires = ccExpires;
        return this;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public OrderDetails withLastModified(String lastModified) {
        this.lastModified = lastModified;
        return this;
    }

    public String getDatePurchased() {
        return datePurchased;
    }

    public void setDatePurchased(String datePurchased) {
        this.datePurchased = datePurchased;
    }

    public OrderDetails withDatePurchased(String datePurchased) {
        this.datePurchased = datePurchased;
        return this;
    }

    public Object getOrdersDateFinished() {
        return ordersDateFinished;
    }

    public void setOrdersDateFinished(Object ordersDateFinished) {
        this.ordersDateFinished = ordersDateFinished;
    }

    public OrderDetails withOrdersDateFinished(Object ordersDateFinished) {
        this.ordersDateFinished = ordersDateFinished;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public OrderDetails withCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public String getCurrencyValue() {
        return currencyValue;
    }

    public void setCurrencyValue(String currencyValue) {
        this.currencyValue = currencyValue;
    }

    public OrderDetails withCurrencyValue(String currencyValue) {
        this.currencyValue = currencyValue;
        return this;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public OrderDetails withOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
        return this;
    }

    public String getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(String shippingCost) {
        this.shippingCost = shippingCost;
    }

    public OrderDetails withShippingCost(String shippingCost) {
        this.shippingCost = shippingCost;
        return this;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public OrderDetails withShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
        return this;
    }

    public Object getShippingDuration() {
        return shippingDuration;
    }

    public void setShippingDuration(Object shippingDuration) {
        this.shippingDuration = shippingDuration;
    }

    public OrderDetails withShippingDuration(Object shippingDuration) {
        this.shippingDuration = shippingDuration;
        return this;
    }

    public String getOrderInformation() {
        return orderInformation;
    }

    public void setOrderInformation(String orderInformation) {
        this.orderInformation = orderInformation;
    }

    public OrderDetails withOrderInformation(String orderInformation) {
        this.orderInformation = orderInformation;
        return this;
    }

    public long getIsSeen() {
        return isSeen;
    }

    public void setIsSeen(long isSeen) {
        this.isSeen = isSeen;
    }

    public OrderDetails withIsSeen(long isSeen) {
        this.isSeen = isSeen;
        return this;
    }

    public String getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(String couponAmount) {
        this.couponAmount = couponAmount;
    }

    public OrderDetails withCouponAmount(String couponAmount) {
        this.couponAmount = couponAmount;
        return this;
    }

    public List<Object> getExcludeProductIds() {
        return excludeProductIds;
    }

    public void setExcludeProductIds(List<Object> excludeProductIds) {
        this.excludeProductIds = excludeProductIds;
    }

    public OrderDetails withExcludeProductIds(List<Object> excludeProductIds) {
        this.excludeProductIds = excludeProductIds;
        return this;
    }

    public List<Object> getProductCategories() {
        return productCategories;
    }

    public void setProductCategories(List<Object> productCategories) {
        this.productCategories = productCategories;
    }

    public OrderDetails withProductCategories(List<Object> productCategories) {
        this.productCategories = productCategories;
        return this;
    }

    public List<Object> getExcludedProductCategories() {
        return excludedProductCategories;
    }

    public void setExcludedProductCategories(List<Object> excludedProductCategories) {
        this.excludedProductCategories = excludedProductCategories;
    }

    public OrderDetails withExcludedProductCategories(List<Object> excludedProductCategories) {
        this.excludedProductCategories = excludedProductCategories;
        return this;
    }

    public long getFreeShipping() {
        return freeShipping;
    }

    public void setFreeShipping(long freeShipping) {
        this.freeShipping = freeShipping;
    }

    public OrderDetails withFreeShipping(long freeShipping) {
        this.freeShipping = freeShipping;
        return this;
    }

    public List<Object> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Object> productIds) {
        this.productIds = productIds;
    }

    public OrderDetails withProductIds(List<Object> productIds) {
        this.productIds = productIds;
        return this;
    }

    public long getOrderedSource() {
        return orderedSource;
    }

    public void setOrderedSource(long orderedSource) {
        this.orderedSource = orderedSource;
    }

    public OrderDetails withOrderedSource(long orderedSource) {
        this.orderedSource = orderedSource;
        return this;
    }

    public String getDeliveryPhone() {
        return deliveryPhone;
    }

    public void setDeliveryPhone(String deliveryPhone) {
        this.deliveryPhone = deliveryPhone;
    }

    public OrderDetails withDeliveryPhone(String deliveryPhone) {
        this.deliveryPhone = deliveryPhone;
        return this;
    }

    public String getBillingPhone() {
        return billingPhone;
    }

    public void setBillingPhone(String billingPhone) {
        this.billingPhone = billingPhone;
    }

    public OrderDetails withBillingPhone(String billingPhone) {
        this.billingPhone = billingPhone;
        return this;
    }

    public Object getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Object transactionId) {
        this.transactionId = transactionId;
    }

    public OrderDetails withTransactionId(Object transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
    }

    public OrderDetails withCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<DeliveryboyInfo> getDeliveryboyInfo() {
        return deliveryboyInfo;
    }

    public void setDeliveryboyInfo(List<DeliveryboyInfo> deliveryboyInfo) {
        this.deliveryboyInfo = deliveryboyInfo;
    }

    public OrderDetails withUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public long getVendorsId() {
        return vendorsId;
    }

    public void setVendorsId(long vendorsId) {
        this.vendorsId = vendorsId;
    }

    public OrderDetails withVendorsId(long vendorsId) {
        this.vendorsId = vendorsId;
        return this;
    }

    public Object getDiscountType() {
        return discountType;
    }

    public void setDiscountType(Object discountType) {
        this.discountType = discountType;
    }

    public OrderDetails withDiscountType(Object discountType) {
        this.discountType = discountType;
        return this;
    }

    public List<Object> getAmount() {
        return amount;
    }

    public void setAmount(List<Object> amount) {
        this.amount = amount;
    }

    public OrderDetails withAmount(List<Object> amount) {
        this.amount = amount;
        return this;
    }

    public List<Object> getUsageLimit() {
        return usageLimit;
    }

    public void setUsageLimit(List<Object> usageLimit) {
        this.usageLimit = usageLimit;
    }

    public OrderDetails withUsageLimit(List<Object> usageLimit) {
        this.usageLimit = usageLimit;
        return this;
    }

    public List<CouponsInfo> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<CouponsInfo> coupons) {
        this.coupons = coupons;
    }

    public OrderDetails withCoupons(List<CouponsInfo> coupons) {
        this.coupons = coupons;
        return this;
    }

    public long getOrdersStatusId() {
        return ordersStatusId;
    }

    public void setOrdersStatusId(long ordersStatusId) {
        this.ordersStatusId = ordersStatusId;
    }

    public OrderDetails withOrdersStatusId(long ordersStatusId) {
        this.ordersStatusId = ordersStatusId;
        return this;
    }

    public String getOrdersStatus() {
        return ordersStatus;
    }

    public void setOrdersStatus(String ordersStatus) {
        this.ordersStatus = ordersStatus;
    }

    public OrderDetails withOrdersStatus(String ordersStatus) {
        this.ordersStatus = ordersStatus;
        return this;
    }

    public String getCustomerComments() {
        return customerComments;
    }

    public void setCustomerComments(String customerComments) {
        this.customerComments = customerComments;
    }

    public OrderDetails withCustomerComments(String customerComments) {
        this.customerComments = customerComments;
        return this;
    }

    public String getAdminComments() {
        return adminComments;
    }

    public void setAdminComments(String adminComments) {
        this.adminComments = adminComments;
    }

    public OrderDetails withAdminComments(String adminComments) {
        this.adminComments = adminComments;
        return this;
    }

    public List<OrderProducts> getProducts() {
        return data;
    }

    public void setProducts(List<OrderProducts> data) {
        this.data = data;
    }

    public OrderDetails withProducts(List<OrderProducts> data) {
        this.data = data;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(ordersId);
        dest.writeValue(totalTax);
        dest.writeValue(customersId);
        dest.writeValue(customersName);
        dest.writeValue(customersCompany);
        dest.writeValue(customersStreetAddress);
        dest.writeValue(customersSuburb);
        dest.writeValue(customersCity);
        dest.writeValue(customersPostcode);
        dest.writeValue(customersState);
        dest.writeValue(customersCountry);
        dest.writeValue(customersTelephone);
        dest.writeValue(email);
        dest.writeValue(customersAddressFormatId);
        dest.writeValue(deliveryName);
        dest.writeValue(deliveryCompany);
        dest.writeValue(deliveryStreetAddress);
        dest.writeValue(deliverySuburb);
        dest.writeValue(deliveryCity);
        dest.writeValue(deliveryPostcode);
        dest.writeValue(deliveryState);
        dest.writeValue(deliveryCountry);
        dest.writeValue(deliveryAddressFormatId);
        dest.writeValue(billingName);
        dest.writeValue(billingCompany);
        dest.writeValue(billingStreetAddress);
        dest.writeValue(billingSuburb);
        dest.writeValue(billingCity);
        dest.writeValue(billingPostcode);
        dest.writeValue(billingState);
        dest.writeValue(billingCountry);
        dest.writeValue(billingAddressFormatId);
        dest.writeValue(paymentMethod);
        dest.writeValue(ccType);
        dest.writeValue(ccOwner);
        dest.writeValue(ccNumber);
        dest.writeValue(ccExpires);
        dest.writeValue(lastModified);
        dest.writeValue(datePurchased);
        dest.writeValue(ordersDateFinished);
        dest.writeValue(currency);
        dest.writeValue(currencyValue);
        dest.writeValue(orderPrice);
        dest.writeValue(shippingCost);
        dest.writeValue(shippingMethod);
        dest.writeValue(shippingDuration);
        dest.writeValue(orderInformation);
        dest.writeValue(isSeen);
        dest.writeValue(couponAmount);
        dest.writeList(excludeProductIds);
        dest.writeList(productCategories);
        dest.writeList(excludedProductCategories);
        dest.writeValue(freeShipping);
        dest.writeList(productIds);
        dest.writeValue(orderedSource);
        dest.writeValue(deliveryPhone);
        dest.writeValue(billingPhone);
        dest.writeValue(transactionId);
        dest.writeValue(createdAt);
        dest.writeValue(updatedAt);
        dest.writeValue(vendorsId);
        dest.writeValue(discountType);
        dest.writeList(amount);
        dest.writeList(usageLimit);
        dest.writeList(coupons);
        dest.writeValue(ordersStatusId);
        dest.writeValue(ordersStatus);
        dest.writeValue(customerComments);
        dest.writeValue(adminComments);
        dest.writeList(data);
    }

    public int describeContents() {
        return 0;
    }

}