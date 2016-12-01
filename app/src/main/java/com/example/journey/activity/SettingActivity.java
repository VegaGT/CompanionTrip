package com.example.journey.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.journey.R;
import com.example.journey.model.PersonalMsg;
import com.example.journey.model.PersonalMsgAdapter;
import com.example.journey.model.User;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.newim.BmobIM;

/**
 * Created by Li on 2016/8/21.
 */
public class SettingActivity extends AppCompatActivity {
  private List<PersonalMsg> settingMsgList = new ArrayList<PersonalMsg>();
  User currentUser = User.getCurrentUser(User.class);

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.setting_page);
    initPersonalMsg();

    //手机号
    TextView phoneNumber = (TextView) findViewById(R.id.phone_number);
    if (currentUser != null)
      phoneNumber.setText(currentUser.getMobilePhoneNumber());
    //退出当前账号
    Button logout = (Button) findViewById(R.id.logout);
    logout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        User.logOut();
        Toast toast = Toast.makeText(getApplicationContext(), "已退出当前账号", Toast.LENGTH_SHORT);
        toast.show();
        currentUser = User.getCurrentUser(User.class);
        BmobIM.getInstance().disConnect();//断开连接
        finish();
      }
    });
    if (currentUser == null) {
      logout.setVisibility(View.GONE);
    }
  }

  private void initPersonalMsg() {
    if (currentUser != null) {
      PersonalMsg personalMsg0 = new PersonalMsg("修改密码", R.drawable.ic_chevron_right_black_24dp);
      settingMsgList.add(personalMsg0);
      PersonalMsg personalMsg1 = new PersonalMsg("用户协议", R.drawable.ic_chevron_right_black_24dp);
      settingMsgList.add(personalMsg1);
      PersonalMsg personalMsg2 = new PersonalMsg("意见反馈", R.drawable.ic_chevron_right_black_24dp);
      settingMsgList.add(personalMsg2);
      PersonalMsg personalMsg3 = new PersonalMsg("检查新版本", R.drawable.ic_chevron_right_black_24dp);
      settingMsgList.add(personalMsg3);
      PersonalMsg personalMsg4 = new PersonalMsg("关于我们", R.drawable.ic_chevron_right_black_24dp);
      settingMsgList.add(personalMsg4);
      PersonalMsgAdapter adapter = new PersonalMsgAdapter(SettingActivity.this, R.layout.personal_msg_item, settingMsgList);
      ListView listView = (ListView) findViewById(R.id.more_setting);
      listView.setAdapter(adapter);
      listView.setDivider(new ColorDrawable(Color.GRAY));
      listView.setDividerHeight(1);
      listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
          PersonalMsg personalMsg = settingMsgList.get(i);
          if (i == 0) {
            Intent intent = new Intent(SettingActivity.this, ChangePassword.class);
            startActivity(intent);
          }
          if (i == 2) {
            Intent intent = new Intent(SettingActivity.this, FeedBackActivity.class);
            startActivity(intent);
          }
          if (i == 4) {
            Intent intent = new Intent(SettingActivity.this, AboutUsActivity.class);
            startActivity(intent);
          }
        }
      });
    } else {
      PersonalMsg personalMsg1 = new PersonalMsg("用户协议", R.drawable.ic_chevron_right_black_24dp);
      settingMsgList.add(personalMsg1);
      PersonalMsg personalMsg2 = new PersonalMsg("意见反馈", R.drawable.ic_chevron_right_black_24dp);
      settingMsgList.add(personalMsg2);
      PersonalMsg personalMsg3 = new PersonalMsg("检查新版本", R.drawable.ic_chevron_right_black_24dp);
      settingMsgList.add(personalMsg3);
      PersonalMsg personalMsg4 = new PersonalMsg("关于我们", R.drawable.ic_chevron_right_black_24dp);
      settingMsgList.add(personalMsg4);
      PersonalMsgAdapter adapter = new PersonalMsgAdapter(SettingActivity.this, R.layout.personal_msg_item, settingMsgList);
      ListView listView = (ListView) findViewById(R.id.more_setting);
      listView.setAdapter(adapter);
      listView.setDivider(new ColorDrawable(Color.GRAY));
      listView.setDividerHeight(1);
      listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
          PersonalMsg personalMsg = settingMsgList.get(i);
          if (i == 1) {
            Intent intent = new Intent(SettingActivity.this, FeedBackActivity.class);
            startActivity(intent);
          }
          if (i == 3) {
            Intent intent = new Intent(SettingActivity.this, AboutUsActivity.class);
            startActivity(intent);
          }
        }
      });
    }

  }

  public void backToPersonal(View view) {
    finish();
  }
}