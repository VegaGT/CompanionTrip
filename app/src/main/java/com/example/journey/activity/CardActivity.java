package com.example.journey.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.journey.R;
import com.example.journey.model.Post;
import com.example.journey.model.PushMessage;
import com.example.journey.model.User;
import com.example.journey.util.SizeUtils;
import com.example.journey.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.listener.ConversationListener;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.PushListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 高天 on 2016/8/20.
 */

public class CardActivity extends Activity {

  private String postID;
  private String authorID;
  private User user;
  private Post thisPost;

  private boolean isJoined = false;

  private boolean showJoinButton = false;

  private NestedScrollView nestedScrollView;
  private ImageButton backButton;
  private ImageButton shareButton;
  private CircleImageView avatarView;
  private TextView usernameText;
  private TextView personalIntroText;
  private TextView timeText;
  private TextView titleText;
  private TextView dateText;
  private TextView locationText;
  private TextView joinedPeopleNumText;
  private TextView descriptionText;
  private ToggleButton joinButton;
  private RelativeLayout joinLayout;
  private HorizontalScrollView imageScroll;
  private LinearLayout imageLinear;
  private TextView sendMessage;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_card);

    backButton = (ImageButton) findViewById(R.id.back);
    shareButton = (ImageButton) findViewById(R.id.share);
    nestedScrollView = (NestedScrollView) findViewById(R.id.scroll_view);
    avatarView = (CircleImageView) findViewById(R.id.avatar);
    usernameText = (TextView) findViewById(R.id.username);
    personalIntroText = (TextView) findViewById(R.id.personal_intro);
    timeText = (TextView) findViewById(R.id.time);
    titleText = (TextView) findViewById(R.id.title);
    dateText = (TextView) findViewById(R.id.date_text);
    locationText = (TextView) findViewById(R.id.location_text);
    joinedPeopleNumText = (TextView) findViewById(R.id.joined_number_text);
    descriptionText = (TextView) findViewById(R.id.content);
    joinButton = (ToggleButton) findViewById(R.id.join);
    joinLayout = (RelativeLayout) findViewById(R.id.join_layout);
    imageScroll = (HorizontalScrollView) findViewById(R.id.image_scroll);
    imageLinear = (LinearLayout) findViewById(R.id.image_linear);
    sendMessage = (TextView)findViewById(R.id.send_message_btn);

    nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
      @Override
      public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        int dy = scrollY - oldScrollY;
        if ((dy < 0 || (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())))
                && showJoinButton)
          showJoinButton();
        else if (dy > 0) hideJoinButton();
      }
    });

    ImageButton backButton = (ImageButton) findViewById(R.id.back);
    ImageButton shareButton = (ImageButton) findViewById(R.id.share);
    backButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        finish();
      }
    });
    shareButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        share();
      }
    });

    joinButton.setChecked(false);

    Intent intent = getIntent();
    postID = intent.getStringExtra("postID");

    BmobQuery<Post> query = new BmobQuery<Post>();
    query.include("author");
    query.getObject(postID, new QueryListener<Post>() {
      @Override
      public void done(final Post object, BmobException e) {
        if (e == null) {
          loadPost(object);
        } else {
          Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
          Toast toast = Toast.makeText(CardActivity.this, "报错啦！！" + e.getMessage() + e.getErrorCode(), Toast.LENGTH_SHORT);
          toast.show();
        }
      }
    });

    avatarView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent();
        intent.setClass(CardActivity.this, OtherPeople.class);
        intent.putExtra("head", user.getUserIcon().getUrl());
        intent.putExtra("name", user.getRealName());
        intent.putExtra("sex", user.getSex());
        intent.putExtra("age", user.getAge());
        intent.putExtra("info", user.getResume());
        intent.putExtra("user", user.getObjectId());
        startActivity(intent);
      }
    });

    joinButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        final User currentUser = User.getCurrentUser(User.class);
        if (currentUser != null) {  //如果用户已经登录
          if (!joinButton.isChecked()) { // 如果已经加入
            joinButton.setChecked(true);
            if (!thisPost.getIsDone()) {
              quit();
            } else {
              Toast.makeText(CardActivity.this, "行程已结束，不能退出", Toast.LENGTH_SHORT).show();
            }
          } else { // 如果还没加入
            joinButton.setChecked(false);
            join();
          }
        } else {
          Toast.makeText(CardActivity.this, "请先登录哈", Toast.LENGTH_SHORT).show();
        }
      }
    });

    sendMessage.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        BmobIMUserInfo info = new BmobIMUserInfo(user.getObjectId(),user.getRealName(),user.getUserIcon().getUrl());
        BmobIM.getInstance().startPrivateConversation(info, new ConversationListener() {
          @Override
          public void done(BmobIMConversation c, BmobException e) {
            if(e==null){
              //在此跳转到聊天页面
              //Bundle bundle = new Bundle();
              //bundle.putSerializable("c", c);
              Intent intent1 = new Intent(CardActivity.this,ChatActivity.class);
              //intent1.putExtra("bundle", bundle);
              intent1.putExtra("c", c);
              startActivity(intent1);
            }else{
              Log.d("failed",e.getMessage()+"("+e.getErrorCode()+")");
            }
          }
        });
      }
    });
  }



  private void loadPost(Post object) {
    user = object.getAuthor();
    authorID = user.getObjectId();
    thisPost = object;

    Glide.with(CardActivity.this).load(object.getAuthor().getUserIcon().getUrl()).centerCrop().into(avatarView);

    String realName = object.getAuthor().getRealName();
    SpannableString ss = new SpannableString(realName
            + " " + object.getAuthor().getAge().toString());
    Drawable d;
    if (object.getAuthor().getSex()) {
      d = ContextCompat.getDrawable(CardActivity.this, R.drawable.male_pic);
    } else {
      d = ContextCompat.getDrawable(CardActivity.this, R.drawable.female_pic);
    }
    d.setBounds(0, 0, usernameText.getLineHeight(), usernameText.getLineHeight());
    ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
    ss.setSpan(span, realName.length(), realName.length() + 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
    usernameText.setText(ss);

    personalIntroText.setText(object.getAuthor().getResume());
    timeText.setText(StringUtils.formatTime(object.getCreatedAt()));

    titleText.setText(object.getTitle());
    descriptionText.setText(object.getDescription());

    List<String> imagePaths = new ArrayList<String>();
    for (BmobFile file : object.getImages()) {
      imagePaths.add(file.getFileUrl());
    }
    loadImagesByPaths(imagePaths);

    String dateString = StringUtils.formatDates(object.getStartDate(), object.getEndDate());
    dateText.setText(dateString);

    String locationString = StringUtils.formatLocation(object.getProvince(), object.getCity());
    locationText.setText(locationString);

    joinedPeopleNumText.setText(object.getJoinedNumber() + "/" + object.getNumber());

    if (!object.getIsDone() &&
            User.getCurrentUser(User.class) != null &&
            !User.getCurrentUser(User.class).getObjectId().equals(user.getObjectId())) {
      showJoinButton = true;
      joinLayout.setVisibility(View.VISIBLE);
    } else {
      showJoinButton = false;
      joinLayout.setVisibility(View.GONE);
      LinearLayout layout = (LinearLayout) findViewById(R.id.content_linear);
      layout.setPadding(0, 0, 0, 0);
    }

    //查询当前用户是否加入了该行程
    BmobQuery<User> query2 = new BmobQuery<User>();
    Post post = new Post();
    post.setObjectId(postID);
    query2.addWhereRelatedTo("joinedPeople", new BmobPointer(post));
    query2.findObjects(new FindListener<User>() {
      @Override
      public void done(List<User> object, BmobException e) {
        if (e == null) {
          for (int i = 0; i < object.size(); i++) {
            if (object.get(i).getObjectId().equals(User.getCurrentUser(User.class).getObjectId())) {
              isJoined = true;
              joinButton.setChecked(true);
              joinLayout.setVisibility(View.VISIBLE);
            } else {
              joinButton.setChecked(false);
            }
          }
        } else {
          Log.i("bmob", "失败：" + e.getMessage());
        }
      }

    });
  }

  private void loadImagesByPaths(List<String> imagesPaths) {

    if (imagesPaths.size() > 0) {
      imageScroll.setVisibility(View.VISIBLE);

      if (imageLinear.getChildCount() > 0) imageLinear.removeAllViews();

      for (final String picPath : imagesPaths) {
        final ImageView imageView = new ImageView(this);

        int paramHeight = SizeUtils.dp2px(this, 196);
        int paramWidth = SizeUtils.dp2px(this, 196);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(paramWidth, paramHeight);
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setPadding(4, 4, 4, 4);

        Glide.with(this)
                .load(picPath)
                .into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            Pair<View, String> p1 = Pair.create(view, "image_transition");
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(CardActivity.this, p1);
            FullImageActivity.actionStart(CardActivity.this, options, picPath);
          }
        });

        imageLinear.addView(imageView);
      }

    } else {
      imageScroll.setVisibility(View.GONE);
    }
  }

  private void quit() {

    final User currentUser = User.getCurrentUser(User.class);

    AlertDialog.Builder dialog = new AlertDialog.Builder(CardActivity.this);
    LayoutInflater factory = LayoutInflater.from(CardActivity.this);
    final View textEntryView = factory.inflate(R.layout.add_extra_content, null);

    final EditText user_defined_tag = (EditText) textEntryView.findViewById(R.id.user_defined_tag);
    final TextView user_tag_word_name = (TextView) textEntryView.findViewById(R.id.user_tag_word_num);//字数
    user_defined_tag.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void afterTextChanged(Editable editable) {
        user_tag_word_name.setText(String.valueOf(editable.toString().length()));
      }
    });

    dialog.setTitle("请输入退出原因");
    dialog.setView(textEntryView);

    dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        //子布局中的控件
        if (user_defined_tag.getText().length() > 0) {
          //删除多对多关系
          BmobRelation relation = new BmobRelation();
          relation.remove(currentUser);
          thisPost.setJoinedPeople(relation);
          thisPost.setJoinedNumber(thisPost.getJoinedNumber() - 1);
          thisPost.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
              if (e == null) {
                Toast.makeText(CardActivity.this, "已退出该行程", Toast.LENGTH_SHORT).show();
                finish();
              } else {
                Toast.makeText(CardActivity.this, "退出失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
              }
            }

          });

          //创建消息表的一行
          PushMessage pushMessage = new PushMessage();
          pushMessage.setPusher(currentUser);
          pushMessage.setReceiver(user);
          pushMessage.setContent("已经退出了您的行程");
          pushMessage.setExtraContent(user_defined_tag.getText().toString());
          pushMessage.setHandled(0);
          pushMessage.setType(1);
          pushMessage.setPost(thisPost);
          pushMessage.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
              if (e == null) {
                Toast toast = Toast.makeText(getApplicationContext(), "发送消息成功！", Toast.LENGTH_SHORT);
                toast.show();
              } else {
                Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
              }
            }
          });
          finish();

        } else {
          Toast.makeText(CardActivity.this, "无附加信息", Toast.LENGTH_SHORT).show();
        }
      }
    });
    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
      }
    });
    dialog.show();
  }

  private void join() {

    final User currentUser = User.getCurrentUser(User.class);

    AlertDialog.Builder dialog = new AlertDialog.Builder(CardActivity.this);
    LayoutInflater factory = LayoutInflater.from(CardActivity.this);
    final View textEntryView = factory.inflate(R.layout.add_extra_content, null);

    final EditText user_defined_tag = (EditText) textEntryView.findViewById(R.id.user_defined_tag);
    final TextView user_tag_word_name = (TextView) textEntryView.findViewById(R.id.user_tag_word_num);//字数
    user_defined_tag.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void afterTextChanged(Editable editable) {
        user_tag_word_name.setText(String.valueOf(editable.toString().length()));
      }
    });

    dialog.setTitle("请输入附加信息");
    dialog.setView(textEntryView);

    dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        //子布局中的控件
        if (user_defined_tag.getText().length() > 0) {
          //创建消息表的一行
          PushMessage pushMessage = new PushMessage();
          pushMessage.setPusher(currentUser);
          pushMessage.setReceiver(user);
          pushMessage.setContent("申请加入您的行程");
          pushMessage.setExtraContent(user_defined_tag.getText().toString());
          pushMessage.setHandled(0);
          pushMessage.setType(0);
          pushMessage.setPost(thisPost);
          pushMessage.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
              if (e == null) {
                Toast toast = Toast.makeText(getApplicationContext(), "发送消息成功！", Toast.LENGTH_SHORT);
                toast.show();
              } else {
                Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
              }
            }
          });
          finish();

        } else {
          Toast.makeText(CardActivity.this, "无附加信息", Toast.LENGTH_SHORT).show();
        }
      }
    });
    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
      }
    });
    dialog.show();
  }

  private void showJoinButton() {
    if (joinLayout.getVisibility() == View.GONE) {
      Animation inBottom = AnimationUtils.loadAnimation(this, R.anim.in_bottom_accelerate);

      inBottom.setAnimationListener(new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
          joinLayout.setVisibility(View.VISIBLE);
        }

        @Override
        public void onAnimationEnd(Animation animation) {
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
      });
      joinLayout.startAnimation(inBottom);
    }
  }

  private void hideJoinButton() {
    if (joinLayout.getVisibility() == View.VISIBLE) {
      Animation outBottom = AnimationUtils.loadAnimation(this, R.anim.out_bottom_accelerate);

      outBottom.setAnimationListener(new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
          joinLayout.setVisibility(View.GONE);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
      });
      joinLayout.startAnimation(outBottom);
    }
  }

  public void share() {
    Glide.with(getApplicationContext())
            .load(thisPost.getImages().get(0).getFileUrl())
            .asBitmap()
            .into(new SimpleTarget<Bitmap>() {
              @Override
              public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Uri uri = getLocalBitmapUri(resource);
                shareMsg("分享到",
                        thisPost.getTitle(),
                        thisPost.getDescription()
                                + thisPost.getImages().get(0).getFileUrl(), uri.toString());
              }
            });
  }

  /**
   * 分享功能
   *
   * @param activityTitle Activity的名字
   * @param msgTitle      消息标题
   * @param msgText       消息内容
   * @param imgPath       图片路径，不分享图片则传null
   */
  public void shareMsg(String activityTitle, String msgTitle, String msgText,
                       String imgPath) {
    Intent intent = new Intent(Intent.ACTION_SEND);
    if (imgPath == null || imgPath.equals("")) {
      intent.setType("text/plain"); // 纯文本
    } else {
      intent.setType("image/jpg");
      intent.putExtra(Intent.EXTRA_STREAM, imgPath);
    }
    intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
    intent.putExtra(Intent.EXTRA_TEXT, msgText);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(Intent.createChooser(intent, activityTitle));
  }

  public Uri getLocalBitmapUri(Bitmap bmp) {
    Uri bmpUri = null;
    try {
      File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
      FileOutputStream out = new FileOutputStream(file);
      bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
      out.close();
      bmpUri = Uri.fromFile(file);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return bmpUri;
  }

}
