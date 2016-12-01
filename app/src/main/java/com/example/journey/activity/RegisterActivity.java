package com.example.journey.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.journey.R;
import com.example.journey.model.User;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by 高天 on 2016/8/23.
 */
public class RegisterActivity extends Activity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);

    Button registerButton = (Button) findViewById(R.id.register);
    final EditText userName = (EditText) findViewById(R.id.username_register);
    final EditText passWord1 = (EditText) findViewById(R.id.password_register);
    final EditText passWord2 = (EditText) findViewById(R.id.password2_register);
    //点击登录 实现登录功能
    registerButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        if (passWord1.getText().toString().equals(passWord2.getText().toString())) //如果两次输入密码一致则登录
        {
          User bu = new User();
          bu.setUsername(userName.getText().toString());
          bu.setPassword(passWord1.getText().toString());
          //bu.setEmail("vega_Gt@163.com");
          bu.setSex(true);
          //bu.setRealName("高小天");
          //bu.setRatingNumber(0);
          //bu.setResume("高冷的人");
          //注意：不能用save方法进行注册
          bu.signUp(new SaveListener<User>() {
            @Override
            public void done(User s, BmobException e) {
              if (e == null) {
                Toast toast = Toast.makeText(getApplicationContext(), "注册成功" + s.getObjectId(), Toast.LENGTH_SHORT);
                toast.show();
                finish();
              } else {
                Toast toast = Toast.makeText(getApplicationContext(), "注册失败-用户名已存在/断网", Toast.LENGTH_SHORT);
                toast.show();
              }
            }
          });
        } else {
          Toast toast = Toast.makeText(getApplicationContext(), "注册失败-两次输入密码不一致", Toast.LENGTH_SHORT);
          toast.show();
        }
      }
    });

  }
}
