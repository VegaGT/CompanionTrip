package com.example.journey.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.journey.R;
import com.example.journey.model.ProvinceAndCity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ChooseCityActivity extends AppCompatActivity {

  final List<String> ProvinceList = new ArrayList<>();
  List<ProvinceAndCity> Province = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chose_city);

    TextView title = (TextView) findViewById(R.id.title_text);
    title.setText("选择省份");
    ListView lv = (ListView) findViewById(R.id.choose_listView);
    query(lv);

    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      /**
       *
       * @param parent 当前ListView
       * @param view 代表当前被点击的条目
       * @param position 当前条目的位置
       * @param id 当前被点击的条目的id
       */
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.setClass(ChooseCityActivity.this, ChooseCityActivity2.class);
        intent.putExtra("provinceNumber", position);
        //把选择的省的城市的list传到下一个activity
        intent.putStringArrayListExtra("Province", (ArrayList<String>) Province.get(position).getCity());
        startActivity(intent);
      }
    });

  }

  public void query(final ListView listView) {
    BmobQuery<ProvinceAndCity> query = new BmobQuery<ProvinceAndCity>();
    //返回50条数据，如果不加上这条语句，默认返回10条数据
    query.setLimit(50);
    //执行查询方法
    query.findObjects(new FindListener<ProvinceAndCity>() {
      @Override
      public void done(List<ProvinceAndCity> object, BmobException e) {
        if (e == null) {
          Province = object;
          for (int i = 0; i < object.size(); i++) {
            ProvinceList.add(object.get(i).getProvince());
          }
          Toast toast = Toast.makeText(getApplicationContext(), "查询成功：共" + ProvinceList.size() + "条数据。", Toast.LENGTH_SHORT);
          toast.show();
          listView.setAdapter(new ArrayAdapter<String>(ChooseCityActivity.this, android.R.layout.simple_list_item_1, ProvinceList));
        } else {
          Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
        }
      }
    });
  }

  public void back(View view) {
    finish();
  }
}
