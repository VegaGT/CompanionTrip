package com.example.journey.model;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.journey.activity.OtherPeople;
import com.example.journey.R;

import java.util.List;

import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Li on 2016/9/13.
 */
public class TripMessageItemAdapter extends ArrayAdapter<TripMessageItem> {
  private int resourceId;
  private Context C;

  public TripMessageItemAdapter(Context context, int textViewResourceId, List<TripMessageItem> objects) {
    super(context, textViewResourceId, objects);
    resourceId = textViewResourceId;
    C = context;
  }

  @Override
  public int getViewTypeCount() {
    return 3;
  }

  @Override
  public int getItemViewType(int position) {
    return getItem(position).getIsHandled();
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    final TripMessageItem tripMessageItem = getItem(position);
    View view;
    final ViewHolder viewHolder;
    if (convertView == null) {
      view = LayoutInflater.from(getContext()).inflate(resourceId, null);
      viewHolder = new ViewHolder();
      viewHolder.message_requestor = (TextView) view.findViewById(R.id.message_requester);
      viewHolder.content = (TextView) view.findViewById(R.id.request_content);
      viewHolder.status = (TextView) view.findViewById(R.id.status);
      viewHolder.additional_content = (TextView) view.findViewById(R.id.additional_content);
      viewHolder.trip_message_portrait = (ImageView) view.findViewById(R.id.trip_message_portrait);
      viewHolder.trip_message_sex = (ImageView) view.findViewById(R.id.trip_message_sex);
      viewHolder.trip_message_age = (TextView) view.findViewById(R.id.trip_message_age);
      viewHolder.receive = (TextView) view.findViewById(R.id.trip_message_receive);
      viewHolder.reject = (TextView) view.findViewById(R.id.trip_message_reject);
      view.setTag(viewHolder);
    } else {
      view = convertView;
      viewHolder = (ViewHolder) view.getTag();
    }

    viewHolder.message_requestor.setText(tripMessageItem.getMessage_requestor());
    viewHolder.content.setText(tripMessageItem.getContent());
    viewHolder.additional_content.setText(tripMessageItem.getAdditional_content());
    Glide.with(getContext()).load(tripMessageItem.getTrip_message_portrait()).centerCrop().into(viewHolder.trip_message_portrait);
    //viewHolder.trip_message_portrait.setImageResource(tripMessageItem.getTrip_message_portrait());
    viewHolder.trip_message_sex.setImageResource(tripMessageItem.getTrip_message_sex());
    viewHolder.trip_message_age.setText(tripMessageItem.getTrip_message_age());

    if (tripMessageItem.getType() == 0) { //如果类型是申请加入
      if (tripMessageItem.getIsHandled() == 0) {  //如果没处理
//        Toast toast = Toast.makeText(getContext(), "没处理" + tripMessageItem.getAdditional_content(), Toast.LENGTH_SHORT);
//        toast.show();
        viewHolder.receive.setVisibility(View.VISIBLE);
        viewHolder.reject.setVisibility(View.VISIBLE);
      } else if (tripMessageItem.getIsHandled() == 1) {
        viewHolder.status.setText("已同意");
        viewHolder.receive.setVisibility(View.GONE);
        viewHolder.reject.setVisibility(View.GONE);
      } else if (tripMessageItem.getIsHandled() == 2) {
        viewHolder.status.setText("已拒绝");
//        Toast toast = Toast.makeText(getContext(), "已拒绝" + tripMessageItem.getAdditional_content(), Toast.LENGTH_SHORT);
//        toast.show();
        viewHolder.receive.setVisibility(View.GONE);
        viewHolder.reject.setVisibility(View.GONE);
      }
    } else { //如果是退出行程
      viewHolder.receive.setVisibility(View.GONE);
      viewHolder.reject.setVisibility(View.GONE);
    }
    viewHolder.receive.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Post thisPost = tripMessageItem.getPost();
        User user = tripMessageItem.getPusher();
        int joinedPeople = tripMessageItem.getJoinedPeople();
//        Toast toast1 = Toast.makeText(getContext(), tripMessageItem.getJoinedPeople() + " " + tripMessageItem.getMaxPeople(), Toast.LENGTH_SHORT);
//        toast1.show();
        //if (tripMessageItem.getJoinedPeople() < tripMessageItem.getMaxPeople()) {

        //将当前用户添加到Post表中的joinedPeople字段值中，表明当前用户申请加入
        BmobRelation relation = new BmobRelation();
        //将当前用户添加到多对多关联中
        relation.add(user);
        thisPost.setJoinedPeople(relation);
        thisPost.setJoinedNumber(joinedPeople + 1); //已加入人数+1
        thisPost.update(new UpdateListener() {
          @Override
          public void done(BmobException e) {
            if (e == null) {
              PushMessage pushMessage = new PushMessage();
              pushMessage.setHandled(1);
              pushMessage.update(tripMessageItem.getId(), new UpdateListener() {
                @Override
                public void done(BmobException e) {
                  if (e == null) {
                    Log.i("bmob", "更新成功");
                    Toast toast = Toast.makeText(getContext(), "加入成功！", Toast.LENGTH_SHORT);
                    toast.show();
                    viewHolder.receive.setVisibility(View.GONE);
                    viewHolder.reject.setVisibility(View.GONE);
                    viewHolder.status.setText("已同意");
                  } else {
                    Log.i("bmob", "更新失败：" + e.getMessage() + "," + e.getErrorCode());
                  }
                }
              });
            } else {
              Toast toast = Toast.makeText(getContext(), "失败：" + e.getMessage(), Toast.LENGTH_SHORT);
              toast.show();
            }
          }

        });
      }
    });
    //拒绝按钮添加事件
    viewHolder.reject.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        PushMessage pushMessage = new PushMessage();
        pushMessage.setHandled(2);
        pushMessage.update(tripMessageItem.getId(), new UpdateListener() {
          @Override
          public void done(BmobException e) {
            if (e == null) {
              viewHolder.status.setText("已拒绝");
              viewHolder.receive.setVisibility(View.GONE);
              viewHolder.reject.setVisibility(View.GONE);
              Log.i("bmob", "更新成功");
            } else {
              Log.i("bmob", "更新失败：" + e.getMessage() + "," + e.getErrorCode());
            }
          }
        });
      }
    });

    viewHolder.trip_message_portrait.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent();
        intent.setClass(getContext(), OtherPeople.class);
        intent.putExtra("head", tripMessageItem.getTrip_message_portrait());
        intent.putExtra("name", tripMessageItem.getMessage_requestor());
        intent.putExtra("sex", tripMessageItem.getTrip_message_sex());
        intent.putExtra("age", Integer.valueOf(tripMessageItem.getTrip_message_age()));
        intent.putExtra("info", tripMessageItem.getResume());
        intent.putExtra("user", tripMessageItem.getUserID());
        getContext().startActivity(intent);
      }
    });

    return view;
  }

  class ViewHolder {
    TextView message_requestor;
    TextView additional_content;
    ImageView trip_message_portrait;
    ImageView trip_message_sex;
    TextView trip_message_age;
    TextView receive;
    TextView reject;
    TextView content;
    TextView status;
  }
}
