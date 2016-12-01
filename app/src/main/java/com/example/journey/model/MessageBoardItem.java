package com.example.journey.model;

/**
 * Created by Li on 2016/8/25.
 */
public class MessageBoardItem {
    private String portait_icon; //头像
    private String name; //名字
    private String date; //日期
    private String comment; //评论
    private String post_from;//来自于哪一个行程

    public MessageBoardItem(String portait_icon,String name,String date,String comment,String post_from){
        this.portait_icon = portait_icon;
        this.name = name;
        this.date = date;
        this.comment = comment;
        this.post_from = post_from;
    }
    public String getPortait_icon(){
        return portait_icon;
    }
    public String getName(){
        return name;
    }
    public String getDate(){
        return date;
    }
    public String getComment(){
        return comment;
    }
    public String getPost_from() {
        return post_from;
    }
}