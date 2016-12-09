package com.example.journey.model;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.journey.R;

import java.text.SimpleDateFormat;

import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;

/**
 * Created by Li on 2016/12/2.
 */
public class ReceiveTextHolder extends BaseViewHolder {

    protected ImageView iv_avatar;
    protected TextView tv_time;
    protected TextView tv_message;

    public ReceiveTextHolder(Context context, ViewGroup root, OnRecyclerViewListener onRecyclerViewListener) {
        super(context, root, R.layout.chat_item,onRecyclerViewListener);
        iv_avatar = (ImageView)itemView.findViewById(R.id.chat_room_icon);
        tv_time = (TextView)itemView.findViewById(R.id.chat_room_content_time);
        tv_message = (TextView)itemView.findViewById(R.id.chat_room_content);
    }


    @Override
    public void bindData(Object o) {
        final BmobIMMessage message = (BmobIMMessage)o;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        String time = dateFormat.format(message.getCreateTime());
        tv_time.setText(time);
        final BmobIMUserInfo info = message.getBmobIMUserInfo();
        if (info == null) Log.d("Receive", "info is null");
        else Log.d("Receive", "info is not null");
        //Glide.with(iv_avatar.getContext()).load(info.getAvatar()).centerCrop().into(iv_avatar);
        String content =  message.getContent();
        tv_message.setText(content);
        iv_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"点击" + info.getName() + "的头像",Toast.LENGTH_SHORT).show();
            }
        });
        tv_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"点击"+message.getContent(),Toast.LENGTH_SHORT).show();
                if(onRecyclerViewListener!=null){
                    onRecyclerViewListener.onItemClick(getAdapterPosition());
                }
            }
        });

        tv_message.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onRecyclerViewListener != null) {
                    onRecyclerViewListener.onItemLongClick(getAdapterPosition());
                }
                return true;
            }
        });
    }

    public void showTime(boolean isShow) {
        tv_time.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }
}
