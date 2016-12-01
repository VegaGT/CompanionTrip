package com.example.journey.activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.journey.R;
import com.example.journey.model.Feedback;
import com.example.journey.model.User;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
/**
 * Created by Li on 2016/10/11.
 */
public class FeedBackActivity extends AppCompatActivity{
    private EditText editText_contact;//联系方式
    private EditText editText_content;//反馈内容
    private Button submit;//提交按钮
    private ImageView backFeedback;//返回上一界面
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);
        editText_contact = (EditText)findViewById(R.id.feedback_contact);
        editText_content = (EditText)findViewById(R.id.feedback_content);
        submit = (Button)findViewById(R.id.feedback_submit);
        backFeedback = (ImageView)findViewById(R.id.back_feedback);
        backFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Feedback feedback = new Feedback();
                feedback.setUser(User.getCurrentUser(User.class));
                feedback.setContacts(editText_contact.getText().toString());
                feedback.setContent(editText_content.getText().toString());
                feedback.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if(e == null){
                            Toast.makeText(FeedBackActivity.this,"反馈成功",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(FeedBackActivity.this,SettingActivity.class);
                            startActivity(intent);
                        }
                    }
                });
            }
        });
    }
}