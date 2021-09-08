package com.themescoder.androidstore.models.category_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class CategoryDetails {

    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("childs")
    @Expose
    private List<CategoryDetails> childList;
   // private List<Map<String, List<ChildCategoryDetail>>> childList;
    @SerializedName("categories_id")
    @Expose
    private String id;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("categories_name")
    @Expose
    private String name;
    @SerializedName("parent_id")
    @Expose
    private String parentId;
    @SerializedName("total_products")
    @Expose
    private String totalProducts;

    public String getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(String totalProducts) {
        this.totalProducts = totalProducts;
    }
    public List<CategoryDetails> getChildList() {
        return childList;
    }

    public void setChildList(List<CategoryDetails> childList) {
        this.childList = childList;
    }

   /* public List<Map<String, List<ChildCategoryDetail>>> getChildList() {
        return childList;
    }

    public void setChildList(List<Map<String, List<ChildCategoryDetail>>> childList) {
        this.childList = childList;
    }*/
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }


    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }


}
