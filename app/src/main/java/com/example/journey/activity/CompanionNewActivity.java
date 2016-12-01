package com.example.journey.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.journey.R;
import com.example.journey.model.Comment;
import com.example.journey.model.CompanionItem;
import com.example.journey.model.CompanionItemAdapter;
import com.example.journey.model.Post;
import com.example.journey.model.User;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class CompanionNewActivity extends AppCompatActivity {
  private List<CompanionItem> companionItemList = new ArrayList<CompanionItem>();
  private String postID;
  private Post post;
  private ListView listView;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.all_companion);
    listView = (ListView) findViewById(R.id.companion_list);
  }

  public void onResume() {
    super.onResume();
    companionItemList.clear();
    initPostingItem();
  }

  private void initPostingItem() {
    Intent intent = getIntent();
    postID = intent.getStringExtra("postID");
    Toast.makeText(getApplicationContext(), postID, Toast.LENGTH_SHORT).show();
    post = new Post();
    post.setObjectId(postID);
    BmobQuery<User> query = new BmobQuery<User>();
    query.addWhereRelatedTo("joinedPeople", new BmobPointer(post));
    query.findObjects(new FindListener<User>() {
      @Override
      public void done(final List<User> list, BmobException e) {
        if (e == null) {
          showList();
        } else {
          Log.i("bmob", "失败：" + e.getMessage());
//          Toast toast = Toast.makeText(getApplicationContext(), "失败：" + e.getMessage(), Toast.LENGTH_SHORT);
//          toast.show();
        }
      }
    });
  }

  private void showList() {
    BmobQuery<User> query = new BmobQuery<User>();
    query.addWhereRelatedTo("joinedPeople", new BmobPointer(post));
    query.findObjects(new FindListener<User>() {
      @Override
      public void done(final List<User> list, BmobException e) {
        if (e == null) {

          final Boolean[] queryFinished = new Boolean[list.size()];
          for (int i = 0; i < queryFinished.length; i++) {
            queryFinished[i] = false;
          }

          final List<CompanionItem> newCompanionItemList = new ArrayList<CompanionItem>();

          for (int i = 0; i < list.size(); ++i) {
            final int j = i;
            final String portrait = list.get(i).getUserIcon().getUrl();
            final String name = list.get(i).getRealName();
            final Boolean[] flag = {false};
            final String objID = list.get(i).getObjectId();
            final BmobQuery<Comment> query = new BmobQuery<Comment>();
            query.addWhereEqualTo("post", post);
            query.addWhereEqualTo("commentFrom", User.getCurrentUser(User.class));
            query.addWhereEqualTo("commentTo", list.get(i));
//            Toast toast = Toast.makeText(getApplicationContext(), "Post:" + post.getObjectId() + "commentFrom:" +
//                    User.getCurrentUser(User.class).getObjectId() + "commentTo" + list.get(i).getObjectId(), Toast.LENGTH_SHORT);
//            toast.show();
            query.findObjects(new FindListener<Comment>() {
              @Override
              public void done(List<Comment> object, BmobException e) {
                queryFinished[j] = true;
                if (e == null) {
                  if (object.size() > 0) {
//                    Toast toast = Toast.makeText(getApplicationContext(), "查询成功：共" + object.size() + "条数据。(评价已存在)", Toast.LENGTH_SHORT);
//                    toast.show();
                    flag[0] = true;
                    CompanionItem companionItem = new CompanionItem(portrait, name, flag[0], objID);
                    newCompanionItemList.add(companionItem);
                  } else {
//                    Toast toast = Toast.makeText(getApplicationContext(), "评价不存在", Toast.LENGTH_SHORT);
//                    toast.show();
                    CompanionItem companionItem = new CompanionItem(portrait, name, flag[0], objID);
                    newCompanionItemList.add(companionItem);
                  }
                } else {
//                  Toast toast = Toast.makeText(getApplicationContext(), "SOMETHING WRONG" + e.getMessage() + e.getErrorCode(), Toast.LENGTH_SHORT);
//                  toast.show();
                }

                if (ifAllHaveFinished(queryFinished)) {
                  companionItemList.clear();
                  companionItemList = newCompanionItemList;
                  CompanionItemAdapter adapter = new CompanionItemAdapter(CompanionNewActivity.this, R.layout.companion_item, companionItemList);
                  listView.setAdapter(adapter);
                  listView.requestLayout();
                  adapter.notifyDataSetChanged();
                  listView.setDivider(new ColorDrawable(ContextCompat.getColor(CompanionNewActivity.this, R.color.grey)));
                  listView.setDividerHeight(2);
                  listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                      CompanionItem companionItem = companionItemList.get(i);
                      if (!companionItem.getFlag()) {
                        Intent intent = new Intent(CompanionNewActivity.this, RatingActivity.class);
                        intent.putExtra("commentTo_userID", companionItem.getObjID());
                        intent.putExtra("post_ID", postID);
                        startActivityForResult(intent, 1);
                      }
                    }
                  });
                }
              }
            });
          }
        } else {
          Log.i("bmob", "失败：" + e.getMessage());
//          Toast toast = Toast.makeText(getApplicationContext(), "失败：" + e.getMessage(), Toast.LENGTH_SHORT);
//          toast.show();
        }
      }
    });
  }

  private boolean ifAllHaveFinished(Boolean[] queryFinished) {
    for (Boolean b : queryFinished) {
      if (!b) return false;
    }
    return true;
  }

  public void bnt_back_to_posting(View view) {
    finish();
  }
  //接收是否已评价消息
}
