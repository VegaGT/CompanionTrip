package com.example.journey.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.journey.R;
import com.example.journey.model.ShopItem;
import com.example.journey.model.ShopItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class ShopMessageActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<ShopItem> shopItemList = new ArrayList<>();
    private ShopItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_message);

        recyclerView = (RecyclerView)findViewById(R.id.shop_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new ShopItemAdapter(this);
        recyclerView.setAdapter(adapter);

        initsShopItem();
    }

    public void initsShopItem(){
        ShopItem shopItem1 = new ShopItem("图片","商家名字","优惠商品信息","100","丽江","揶揄路与人民路交叉口向西50米","2016.12.1~2017.12.1");
        shopItemList.add(shopItem1);
        ShopItem shopItem2 = new ShopItem("图片","商家名字","优惠商品信息","100","丽江","揶揄路与人民路交叉口向西50米","2016.12.1~2017.12.1");
        shopItemList.add(shopItem2);
        ShopItem shopItem3 = new ShopItem("图片","商家名字","优惠商品信息","100","丽江","揶揄路与人民路交叉口向西50米","2016.12.1~2017.12.1");
        shopItemList.add(shopItem3);
        ShopItem shopItem4 = new ShopItem("图片","商家名字","优惠商品信息","100","丽江","揶揄路与人民路交叉口向西50米","2016.12.1~2017.12.1");
        shopItemList.add(shopItem4);
        ShopItem shopItem5 = new ShopItem("图片","商家名字","优惠商品信息","100","丽江","揶揄路与人民路交叉口向西50米","2016.12.1~2017.12.1");
        shopItemList.add(shopItem5);
        ShopItem shopItem6 = new ShopItem("图片","商家名字","优惠商品信息","100","丽江","揶揄路与人民路交叉口向西50米","2016.12.1~2017.12.1");
        shopItemList.add(shopItem6);
        ShopItem shopItem7 = new ShopItem("图片","商家名字","优惠商品信息","100","丽江","揶揄路与人民路交叉口向西50米","2016.12.1~2017.12.1");
        shopItemList.add(shopItem7);
        ShopItem shopItem8 = new ShopItem("图片","商家名字","优惠商品信息","100","丽江","揶揄路与人民路交叉口向西50米","2016.12.1~2017.12.1");
        shopItemList.add(shopItem8);
        adapter.setShopItemList(shopItemList);
        adapter.notifyDataSetChanged();
    }
}
