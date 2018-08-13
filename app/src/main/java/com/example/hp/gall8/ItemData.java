package com.example.hp.gall8;

/**
 * Created by HP on 3/22/2018.
 */

public class ItemData {


    private String title;
    private int imageUrl;

    public ItemData(String title,int imageUrl){

        this.title = title;
        this.imageUrl = imageUrl;
    }

    public ItemData(String title){

        this.title = title;

    }


    public int getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }
}