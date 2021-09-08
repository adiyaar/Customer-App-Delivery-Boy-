
package com.themescoder.androidstore.models.news_model.all_news;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewsData {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("news_data")
    @Expose
    private List<NewsDetails> newsData = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("total_record")
    @Expose
    private int totalRecord;


    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<NewsDetails> getNewsData() {
        return newsData;
    }

    public void setNewsData(List<NewsDetails> newsData) {
        this.newsData = newsData;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

}
