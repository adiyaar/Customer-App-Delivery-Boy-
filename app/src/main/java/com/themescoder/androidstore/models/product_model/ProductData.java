package com.themescoder.androidstore.models.product_model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ProductData {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("product_data")
    @Expose
    private List<ProductDetails> productData = new ArrayList<>();
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("total_record")
    @Expose
    private Integer totalRecord;

    /**
     *
     * @return
     * The success
     */
    public String getSuccess() {
        return success;
    }

    /**
     *
     * @param success
     * The success
     */
    public void setSuccess(String success) {
        this.success = success;
    }

    /**
     *
     * @return
     * The productData
     */
    public List<ProductDetails> getProductData() {
        return productData;
    }

    /**
     *
     * @param productData
     * The product_data
     */
    public void setProductData(List<ProductDetails> productData) {
        this.productData = productData;
    }

    /**
     *
     * @return
     * The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *
     * @return
     * The totalRecord
     */
    public Integer getTotalRecord() {
        return totalRecord;
    }

    /**
     *
     * @param totalRecord
     * The total_record
     */
    public void setTotalRecord(Integer totalRecord) {
        this.totalRecord = totalRecord;
    }

}
