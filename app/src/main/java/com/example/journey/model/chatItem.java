package com.example.journey.model;

/**
 * Created by Li on 2016/12/1.
 */
public class ChatItem {
    private String icon;//头像
    private String message;//信息
    private String time;//时间

    ChatItem(String icon,String message,String time){
        this.icon = icon;
        this.message = message;
        this.time = time;
    }
    public String getIcon(){
        return icon;
    }
    public String getMessage(){
        return message;
    }
    public String getTime(){return time;}
}
