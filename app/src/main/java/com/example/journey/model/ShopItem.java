package com.example.journey.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by Li on 2016/12/10.
 */
public class ShopItem extends BmobObject{
    private String pic;
    private String name;
    private String content;//商品详情
    private String price;
    private String city;
    private String address;
    private String time_limit;//期限

    public ShopItem(String pic,String name,String content,String price,String city,String address,String time_limit){
        this.pic = pic;
        this.name = name;
        this.content = content;
        this.price = price;
        this.city = city;
        this.address = address;
        this.time_limit = time_limit;
    }

    public String getTime_limit() {
        return time_limit;
    }

    public void setTime_limit(String time_limit) {
        this.time_limit = time_limit;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }





}
