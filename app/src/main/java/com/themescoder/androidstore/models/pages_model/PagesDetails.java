package com.themescoder.androidstore.models.pages_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PagesDetails {
    
    @SerializedName("page_id")
    @Expose
    private String pageId;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("page_description_id")
    @Expose
    private String pageDescriptionId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("language_id")
    @Expose
    private String languageId;
    
    
    public String getPageId() {
        return pageId;
    }
    
    public void setPageId(String pageId) {
        this.pageId = pageId;
    }
    
    public String getSlug() {
        return slug;
    }
    
    public void setSlug(String slug) {
        this.slug = slug;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getPageDescriptionId() {
        return pageDescriptionId;
    }
    
    public void setPageDescriptionId(String pageDescriptionId) {
        this.pageDescriptionId = pageDescriptionId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getLanguageId() {
        return languageId;
    }
    
    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }
    
}
