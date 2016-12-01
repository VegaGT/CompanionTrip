package com.example.journey.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by 高天 on 2016/10/11.
 */
public class HotPost extends BmobObject {
    private String background;
    private String title;
    private String postID;

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
