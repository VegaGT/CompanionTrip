package com.example.journey.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.journey.R;
import com.example.journey.model.User;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 高天 on 2016/8/24.
 */
public class EditActivity extends Activity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit);

    final EditText name = (EditText) findViewById(R.id.edit_name);
    final EditText age = (EditText) findViewById(R.id.edit_age);
    final Spinner sex = (Spinner) findViewById(R.id.edit_sex);
    final EditText intro = (EditText) findViewById(R.id.edit_information);
    Button preserve = (Button) findViewById(R.id.preserve);
    User user = User.getCurrentUser(User.class);

    if (user.getRealName() != null) {
      name.setText(user.getRealName());
    }
    if (user.getAge() != null) {
      age.setText(user.getAge().toString());
    }
    if (user.getSex() != null) {
      if (user.getSex()) {
        sex.setSelection(0);
      } else {
        sex.setSelection(1);
      }
    }
    if (user.getResume() != null) {
      intro.setText(user.getResume());
    }

    final User newUser = new User();
    //性别选择
    sex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (sex.getSelectedItem().toString().equals("男")) {
          newUser.setSex(true);
        } else {
          newUser.setSex(false);
        }
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
    preserve.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //新建一个用户对象，并调用update(objectId,updateListener)方法来更新用户资料
        newUser.setRealName(name.getText().toString());
        //年龄
        if (age.getText() != null) {
          newUser.setAge(Integer.parseInt(age.getText().toString().trim()));
        }

        //个人简介
        newUser.setResume(intro.getText().toString());
        //更新数据
        User currentUser = User.getCurrentUser(User.class);
        newUser.update(currentUser.getObjectId(), new UpdateListener() {
          @Override
          public void done(BmobException e) {
            if (e == null) {
              Toast toast = Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_SHORT);
              toast.show();
            } else {
              Toast toast = Toast.makeText(getApplicationContext(), "保存失败", Toast.LENGTH_SHORT);
              toast.show();
            }
          }
        });
      }
    });
  }

  public void back(View view) {
    finish();
  }

}
