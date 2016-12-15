package com.example.journey.model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.journey.R;

import java.util.List;

/**
 * Created by Li on 2016/12/10.
 */
public class ShopItemAdapter extends RecyclerView.Adapter<ShopItemAdapter.ViewHolder> {

    protected Context mContext;
    protected List<ShopItem> shopItemList;

    public ShopItemAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent,int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        ShopItem shopItem = shopItemList.get(position);
        Glide.with(this.mContext).load(shopItem.getPic()).centerCrop().into(holder.pic);
        holder.name.setText(shopItem.getName());
        holder.content.setText(shopItem.getContent());
        holder.price.setText(shopItem.getPrice());
        holder.city.setText(shopItem.getCity());
        holder.address.setText(shopItem.getAddress());
        holder.time_limit.setText(shopItem.getTime_limit());
    }

    @Override
    public int getItemCount() {
        return shopItemList.size();
    }

    public List<ShopItem> getShopItemList() {
        return shopItemList;
    }

    public void setShopItemList(List<ShopItem> shopItemList) {
        this.shopItemList = shopItemList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView pic;
        private TextView name;
        private TextView content;
        private TextView price;
        private TextView city;
        private TextView address;
        private TextView time_limit;


        public ViewHolder(View view){
            super(view);
            pic = (ImageView)view.findViewById(R.id.shop_pic);
            name = (TextView)view.findViewById(R.id.shop_name);
            content = (TextView)view.findViewById(R.id.shop_content);
            price = (TextView)view.findViewById(R.id.shop_price);
            city = (TextView)view.findViewById(R.id.shop_city);
            address = (TextView)view.findViewById(R.id.shop_address);
            time_limit = (TextView)view.findViewById(R.id.time_limit);
        }


    }
}
