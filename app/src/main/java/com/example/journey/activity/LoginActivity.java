package com.example.journey.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.journey.R;
import com.example.journey.model.MyBmobInstallation;
import com.example.journey.model.User;

import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 高天 on 2016/8/21.
 */
public class LoginActivity extends Activity {
  private int password_visible = 0;
  private ImageView bt_username_clear; //清空用户账号栏
  private ImageView bt_pwd_clear; //清空密码栏
  private ImageView bt_pw_eye; //显示或隐藏密码按钮

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    bt_username_clear = (ImageView) findViewById(R.id.bt_username_clear);
    bt_pwd_clear = (ImageView) findViewById(R.id.bt_pwd_clear);
    bt_pw_eye = (ImageView) findViewById(R.id.bt_pwd_eye);

    final EditText username = (EditText) findViewById(R.id.username);
    final EditText password = (EditText) findViewById(R.id.password);
    Button login = (Button) findViewById(R.id.login);

    //输入用户账号时清除键显示
    username.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void afterTextChanged(Editable editable) {
        if (editable.toString().length() > 0) {
          bt_username_clear.setVisibility(View.VISIBLE);
        } else {
          bt_username_clear.setVisibility(View.INVISIBLE);
        }
      }
    });

    //输入密码时清除键显示
    password.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void afterTextChanged(Editable editable) {
        if (editable.toString().length() > 0) {
          bt_pwd_clear.setVisibility(View.VISIBLE);
        } else {
          bt_pwd_clear.setVisibility(View.INVISIBLE);
        }
      }
    });

    //账号栏点击清除按钮
    bt_username_clear.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        username.setText("");
      }
    });

    //密码栏点击清除按钮
    bt_pwd_clear.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        password.setText("");
      }
    });

    //点击密码显示或隐藏按钮
    bt_pw_eye.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (password_visible == 0) {//显示密码
          password_visible = 1;
          bt_pw_eye.setImageDrawable(getResources().getDrawable(R.drawable.ic_visibility_off_black_24dp));
          password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else if (password_visible == 1) {//隐藏密码
          password_visible = 0;
          bt_pw_eye.setImageDrawable(getResources().getDrawable(R.drawable.ic_visibility_black_24dp));
          password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
      }
    });


    //登录
    login.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //Toast.makeText(LoginActivity.this, "Fuck me", Toast.LENGTH_SHORT).show();
        User.loginByAccount(username.getText().toString(), password.getText().toString(), new LogInListener<User>() {
          @Override
          public void done(User user, BmobException e) {
            if (e == null) {
              Toast toast = Toast.makeText(getApplicationContext(), "欢迎，" + user.getRealName(), Toast.LENGTH_SHORT);
              toast.show();

              //连接服务器
              BmobIM.connect(user.getObjectId(), new ConnectListener() {
                @Override
                public void done(String s, BmobException e) {
                  if(e == null){
                    Log.d("success","connect success");
                  }else{
                    Log.d("chatwrong",e.getErrorCode()+'/'+e.getMessage());
                  }
                }
              });


              //BmobInstallation和用户进行绑定
              final String userID = user.getObjectId();
              BmobQuery<MyBmobInstallation> query = new BmobQuery<MyBmobInstallation>();
              query.addWhereEqualTo("installationId", BmobInstallation.getInstallationId(getApplicationContext()));
              query.findObjects(new FindListener<MyBmobInstallation>() {
                @Override
                public void done(List<MyBmobInstallation> list, BmobException e) {

                  if (list.size() > 0) {
                    MyBmobInstallation mbi = list.get(0);
                    mbi.setUid(userID);
                    mbi.update(new UpdateListener() {

                      @Override
                      public void done(BmobException e) {
                        if (e == null) {
                          Toast toast = Toast.makeText(getApplicationContext(), "bmob" + "设备信息更新成功", Toast.LENGTH_SHORT);
                          toast.show();
                        } else {
                          Log.i("bmob", "设备信息更新失败:" + e.getMessage());
                        }
                      }
                    });
                  } else {

                  }
                }


                public void onError(int code, String msg) {
                  // TODO Auto-generated method stub
                  Toast toast = Toast.makeText(getApplicationContext(), "查询失败" + msg + code, Toast.LENGTH_SHORT);
                  toast.show();
                }
              });

              finish();
            } else {
              Log.d("Login", e.getMessage());
              Toast toast = Toast.makeText(getApplicationContext(), "用户名/密码错误", Toast.LENGTH_SHORT);
              toast.show();
            }
          }
        });
      }
    });

    //点击“注册” 进行注册
    Button register = (Button) findViewById(R.id.register);
    register.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
      }
    });
  }
}

