package com.themescoder.androidstore.models.order_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Extras {

    @SerializedName("empty")
    @Expose
    private Boolean empty;
    @SerializedName("parcelled")
    @Expose
    private Boolean parcelled;
    @SerializedName("size")
    @Expose
    private Integer size;

    public Boolean getEmpty() {
        return empty;
    }

    public void setEmpty(Boolean empty) {
        this.empty = empty;
    }

    public Boolean getParcelled() {
        return parcelled;
    }

    public void setParcelled(Boolean parcelled) {
        this.parcelled = parcelled;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

}