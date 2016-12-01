package com.example.journey.model;

/**
 * Created by Li on 2016/8/24.
 */
public class CompanionItem {
    private String portrait_icon;
    private String name;
    private Boolean flag;
    private String objID;

    public CompanionItem(String portrait_icon,String name,Boolean flag,String objID){
        this.portrait_icon = portrait_icon;
        this.name = name;
        this.flag = flag;
        this.objID = objID;
    }

    public String getPortrait_icon(){
        return portrait_icon;
    }
    public String getName(){
        return name;
    }
    public Boolean getFlag(){
        return flag;
    }
    public String getObjID(){
        return objID;
    }
}