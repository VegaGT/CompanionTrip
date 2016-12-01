package com.example.journey.model;

/**
 * Created by Li on 2016/8/20.
 */
public class PersonalMsg {
  private String text;


  private int leftRes;
  private int rightRes;

  public PersonalMsg(int leftRes, String text1, int rightRes) {
    this.leftRes = leftRes;
    this.text = text1;
    this.rightRes = rightRes;
  }

  public PersonalMsg(String text1, int rightRes) {
    this.leftRes = 0;
    this.text = text1;
    this.rightRes = rightRes;
  }

  public String getText() {
    return text;
  }

  public int getRightRes() {
    return rightRes;
  }

  public int getLeftRes() {
    return leftRes;
  }

  public void setLeftRes(int leftRes) {
    this.leftRes = leftRes;
  }
}
