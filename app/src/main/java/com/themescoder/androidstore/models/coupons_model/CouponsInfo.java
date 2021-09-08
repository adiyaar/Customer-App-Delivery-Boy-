
package com.themescoder.androidstore.models.coupons_model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CouponsInfo implements Parcelable {

    @SerializedName("coupans_id")
    @Expose
    private String coupansId;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("discount_type")
    @Expose
    private String discountType;
    @SerializedName("date_created")
    @Expose
    private String dateCreated;
    @SerializedName("date_modified")
    @Expose
    private String dateModified;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("expiry_date")
    @Expose
    private String expiryDate;
    @SerializedName("usage_count")
    @Expose
    private String usageCount;
    @SerializedName("individual_use")
    @Expose
    private String individualUse;
    @SerializedName("product_ids")
    @Expose
    private List<String> productIds = new ArrayList<>();
    @SerializedName("exclude_product_ids")
    @Expose
    private List<String> excludeProductIds = new ArrayList<>();
    @SerializedName("usage_limit")
    @Expose
    private String usageLimit;
    @SerializedName("usage_limit_per_user")
    @Expose
    private String usageLimitPerUser;
    @SerializedName("limit_usage_to_x_items")
    @Expose
    private String limitUsageToXItems;
    @SerializedName("free_shipping")
    @Expose
    private String freeShipping;
    @SerializedName("product_categories")
    @Expose
    private List<String> productCategories = new ArrayList<>();
    @SerializedName("excluded_product_categories")
    @Expose
    private List<String> excludedProductCategories = new ArrayList<>();
    @SerializedName("exclude_sale_items")
    @Expose
    private String excludeSaleItems;
    @SerializedName("minimum_amount")
    @Expose
    private String minimumAmount;
    @SerializedName("maximum_amount")
    @Expose
    private String maximumAmount;
    @SerializedName("email_restrictions")
    @Expose
    private List<String> emailRestrictions = new ArrayList<>();
    @SerializedName("used_by")
    @Expose
    private List<String> usedBy = new ArrayList<>();



    public CouponsInfo() {
    }


    public String getCoupansId() {
        return coupansId;
    }

    public void setCoupansId(String coupansId) {
        this.coupansId = coupansId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
    
    public String getDiscount() {
        return discount;
    }
    
    public void setDiscount(String discount) {
        this.discount = discount;
    }
    
    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(String usageCount) {
        this.usageCount = usageCount;
    }

    public String getIndividualUse() {
        return individualUse;
    }

    public void setIndividualUse(String individualUse) {
        this.individualUse = individualUse;
    }

    public List<String> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<String> productIds) {
        this.productIds = productIds;
    }

    public List<String> getExcludeProductIds() {
        return excludeProductIds;
    }

    public void setExcludeProductIds(List<String> excludeProductIds) {
        this.excludeProductIds = excludeProductIds;
    }

    public String getUsageLimit() {
        return usageLimit;
    }

    public void setUsageLimit(String usageLimit) {
        this.usageLimit = usageLimit;
    }

    public String getUsageLimitPerUser() {
        return usageLimitPerUser;
    }

    public void setUsageLimitPerUser(String usageLimitPerUser) {
        this.usageLimitPerUser = usageLimitPerUser;
    }

    public String getLimitUsageToXItems() {
        return limitUsageToXItems;
    }

    public void setLimitUsageToXItems(String limitUsageToXItems) {
        this.limitUsageToXItems = limitUsageToXItems;
    }

    public String getFreeShipping() {
        return freeShipping;
    }

    public void setFreeShipping(String freeShipping) {
        this.freeShipping = freeShipping;
    }

    public List<String> getProductCategories() {
        return productCategories;
    }

    public void setProductCategories(List<String> productCategories) {
        this.productCategories = productCategories;
    }

    public List<String> getExcludedProductCategories() {
        return excludedProductCategories;
    }

    public void setExcludedProductCategories(List<String> excludedProductCategories) {
        this.excludedProductCategories = excludedProductCategories;
    }

    public String getExcludeSaleItems() {
        return excludeSaleItems;
    }

    public void setExcludeSaleItems(String excludeSaleItems) {
        this.excludeSaleItems = excludeSaleItems;
    }

    public String getMinimumAmount() {
        return minimumAmount;
    }

    public void setMinimumAmount(String minimumAmount) {
        this.minimumAmount = minimumAmount;
    }

    public String getMaximumAmount() {
        return maximumAmount;
    }

    public void setMaximumAmount(String maximumAmount) {
        this.maximumAmount = maximumAmount;
    }

    public List<String> getEmailRestrictions() {
        return emailRestrictions;
    }

    public void setEmailRestrictions(List<String> emailRestrictions) {
        this.emailRestrictions = emailRestrictions;
    }

    public List<String> getUsedBy() {
        return usedBy;
    }

    public void setUsedBy(List<String> usedBy) {
        this.usedBy = usedBy;
    }



    //********** Describes the kinds of Special Objects contained in this Parcelable Instance's marshaled representation *********//

    @Override
    public int describeContents() {
        return 0;
    }



    //********** Writes the values to the Parcel *********//

    public void writeToParcel(Parcel parcel_out, int flags) {
        parcel_out.writeValue(coupansId);
        parcel_out.writeValue(code);
        parcel_out.writeValue(dateCreated);
        parcel_out.writeValue(dateModified);
        parcel_out.writeValue(description);
        parcel_out.writeValue(discountType);
        parcel_out.writeValue(amount);
        parcel_out.writeValue(discount);
        parcel_out.writeValue(expiryDate);
        parcel_out.writeValue(usageCount);
        parcel_out.writeValue(individualUse);
        parcel_out.writeValue(usageLimit);
        parcel_out.writeValue(usageLimitPerUser);
        parcel_out.writeValue(limitUsageToXItems);
        parcel_out.writeValue(freeShipping);
        parcel_out.writeValue(excludeSaleItems);
        parcel_out.writeValue(minimumAmount);
        parcel_out.writeValue(maximumAmount);
        parcel_out.writeList(productIds);
        parcel_out.writeList(excludeProductIds);
        parcel_out.writeList(productCategories);
        parcel_out.writeList(excludedProductCategories);
        parcel_out.writeList(emailRestrictions);
        parcel_out.writeList(usedBy);
    }



    //********** Generates Instances of Parcelable class from a Parcel *********//

    public static final Creator<CouponsInfo> CREATOR = new Creator<CouponsInfo>() {

        // Creates a new Instance of the Parcelable class, Instantiating it from the given Parcel
        @Override
        public CouponsInfo createFromParcel(Parcel parcel_in) {
            return new CouponsInfo(parcel_in);
        }

        // Creates a new array of the Parcelable class
        @Override
        public CouponsInfo[] newArray(int size) {
            return new CouponsInfo[size];
        }
    };



    //********** Retrieves the values from the Parcel *********//

    protected CouponsInfo(Parcel parcel_in) {
        this.coupansId = parcel_in.readString();
        this.code = parcel_in.readString();
        this.dateCreated = parcel_in.readString();
        this.dateModified = parcel_in.readString();
        this.description = parcel_in.readString();
        this.discountType = parcel_in.readString();
        this.amount = parcel_in.readString();
        this.discount = parcel_in.readString();
        this.expiryDate = parcel_in.readString();
        this.usageCount = parcel_in.readString();
        this.individualUse = parcel_in.readString();
        this.usageLimit = parcel_in.readString();
        this.usageLimitPerUser = parcel_in.readString();
        this.limitUsageToXItems = parcel_in.readString();
        this.freeShipping = parcel_in.readString();
        this.excludeSaleItems = parcel_in.readString();
        this.minimumAmount = parcel_in.readString();
        this.maximumAmount = parcel_in.readString();
        this.productIds = parcel_in.createStringArrayList();
        this.excludeProductIds = parcel_in.createStringArrayList();
        this.productCategories = parcel_in.createStringArrayList();
        this.excludedProductCategories = parcel_in.createStringArrayList();
        this.emailRestrictions = parcel_in.createStringArrayList();
        this.usedBy = parcel_in.createStringArrayList();
    }

}
