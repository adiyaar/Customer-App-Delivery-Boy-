package com.themescoder.androidstore.models.order_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderProductCategory {

    @SerializedName("categories_id")
    @Expose
    private long categoriesId;
    @SerializedName("categories_name")
    @Expose
    private String categoriesName;
    @SerializedName("categories_image")
    @Expose
    private String categoriesImage;
    @SerializedName("categories_icon")
    @Expose
    private String categoriesIcon;
    @SerializedName("parent_id")
    @Expose
    private long parentId;

    public long getCategoriesId() {
        return categoriesId;
    }

    public void setCategoriesId(long categoriesId) {
        this.categoriesId = categoriesId;
    }

    public OrderProductCategory withCategoriesId(long categoriesId) {
        this.categoriesId = categoriesId;
        return this;
    }

    public String getCategoriesName() {
        return categoriesName;
    }

    public void setCategoriesName(String categoriesName) {
        this.categoriesName = categoriesName;
    }

    public OrderProductCategory withCategoriesName(String categoriesName) {
        this.categoriesName = categoriesName;
        return this;
    }

    public String getCategoriesImage() {
        return categoriesImage;
    }

    public void setCategoriesImage(String categoriesImage) {
        this.categoriesImage = categoriesImage;
    }

    public OrderProductCategory withCategoriesImage(String categoriesImage) {
        this.categoriesImage = categoriesImage;
        return this;
    }

    public String getCategoriesIcon() {
        return categoriesIcon;
    }

    public void setCategoriesIcon(String categoriesIcon) {
        this.categoriesIcon = categoriesIcon;
    }

    public OrderProductCategory withCategoriesIcon(String categoriesIcon) {
        this.categoriesIcon = categoriesIcon;
        return this;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public OrderProductCategory withParentId(long parentId) {
        this.parentId = parentId;
        return this;
    }

}