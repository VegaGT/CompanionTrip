package com.example.journey.model;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.journey.R;

import java.util.List;

/**
 * Created by Li on 2016/12/9.
 */
public class HotGoodsAdapter extends ArrayAdapter<HotGoods> {
    private int resourceId;

    public HotGoodsAdapter(Context context, int textViewResourceId, List<HotGoods> objects) {
        super(context, textViewResourceId, objects);
        this.resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HotGoods hotGoods = getItem(position);
        View view;
        final ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.grid_view_item_picture = (ImageView) view.findViewById(R.id.grid_view_item_picture);
            viewHolder.grid_view_item_tag = (TextView) view.findViewById(R.id.grid_view_item_tag);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        Glide.with(getContext())
                .load(hotGoods.getBackground())
                .asBitmap()
                .into(new BitmapImageViewTarget(viewHolder.grid_view_item_picture) {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                        super.onResourceReady(bitmap, glideAnimation);
                        Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {
                                int colorFrom = ContextCompat.getColor(getContext(), R.color.colorPrimaryDarkTransparent);
                                int colorTo = palette.getDarkVibrantColor(colorFrom);
                                if (colorTo == colorFrom) colorTo = palette.getDarkMutedColor(colorFrom);
                                ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
                                colorAnimation.setDuration(1000);
                                colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                    @Override
                                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                        int color = ((int) valueAnimator.getAnimatedValue() & 0x00ffffff) | (200 << 24);
                                        viewHolder.grid_view_item_tag.setBackgroundColor(color);
                                    }
                                });
                                colorAnimation.start();
                            }
                        });
                    }
                });
        viewHolder.grid_view_item_tag.setText(hotGoods.getName());
        return view;
    }

    private class ViewHolder {
        ImageView grid_view_item_picture;
        TextView grid_view_item_tag;
    }
}
