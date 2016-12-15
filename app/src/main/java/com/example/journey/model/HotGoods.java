package com.example.journey.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by Li on 2016/12/9.
 */
public class HotGoods extends BmobObject {
    private String background;
    private String name;
    public HotGoods(String background,String name){
        this.background = background;
        this.name = name;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
