package com.example.journey.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 高天 on 2016/9/15.
 */
public class ProvinceAndCity {
    String Province;
    List<String> City = new ArrayList<>();

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    public List<String> getCity() {
        return City;
    }

    public void setCity(List<String> city) {
        City = city;
    }
}
