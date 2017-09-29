package com.miku.ktv.miku_android.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.miku.ktv.miku_android.R;
import com.miku.ktv.miku_android.model.bean.RoomsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 焦帆 on 2017/9/29.
 */

public class RoomsAdapter extends BaseAdapter {

    private Context context;
    private List<RoomsBean.BodyBean.RoomListBean> list=new ArrayList<>();

    public RoomsAdapter(Context context, List<RoomsBean.BodyBean.RoomListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            convertView = View.inflate(context, R.layout.item_home_listview, null);
            holder=new ViewHolder();
            holder.headIv= (ImageView) convertView.findViewById(R.id.Item_Home_ImageView_Head);
            holder.nickTv= (TextView) convertView.findViewById(R.id.Item_Home_TextView_Nick);
            holder.numTv= (TextView) convertView.findViewById(R.id.Item_Home_TextView_Num);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }

        Glide.with(context)
                .load(list.get(position).getParticipants().get(0).getAvatar())
                .error(R.mipmap.bg9)
                .placeholder(R.mipmap.bg9)
                .centerCrop()
                .fitCenter()
                .into(holder.headIv);

        holder.nickTv.setText(list.get(position).getCreator_nick());
        holder.numTv.setText(list.get(position).getNumbers()+"");
        return convertView;
    }

    class ViewHolder{
        ImageView headIv;
        TextView nickTv;
        TextView numTv;
    }
}
