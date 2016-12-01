package com.example.journey.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.journey.R;
import com.example.journey.lib.MyGridView;
import com.example.journey.model.GridItem;
import com.example.journey.model.GridItemAdapter;
import com.example.journey.model.HotPost;
import com.example.journey.model.HotSpot;
import com.example.journey.model.ProvinceAndCity;
import com.example.journey.model.Theme;
import com.example.journey.model.ThemeItem;
import com.example.journey.model.ThemeItemAdapter;
import com.example.journey.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by 高天 on 2016/8/19.
 */
public class HomeFragment extends Fragment {
  private ViewPager viewPager;

  private GridItemAdapter adapter1; //热门地点的adapter
  private GridItemAdapter adapter2; //热门帖子的adapter
  private CircleIndicator indicator;
  private FloatingActionButton plus;
  private ImageButton chooseLocation;
  private ProgressBar siteProgress;
  private ProgressBar postProgress;
  private MyGridView gridView_site;
  private MyGridView gridView_post;
  private EditText mEditText;
  private ImageView mImageView;
  private ListView mListView;
  private Button mButton;

  private ThemeItemAdapter adapter;
  private List<ThemeItem> themeItemList = new ArrayList<>();
  private List<GridItem> gridItemList_site = new ArrayList<>(); //热门地点
  private List<GridItem> gridItemList_post = new ArrayList<>(); //热门帖子
  private List<ProvinceAndCity> location = new ArrayList<>();
  private List<String> cities = new ArrayList<>();

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_home, container, false);

    viewPager = (ViewPager) view.findViewById(R.id.vp_hottest);
    indicator = (CircleIndicator) view.findViewById(R.id.indicator);
    siteProgress = (ProgressBar) view.findViewById(R.id.city_progress_bar);
    postProgress = (ProgressBar) view.findViewById(R.id.post_progress_bar);
    gridView_site = (MyGridView) view.findViewById(R.id.grid_view_site);
    gridView_post = (MyGridView) view.findViewById(R.id.grid_view_post);
    chooseLocation = (ImageButton) view.findViewById(R.id.choose_location);
    plus = (FloatingActionButton) view.findViewById(R.id.create_in_first_page);

    adapter = new ThemeItemAdapter(getActivity());
    viewPager.setAdapter(adapter);
    indicator.setViewPager(viewPager);
    adapter.registerDataSetObserver(indicator.getDataSetObserver());

    initThemeItem();
    initGridItem_site();
    initGridItem_post();

    chooseLocation.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getActivity(), ChooseCityActivity.class);
        startActivity(intent);
      }
    });

    //点击加号进入创建行程界面
    plus.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        User userInfo = User.getCurrentUser(User.class);
        if (userInfo != null) {
          Intent intent = new Intent(getActivity(), CreateActivity.class);
          startActivity(intent);
        } else {
          Toast toast = Toast.makeText(getActivity(), "请先登录哈", Toast.LENGTH_SHORT);
          toast.show();
        }
      }
    });

    return view;
  }

  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    initSearch();
  }

  private void initThemeItem() {
    BmobQuery<Theme> query = new BmobQuery<Theme>();
    query.order("-createdAt");
    query.findObjects(new FindListener<Theme>() {
      @Override
      public void done(List<Theme> object, BmobException e) {
        if (e == null) {
          for (int i = 0; i < object.size(); i++) {
            ThemeItem themeItem = new ThemeItem(object.get(i).getName(), object.get(i).getImage());
            themeItemList.add(themeItem);
          }
          adapter.setThemes(themeItemList);
          adapter.notifyDataSetChanged();
        } else {
          Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
        }
      }
    });
  }

  private void initGridItem_site() {
    siteProgress.setVisibility(View.VISIBLE);
    BmobQuery<HotSpot> query = new BmobQuery<HotSpot>();
    query.findObjects(new FindListener<HotSpot>() {
      @Override
      public void done(List<HotSpot> object, BmobException e) {
        if (e == null) {
          siteProgress.setVisibility(View.GONE);
          for (int i = 0; i < object.size(); i++) {
            GridItem gridItem = new GridItem(object.get(i).getBackground(), object.get(i).getName(), object.get(i).getPostID());
            gridItemList_site.add(gridItem);
          }
          adapter1 = new GridItemAdapter(getActivity(), R.layout.gridview_item, gridItemList_site);
          gridView_site.setAdapter(adapter1);
          gridView_site.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              GridItem gridItem = gridItemList_site.get(i);
              Intent intent = new Intent();
              intent.setClass(getActivity(), PostingActivity.class);
              intent.putExtra("City", gridItem.getPostID());
              startActivity(intent);
            }
          });
        } else {
          Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
        }
      }
    });
  }

  private void initGridItem_post() {
    postProgress.setVisibility(View.VISIBLE);
    BmobQuery<HotPost> query = new BmobQuery<HotPost>();
    query.findObjects(new FindListener<HotPost>() {
      @Override
      public void done(List<HotPost> object, BmobException e) {
        if (e == null) {
          postProgress.setVisibility(View.GONE);
          for (int i = 0; i < object.size(); i++) {
            GridItem gridItem = new GridItem(object.get(i).getBackground(),
                    object.get(i).getTitle(), object.get(i).getPostID());
            gridItemList_post.add(gridItem);
          }
          adapter2 = new GridItemAdapter(getActivity(), R.layout.gridview_item, gridItemList_post);
          gridView_post.setAdapter(adapter2);
          gridView_post.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              GridItem gridItem = gridItemList_post.get(i);
              Intent intent = new Intent(getActivity(), CardActivity.class);
              intent.putExtra("postID", gridItem.getPostID());
              startActivity(intent);
            }
          });
        } else {
          Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
        }
      }
    });
  }

  @Override
  public void onResume() {
    super.onResume();
  }


  private void initSearch() {
    mButton = (Button) getActivity().findViewById(R.id.search_btn_back);
    mEditText = (EditText) getActivity().findViewById(R.id.search_et_input);
    mImageView = (ImageView) getActivity().findViewById(R.id.search_iv_delete);
    mListView = (ListView) getActivity().findViewById(R.id.main_lv_search_results);

    //设置删除图片的点击事件
    mImageView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //把EditText内容设置为空
        mEditText.setText("");
        //把ListView隐藏
        mListView.setVisibility(View.GONE);
      }
    });

    mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
      @Override
      public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
          search();
          return true;
        }
        return false;
      }
    });

    //EditText添加监听
    mEditText.addTextChangedListener(new TextWatcher() {

      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      }//文本改变之前执行

      @Override
      //文本改变的时候执行
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        //如果长度为0
        if (s.length() == 0) {
          //隐藏“删除”图片
          mImageView.setVisibility(View.GONE);
          cities.clear();
          mListView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, cities));
        } else {//长度不为0
          //显示“删除图片”
          mImageView.setVisibility(View.VISIBLE);
          //显示ListView
          showListView();
        }
      }

      public void afterTextChanged(Editable s) {
      }//文本改变之后执行
    });

    mButton.setOnClickListener(new View.OnClickListener() {

      public void onClick(View v) {
        search();
      }
    });
  }

  private void showListView() {
    mListView.setVisibility(View.VISIBLE);
    final String str = mEditText.getText().toString().trim();

    BmobQuery<ProvinceAndCity> query = new BmobQuery<ProvinceAndCity>();
    query.addWhereContains("City", str);
    query.include("City");
    query.findObjects(new FindListener<ProvinceAndCity>() {
      @Override
      public void done(List<ProvinceAndCity> object, BmobException e) {
        if (e == null) {
//          Toast toast = Toast.makeText(getActivity(), "查询个数：" + object.size(), Toast.LENGTH_SHORT);
//          toast.show();
          location = object;
          extractCitiesFromArray(location, cities, str);
          mListView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, cities));

        } else {
          Log.i("bmob", "失败：" + e.getMessage());
          Toast toast = Toast.makeText(getActivity(), "失败：" + e.getMessage(), Toast.LENGTH_SHORT);
          toast.show();
        }
      }
    });

    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), PostingActivity.class);
        intent.putExtra("City", cities.get(position));
        startActivity(intent);
      }
    });
  }

  void extractCitiesFromArray(List<ProvinceAndCity> list1, List<String> list2, String word) {
    list2.clear();
    for (int i = 0; i < list1.size(); i++) {
      for (int j = 0; j < list1.get(i).getCity().size(); j++) {
        if (list1.get(i).getCity().get(j).contains(word)) {
          list2.add(list1.get(i).getCity().get(j));
        }
      }
    }
  }

  void search() {
    //如果输入框内容为空，提示请输入搜索内容

    if ((mEditText.getText().toString().trim()).equals("")) {
      Toast toast = Toast.makeText(getActivity(), "请输入您要搜索的内容", Toast.LENGTH_SHORT);
      toast.show();
    } else {
      BmobQuery<ProvinceAndCity> query = new BmobQuery<ProvinceAndCity>();
      String[] city = {mEditText.getText().toString().trim()};
      query.addWhereContainsAll("City", Arrays.asList(city));
      query.include("author");
      query.findObjects(new FindListener<ProvinceAndCity>() {
        @Override
        public void done(List<ProvinceAndCity> object, BmobException e) {
          if (e == null) {
            if (object.size() > 0) {
              Intent intent = new Intent();
              intent.setClass(getActivity(), PostingActivity.class);
              intent.putExtra("City", mEditText.getText().toString().trim());
              startActivity(intent);
            } else {
              Toast toast = Toast.makeText(getActivity(), "没有这个地点哦", Toast.LENGTH_SHORT);
              toast.show();
            }
          } else {
            Toast toast = Toast.makeText(getActivity(), "错误" + e.getMessage(), Toast.LENGTH_SHORT);
            toast.show();
          }
        }
      });
    }
  }
}
