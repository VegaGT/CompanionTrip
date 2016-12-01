package com.example.journey.model;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.journey.R;
import com.example.journey.activity.CompanionNewActivity;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Li on 2016/8/23.
 */
public class CreatedPostingItemAdapter extends ArrayAdapter<PostingItem> {
  private int resourceId;
  private Context C;

  public CreatedPostingItemAdapter(Context context, int textViewResourceId, List<PostingItem> objects) {
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
    if (getItem(position).getIsDone())
      return 0;
    else
      return 1;
  }

  @Override
  public View getView(final int position, View convertView, ViewGroup parent) {
    PostingItem postingItem = getItem(position);
    View view;
    ViewHolder viewHolder;
    if (convertView == null) {
      view = LayoutInflater.from(getContext()).inflate(resourceId, null);
      viewHolder = new ViewHolder();
      viewHolder.portrait_icon = (ImageView) view.findViewById(R.id.posting_portrait);
      viewHolder.name = (TextView) view.findViewById(R.id.posting_name);
      viewHolder.sex_icon = (ImageView) view.findViewById(R.id.posting_sex);
      viewHolder.post_content = (TextView) view.findViewById(R.id.posting_content);
      viewHolder.keyword0 = (TextView) view.findViewById(R.id.posting_keyword0);
      viewHolder.keyword1 = (TextView) view.findViewById(R.id.posting_keyword1);
      viewHolder.keyword2 = (TextView) view.findViewById(R.id.posting_keyword2);
      viewHolder.people_number = (TextView) view.findViewById(R.id.posting_number);
      viewHolder.date = (TextView) view.findViewById(R.id.posting_date);
      viewHolder.posting_complished = (Button) view.findViewById(R.id.posting_accomplished);
      viewHolder.background = (ImageView) view.findViewById(R.id.posting_background);
      view.setTag(viewHolder);
    } else {
      view = convertView;
      viewHolder = (ViewHolder) view.getTag();
    }
    Glide.with(this.getContext()).load(postingItem.getPortrait_icon()).centerCrop().into(viewHolder.portrait_icon);
    //viewHolder.portrait_icon.setImageResource(postingItem.getPortrait_icon());
    viewHolder.name.setText(postingItem.getName());
    viewHolder.sex_icon.setImageResource(postingItem.getSex_icon());
    viewHolder.post_content.setText(postingItem.getPost_content());
    viewHolder.keyword0.setText(postingItem.getKeyword0());
    viewHolder.keyword1.setText(postingItem.getKeyword1());
    viewHolder.keyword2.setText(postingItem.getKeyword2());
    viewHolder.people_number.setText(postingItem.getPeople_number());
    viewHolder.date.setText(postingItem.getDate());
    Glide.with(this.getContext()).load(postingItem.getBackground()).into(viewHolder.background);
    if ("".equals(viewHolder.keyword0.getText())) {
      viewHolder.keyword0.setVisibility(View.GONE);
    } else {
      viewHolder.keyword0.setVisibility(View.VISIBLE);
    }
    if ("".equals(viewHolder.keyword1.getText())) {
      viewHolder.keyword1.setVisibility(View.GONE);
    } else {
      viewHolder.keyword1.setVisibility(View.VISIBLE);
    }
    if ("".equals(viewHolder.keyword2.getText())) {
      viewHolder.keyword2.setVisibility(View.GONE);
    } else {
      viewHolder.keyword2.setVisibility(View.VISIBLE);
    }
    viewHolder.posting_complished.setVisibility(View.VISIBLE);
    if (postingItem.getIsDone()) {
      viewHolder.posting_complished.setText("评价");
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        viewHolder.posting_complished.setBackground(ContextCompat.getDrawable(viewHolder.posting_complished.getContext(), R.drawable.tag_bg_white));
        viewHolder.posting_complished.setTextColor(ContextCompat.getColor(viewHolder.posting_complished.getContext(), R.color.colorPrimary));
      }
    } else {
      viewHolder.posting_complished.setText("完成");
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        viewHolder.posting_complished.setBackground(ContextCompat.getDrawable(viewHolder.posting_complished.getContext(), R.drawable.tag_bg_primary));
        viewHolder.posting_complished.setTextColor(ContextCompat.getColor(viewHolder.posting_complished.getContext(), R.color.white));
      }
    }
    //添加帖子完成的事件
    viewHolder.posting_complished.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        BmobQuery<Post> query = new BmobQuery<Post>();
        query.getObject(getItem(position).getPostID(), new QueryListener<Post>() {
          @Override
          public void done(Post object, BmobException e) {
            if (e == null) {
              object.setIsDone(true);
              object.update(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                  if (e == null) {

                  } else {
                    Toast toast = Toast.makeText(getContext(), "失败：" + e.getMessage(), Toast.LENGTH_SHORT);
                    toast.show();
                  }
                }

              });
            } else {
              Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
            }
          }

        });
        Intent intent = new Intent(C.getApplicationContext(), CompanionNewActivity.class);
        intent.putExtra("postID", getItem(position).getPostID());
        C.startActivity(intent);
      }
    });
    return view;
  }

  class ViewHolder {
    ImageView portrait_icon;
    TextView name;
    ImageView sex_icon;
    TextView post_content;
    TextView keyword0;
    TextView keyword1;
    TextView keyword2;
    TextView people_number;
    TextView date;
    ImageView background;
    Button posting_complished;
  }
}