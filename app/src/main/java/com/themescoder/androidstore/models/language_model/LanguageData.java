package com.themescoder.androidstore.models.language_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class LanguageData {
    
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("languages")
    @Expose
    private List<LanguageDetails> languages = null;
    @SerializedName("message")
    @Expose
    private String message;
    
    public String getSuccess() {
        return success;
    }
    
    public void setSuccess(String success) {
        this.success = success;
    }
    
    public List<LanguageDetails> getLanguages() {
        return languages;
    }
    
    public void setLanguages(List<LanguageDetails> languages) {
        this.languages = languages;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
}
