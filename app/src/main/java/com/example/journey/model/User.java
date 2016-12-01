package com.example.journey.model;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Li on 2016/8/16.
 */
public class User extends BmobUser {
  private Integer age;     //年龄
  private String realName;  //真实姓名
  private Boolean sex;
  private BmobFile userIcon; //头像
  private Double score;         //平均评分
  private Integer ratingNumber; //已经给此用户评分的人数
  private String resume;  //个人简介
  private String studentID; //学号

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public String getRealName() {
    return realName;
  }

  public void setRealName(String realName) {
    this.realName = realName;
  }

  public Boolean getSex() {
    return sex;
  }

  public void setSex(Boolean sex) {
    this.sex = sex;
  }

  public BmobFile getUserIcon() {
    return userIcon;
  }

  public void setUserIcon(BmobFile userIcon) {
    this.userIcon = userIcon;
  }

  public Integer getRatingNumber() {
    return ratingNumber;
  }

  public void setRatingNumber(Integer ratingNumber) {
    this.ratingNumber = ratingNumber;
  }

  public Double getScore() {
    return score;
  }

  public void setScore(Double score) {
    this.score = score;
  }

  public String getResume() {
    return resume;
  }

  public void setResume(String resume) {
    this.resume = resume;
  }

  public String getStudentID() {
    return studentID;
  }

  public void setStudentID(String studentID) {
    this.studentID = studentID;
  }
}

