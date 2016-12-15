package com.example.journey.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.journey.R;
import com.example.journey.model.ShopItem;
import com.example.journey.model.ShopItemAdapter;

public class ShopMessageActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_message);

        recyclerView = (RecyclerView)findViewById(R.id.shop_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        ShopItemAdapter adapter = new ShopItemAdapter(this);
        recyclerView.setAdapter(adapter);



    }
}
