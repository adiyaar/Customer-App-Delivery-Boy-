package com.themescoder.androidstore.models;

public class ColorsModal {

    int themeID;
    String colorName;
    int colorPrimary;


    public ColorsModal(int themeID, String colorName, int colorPrimary) {
        this.themeID = themeID;
        this.colorName = colorName;
        this.colorPrimary = colorPrimary;
    }

    public int getThemeID() {
        return themeID;
    }

    public void setThemeID(int themeID) {
        this.themeID = themeID;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public int getColorPrimary() {
        return colorPrimary;
    }

    public void setColorPrimary(int colorPrimary) {
        this.colorPrimary = colorPrimary;
    }
}