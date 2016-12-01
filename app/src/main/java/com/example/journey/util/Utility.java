package com.example.journey.util;

import android.text.TextUtils;

import com.example.journey.model.*;

/**
 * Created by Li on 2016/8/17.
 */
public class Utility {
    /* 解析和处理服务器返回的省级数据 */
    public synchronized static boolean handleProvincesResponse(JoruneyDB joruneyDB,String response){
        if(!TextUtils.isEmpty(response)){
            String[] allProvinces = response.split(",");
            if(allProvinces != null && allProvinces.length>0){
                for(String p : allProvinces){
                    String[] array = p.split("\\|");
                    Province province = new Province();
                    province.setProvinceCode(array[0]);
                    province.setProvinceName(array[1]);
                    //将解析出来的数据存储到Province表里
                    joruneyDB.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }

    /* 解析和处理服务器返回的市级数据 */
    public synchronized static boolean handleCitiesResponse(JoruneyDB joruneyDB,String response,int provinceId){
        if(!TextUtils.isEmpty(response)){
            String[] allCities = response.split(",");
            if(allCities != null && allCities.length>0){
                for(String c : allCities){
                    String[] array = c.split("\\|");
                    City city = new City();
                    city.setCityCode(array[0]);
                    city.setCityName(array[1]);
                    city.setProvinceId(provinceId);
                    //将解析出来的数据存储到Province表里
                    joruneyDB.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }
}
