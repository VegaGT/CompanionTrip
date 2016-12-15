package com.example.journey.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by Li on 2016/12/9.
 */
public class Goods extends BmobObject {
    String name;
    String image;

    public Goods(String name,String image){
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
