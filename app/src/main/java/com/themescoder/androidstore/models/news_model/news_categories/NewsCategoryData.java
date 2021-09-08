
package com.themescoder.androidstore.models.news_model.news_categories;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewsCategoryData {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("data")
    @Expose
    private List<NewsCategoryDetails> data = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("categories")
    @Expose
    private Object categories = null;


    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<NewsCategoryDetails> getData() {
        return data;
    }

    public void setData(List<NewsCategoryDetails> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getCategories() {
        return categories;
    }

    public void setCategories(Object categories) {
        this.categories = categories;
    }

}
