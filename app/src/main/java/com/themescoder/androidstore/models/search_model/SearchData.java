package com.themescoder.androidstore.models.search_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchData {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("product_data")
    @Expose
    private SearchDetails productData;
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
     *     The productData
     */
    public SearchDetails getProductData() {
        return productData;
    }

    /**
     * 
     * @param productData
     *     The product_data
     */
    public void setProductData(SearchDetails productData) {
        this.productData = productData;
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
