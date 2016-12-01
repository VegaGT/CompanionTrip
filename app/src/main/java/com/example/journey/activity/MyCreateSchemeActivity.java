package com.example.journey.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.journey.R;
import com.example.journey.model.CreatedPostingItemAdapter;
import com.example.journey.model.Post;
import com.example.journey.model.PostingItem;
import com.example.journey.model.User;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Li on 2016/9/14.
 */
public class MyCreateSchemeActivity extends AppCompatActivity {
  private List<PostingItem> postingItemList = new ArrayList<PostingItem>();
  private ImageView addNewScheme;//添加行程按钮
  SwipeRefreshLayout swipeRefreshLayout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.my_create_scheme);
    addNewScheme = (ImageView) findViewById(R.id.add_new_scheme);
    addNewScheme.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(MyCreateSchemeActivity.this, CreateActivity.class);
        startActivity(intent);
      }
    });
    swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container_my_scheme);
    //设置刷新时动画的颜色，可以设置4个
    swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        initPostingItem();
      }
    });
  }

  public void onResume() {
    super.onResume();
    initPostingItem();
  }

  //生成listView的实例
  public void initPostingItem() {
    //查询当前用户创建的所有帖子

    if (!swipeRefreshLayout.isRefreshing())
      swipeRefreshLayout.setRefreshing(true);

    User user = User.getCurrentUser(User.class);
    BmobQuery<Post> query = new BmobQuery<Post>();
    query.addWhereEqualTo("author", user);  // 查询当前用户的所有帖子
    query.order("-updatedAt");
    query.include("author");// 希望在查询帖子信息的同时也把发布人的信息查询出来
    query.findObjects(new FindListener<Post>() {
      @Override
      public void done(List<Post> object, BmobException e) {
        if (e == null) {
          if (swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
          postingItemList.clear();
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
            //一句话解决问题
            //adapter.notifyDataSetChanged();
            CreatedPostingItemAdapter adapter = new CreatedPostingItemAdapter(MyCreateSchemeActivity.this, R.layout.my_posting_item, postingItemList);
            ListView listView = (ListView) findViewById(R.id.my_create_posting_list);
            listView.setAdapter(adapter);
            //添加监听事件
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              @Override
              public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PostingItem postingItem = postingItemList.get(i);
                Intent intent = new Intent(MyCreateSchemeActivity.this, CardActivity.class);
                intent.putExtra("postID", postingItem.getPostID());
                startActivity(intent);
              }
            });
          }
        } else {
          Toast toast = Toast.makeText(getApplicationContext(), "错误" + e.getMessage(), Toast.LENGTH_SHORT);
          toast.show();
        }
      }
    });
  }

  public void backToMyCreateScheme(View view) {
    finish();
  }
}