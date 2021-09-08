package com.themescoder.androidstore.models.filter_model.post_filters;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PostFilterData {

    @SerializedName("filters")
    @Expose
    private List<FiltersAttributes> filters = new ArrayList<FiltersAttributes>();
    @SerializedName("price")
    @Expose
    private FiltersPrice price;


    /**
     * 
     * @return
     *     The filters
     */
    public List<FiltersAttributes> getFilters() {
        return filters;
    }

    /**
     * 
     * @param filters
     *     The filters
     */
    public void setFilters(List<FiltersAttributes> filters) {
        this.filters = filters;
    }

    /**
     * 
     * @return
     *     The price
     */
    public FiltersPrice getPrice() {
        return price;
    }

    /**
     * 
     * @param price
     *     The price
     */
    public void setPrice(FiltersPrice price) {
        this.price = price;
    }

}
