package com.themescoder.androidstore.models.ratings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RatingDataList {

    @SerializedName("reviews_id")
    @Expose
    private Integer reviewsId;
    @SerializedName("products_id")
    @Expose
    private Integer productsId;
    @SerializedName("customers_id")
    @Expose
    private Integer customersId;
    @SerializedName("customers_name")
    @Expose
    private String customersName;
    @SerializedName("reviews_rating")
    @Expose
    private Integer reviewsRating;
    @SerializedName("created_at")
    @Expose
    private Object createdAt;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;
    @SerializedName("reviews_status")
    @Expose
    private Integer reviewsStatus;
    @SerializedName("reviews_read")
    @Expose
    private Integer reviewsRead;
    @SerializedName("vendors_id")
    @Expose
    private Integer vendorsId;
    @SerializedName("image")
    @Expose
    private String image;

    public Integer getReviewsId() {
        return reviewsId;
    }

    public void setReviewsId(Integer reviewsId) {
        this.reviewsId = reviewsId;
    }

    public Integer getProductsId() {
        return productsId;
    }

    public void setProductsId(Integer productsId) {
        this.productsId = productsId;
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

    public Integer getReviewsRating() {
        return reviewsRating;
    }

    public void setReviewsRating(Integer reviewsRating) {
        this.reviewsRating = reviewsRating;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getReviewsStatus() {
        return reviewsStatus;
    }

    public void setReviewsStatus(Integer reviewsStatus) {
        this.reviewsStatus = reviewsStatus;
    }

    public Integer getReviewsRead() {
        return reviewsRead;
    }

    public void setReviewsRead(Integer reviewsRead) {
        this.reviewsRead = reviewsRead;
    }

    public Integer getVendorsId() {
        return vendorsId;
    }

    public void setVendorsId(Integer vendorsId) {
        this.vendorsId = vendorsId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
