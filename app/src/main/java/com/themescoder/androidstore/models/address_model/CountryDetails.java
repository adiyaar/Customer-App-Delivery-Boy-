package com.themescoder.androidstore.models.address_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountryDetails {

    @SerializedName("countries_id")
    @Expose
    private int countriesId;
    @SerializedName("countries_name")
    @Expose
    private String countriesName;
    @SerializedName("countries_iso_code_2")
    @Expose
    private String countriesIsoCode2;
    @SerializedName("countries_iso_code_3")
    @Expose
    private String countriesIsoCode3;
    @SerializedName("address_format_id")
    @Expose
    private int addressFormatId;

    /**
     * 
     * @return
     *     The countriesId
     */
    public int getCountriesId() {
        return countriesId;
    }

    /**
     * 
     * @param countriesId
     *     The countries_id
     */
    public void setCountriesId(int countriesId) {
        this.countriesId = countriesId;
    }

    /**
     * 
     * @return
     *     The countriesName
     */
    public String getCountriesName() {
        return countriesName;
    }

    /**
     * 
     * @param countriesName
     *     The countries_name
     */
    public void setCountriesName(String countriesName) {
        this.countriesName = countriesName;
    }

    /**
     * 
     * @return
     *     The countriesIsoCode2
     */
    public String getCountriesIsoCode2() {
        return countriesIsoCode2;
    }

    /**
     * 
     * @param countriesIsoCode2
     *     The countries_iso_code_2
     */
    public void setCountriesIsoCode2(String countriesIsoCode2) {
        this.countriesIsoCode2 = countriesIsoCode2;
    }

    /**
     * 
     * @return
     *     The countriesIsoCode3
     */
    public String getCountriesIsoCode3() {
        return countriesIsoCode3;
    }

    /**
     * 
     * @param countriesIsoCode3
     *     The countries_iso_code_3
     */
    public void setCountriesIsoCode3(String countriesIsoCode3) {
        this.countriesIsoCode3 = countriesIsoCode3;
    }

    /**
     * 
     * @return
     *     The addressFormatId
     */
    public int getAddressFormatId() {
        return addressFormatId;
    }

    /**
     * 
     * @param addressFormatId
     *     The address_format_id
     */
    public void setAddressFormatId(int addressFormatId) {
        this.addressFormatId = addressFormatId;
    }

}
