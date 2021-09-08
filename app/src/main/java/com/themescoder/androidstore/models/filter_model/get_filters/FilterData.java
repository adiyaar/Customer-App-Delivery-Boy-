package com.themescoder.androidstore.models.filter_model.get_filters;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class FilterData {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("filters")
    @Expose
    private List<FilterDetails> filters = new ArrayList<FilterDetails>();
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("maxPrice")
    @Expose
    private String maxPrice;


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
     *     The filters
     */
    public List<FilterDetails> getFilters() {
        return filters;
    }

    /**
     * 
     * @param filters
     *     The filters
     */
    public void setFilters(List<FilterDetails> filters) {
        this.filters = filters;
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


    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }


}
