package com.example.journey.model;
import cn.bmob.v3.BmobObject;
/**
 * Created by Li on 2016/10/11.
 */
public class Feedback extends BmobObject {
    //用户
    private User user;
    //反馈内容
    private String content;
    //联系方式
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    private String contact;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getContact() {
        return contact;
    }
    public void setContacts(String contact) {
        this.contact = contact;
    }
}