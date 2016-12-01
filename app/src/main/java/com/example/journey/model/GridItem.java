package com.example.journey.model;

/**
 * Created by Li on 2016/10/8.
 */
public class GridItem {
    private String grid_view_item_picture;//图片
    private String grid_view_item_tag;
    private String postID;
    public GridItem(String grid_view_item_picture, String grid_view_item_tag ,String postID){
        this.grid_view_item_picture = grid_view_item_picture;
        this.grid_view_item_tag = grid_view_item_tag;
        this.postID = postID;
    }
    public String getGrid_view_item_picture(){
        return grid_view_item_picture;
    }
    public String getGrid_view_item_tag(){
        return grid_view_item_tag;
    }
    public String getPostID(){
        return postID;
    }
}