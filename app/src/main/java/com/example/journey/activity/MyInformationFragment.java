package com.example.journey.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.journey.R;
import com.example.journey.model.PersonalMsg;
import com.example.journey.model.PersonalMsgAdapter;
import com.example.journey.model.PushMessage;
import com.example.journey.model.User;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;

public class MyInformationFragment extends Fragment {
  private List<PersonalMsg> personalMsgList = new ArrayList<PersonalMsg>();
  private ImageView head;
  private TextView username;
  private TextView age;
  private ImageView sexIcon;
  private TextView resume;
  private ImageButton message;//行程消息
  private ImageButton chat;//会话
  private TextView edit;
  private LinearLayout settings;
  //得到当前用户
  private User userInfo = User.getCurrentUser(User.class);
  private boolean hasNewMessage = false;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.personal_layout, container, false);
    return v;
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    //点击头像进行登陆
    head = (ImageView) getActivity().findViewById(R.id.portrait_button);
    head.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        if (userInfo == null) {      //如果没登录就登录
          Intent intent = new Intent(getActivity(), LoginActivity.class);
          startActivity(intent);
        } else {                     //登录了就可以点击头像进行改头像
          Intent intent = new Intent(getActivity(), SetPortraitActivity.class);
          startActivity(intent);
        }
      }
    });
    //点击消息图标进入消息列表
    message = (ImageButton) getActivity().findViewById(R.id.message_button);
    message.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getActivity(), TripMessageActivity.class);
        startActivity(intent);
      }
    });

    //点击会话图标进入会话列表
    chat = (ImageButton)getActivity().findViewById(R.id.chat_button);
    chat.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getActivity(),ChatListActivity.class);
        startActivity(intent);
      }
    });

    //点击图标进入资料编辑
    edit = (TextView) getActivity().findViewById(R.id.edit_button);
    edit.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        if (userInfo != null) {
          Intent intent = new Intent(getActivity(), EditActivity.class);
          startActivity(intent);
        } else {
          loginTip();
        }
      }
    });
    //列表的
    initPersonalMsg();
    PersonalMsgAdapter adapter = new PersonalMsgAdapter(getActivity(), R.layout.personal_msg_item, personalMsgList);
    final ListView listView = (ListView) getActivity().findViewById(R.id.personal_msg_view);
    listView.setAdapter(adapter);
    listView.setDivider(new ColorDrawable(Color.GRAY));
    listView.setDividerHeight(1);
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (i == 0) {
          if (userInfo != null) {
            Intent intent = new Intent(getActivity(), MyCreateSchemeActivity.class);
            startActivity(intent);
          } else {
            loginTip();
          }
        } else if (i == 1) {
          if (userInfo != null) {
            Intent intent = new Intent(getActivity(), PostInvolveMeActivity.class);
            startActivity(intent);
          } else {
            loginTip();
          }
        } else if (i == 2) {
          if (userInfo != null) {
            Intent intent = new Intent(getActivity(), MessageBoardActivity.class);
            intent.putExtra("title", personalMsgList.get(i).getText());
            startActivity(intent);
          } else {
            loginTip();
          }
        }
      }
    });
    //登录后的变化
    username = (TextView) getActivity().findViewById(R.id.id_text);
    age = (TextView) getActivity().findViewById(R.id.age_text);
    sexIcon = (ImageView) getActivity().findViewById(R.id.sex_icon);
    resume = (TextView) getActivity().findViewById(R.id.personal_intro);
    settings = (LinearLayout) getActivity().findViewById(R.id.settings);

    settings.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getActivity(), SettingActivity.class);
        startActivity(intent);
      }
    });
  }

  @Override
  public void onResume() {
    super.onResume();
    //如果缓存中有登录后的用户信息
    userInfo = User.getCurrentUser(User.class);
    if (userInfo != null) {
      //从Bmob得到用户头像
      if (userInfo.getUserIcon() != null) {
        Glide.with(this).load(userInfo.getUserIcon().getFileUrl()).centerCrop().into(head);
      } else {
        head.setImageResource(R.drawable.ic_account_circle_white_24dp);
      }
      //判断真实姓名是否为空
      if (userInfo.getRealName() == null) {
        username.setText("请编辑个人信息");
      } else {
        username.setText(userInfo.getRealName());
      }
      //判断年龄是否为空
      if (userInfo.getAge() == null) {
        age.setVisibility(View.GONE);
      } else {
        age.setText(userInfo.getAge().toString());
        age.setVisibility(View.VISIBLE);
      }
      //判断性别是否为空
      if (userInfo.getSex() == null) {
        sexIcon.setVisibility(View.GONE);
      } else {
        if (userInfo.getSex()) {
          sexIcon.setImageResource(R.drawable.male_pic);
          sexIcon.setVisibility(View.VISIBLE);
        } else {
          sexIcon.setImageResource(R.drawable.female_pic);
          sexIcon.setVisibility(View.VISIBLE);
        }
      }
      //判断个人简介是否为空
      if (userInfo.getResume() == null) {
        resume.setVisibility(View.GONE);
      } else {
        resume.setText(userInfo.getResume());
        resume.setVisibility(View.VISIBLE);
      }
      message.setVisibility(View.VISIBLE);
      edit.setVisibility(View.VISIBLE);
    } else {
      head.setImageResource(R.drawable.ic_account_circle_white_24dp);
      username.setText("请点击头像登录");
      sexIcon.setVisibility(View.GONE);
      age.setVisibility(View.GONE);
      resume.setVisibility(View.GONE);
      message.setVisibility(View.GONE);
      edit.setVisibility(View.GONE);
    }

    checkNewMessages();
  }

  private void initPersonalMsg() {
    PersonalMsg personalMsg1 = new PersonalMsg(R.drawable.ic_folder_add, "我创建的行程", R.drawable.ic_chevron_right_black_24dp);
    personalMsgList.add(personalMsg1);
    PersonalMsg personalMsg2 = new PersonalMsg(R.drawable.ic_folder_check, "我加入的行程", R.drawable.ic_chevron_right_black_24dp);
    personalMsgList.add(personalMsg2);
    PersonalMsg personalMsg3 = new PersonalMsg(R.drawable.ic_vote_up, "我的评价", R.drawable.ic_chevron_right_black_24dp);
    personalMsgList.add(personalMsg3);
  }

  private void loginTip() {
    Toast toast = Toast.makeText(getActivity(), "请先登录哈", Toast.LENGTH_SHORT);
    toast.show();
  }

  private void checkNewMessages() {
    final TransitionDrawable bell = (TransitionDrawable) message.getDrawable();
    BmobQuery<PushMessage> query = new BmobQuery<>();
    query.addWhereEqualTo("receiver", User.getCurrentUser(User.class));
    query.addWhereEqualTo("isHandled", 0);
    query.count(PushMessage.class, new CountListener() {
      @Override
      public void done(Integer integer, BmobException e) {
        if (e == null) {
          if (integer > 0) {
            Log.d("checkNewMessages", integer.toString() + ", " + "正在加载动画" + "; Level: " + bell.getLevel());
            bell.setCrossFadeEnabled(true);
            if (!hasNewMessage) {
              bell.startTransition(500);
              hasNewMessage = true;
            }
            message.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.message_shake));
          } else {
            Log.d("checkNewMessages", integer.toString() + ", " + "取消加载动画" + "; Level: " + bell.getLevel());
            if (hasNewMessage) {
              bell.reverseTransition(500);
              hasNewMessage = false;
            }
            message.clearAnimation();
          }
        } else {
          bell.reverseTransition(500);
          message.clearAnimation();
          Log.d("checkNewMessages", e.getErrorCode() + ", " + e.getMessage());
        }
      }
    });
  }
}