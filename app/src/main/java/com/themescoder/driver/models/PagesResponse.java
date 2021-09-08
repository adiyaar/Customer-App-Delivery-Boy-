
package com.themescoder.driver.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PagesResponse {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("pages_data")
    @Expose
    private List<PagesDetails> pagesData = null;
    @SerializedName("message")
    @Expose
    private String message;

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
