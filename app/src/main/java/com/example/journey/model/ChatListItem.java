package com.example.journey.model;

/**
 * Created by Li on 2016/11/28.
 */
public class ChatListItem {


    private String id;
    private User oppuser;
    private String chat_item_portrait;//头像
    private String chat_item_name;//名字
    private String chat_item_content;//内容
    private String chat_item_date;//日期
    private String num_not_read;//未读信息数

    ChatListItem(String id,User oppuser,String chat_item_portrait,String chat_item_name,String chat_item_content,String chat_item_date,String num_not_read){
        this.id = id;
        this.oppuser = oppuser;
        this.chat_item_portrait = chat_item_portrait;
        this.chat_item_name = chat_item_name;
        this.chat_item_content = chat_item_content;
        this.chat_item_date = chat_item_date;
        this.num_not_read = num_not_read;
    }

    public String getId() {return id;}
    public User getOppuser() {
        return oppuser;
    }

    public String getChat_item_portrait() {
        return chat_item_portrait;
    }

    public String getChat_item_name() {
        return chat_item_name;
    }

    public String getChat_item_content() {
        return chat_item_content;
    }

    public String getChat_item_date() {
        return chat_item_date;
    }

    public String getNum_not_read() {
        return num_not_read;
    }

    public void setNum_not_read(String num){
        this.num_not_read = num;
    }
}
