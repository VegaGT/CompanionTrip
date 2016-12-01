package com.example.journey.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;


public class DrawableUtils {

  public static Bitmap getBitmap(Context context, int drawableRes, double multiple) {
    Drawable drawable = context.getResources().getDrawable(drawableRes);
    Canvas canvas = new Canvas();
    int width = (int) multiple*drawable.getIntrinsicWidth();
    int height = (int) multiple*drawable.getIntrinsicHeight();
    Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    canvas.setBitmap(bitmap);
    drawable.setBounds(0, 0, width, height);
    drawable.draw(canvas);

    return bitmap;
  }
}
