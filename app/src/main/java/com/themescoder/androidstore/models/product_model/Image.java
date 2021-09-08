package com.themescoder.androidstore.models.product_model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Image implements Parcelable {

    @SerializedName("image")
    @Expose
    private String image;



    public Image() {
    }



    /**
     * 
     * @return
     *     The image
     */
    public String getImage() {
        return image;
    }

    /**
     * 
     * @param image
     *     The image
     */
    public void setImage(String image) {
        this.image = image;
    }




    //********** Describes the kinds of Special Objects contained in this Parcelable Instance's marshaled representation *********//

    @Override
    public int describeContents() {
        return 0;
    }



    //********** Writes the values to the Parcel *********//

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(image);
    }



    //********** Generates Instances of Parcelable class from a Parcel *********//

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        // Creates a new Instance of the Parcelable class, Instantiating it from the given Parcel
        @Override
        public Image createFromParcel(Parcel parcel) {
            return new Image(parcel);
        }

        // Creates a new array of the Parcelable class
        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };



    //********** Retrieves the values from the Parcel *********//

    protected Image(Parcel parcel) {
        this.image = parcel.readString();
    }

}
