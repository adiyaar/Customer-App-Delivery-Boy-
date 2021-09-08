package com.themescoder.androidstore.models.pages_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class PagesData {
    
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("pages_data")
    @Expose
    private List<PagesDetails> pagesData = new ArrayList<>();
    
    
    public String getSuccess() {
        return success;
    }
    
    public void setSuccess(String success) {
        this.success = success;
    }
    
    public List<PagesDetails> getPagesData() {
        return pagesData;
    }
    
    public void setPagesData(List<PagesDetails> pagesData) {
        this.pagesData = pagesData;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
}
