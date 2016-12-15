package com.example.journey.model;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Li on 2016/12/9.
 */
public class GoodsAdapter extends PagerAdapter{
    private Context context;
    private List<Goods> goodsList = new ArrayList<>();

    public GoodsAdapter(Context context){
        this.context = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position){
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(context).load(goodsList.get(position).getImage()).centerCrop().into(imageView);
        ((ViewPager)container).addView(imageView,0);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager)container).removeView((ImageView)object);
    }
    @Override
    public int getCount() {
        return goodsList.size();
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (View) object;
    }
    public void setGoodsList(List<Goods> goodsList) {
        this.goodsList = goodsList;
    }
}
