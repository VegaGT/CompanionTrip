package com.example.journey.activity;


import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.journey.R;
import com.example.journey.lib.MyGridView;
import com.example.journey.model.GridItem;
import com.example.journey.model.GridItemAdapter;
import com.example.journey.model.ProvinceAndCity;
import com.example.journey.model.ThemeItem;
import com.example.journey.model.ThemeItemAdapter;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class CreditMallActivity extends AppCompatActivity {
    private ViewPager viewPager;

    private GridItemAdapter adapter1; //热门商品的adapter
    private CircleIndicator indicator;
    private ProgressBar goodsProgress;
    private MyGridView gridView_goods;
    private ImageView back_btn;//返回按钮

    private ThemeItemAdapter adapter;
    private List<ThemeItem> themeItemList = new ArrayList<>();
    private List<GridItem> gridItemList_site = new ArrayList<>(); //热门地点
    private List<GridItem> gridItemList_post = new ArrayList<>(); //热门帖子
    private List<ProvinceAndCity> location = new ArrayList<>();
    private List<String> cities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_mall);
    }

    //点击进入我的积分界面
    public void my_credit_btn(View view){

    }
    //点击进入我的订单界面
    public void my_order_btn(View view){

    }
    //点击进入如何赚取积分界面
    public void way_get_credit(View view){

    }
}
