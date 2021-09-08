package com.themescoder.androidstore.models.pay_tm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Checksum {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("data")
    @Expose
    private ChecksumData data;
    @SerializedName("message")
    @Expose
    private String message;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public ChecksumData getData() {
        return data;
    }

    public void setData(ChecksumData data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}