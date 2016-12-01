package com.example.journey.activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;
import com.example.journey.R;
import com.example.journey.model.ThemeItem;
import com.example.journey.model.ThemeItemAdapter;
import java.util.ArrayList;
import java.util.List;
/*
 * Created by Li on 2016/8/18.
 */
public class FirstPageActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private List<ThemeItem> themeItemList = new ArrayList<ThemeItem>();
    private ThemeItemAdapter adapter;
    //热门地点和帖子
    private GridView gridView;
    private String[] titles = new String[]{ "pic1", "pic2", "pic3", "pic4"};
    private int[] images = new int[]{R.drawable.bojack, R.drawable.bojack, R.drawable.bojack,R.drawable.bojack};
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);
        viewPager = (ViewPager)findViewById(R.id.vp_hottest);
        //gridView = (GridView)findViewById(R.id.grid_view);
        initThemeItem();
    }
    private void initThemeItem(){
        ThemeItem themeItem1 = new ThemeItem("国庆去哪","机智男神");
        themeItemList.add(themeItem1);
        ThemeItem themeItem2 = new ThemeItem("国庆去哪","机智男神");
        themeItemList.add(themeItem2);
        ThemeItem themeItem3 = new ThemeItem("国庆去哪","机智男神");
        themeItemList.add(themeItem3);
        ThemeItem themeItem4 = new ThemeItem("国庆去哪","机智男神");
        themeItemList.add(themeItem4);
        adapter = new ThemeItemAdapter(FirstPageActivity.this);
        adapter.setThemes(themeItemList);
        adapter.notifyDataSetChanged();
        viewPager.setAdapter(adapter);
    }
    public void bntOnClick(View view){
        Intent intent = new Intent(FirstPageActivity.this,ChooseAreaActivity.class);
        startActivity(intent);
    }
    public void bntOnClick_plus(View view){
    }
}