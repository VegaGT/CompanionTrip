package com.example.journey.model;
/**
 * Created by Li on 2016/8/23.
 */
public class PostingItem {
    private String portrait_icon;
    private String name;
    private int sex_icon;
    private String post_content;
    private String keyword0;
    private String keyword1;
    private String keyword2;
    private String people_number;
    private String date;
    private String postID;
    private Boolean isDone;
    private String background;//背景图

    public PostingItem(String portrait_icon, String name, int sex_icon, String post_content,
                       String keyword0, String keyword1, String keyword2, String people_number, String date, String postID, Boolean isDone, String background) {
        this.portrait_icon = portrait_icon;
        this.name = name;
        this.sex_icon = sex_icon;
        this.post_content = post_content;
        this.keyword0 = keyword0;
        this.keyword1 = keyword1;
        this.keyword2 = keyword2;
        this.people_number = people_number;
        this.date = date;
        this.postID = postID;
        this.isDone = isDone;
        this.background = background;
    }

    public String getPortrait_icon() {
        return portrait_icon;
    }

    public String getName() {
        return name;
    }

    public int getSex_icon() {
        return sex_icon;
    }

    public String getPost_content() {
        return post_content;
    }

    public String getKeyword0() {
        return keyword0;
    }

    public String getKeyword1() {
        return keyword1;
    }

    public String getKeyword2() {
        return keyword2;
    }

    public String getPeople_number() {
        return people_number;
    }

    public String getDate() {
        return date;
    }

    public String getPostID() {
        return postID;
    }

    public Boolean getIsDone() {
        return isDone;
    }

    public String getBackground() {
        return background;
    }
}
