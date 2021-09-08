package com.themescoder.androidstore.models.search_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("name")
    @Expose
    private String name;

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
}
