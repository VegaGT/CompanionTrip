package com.example.journey.model;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by 高天 on 2016/8/25.
 */
public class Post extends BmobObject {
  private List<String> province = new ArrayList<>();//省份
  private List<String> city = new ArrayList<>();  //城市
  private User author;          //帖子作者
  private String startDate;   //开始时间
  private String endDate;     //结束时间
  private String title;        //帖子标题
  private String description;  //行程描述
  private Integer number;     //人数限制
  private Integer joinedNumber; //已加入人数
  private BmobRelation joinedPeople; //多对多关系-已经加入行程的用户
  private List<BmobFile> image = new ArrayList<>(); //4张图片
  private List<String> tag = new ArrayList<>(); //3个标签
  private Boolean isDone;          //行程是否已经完成

  public void setAuthor(User author) {
    this.author = author;
  }

  public User getAuthor() {
    return this.author;
  }

  public void setProvince(List<String> province) {
    this.province = province;
  }

  public List<String> getProvince() {
    return this.province;
  }

  public void setCity(List<String> city) {
    this.city = city;
  }

  public List<String> getCity() {
    return this.city;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getStartDate() {
    return this.startDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public String getEndDate() {
    return this.endDate;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDescription() {
    return this.description;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getTitle() {
    return this.title;
  }

  public void setNumber(Integer number) {
    this.number = number;
  }

  public Integer getNumber() {
    return this.number;
  }

  public void setJoinedNumber(Integer joinedNumber) {
    this.joinedNumber = joinedNumber;
  }

  public Integer getJoinedNumber() {
    return this.joinedNumber;
  }

  public void setJoinedPeople(BmobRelation joinedPeople) {
    this.joinedPeople = joinedPeople;
  }

  public BmobRelation getJoinedPeople() {
    return this.joinedPeople;
  }


  public void setImage(BmobFile image) {
    this.image.add(image);
  }

  public BmobFile getImage(int i) {
    return image.get(i);
  }

  public List<BmobFile> getImages() {
    return image;
  }

  public void setTag(List<String> tag) {
    this.tag = tag;
  }

  public List<String> getTag() {
    return tag;
  }

  public Boolean getIsDone() {
    return isDone;
  }

  public void setIsDone(Boolean isDone) {
    this.isDone = isDone;
  }
}
