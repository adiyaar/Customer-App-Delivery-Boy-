package com.themescoder.androidstore.models.product_model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ProductCategories implements Parcelable {

    @SerializedName("categories_id")
    @Expose
    private String categoriesId;
    @SerializedName("categories_name")
    @Expose
    private String categoriesName;
    @SerializedName("categories_image")
    @Expose
    private String categoriesImage;
    @SerializedName("categories_icon")
    @Expose
    private String categoriesIcon;
    @SerializedName("parent_id")
    @Expose
    private String parentId;
    
    
    public String getCategoriesId() {
        return categoriesId;
    }
    
    public void setCategoriesId(String categoriesId) {
        this.categoriesId = categoriesId;
    }
    
    public String getCategoriesName() {
        return categoriesName;
    }
    
    public void setCategoriesName(String categoriesName) {
        this.categoriesName = categoriesName;
    }
    
    public String getCategoriesImage() {
        return categoriesImage;
    }
    
    public void setCategoriesImage(String categoriesImage) {
        this.categoriesImage = categoriesImage;
    }
    
    public String getCategoriesIcon() {
        return categoriesIcon;
    }
    
    public void setCategoriesIcon(String categoriesIcon) {
        this.categoriesIcon = categoriesIcon;
    }
    
    public String getParentId() {
        return parentId;
    }
    
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    
    
    
    //********** Describes the kinds of Special Objects contained in this Parcelable Instance's marshaled representation *********//
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    
    
    //********** Writes the values to the Parcel *********//
    
    @Override
    public void writeToParcel(Parcel parcel_out, int flags) {
        parcel_out.writeString(categoriesId);
        parcel_out.writeString(categoriesName);
        parcel_out.writeString(categoriesImage);
        parcel_out.writeString(categoriesIcon);
        parcel_out.writeString(parentId);
    }
    
    
    
    //********** Generates Instances of Parcelable class from a Parcel *********//
    
    public static final Creator<ProductCategories> CREATOR = new Creator<ProductCategories>() {
        // Creates a new Instance of the Parcelable class, Instantiating it from the given Parcel
        @Override
        public ProductCategories createFromParcel(Parcel parcel_in) {
            return new ProductCategories(parcel_in);
        }
        
        // Creates a new array of the Parcelable class
        @Override
        public ProductCategories[] newArray(int size) {
            return new ProductCategories[size];
        }
    };
    
    
    
    //********** Retrieves the values from the Parcel *********//
    
    protected ProductCategories(Parcel parcel_in) {
        this.categoriesId = parcel_in.readString();
        this.categoriesName = parcel_in.readString();
        this.categoriesImage = parcel_in.readString();
        this.categoriesIcon = parcel_in.readString();
        this.parentId = parcel_in.readString();
    }
    
}
