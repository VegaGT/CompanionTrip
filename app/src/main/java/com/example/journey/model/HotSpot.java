package com.example.journey.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by 高天 on 2016/10/11.
 */
public class HotSpot extends BmobObject {
    private String background;
    private String name;
    private String postID;

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

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }
}
