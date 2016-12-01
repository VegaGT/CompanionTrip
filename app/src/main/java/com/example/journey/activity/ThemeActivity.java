package com.example.journey.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.journey.R;
import com.example.journey.model.Post;
import com.example.journey.model.PostingItem;
import com.example.journey.model.PostingItemAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 高天 on 2016/10/12.
 */
public class ThemeActivity extends AppCompatActivity implements View.OnClickListener {
  private Button date, joinedAmount, personAmount;
  private TextView city;
  private List<PostingItem> postingItemList = new ArrayList<PostingItem>();
  private String cityName;
  private PostingItemAdapter adapter;
  private SwipeRefreshLayout swipeRefreshLayout;
  private int sortNum = 0;//排序参数(默认0为按出发日期排序)
  private int provinceNumber;
  private int cityNumber;
  private ImageButton create;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.posting_list);
    //注册变量
    city = (TextView) findViewById(R.id.city);
    date = (Button) findViewById(R.id.base_on_date);
    joinedAmount = (Button) findViewById(R.id.base_on_joined_number);
    personAmount = (Button) findViewById(R.id.base_on_person_number);
    create = (ImageButton) findViewById(R.id.posting_create);
    date.setOnClickListener(this);
    joinedAmount.setOnClickListener(this);
    personAmount.setOnClickListener(this);
    create.setOnClickListener(this);
    Intent intent = getIntent();
    cityName = intent.getStringExtra("City");
    city.setText(cityName);
    provinceNumber = intent.getIntExtra("provinceNumber", 0);
    cityNumber = intent.getIntExtra("cityNumber", 0);
    swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
    //设置刷新时动画的颜色，可以设置4个
    swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        postingItemList.clear();
        initPostingItem();
      }
    });
    date.setTextColor(Color.RED);
  }

  public void onResume() {
    super.onResume();
    postingItemList.clear();
    initPostingItem();
  }

  //三种点击排序事件
  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.base_on_date:
        date.setTextColor(Color.RED);
        joinedAmount.setTextColor(Color.GRAY);
        personAmount.setTextColor(Color.GRAY);
        sortNum = 0;
        onResume();
        break;
      case R.id.base_on_joined_number:
        date.setTextColor(Color.GRAY);
        joinedAmount.setTextColor(Color.RED);
        personAmount.setTextColor(Color.GRAY);
        sortNum = 1;
        onResume();
        break;
      case R.id.base_on_person_number:
        date.setTextColor(Color.GRAY);
        joinedAmount.setTextColor(Color.GRAY);
        personAmount.setTextColor(Color.RED);
        sortNum = 2;
        onResume();
        break;
      case R.id.posting_create:
        Intent intent = new Intent(ThemeActivity.this, CreateActivity.class);
        intent.putExtra("provinceNumber", provinceNumber);
        intent.putExtra("cityNumber", cityNumber);
        startActivity(intent);
        break;
      default:
        date.setTextColor(Color.RED);
        joinedAmount.setTextColor(Color.GRAY);
        personAmount.setTextColor(Color.GRAY);
        sortNum = 0;
        break;
    }
  }

  //生成ListView的实例
  private void initPostingItem() {
    //查询含有该城市名的帖子
    BmobQuery<Post> query = new BmobQuery<Post>();
    String[] city = {cityName};
    query.addWhereContainsAll("channels", Arrays.asList(city));
    query.order("-createdAt");
    query.include("author");
    if (sortNum == 1) {
      query.order("-joinedNumber");
    }
    if (sortNum == 2) {
      query.order("-number");
    }
    query.findObjects(new FindListener<Post>() {
      @Override
      public void done(List<Post> object, BmobException e) {
        if (e == null) {

          if (swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);

          for (int i = 0; i < object.size(); i++) {
            String head = object.get(i).getAuthor().getUserIcon().getUrl();
            String authorName = object.get(i).getAuthor().getRealName();
            Boolean sex = object.get(i).getAuthor().getSex();
            int sexIcon;
            if (sex) {
              sexIcon = R.drawable.male_pic;
            } else {
              sexIcon = R.drawable.female_pic;
            }
            String title = object.get(i).getTitle();
            List<String> tags = object.get(i).getTag();
            String tag1 = "";
            String tag2 = "";
            String tag3 = "";
            switch (tags.size()) {
              case 0:
                tag1 = tag2 = tag3 = "";
                break;
              case 1:
                tag1 = tags.get(0);
                tag2 = tag3 = "";
                break;
              case 2:
                tag1 = tags.get(0);
                tag2 = tags.get(1);
                tag3 = "";
                break;
              case 3:
                tag1 = tags.get(0);
                tag2 = tags.get(1);
                tag3 = tags.get(2);
                break;
              default:
                break;
            }
            int joinedNumber = object.get(i).getJoinedNumber();
            int number = object.get(i).getNumber();
            String startDate = object.get(i).getStartDate();
            String endDate = object.get(i).getEndDate();
            String postId = object.get(i).getObjectId();
            Boolean isDone = object.get(i).getIsDone();
            String background;
            if (object.get(i).getImages().size() == 0) {
              background = "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1609/27/c0/27587200_1474952314810_800x600.jpg";
            } else {
              background = object.get(i).getImage(0).getUrl();
            }
            PostingItem postingItem = new PostingItem(head, authorName, sexIcon,
                    title, tag1, tag2, tag3, "人数:" + joinedNumber + "/" + number, startDate + "-" + endDate, postId, isDone, background);
            postingItemList.add(postingItem);
            adapter = new PostingItemAdapter(ThemeActivity.this, R.layout.posting_item, postingItemList);
            ListView listView = (ListView) findViewById(R.id.postings);
            listView.setAdapter(adapter);

            //添加监听事件
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              @Override
              public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PostingItem postingItem = postingItemList.get(i);
                Intent intent = new Intent(ThemeActivity.this, CardActivity.class);
                intent.putExtra("postID", postingItem.getPostID());
                startActivity(intent);
              }
            });
          }
        } else {
          Log.d("theme", "错误" + e.getMessage());
//          Toast toast = Toast.makeText(getApplicationContext(), "错误" + e.getMessage(), Toast.LENGTH_SHORT);
//          toast.show();
        }
      }
    });
  }

  public void backToPersonal(View view) {
    finish();
  }
}