package com.chm.live.list;

/**
 * Created by ASUS on 2018/11/14.
 */

public class Item {

    private String id;
    private String name;
    private Pictures pictures;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public void setPictures(Pictures pictures) {
        this.pictures = pictures;
    }

    public Pictures getPictures() {
        return pictures;
    }

}
