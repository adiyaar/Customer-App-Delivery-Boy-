package com.themescoder.androidstore.models.filter_model.get_filters;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class FilterDetails {

    @SerializedName("option")
    @Expose
    private FilterOption option;
    @SerializedName("values")
    @Expose
    private List<FilterValue> values = new ArrayList<FilterValue>();

    /**
     * 
     * @return
     *     The option
     */
    public FilterOption getOption() {
        return option;
    }

    /**
     * 
     * @param option
     *     The option
     */
    public void setOption(FilterOption option) {
        this.option = option;
    }

    /**
     * 
     * @return
     *     The values
     */
    public List<FilterValue> getValues() {
        return values;
    }

    /**
     * 
     * @param values
     *     The values
     */
    public void setValues(List<FilterValue> values) {
        this.values = values;
    }

}
