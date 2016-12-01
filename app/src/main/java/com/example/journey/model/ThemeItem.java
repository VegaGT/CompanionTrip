package com.example.journey.model;
/**
 * Created by Li on 2016/10/2.
 */
public class ThemeItem {
    private String themeContent;//主题内容
    private String imageUrl;//图片
    public ThemeItem(String themeContent,String imageUrl){
        this.themeContent = themeContent;
        this.imageUrl = imageUrl;
    }
    public String getThemeContent() {
        return themeContent;
    }
    public void setThemeContent(String themeContent) {
        this.themeContent = themeContent;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}