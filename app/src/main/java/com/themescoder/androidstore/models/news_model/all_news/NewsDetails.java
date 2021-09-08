
package com.themescoder.androidstore.models.news_model.all_news;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewsDetails implements Parcelable {

    @SerializedName("news_id")
    @Expose
    private String newsId;
    @SerializedName("news_image")
    @Expose
    private String newsImage;
    @SerializedName("news_date_added")
    @Expose
    private String newsDateAdded;
    @SerializedName("news_last_modified")
    @Expose
    private String newsLastModified;
    @SerializedName("news_status")
    @Expose
    private String newsStatus;
    @SerializedName("language_id")
    @Expose
    private String languageId;
    @SerializedName("news_name")
    @Expose
    private String newsName;
    @SerializedName("news_description")
    @Expose
    private String newsDescription;
    @SerializedName("news_url")
    @Expose
    private String newsUrl;
    @SerializedName("news_viewed")
    @Expose
    private String newsViewed;
    @SerializedName("categories_id")
    @Expose
    private String categoriesId;


    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getNewsImage() {
        return newsImage;
    }

    public void setNewsImage(String newsImage) {
        this.newsImage = newsImage;
    }

    public String getNewsDateAdded() {
        return newsDateAdded;
    }

    public void setNewsDateAdded(String newsDateAdded) {
        this.newsDateAdded = newsDateAdded;
    }

    public String getNewsLastModified() {
        return newsLastModified;
    }

    public void setNewsLastModified(String newsLastModified) {
        this.newsLastModified = newsLastModified;
    }

    public String getNewsStatus() {
        return newsStatus;
    }

    public void setNewsStatus(String newsStatus) {
        this.newsStatus = newsStatus;
    }

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }

    public String getNewsName() {
        return newsName;
    }

    public void setNewsName(String newsName) {
        this.newsName = newsName;
    }

    public String getNewsDescription() {
        return newsDescription;
    }

    public void setNewsDescription(String newsDescription) {
        this.newsDescription = newsDescription;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public String getNewsViewed() {
        return newsViewed;
    }

    public void setNewsViewed(String newsViewed) {
        this.newsViewed = newsViewed;
    }

    public String getCategoriesId() {
        return categoriesId;
    }

    public void setCategoriesId(String categoriesId) {
        this.categoriesId = categoriesId;
    }




    //********** Describes the kinds of Special Objects contained in this Parcelable Instance's marshaled representation *********//

    @Override
    public int describeContents() {
        return 0;
    }



    //********** Writes the values to the Parcel *********//

    @Override
    public void writeToParcel(Parcel parcel_out, int flags) {
        parcel_out.writeString(newsId);
        parcel_out.writeString(newsImage);
        parcel_out.writeString(newsDateAdded);
        parcel_out.writeString(newsLastModified);
        parcel_out.writeString(newsStatus);
        parcel_out.writeString(languageId);
        parcel_out.writeString(newsName);
        parcel_out.writeString(newsDescription);
        parcel_out.writeString(newsUrl);
        parcel_out.writeString(newsViewed);
        parcel_out.writeString(categoriesId);
    }



    //********** Generates Instances of Parcelable class from a Parcel *********//

    public static final Creator<NewsDetails> CREATOR = new Creator<NewsDetails>() {
        // Creates a new Instance of the Parcelable class, Instantiating it from the given Parcel
        @Override
        public NewsDetails createFromParcel(Parcel parcel_in) {
            return new NewsDetails(parcel_in);
        }

        // Creates a new array of the Parcelable class
        @Override
        public NewsDetails[] newArray(int size) {
            return new NewsDetails[size];
        }
    };



    //********** Retrieves the values from the Parcel *********//

    protected NewsDetails(Parcel parcel_in) {
        this.newsId = parcel_in.readString();
        this.newsImage = parcel_in.readString();
        this.newsDateAdded = parcel_in.readString();
        this.newsLastModified = parcel_in.readString();
        this.newsStatus = parcel_in.readString();
        this.languageId = parcel_in.readString();
        this.newsName = parcel_in.readString();
        this.newsDescription = parcel_in.readString();
        this.newsUrl = parcel_in.readString();
        this.newsViewed = parcel_in.readString();
        this.categoriesId = parcel_in.readString();
    }
}
