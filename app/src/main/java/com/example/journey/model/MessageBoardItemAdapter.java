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
 * Created by Li on 2016/8/25.
 */
public class MessageBoardItemAdapter extends ArrayAdapter<MessageBoardItem> {
    private int resourceId;

    public MessageBoardItemAdapter(Context context, int textViewResourceId, List<MessageBoardItem> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        MessageBoardItem messageBoardItem = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder = new ViewHolder();
            viewHolder.portrait_icon = (ImageView) view.findViewById(R.id.reviewer_portrait);
            viewHolder.name = (TextView) view.findViewById(R.id.reviewer_name);
            viewHolder.date = (TextView)view.findViewById(R.id.message_board_date);
            viewHolder.comment = (TextView)view.findViewById(R.id.comment_content);
            viewHolder.post_from = (TextView)view.findViewById(R.id.post_from);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }
        Glide.with(this.getContext()).load(messageBoardItem.getPortait_icon()).centerCrop().into(viewHolder.portrait_icon);
        viewHolder.name.setText(messageBoardItem.getName());
        viewHolder.date.setText(messageBoardItem.getDate());
        viewHolder.comment.setText(messageBoardItem.getComment());
        viewHolder.post_from.setText(messageBoardItem.getPost_from());
        return view;
    }

    class ViewHolder{
        ImageView portrait_icon;
        TextView name;
        TextView date;
        TextView comment;
        TextView post_from;
    }
}