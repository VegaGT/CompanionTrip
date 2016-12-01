package com.example.journey.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.journey.R;

public class FullImageActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_full_image);

    FrameLayout frameLayout = (FrameLayout) findViewById(R.id.screen);
    ImageView imageView = (ImageView) findViewById(R.id.image);
    String picPath = getIntent().getStringExtra("imagePath");
    Glide.with(this)
            .load(picPath)
            .into(imageView);

    frameLayout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        onBackPressed();
      }
    });
  }

  @Override
  public void onBackPressed() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
      supportFinishAfterTransition();
    } else {
      finish();
    }
  }

  // 用于需要共享元素转化时调用.
  public static void actionStart(Context context, ActivityOptionsCompat options, String imagePath) {
    Intent intent = new Intent(context, FullImageActivity.class);
    intent.putExtra("imagePath", imagePath);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
      context.startActivity(intent, options.toBundle());
    } else {
      context.startActivity(intent);
    }
  }
}
