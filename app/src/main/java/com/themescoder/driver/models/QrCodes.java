
package com.themescoder.driver.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QrCodes {

    @SerializedName("qr_codes_id")
    @Expose
    private String qrCodesId;
    @SerializedName("qr_codes_name")
    @Expose
    private String qrCodesName;
    @SerializedName("qr_codes_image")
    @Expose
    private String qrCodesImage;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public String getQrCodesId() {
        return qrCodesId;
    }

    public void setQrCodesId(String qrCodesId) {
        this.qrCodesId = qrCodesId;
    }

    public String getQrCodesName() {
        return qrCodesName;
    }

    public void setQrCodesName(String qrCodesName) {
        this.qrCodesName = qrCodesName;
    }

    public String getQrCodesImage() {
        return qrCodesImage;
    }

    public void setQrCodesImage(String qrCodesImage) {
        this.qrCodesImage = qrCodesImage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

}
