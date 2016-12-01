package com.example.journey.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by 高天 on 2016/9/27.
 */
public class Comment extends BmobObject {
    User commentTo;   //被评价人
    User commentFrom; //评价人
    String content;   //评价内容
    Float score;        //评分
    Post post;        //所属行程

    public User getCommentTo() {
        return commentTo;
    }

    public void setCommentTo(User commentTo) {
        this.commentTo = commentTo;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getCommentFrom() {
        return commentFrom;
    }

    public void setCommentFrom(User commentFrom) {
        this.commentFrom = commentFrom;
    }
}
