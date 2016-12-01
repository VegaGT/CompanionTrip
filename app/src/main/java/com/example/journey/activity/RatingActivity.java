package com.example.journey.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.journey.R;
import com.example.journey.model.Comment;
import com.example.journey.model.Post;
import com.example.journey.model.User;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Li on 2016/8/25.
 */
public class RatingActivity extends AppCompatActivity {
    private RatingBar ratingBar;//星条
    private EditText editText; //评价
    private Button submitButton; //提交按钮
    private ImageView head;
    User commentTo;
    Post post;
    Comment comment = new Comment();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rate_layout);

        Intent intent = getIntent();
        //查用户
        BmobQuery<User> query1 = new BmobQuery<User>();
        query1.getObject(intent.getStringExtra("commentTo_userID"), new QueryListener<User>() {
            @Override
            public void done(User object, BmobException e) {
                if(e==null){
                    commentTo = object;
                    Glide.with(getApplicationContext()).load(object.getUserIcon().getUrl()).into(head);
                    Toast toast = Toast.makeText(getApplicationContext(), "查了用户", Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
        //查帖子
        BmobQuery<Post> query2 = new BmobQuery<Post>();
        query2.getObject(intent.getStringExtra("post_ID"), new QueryListener<Post>() {
            @Override
            public void done(Post object, BmobException e) {
                if(e==null){
                    post = object;
                    Toast toast = Toast.makeText(getApplicationContext(), "查了帖子", Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });

        ratingBar = (RatingBar)findViewById(R.id.rating_bar);
        editText = (EditText)findViewById(R.id.comment_to_companion);
        submitButton = (Button)findViewById(R.id.submit_comment);
        head = (ImageView)findViewById(R.id.rating_portrait) ;
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
            }
        });


        //提交添加事件
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comment.setCommentTo(commentTo);
                comment.setCommentFrom(User.getCurrentUser(User.class));
                comment.setContent(editText.getText().toString());
                comment.setPost(post);
                comment.setScore(ratingBar.getRating());
                comment.save(new SaveListener<String>() {
                    @Override
                    public void done(String objectId, BmobException e) {
                        if(e==null){
                            Toast toast = Toast.makeText(getApplicationContext(), "评价成功", Toast.LENGTH_SHORT);
                            toast.show();
                        }else{
                            Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                        }
                    }
                });
                finish();
            }
        });
    }
    public void bnt_back_to_companion(View view){
        finish();
    }
}
