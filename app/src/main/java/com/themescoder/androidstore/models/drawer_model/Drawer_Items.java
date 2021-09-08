package com.themescoder.androidstore.models.drawer_model;


public class Drawer_Items {

    Integer icon;
    String title;


    public Drawer_Items(Integer icon, String title) {
        this.icon = icon;
        this.title = title;
    }


    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
