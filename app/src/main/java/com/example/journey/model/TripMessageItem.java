package com.example.journey.model;

/**
 * Created by Li on 2016/9/13.
 */
public class TripMessageItem {
  private String id;
  private String message_requestor;//发出请求的用户的姓名
  private String content; //内容
  private String additional_content;//附加信息
  private String trip_message_portrait;//头像
  private int trip_message_sex;//性别
  private String trip_message_age;//年龄
  private int type;
  private Post post;
  private User pusher;
  private int isHandled;
  private int joinedPeople;
  private int maxPeople;
  private String resume;
  private String userID;

  public TripMessageItem(String id, String message_requestor, String content, String additional_content,
                         String trip_message_portrait, int trip_message_sex,
                         String trip_message_age, int type, Post post, User pusher, int isHandled, int joinedPeople,
                         int maxPeople, String resume, String userID) {
    this.id = id;
    this.message_requestor = message_requestor;
    this.content = content;
    this.additional_content = additional_content;
    this.trip_message_portrait = trip_message_portrait;
    this.trip_message_sex = trip_message_sex;
    this.trip_message_age = trip_message_age;
    this.type = type;
    this.post = post;
    this.pusher = pusher;
    this.isHandled = isHandled;
    this.joinedPeople = joinedPeople;
    this.maxPeople = maxPeople;
    this.resume = resume;
    this.userID = userID;
  }

  public String getMessage_requestor() {
    return message_requestor;
  }

  public String getContent() {
    return content;
  }

  public String getAdditional_content() {
    return additional_content;
  }

  public String getTrip_message_portrait() {
    return trip_message_portrait;
  }

  public int getTrip_message_sex() {
    return trip_message_sex;
  }

  public String getTrip_message_age() {
    return trip_message_age;
  }

  public int getType() {
    return type;
  }

  public Post getPost() {
    return post;
  }

  public User getPusher() {
    return pusher;
  }

  public int getIsHandled() {
    return isHandled;
  }

  public String getId() {
    return id;
  }

  public int getJoinedPeople() {
    return joinedPeople;
  }

  public int getMaxPeople() {
    return maxPeople;
  }

  public String getResume() {
    return resume;
  }

  public String getUserID() {
    return userID;
  }

}
