package com.themescoder.androidstore.models.cart_model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import com.themescoder.androidstore.models.product_model.Option;
import com.themescoder.androidstore.models.product_model.Value;


public class CartProductAttributes implements Parcelable {

    @SerializedName("customers_basket_id")
    @Expose
    private int customersBasketId;
    @SerializedName("products_id")
    @Expose
    private String productsId;
    @SerializedName("option")
    @Expose
    private Option option;
    @SerializedName("values")
    @Expose
    private List<Value> values = new ArrayList<Value>();
    
    
    public CartProductAttributes() {
    }
    
    
    public int getCustomersBasketId() {
        return customersBasketId;
    }

    public void setCustomersBasketId(int customersBasketId) {
        this.customersBasketId = customersBasketId;
    }

    public String getProductsId() {
        return productsId;
    }

    public void setProductsId(String productsId) {
        this.productsId = productsId;
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
    
    public void writeToParcel(Parcel parcel_out, int flags) {
        parcel_out.writeValue(customersBasketId);
        parcel_out.writeValue(productsId);
        parcel_out.writeParcelable(option, flags);
        parcel_out.writeList(values);
    }
    
    
    
    //********** Generates Instances of Parcelable class from a Parcel *********//
    
    public static final Creator<CartProductAttributes> CREATOR = new Creator<CartProductAttributes>() {
        
        // Creates a new Instance of the Parcelable class, Instantiating it from the given Parcel
        @Override
        public CartProductAttributes createFromParcel(Parcel parcel_in) {
            return new CartProductAttributes(parcel_in);
        }
        
        // Creates a new array of the Parcelable class
        @Override
        public CartProductAttributes[] newArray(int size) {
            return new CartProductAttributes[size];
        }
    };
    
    
    
    //********** Retrieves the values from the Parcel *********//
    
    protected CartProductAttributes(Parcel parcel_in) {
        this.customersBasketId = parcel_in.readInt();
        this.productsId = parcel_in.readString();
        
        this.option = parcel_in.readParcelable(Option.class.getClassLoader());
    
        this.values = new ArrayList<Value>();
        parcel_in.readList(values, Value.class.getClassLoader());
    }

}
