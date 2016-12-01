package com.example.journey.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.journey.R;
import com.example.journey.model.Post;
import com.example.journey.model.PushMessage;
import com.example.journey.model.TripMessageItem;
import com.example.journey.model.TripMessageItemAdapter;
import com.example.journey.model.User;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Li on 2016/9/13.
 */
public class TripMessageActivity extends AppCompatActivity {

  private List<TripMessageItem> tripMessageItemList = new ArrayList<TripMessageItem>();
  User pusher;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.trip_messsage);

    initTripMessageItem();
  }

  private void initTripMessageItem() {

    BmobQuery<PushMessage> query = new BmobQuery<PushMessage>();
    query.addWhereEqualTo("receiver", User.getCurrentUser(User.class));
    query.order("-createdAt");
    query.include("pusher,post");
    query.setLimit(50);
    query.findObjects(new FindListener<PushMessage>() {
      @Override
      public void done(List<PushMessage> object, BmobException e) {
        if (e == null) {
          Log.d("tripMessage", "查询成功：共" + object.size() + "条数据。");
          for (int i = 0; i < object.size(); i++) {
            final String id = object.get(i).getObjectId();
            final String name = object.get(i).getPusher().getRealName();
            final String head = object.get(i).getPusher().getUserIcon().getUrl();
            final String content = object.get(i).getContent();
            final String extraContent = object.get(i).getExtraContent();
            final String age = String.valueOf(object.get(i).getPusher().getAge());
            Boolean sex = object.get(i).getPusher().getSex();
            final User pusher = object.get(i).getPusher();
            final int isHandled = object.get(i).getHandled();
            final int[] joinedPeople = new int[1];
            final int[] maxPeople = new int[1];
            final int sexIcon;
            if (sex) {
              sexIcon = R.drawable.male_pic;
            } else {
              sexIcon = R.drawable.female_pic;
            }
            final int type = object.get(i).getType();
            final Post post = object.get(i).getPost();
            joinedPeople[0] = object.get(i).getPost().getJoinedNumber();
            maxPeople[0] = object.get(i).getPost().getNumber();
            String resume = object.get(i).getPusher().getResume();
            String pusherID = object.get(i).getPusher().getObjectId();
            TripMessageItem tripMessageItem = new TripMessageItem(id, name, content, extraContent, head, sexIcon, age, type, post, pusher,
                    isHandled, joinedPeople[0], maxPeople[0], resume, pusherID);
            tripMessageItemList.add(tripMessageItem);
            TripMessageItemAdapter adapter = new TripMessageItemAdapter(TripMessageActivity.this, R.layout.trip_message_item, tripMessageItemList);
            ListView listView = (ListView) findViewById(R.id.message_request_list);
            listView.setAdapter(adapter);
          }
        } else {
          Log.d("tripMessage", "错误" + e.getMessage() + e.getErrorCode());
        }
      }
    });
  }

  public void back_to_message_icon(View view) {
    finish();
  }
}
