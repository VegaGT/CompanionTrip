package com.example.journey.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.journey.R;

import java.util.List;

public class ChooseCityActivity2 extends AppCompatActivity {

  int provinceNumber; //需要传到下一个activity

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chose_city);

    TextView title = (TextView) findViewById(R.id.title_text);
    ListView lv = (ListView) findViewById(R.id.choose_listView);

    Intent intent = getIntent();
    final List<String> City = intent.getStringArrayListExtra("Province");
    provinceNumber = intent.getIntExtra("provinceNumber", 0);  //第二个参数是默认值

    title.setText("选择城市");
    lv.setAdapter(new ArrayAdapter<>(ChooseCityActivity2.this, android.R.layout.simple_list_item_1, City));
    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.setClass(ChooseCityActivity2.this, PostingActivity.class);
        intent.putExtra("City", City.get(position));
        intent.putExtra("provinceNumber", provinceNumber);
        intent.putExtra("cityNumber", position);
        startActivity(intent);
      }
    });
  }

  public void back(View view) {
    finish();
  }

}
