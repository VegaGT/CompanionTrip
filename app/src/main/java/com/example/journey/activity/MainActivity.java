package com.example.journey.activity;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.journey.R;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import cn.bmob.v3.Bmob;

/**
 * Created by 高天 on 2016/8/19.
 */
public class MainActivity extends AppCompatActivity {

  private Fragment homeFragment;
  private Fragment listFragment;
  private Fragment myInformationFragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Bmob.initialize(this, "6ee9523075948328b47697b65ee59f1f");

    initView();
    // 初始化并设置当前Fragment
    initFragment(0);
  }

  private void initFragment(int index) {
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction transaction = fragmentManager.beginTransaction();
    hideFragment(transaction);

    switch (index) {
      case 0:
        if (homeFragment == null) {
          homeFragment = new HomeFragment();
          transaction.add(R.id.fl_content, homeFragment, "homeFragment");
          Log.d("main", "add homeFragment");
        } else {
          transaction.show(homeFragment);
          Log.d("main", "show homeFragment");
        }
        break;

      case 1:
        if (listFragment == null) {
          listFragment = new ListFragment();
          transaction.add(R.id.fl_content, listFragment, "listFragment");
          Log.d("main", "add listFragment");
        } else {
          transaction.show(listFragment);
          Log.d("main", "show listFragment");
        }
        break;

      case 2:
        if (myInformationFragment == null) {
          myInformationFragment = new MyInformationFragment();
          transaction.add(R.id.fl_content, myInformationFragment, "myInformationFragment");
          Log.d("main", "add myInformationFragment");
        } else {
          transaction.show(myInformationFragment);
          Log.d("main", "show myInformationFragment");
        }
        break;

      default:
        break;
    }

    // 提交事务
    transaction.commit();

  }

  //隐藏Fragment
  private void hideFragment(FragmentTransaction transaction) {
    if (homeFragment != null) {
      transaction.hide(homeFragment);
    }
    if (listFragment != null) {
      transaction.hide(listFragment);
    }
    if (myInformationFragment != null) {
      transaction.hide(myInformationFragment);
    }
  }

  private void initView() {

    BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
    bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
      @Override
      public void onTabSelected(@IdRes int tabId) {
        if (tabId == R.id.tab_home) {
          initFragment(0);
        } else if (tabId == R.id.tab_discover) {
          initFragment(1);
        } else if (tabId == R.id.tab_my_info) {
          initFragment(2);
        }
      }
    });

    int[] attrs = new int[]{android.R.attr.selectableItemBackgroundBorderless};
    TypedArray typedArray = this.obtainStyledAttributes(attrs);
    int backgroundResource = typedArray.getResourceId(0, 0);
    for (int i = 0; i < 3; i++)
      bottomBar.getTabAtPosition(i).setBackgroundResource(backgroundResource);
  }
}
