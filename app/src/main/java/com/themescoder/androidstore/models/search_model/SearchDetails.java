package com.themescoder.androidstore.models.search_model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.themescoder.androidstore.models.product_model.ProductDetails;

public class SearchDetails {

    @SerializedName("mainCategories")
    @Expose
    private List<MainCategory> mainCategories = new ArrayList<MainCategory>();
    @SerializedName("subCategories")
    @Expose
    private List<SubCategory> subCategories = new ArrayList<SubCategory>();
    @SerializedName("manufacturers")
    @Expose
    private List<Manufacturers> manufacturers = new ArrayList<Manufacturers>();
    @SerializedName("products")
    @Expose
    private List<ProductDetails> products = new ArrayList<ProductDetails>();

    /**
     * 
     * @return
     *     The mainCategories
     */
    public List<MainCategory> getMainCategories() {
        return mainCategories;
    }

    /**
     * 
     * @param mainCategories
     *     The mainCategories
     */
    public void setMainCategories(List<MainCategory> mainCategories) {
        this.mainCategories = mainCategories;
    }

    /**
     * 
     * @return
     *     The subCategories
     */
    public List<SubCategory> getSubCategories() {
        return subCategories;
    }

    /**
     * 
     * @param subCategories
     *     The subCategories
     */
    public void setSubCategories(List<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }

    /**
     * 
     * @return
     *     The manufacturers
     */
    public List<Manufacturers> getManufacturers() {
        return manufacturers;
    }

    /**
     * 
     * @param manufacturers
     *     The manufacturers
     */
    public void setManufacturers(List<Manufacturers> manufacturers) {
        this.manufacturers = manufacturers;
    }

    /**
     * 
     * @return
     *     The products
     */
    public List<ProductDetails> getProducts() {
        return products;
    }

    /**
     * 
     * @param products
     *     The products
     */
    public void setProducts(List<ProductDetails> products) {
        this.products = products;
    }

}
