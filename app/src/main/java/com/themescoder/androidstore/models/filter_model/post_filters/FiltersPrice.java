package com.themescoder.androidstore.models.filter_model.post_filters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class FiltersPrice {

    @SerializedName("minPrice")
    @Expose
    private int minPrice;
    @SerializedName("maxPrice")
    @Expose
    private int maxPrice;

    /**
     * 
     * @return
     *     The minPrice
     */
    public int getMinPrice() {
        return minPrice;
    }

    /**
     * 
     * @param minPrice
     *     The minPrice
     */
    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    /**
     * 
     * @return
     *     The maxPrice
     */
    public int getMaxPrice() {
        return maxPrice;
    }

    /**
     * 
     * @param maxPrice
     *     The maxPrice
     */
    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

}
