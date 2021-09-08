package com.themescoder.androidstore.models.product_model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Option implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;



    public Option() {
    }



    /**
     * 
     * @return
     *     The id
     */
    public int getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }




    //********** Describes the kinds of Special Objects contained in this Parcelable Instance's marshaled representation *********//

    @Override
    public int describeContents() {
        return 0;
    }



    //********** Writes the values to the Parcel *********//

    @Override
    public void writeToParcel(Parcel parcel_out, int flags) {
        parcel_out.writeInt(id);
        parcel_out.writeString(name);
    }



    //********** Generates Instances of Parcelable class from a Parcel *********//

    public static final Creator<Option> CREATOR = new Creator<Option>() {
        // Creates a new Instance of the Parcelable class, Instantiating it from the given Parcel
        @Override
        public Option createFromParcel(Parcel parcel_in) {
            return new Option(parcel_in);
        }

        // Creates a new array of the Parcelable class
        @Override
        public Option[] newArray(int size) {
            return new Option[size];
        }
    };



    //********** Retrieves the values from the Parcel *********//

    protected Option(Parcel parcel_in) {
        this.id = parcel_in.readInt();
        this.name = parcel_in.readString();
    }
}
