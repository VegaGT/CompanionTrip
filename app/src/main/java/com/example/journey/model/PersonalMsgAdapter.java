package com.example.journey.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.journey.R;

import java.util.List;

/**
 * Created by Li on 2016/8/20.
 */
public class PersonalMsgAdapter extends ArrayAdapter<PersonalMsg> {
  private int resourceId;

  public PersonalMsgAdapter(Context context, int textViewResourceId, List<PersonalMsg> objects) {
    super(context, textViewResourceId, objects);
    resourceId = textViewResourceId;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    PersonalMsg personalMsg = getItem(position);
    View view;
    ViewHolder viewHolder;
    if (convertView == null) {
      view = LayoutInflater.from(getContext()).inflate(resourceId, null);
      viewHolder = new ViewHolder();
      viewHolder.leftRes = (ImageView) view.findViewById(R.id.left_res);
      viewHolder.text1 = (TextView) view.findViewById(R.id.item1);
      viewHolder.rightRes = (ImageView) view.findViewById(R.id.right_res);
      view.setTag(viewHolder);
    } else {
      view = convertView;
      viewHolder = (ViewHolder) view.getTag();
    }
    if (personalMsg.getLeftRes() == 0) {
      viewHolder.leftRes.setVisibility(View.GONE);
    } else {
      viewHolder.leftRes.setVisibility(View.VISIBLE);
      viewHolder.leftRes.setImageResource(personalMsg.getLeftRes());
    }
    viewHolder.text1.setText(personalMsg.getText());
    viewHolder.rightRes.setImageResource(personalMsg.getRightRes());
    return view;
  }

  class ViewHolder {
    ImageView leftRes;
    TextView text1;
    ImageView rightRes;
  }
}
