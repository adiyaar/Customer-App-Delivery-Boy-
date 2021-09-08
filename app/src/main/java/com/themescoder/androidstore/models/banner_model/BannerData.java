package com.themescoder.androidstore.models.banner_model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class BannerData {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("data")
    @Expose
    private List<BannerDetails> data = new ArrayList<BannerDetails>();
    @SerializedName("message")
    @Expose
    private String message;

    /**
     * 
     * @return
     *     The success
     */
    public String getSuccess() {
        return success;
    }

    /**
     * 
     * @param success
     *     The success
     */
    public void setSuccess(String success) {
        this.success = success;
    }

    /**
     * 
     * @return
     *     The data
     */
    public List<BannerDetails> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<BannerDetails> data) {
        this.data = data;
    }

    /**
     * 
     * @return
     *     The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * 
     * @param message
     *     The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
