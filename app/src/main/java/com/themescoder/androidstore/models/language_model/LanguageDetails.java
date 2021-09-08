package com.themescoder.androidstore.models.language_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class LanguageDetails {
    
    @SerializedName("languages_id")
    @Expose
    private String languagesId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("directory")
    @Expose
    private String directory;
    @SerializedName("sort_order")
    @Expose
    private String sortOrder;
    @SerializedName("direction")
    @Expose
    private String direction;
    @SerializedName("is_default")
    @Expose
    private String isDefault;
    
    
    public String getLanguagesId() {
        return languagesId;
    }
    
    public void setLanguagesId(String languagesId) {
        this.languagesId = languagesId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getImage() {
        return image;
    }
    
    public void setImage(String image) {
        this.image = image;
    }
    
    public String getDirectory() {
        return directory;
    }
    
    public void setDirectory(String directory) {
        this.directory = directory;
    }
    
    public String getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    public String getDirection() {
        return direction;
    }
    
    public void setDirection(String direction) {
        this.direction = direction;
    }
    
    public String getIsDefault() {
        return isDefault;
    }
    
    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }
    
}
