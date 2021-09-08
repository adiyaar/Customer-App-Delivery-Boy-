
package com.themescoder.androidstore.models.currency_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrencyList {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("symbol_left")
    @Expose
    private Object symbolLeft;
    @SerializedName("symbol_right")
    @Expose
    private String symbolRight;
    @SerializedName("decimal_point")
    @Expose
    private Object decimalPoint;
    @SerializedName("thousands_point")
    @Expose
    private Object thousandsPoint;
    @SerializedName("decimal_places")
    @Expose
    private String decimalPlaces;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("value")
    @Expose
    private Double value;
    @SerializedName("is_default")
    @Expose
    private Integer isDefault;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("is_current")
    @Expose
    private Integer isCurrent;
    private final static long serialVersionUID = -1716858892204819911L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getSymbolLeft() {
        return symbolLeft;
    }

    public void setSymbolLeft(Object symbolLeft) {
        this.symbolLeft = symbolLeft;
    }

    public String getSymbolRight() {
        return symbolRight;
    }

    public void setSymbolRight(String symbolRight) {
        this.symbolRight = symbolRight;
    }

    public Object getDecimalPoint() {
        return decimalPoint;
    }

    public void setDecimalPoint(Object decimalPoint) {
        this.decimalPoint = decimalPoint;
    }

    public Object getThousandsPoint() {
        return thousandsPoint;
    }

    public void setThousandsPoint(Object thousandsPoint) {
        this.thousandsPoint = thousandsPoint;
    }

    public String getDecimalPlaces() {
        return decimalPlaces;
    }

    public void setDecimalPlaces(String decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(Integer isCurrent) {
        this.isCurrent = isCurrent;
    }
}
