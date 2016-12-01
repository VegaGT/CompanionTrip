package com.example.journey.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.journey.R;
import com.example.journey.model.Comment;
import com.example.journey.model.MessageBoardItem;
import com.example.journey.model.MessageBoardItemAdapter;
import com.example.journey.model.User;
import com.example.journey.util.ScreenUtils;
import com.example.journey.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by Li on 2016/8/25.
 */
public class MessageBoardActivity extends AppCompatActivity {
  private List<MessageBoardItem> messageBoardItemList = new ArrayList<MessageBoardItem>();

  private float rate = 0;//平均评分
  private float allScore = 0;//总评分和

  private ImageView portrait;//用户头像
  private RatingBar ratingBar;//我的星级
  private TextView title;
  private ListView listView;
  private User userNow;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.my_comments);

    title = (TextView) findViewById(R.id.comment_title);
    listView = (ListView) findViewById(R.id.my_comments_list);

    title.setText(getIntent().getStringExtra("title"));

    View header = getLayoutInflater().inflate(R.layout.comments_header, null);
    ListView.LayoutParams params = new ListView.LayoutParams(ScreenUtils.getScreenWidth(this), ViewGroup.LayoutParams.WRAP_CONTENT);
    header.setLayoutParams(params);
    portrait = (ImageView) header.findViewById(R.id.comment_portrait);
    ratingBar = (RatingBar) header.findViewById(R.id.my_rating_bar);
    listView.addHeaderView(header);

    Intent intent = getIntent();
    if (intent.getStringExtra("user") != null) {
      BmobQuery<User> query = new BmobQuery<User>();
      query.getObject(intent.getStringExtra("user"), new QueryListener<User>() {
        @Override
        public void done(User object, BmobException e) {
          if (e == null) {
            userNow = object;
            Glide.with(MessageBoardActivity.this).load(userNow.getUserIcon().getFileUrl()).centerCrop().into(portrait);
            initMessageBoardItem();
          } else {
            Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
          }
        }
      });
    } else {
      userNow = User.getCurrentUser(User.class);
      Glide.with(this).load(userNow.getUserIcon().getFileUrl()).centerCrop().into(portrait);
      initMessageBoardItem();
    }
  }

  private void initMessageBoardItem() {

    BmobQuery<Comment> query = new BmobQuery<Comment>();
    query.addWhereEqualTo("commentTo", userNow);
    query.include("commentFrom,post");
    query.findObjects(new FindListener<Comment>() {
      @Override
      public void done(List<Comment> list, BmobException e) {
        if (e == null) {
          allScore = 0;
          messageBoardItemList.clear();
          for (int i = 0; i < list.size(); ++i) {
            //Toast.makeText(getApplicationContext(), "成功" + list.size(), Toast.LENGTH_SHORT).show();
            String portrait = list.get(i).getCommentFrom().getUserIcon().getUrl();
            String name = list.get(i).getCommentFrom().getRealName();
            String date = StringUtils.formatTimeOnlyDate(list.get(i).getCreatedAt());
            String comment = list.get(i).getContent();
            String post_from = list.get(i).getPost().getTitle();
            MessageBoardItem messageBoardItem = new MessageBoardItem(portrait, name, date, comment, post_from);
            messageBoardItemList.add(messageBoardItem);
            allScore += list.get(i).getScore();
          }

          MessageBoardItemAdapter adapter = new MessageBoardItemAdapter(MessageBoardActivity.this, R.layout.message_board_item, messageBoardItemList);
          listView.setAdapter(adapter);
          rate = allScore / list.size();
          ratingBar.setRating(rate);
        } else {
          Toast.makeText(getApplicationContext(), e.getErrorCode() + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
      }
    });
  }

  public void button_back_to_personal(View view) {
    finish();
  }
}