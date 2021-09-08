package com.themescoder.androidstore.models.product_model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Attribute implements Parcelable {

    @SerializedName("option")
    @Expose
    private Option option;
    @SerializedName("values")
    @Expose
    private List<Value> values = new ArrayList<Value>();



    public Attribute() {
    }



    /**
     * 
     * @return
     *     The option
     */
    public Option getOption() {
        return option;
    }

    /**
     * 
     * @param option
     *     The option
     */
    public void setOption(Option option) {
        this.option = option;
    }

    /**
     * 
     * @return
     *     The values
     */
    public List<Value> getValues() {
        return values;
    }

    /**
     * 
     * @param values
     *     The values
     */
    public void setValues(List<Value> values) {
        this.values = values;
    }




    //********** Describes the kinds of Special Objects contained in this Parcelable Instance's marshaled representation *********//

    @Override
    public int describeContents() {
        return 0;
    }



    //********** Writes the values to the Parcel *********//

    @Override
    public void writeToParcel(Parcel parcel_out, int flags) {
        parcel_out.writeParcelable(option, flags);
        parcel_out.writeList(values);
    }



    //********** Generates Instances of Parcelable class from a Parcel *********//

    public static final Creator<Attribute> CREATOR = new Creator<Attribute>() {
        // Creates a new Instance of the Parcelable class, Instantiating it from the given Parcel
        @Override
        public Attribute createFromParcel(Parcel parcel_in) {
            return new Attribute(parcel_in);
        }

        // Creates a new array of the Parcelable class
        @Override
        public Attribute[] newArray(int size) {
            return new Attribute[size];
        }
    };



    //********** Retrieves the values from the Parcel *********//

    protected Attribute(Parcel parcel_in) {

        this.option = parcel_in.readParcelable(Option.class.getClassLoader());

        this.values = new ArrayList<Value>();
        parcel_in.readList(values, Value.class.getClassLoader());
    }

}
