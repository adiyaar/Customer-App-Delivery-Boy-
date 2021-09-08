package com.themescoder.androidstore.models.filter_model.get_filters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class FilterOption {

    @SerializedName("name")
    @Expose
    private String name;

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

}
