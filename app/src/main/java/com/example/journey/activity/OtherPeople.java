package com.example.journey.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.journey.R;
import com.example.journey.model.PersonalMsg;
import com.example.journey.model.PersonalMsgAdapter;

import java.util.ArrayList;
import java.util.List;

public class OtherPeople extends AppCompatActivity {
  private Boolean sex;
  private ImageView sexIcon;
  private List<PersonalMsg> personalMsgList = new ArrayList<PersonalMsg>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_other_people);

    ImageView back = (ImageView) findViewById(R.id.backToPost);
    ImageView head = (ImageView) findViewById(R.id.other_portrait_button);
    TextView name = (TextView) findViewById(R.id.other_id_text);
    sexIcon = (ImageView) findViewById(R.id.other_sex_icon);
    TextView age = (TextView) findViewById(R.id.other_age_text);
    TextView info = (TextView) findViewById(R.id.other_personal_intro);
    Intent intent = getIntent();
    final String userID = intent.getStringExtra("user");
    Glide.with(this).load(intent.getStringExtra("head")).centerCrop().into(head);
    name.setText(intent.getStringExtra("name"));
    sex = intent.getBooleanExtra("sex", true);
    if (sex) {
      sexIcon.setImageResource(R.drawable.male_pic);
    } else {
      sexIcon.setImageResource(R.drawable.female_pic);
    }
    age.setText(String.valueOf(intent.getIntExtra("age", -1)));
    info.setText(intent.getStringExtra("info"));

    sexIcon.setVisibility(View.VISIBLE);
    age.setVisibility(View.VISIBLE);
    info.setVisibility(View.VISIBLE);

    //返回
    back.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });

    //列表的
    initPersonalMsg();
    PersonalMsgAdapter adapter = new PersonalMsgAdapter(this, R.layout.personal_msg_item, personalMsgList);
    final ListView listView = (ListView) findViewById(R.id.other_personal_msg_view);
    listView.setAdapter(adapter);
    listView.setDivider(new ColorDrawable(Color.GRAY));
    listView.setDividerHeight(1);
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        PersonalMsg personalMsg = personalMsgList.get(i);
        if (i == 0) {
          Intent intent = new Intent(OtherPeople.this, OtherCreatedSchemeActivity.class);
          intent.putExtra("title", personalMsg.getText());
          intent.putExtra("user", userID);
          startActivity(intent);
        }
        if (i == 1) {
          Intent intent = new Intent(OtherPeople.this, MessageBoardActivity.class);
          intent.putExtra("user", userID);
          intent.putExtra("title", personalMsg.getText());
          startActivity(intent);
        }
      }
    });
  }

  private void initPersonalMsg() {
    if (sex) {
      PersonalMsg personalMsg1 = new PersonalMsg(R.drawable.ic_folder_add, "他创建的行程", R.drawable.ic_chevron_right_black_24dp);
      personalMsgList.add(personalMsg1);
      PersonalMsg personalMsg3 = new PersonalMsg(R.drawable.ic_folder_check, "他的评价", R.drawable.ic_chevron_right_black_24dp);
      personalMsgList.add(personalMsg3);
    } else {
      PersonalMsg personalMsg1 = new PersonalMsg(R.drawable.ic_folder_add, "她创建的行程", R.drawable.ic_chevron_right_black_24dp);
      personalMsgList.add(personalMsg1);
      PersonalMsg personalMsg3 = new PersonalMsg(R.drawable.ic_folder_check, "她的评价", R.drawable.ic_chevron_right_black_24dp);
      personalMsgList.add(personalMsg3);
    }
  }
}
