package com.example.journey.activity;


import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.journey.R;
import com.example.journey.lib.MyGridView;
import com.example.journey.model.Goods;
import com.example.journey.model.GoodsAdapter;
import com.example.journey.model.HotGoods;
import com.example.journey.model.HotGoodsAdapter;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class CreditMallActivity extends AppCompatActivity {
    private ViewPager viewPager;

    private HotGoodsAdapter adapter1; //热门商品的adapter
    private CircleIndicator indicator;
    private ProgressBar goodsProgress;
    private MyGridView gridView_goods;
    private ImageView back_btn;//返回按钮

    private GoodsAdapter adapter;
    private List<Goods> goodsList = new ArrayList<>();
    private List<HotGoods> gridItemList_goods = new ArrayList<>(); //热门商品

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_mall);

        viewPager = (ViewPager)findViewById(R.id.goods_hottest);
        indicator = (CircleIndicator)findViewById(R.id.goods_indicator);
        goodsProgress = (ProgressBar)findViewById(R.id.goods_progress_bar);
        gridView_goods = (MyGridView)findViewById(R.id.grid_view_goods);

        adapter = new GoodsAdapter(this);
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);
        adapter.registerDataSetObserver(indicator.getDataSetObserver());

        initGoodsItem();
        initGridItem_goods();
    }

    private void initGoodsItem(){
        Goods good1 = new Goods("名字","图片网址");
        goodsList.add(good1);
        Goods good2 = new Goods("名字","图片网址");
        goodsList.add(good2);
        Goods good3 = new Goods("名字","图片网址");
        goodsList.add(good3);
        Goods good4 = new Goods("名字","图片网址");
        goodsList.add(good4);
        adapter.setGoodsList(goodsList);
        adapter.notifyDataSetChanged();
    }

    private void initGridItem_goods(){
        HotGoods hotGood1 = new HotGoods("图片网址","名字");
        gridItemList_goods.add(hotGood1);
        HotGoods hotGood2 = new HotGoods("图片网址","名字");
        gridItemList_goods.add(hotGood2);
        HotGoods hotGood3 = new HotGoods("图片网址","名字");
        gridItemList_goods.add(hotGood3);
        HotGoods hotGood4 = new HotGoods("图片网址","名字");
        gridItemList_goods.add(hotGood4);
        adapter1 = new HotGoodsAdapter(this,R.layout.gridview_item,gridItemList_goods);
        gridView_goods.setAdapter(adapter1);
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
