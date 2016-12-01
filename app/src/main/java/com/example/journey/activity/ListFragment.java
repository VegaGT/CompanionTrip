package com.example.journey.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.journey.R;
import com.example.journey.model.Post;
import com.example.journey.model.PostingItem;
import com.example.journey.model.PostingItemAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 高天 on 2016/8/19.
 */
public class ListFragment extends Fragment implements View.OnClickListener {

  private SwipeRefreshLayout swipeRefreshLayout;
  private Button date, joinedAmount, personAmount;
  private TextView city;
  private List<PostingItem> postingItemList = new ArrayList<PostingItem>();
  private ListView listView;
  private PostingItemAdapter adapter;

  private int sortNum = 0; //排序参数(默认0为按出发日期排序)
  private int actionBarClickTime = 0;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_list, container, false);

    RelativeLayout actionBar = (RelativeLayout) v.findViewById(R.id.top_relative);
    listView = (ListView) v.findViewById(R.id.postings);

    actionBar.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        actionBarClickTime++;
        Handler handler = new Handler();
        Runnable r = new Runnable() {
          @Override
          public void run() {
            actionBarClickTime = 0;
          }
        };

        if (actionBarClickTime == 1) {
          //Single click
          handler.postDelayed(r, 250);
        } else if (actionBarClickTime == 2) {
          //Double click
          actionBarClickTime = 0;
          listView.smoothScrollToPosition(0);
        }
      }
    });

    View header = getActivity().getLayoutInflater().inflate(R.layout.posting_list_header, null);
    date = (Button) header.findViewById(R.id.base_on_date);
    joinedAmount = (Button) header.findViewById(R.id.base_on_joined_number);
    personAmount = (Button) header.findViewById(R.id.base_on_person_number);
    listView.addHeaderView(header);

    return v;
  }

  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    city = (TextView) getActivity().findViewById(R.id.city);

    date.setOnClickListener(this);
    date.setTextColor(Color.RED);
    joinedAmount.setOnClickListener(this);
    personAmount.setOnClickListener(this);

    city.setText("发现");

    swipeRefreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipe_container);
    swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
            R.color.colorAccent);
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

  //三种点击排序事件
  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.base_on_date:
        if (sortNum != 0) {
          date.setTextColor(Color.RED);
          joinedAmount.setTextColor(Color.GRAY);
          personAmount.setTextColor(Color.GRAY);
          sortNum = 0;
          onResume();
        }
        break;
      case R.id.base_on_joined_number:
        if (sortNum != 1) {
          date.setTextColor(Color.GRAY);
          joinedAmount.setTextColor(Color.RED);
          personAmount.setTextColor(Color.GRAY);
          sortNum = 1;
          onResume();
        }
        break;
      case R.id.base_on_person_number:
        if (sortNum != 2) {
          date.setTextColor(Color.GRAY);
          joinedAmount.setTextColor(Color.GRAY);
          personAmount.setTextColor(Color.RED);
          sortNum = 2;
          onResume();
        }
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

    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        swipeRefreshLayout.setRefreshing(true);
      }
    }, 100);

    BmobQuery<Post> query = new BmobQuery<Post>();
    query.setLimit(30);
    query.order("-createdAt");
    query.include("author");
    query.addWhereNotEqualTo("isDone", true);
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

            adapter = new PostingItemAdapter(getActivity(), R.layout.posting_item, postingItemList);
            listView.setAdapter(adapter);

            //添加监听事件
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              @Override
              public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PostingItem postingItem = postingItemList.get(i - 1);
                Intent intent = new Intent(getActivity(), CardActivity.class);
                intent.putExtra("postID", postingItem.getPostID());
                startActivity(intent);
              }
            });
          }
        } else {
          Toast toast = Toast.makeText(getActivity().getApplicationContext(), "查询出错：" + e.getErrorCode(), Toast.LENGTH_SHORT);
          Log.d("bmob", e.getErrorCode() + ", " + e.getMessage());
          toast.show();
        }
      }

    });
  }

}