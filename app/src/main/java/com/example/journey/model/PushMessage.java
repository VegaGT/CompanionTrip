package com.example.journey.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by 高天 on 2016/9/23.
 */
public class PushMessage extends BmobObject {
    String content;  //消息内容
    String extraContent; //附加内容（以后再添加）
    User pusher;     //发送消息者
    User receiver;   //收到消息者
    Integer isHandled;  //消息是否处理（对申请加入行程有用） 0：等待加入 1：已同意 2：已拒绝
    Integer type;   //0代表申请加入，1代表退出行程，……
    Post post;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getPusher() {
        return pusher;
    }

    public void setPusher(User pusher) {
        this.pusher = pusher;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public Integer getHandled() {
        return isHandled;
    }

    public void setHandled(Integer handled) {
        isHandled = handled;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getExtraContent() {
        return extraContent;
    }

    public void setExtraContent(String extraContent) {
        this.extraContent = extraContent;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
