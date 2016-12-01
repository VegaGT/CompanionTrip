package com.example.journey.model;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.journey.activity.ThemeActivity;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Li on 2016/10/2.
 */
public class ThemeItemAdapter extends PagerAdapter {
    private Context context;
    private List<ThemeItem> themes = new ArrayList<>();
    public ThemeItemAdapter(Context context){
        this.context = context;
    }
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(context).load(themes.get(position).getImageUrl()).centerCrop().into(imageView);
        ((ViewPager)container).addView(imageView,0);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //添加事件
                Intent intent = new Intent();
                intent.setClass(context, ThemeActivity.class);
                intent.putExtra("City",themes.get(position).getThemeContent());
                context.startActivity(intent);
            }
        });
        return imageView;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager)container).removeView((ImageView)object);
    }
    @Override
    public int getCount() {
        return themes.size();
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (View) object;
    }
    public void setThemes(List<ThemeItem> themes) {
        this.themes = themes;
    }
}