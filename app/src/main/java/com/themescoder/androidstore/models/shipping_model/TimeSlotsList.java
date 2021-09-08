
package com.themescoder.androidstore.models.shipping_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimeSlotsList {

    @SerializedName("time_from")
    @Expose
    private String timeFrom;
    @SerializedName("time_to")
    @Expose
    private String timeTo;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("server_time")
    @Expose
    private String serverTime;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    private boolean isSelected;
    
    public String getServerTime() {
        return serverTime;
    }
    
    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }
    
    
    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
