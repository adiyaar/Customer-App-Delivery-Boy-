package com.themescoder.androidstore.models.ratings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by muneeb.vectorcoder@gmail.com on 07/05/2018.
 */

public class ProductReviews {
    
    @SerializedName("reviews_id")
    @Expose
    private int id;
    @SerializedName("created_at")
    @Expose
    private String dateCreated;
    @SerializedName("comments")
    @Expose
    private String review;
    @SerializedName("rating")
    @Expose
    private int rating;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("first_name")
    @Expose
    private String first_name;

    @SerializedName("last_name")
    @Expose
    private String last_name;

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
    
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getDateCreated() {
        return dateCreated;
    }
    
    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
    

    public String getReview() {
        return review;
    }
    
    public void setReview(String review) {
        this.review = review;
    }
    
    public int getRating() {
        return rating;
    }
    
    public void setRating(int rating) {
        this.rating = rating;
    }

    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    


    
}
