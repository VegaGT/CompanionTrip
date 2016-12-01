package com.example.journey.activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.journey.R;
import com.example.journey.model.JoinedPostingItemAdapter;
import com.example.journey.model.Post;
import com.example.journey.model.PostingItem;
import com.example.journey.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
/**
 * Created by Li on 2016/9/14.
 */
public class PostInvolveMeActivity extends AppCompatActivity {
    private List<PostingItem> postingItemList = new ArrayList<PostingItem>();
    private JoinedPostingItemAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_involve_me);
        //ListView的实例创建
        initPostingItem();
    }
    private void initPostingItem(){
        // 查询用户加入的所有行程
        BmobQuery<Post> query = new BmobQuery<Post>();
        final User user;
        user = BmobUser.getCurrentUser(User.class);
        //joinedPeople是Post表中的字段，用来存储所有加入该帖子的用户
        String[] id = {user.getObjectId()};
        query.addWhereContainsAll("joinedPeople", Arrays.asList(id));
        query.include("author");
        query.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> object,BmobException e) {
                if(e==null){
//                    Toast toast = Toast.makeText(getApplicationContext(), user.getObjectId()+"查询个数："+object.size(), Toast.LENGTH_SHORT);
//                    toast.show();
                    for(int i=0 ;i < object.size() ;i++) {
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
                        if(object.get(i).getImages().size() == 0){
                            background = "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1609/27/c0/27587200_1474952314810_800x600.jpg";
                        }else{
                            background = object.get(i).getImage(0).getUrl();
                        }

                        PostingItem postingItem = new PostingItem(head, authorName, sexIcon,
                                title, tag1, tag2, tag3, "人数:" + joinedNumber + "/" + number, startDate + "-" + endDate, postId,isDone,background);
                        postingItemList.add(postingItem);
                        //一句话解决问题
                        //adapter.notifyDataSetChanged();
                        adapter = new JoinedPostingItemAdapter(PostInvolveMeActivity.this, R.layout.my_posting_item, postingItemList);
                        ListView listView = (ListView) findViewById(R.id.post_involve_me_list);
                        listView.setAdapter(adapter);

                        //添加监听事件
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                PostingItem postingItem = postingItemList.get(i);
                                Intent intent = new Intent(PostInvolveMeActivity.this, CardActivity.class);
                                intent.putExtra("postID", postingItem.getPostID());
                                intent.putExtra("comeFrom",1); //1代表来自PostInvolveMe的请求
                                startActivity(intent);
                            }
                        });
                    }
                }else{
                    Log.i("bmob","失败："+e.getMessage());
//                    Toast toast = Toast.makeText(getApplicationContext(), "失败："+e.getMessage(), Toast.LENGTH_SHORT);
//                    toast.show();
                }
            }
        });
    }
    public void backToPostInvolveMe(View view){
        finish();
    }
}