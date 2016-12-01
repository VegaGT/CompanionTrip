package com.example.journey.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by 高天 on 2016/9/18.
 */
public class Evaluation extends BmobObject{
    User evaluateFrom;
    User evaluateTo;
    float score;
    String comment;

    public User getEvaluateFrom() {
        return evaluateFrom;
    }

    public void setEvaluateFrom(User evaluateFrom) {
        this.evaluateFrom = evaluateFrom;
    }

    public User getEvaluateTo() {
        return evaluateTo;
    }

    public void setEvaluateTo(User evaluateTo) {
        this.evaluateTo = evaluateTo;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
