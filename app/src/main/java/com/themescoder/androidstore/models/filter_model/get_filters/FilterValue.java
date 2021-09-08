package com.themescoder.androidstore.models.filter_model.get_filters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class FilterValue {

    @SerializedName("value")
    @Expose
    private String value;

    /**
     * 
     * @return
     *     The value
     */
    public String getValue() {
        return value;
    }

    /**
     * 
     * @param value
     *     The value
     */
    public void setValue(String value) {
        this.value = value;
    }

}
