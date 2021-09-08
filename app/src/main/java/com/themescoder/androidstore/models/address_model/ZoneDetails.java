package com.themescoder.androidstore.models.address_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ZoneDetails {

    @SerializedName("zone_id")
    @Expose
    private int zoneId;
    @SerializedName("zone_country_id")
    @Expose
    private int zoneCountryId;
    @SerializedName("zone_code")
    @Expose
    private String zoneCode;
    @SerializedName("zone_name")
    @Expose
    private String zoneName;

    /**
     * 
     * @return
     *     The zoneId
     */
    public int getZoneId() {
        return zoneId;
    }

    /**
     * 
     * @param zoneId
     *     The zone_id
     */
    public void setZoneId(int zoneId) {
        this.zoneId = zoneId;
    }

    /**
     * 
     * @return
     *     The zoneCountryId
     */
    public int getZoneCountryId() {
        return zoneCountryId;
    }

    /**
     * 
     * @param zoneCountryId
     *     The zone_country_id
     */
    public void setZoneCountryId(int zoneCountryId) {
        this.zoneCountryId = zoneCountryId;
    }

    /**
     * 
     * @return
     *     The zoneCode
     */
    public String getZoneCode() {
        return zoneCode;
    }

    /**
     * 
     * @param zoneCode
     *     The zone_code
     */
    public void setZoneCode(String zoneCode) {
        this.zoneCode = zoneCode;
    }

    /**
     * 
     * @return
     *     The zoneName
     */
    public String getZoneName() {
        return zoneName;
    }

    /**
     * 
     * @param zoneName
     *     The zone_name
     */
    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

}
