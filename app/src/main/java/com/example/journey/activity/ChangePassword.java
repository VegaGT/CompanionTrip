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
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 高天 on 2016/10/20.
 */
public class ChangePassword extends Activity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_changepassword);

    Button registerButton = (Button) findViewById(R.id.register);
    final EditText oldPsw = (EditText) findViewById(R.id.username_register);
    final EditText passWord1 = (EditText) findViewById(R.id.password_register);
    final EditText passWord2 = (EditText) findViewById(R.id.password2_register);
    //点击登录 实现登录功能
    registerButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        if (passWord1.getText().toString().equals(passWord2.getText().toString())) {
          User.updateCurrentUserPassword(oldPsw.getText().toString(), passWord1.getText().toString(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
              if (e == null) {
                Toast toast = Toast.makeText(getApplicationContext(), "密码修改成功，可以用新密码进行登录啦", Toast.LENGTH_SHORT);
                toast.show();
                finish();
              } else {
                Toast toast = Toast.makeText(getApplicationContext(), "修改失败:" + e.getMessage(), Toast.LENGTH_SHORT);
                toast.show();
              }
            }

          });
        } else {
          Toast toast = Toast.makeText(getApplicationContext(), "两次输入密码不一致", Toast.LENGTH_SHORT);
          toast.show();
        }
      }
    });
  }

  public void back(View view) {
    finish();
  }
}
