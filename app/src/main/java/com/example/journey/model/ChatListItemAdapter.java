package com.example.journey.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.journey.R;

import java.util.List;

/**
 * Created by Li on 2016/11/28.
 */
public class ChatListItemAdapter extends ArrayAdapter<ChatListItem> {
    private int resourceId;
    private Context C;

    public ChatListItemAdapter(Context context, int textViewResourceId, List<ChatListItem> objects){
        super(context,textViewResourceId,objects);
        C = context;
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        ChatListItem chatListItem = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder = new ViewHolder();
            viewHolder.portrait = (ImageView)view.findViewById(R.id.chat_item_portrait);
            viewHolder.name = (TextView)view.findViewById(R.id.chat_item_name);
            viewHolder.content = (TextView)view.findViewById(R.id.chat_item_content);
            viewHolder.num = (TextView)view.findViewById(R.id.num_not_read);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }
        Glide.with(this.getContext()).load(chatListItem.getChat_item_portrait()).centerCrop().into(viewHolder.portrait);
        viewHolder.name.setText(chatListItem.getChat_item_portrait());
        viewHolder.content.setText(chatListItem.getChat_item_content());
        viewHolder.date.setText(chatListItem.getChat_item_date());
        viewHolder.num.setText(chatListItem.getNum_not_read());
        if("+0".equals(chatListItem.getNum_not_read())){
            viewHolder.num.setVisibility(View.INVISIBLE);
        }
        return view;
    }

    class ViewHolder{
        ImageView portrait;
        TextView name;
        TextView content;
        TextView date;
        TextView num;
    }
}
