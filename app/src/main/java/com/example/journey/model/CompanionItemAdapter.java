package com.example.journey.model;

import android.content.Context;
import android.util.Log;
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
 * Created by Li on 2016/8/24.
 */
public class CompanionItemAdapter extends ArrayAdapter<CompanionItem> {
  private int resourceId;

  public CompanionItemAdapter(Context context, int textViewResourceId, List<CompanionItem> objects) {
    super(context, textViewResourceId, objects);
    resourceId = textViewResourceId;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    CompanionItem companionItem = getItem(position);
    Log.d("CompanionItem", String.valueOf(position) + ", " + companionItem.getName());
    View view;
    ViewHolder viewHolder;
    if (convertView == null) {
      view = LayoutInflater.from(getContext()).inflate(resourceId, null);
      viewHolder = new ViewHolder();
      viewHolder.portrait_icon = (ImageView) view.findViewById(R.id.companion_portrait);
      viewHolder.name = (TextView) view.findViewById(R.id.companion_name);
      viewHolder.textView = (TextView) view.findViewById(R.id.rateTextView);
      viewHolder.enterRate = (ImageView) view.findViewById(R.id.enter_rate);
      view.setTag(viewHolder);
    } else {
      view = convertView;
      viewHolder = (ViewHolder) view.getTag();
    }
    Glide.with(this.getContext()).load(companionItem.getPortrait_icon()).centerCrop().into(viewHolder.portrait_icon);
    viewHolder.name.setText(companionItem.getName());
    if (companionItem.getFlag()) {
      viewHolder.textView.setText("已评价");
      viewHolder.enterRate.setVisibility(View.INVISIBLE);
    } else {
      viewHolder.textView.setText("评价他 / 她");
      viewHolder.enterRate.setVisibility(View.VISIBLE);
    }
    return view;
  }

  class ViewHolder {
    ImageView portrait_icon;
    TextView name;
    TextView textView;
    ImageView enterRate;
  }
}
