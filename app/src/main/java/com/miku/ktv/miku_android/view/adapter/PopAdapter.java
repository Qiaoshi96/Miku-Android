package com.miku.ktv.miku_android.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.miku.ktv.miku_android.R;
import com.miku.ktv.miku_android.model.bean.AddListBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 焦帆 on 2017/10/16.
 */

public class PopAdapter extends BaseAdapter {
    public static final String TAG="PopAdapter";

    private Context context;
    private List<AddListBean.BodyBean.SingerListBean> list=new ArrayList<>();
    ViewHolder holder=null;
    HashMap<Integer,View> map=new HashMap<>();

    public PopAdapter(Context context, List<AddListBean.BodyBean.SingerListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        Log.d(TAG, "getCount: "+list.size());
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        Log.d(TAG, "getItem: ");
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        Log.d(TAG, "getItemId: ");
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "getView: ");
        if (map.get(position)==null){
            convertView = View.inflate(context, R.layout.ktv_pop_item, null);
            holder=new ViewHolder();
            holder.musicTV= (TextView) convertView.findViewById(R.id.Pop_item_TextView_Music);
            holder.singerTV= (TextView) convertView.findViewById(R.id.Pop_item_TextView_Singer);
            holder.nickTV= (TextView) convertView.findViewById(R.id.Pop_item_TextView_Nick);
            holder.deleteTV= (TextView) convertView.findViewById(R.id.Pop_item_TextView_Delete);
            convertView.setTag(holder);

            map.put(position,convertView);
        }else {
            convertView=map.get(position);

            holder= (ViewHolder) convertView.getTag();

            AddListBean.BodyBean.SingerListBean bean = list.get(position);
            holder.musicTV.setText(bean.getSong().getName());
            holder.singerTV.setText(bean.getSong().getAuthor());
            holder.nickTV.setText(bean.getCreator().getNick());

            Log.d(TAG, "getView: "+bean.getSong().getName());
            Log.d(TAG, "getView: "+bean.getSong().getAuthor());
            Log.d(TAG, "getView: "+bean.getCreator().getNick());
        }
        return convertView;
    }

    class ViewHolder{
        TextView musicTV;
        TextView singerTV;
        TextView nickTV;
        TextView deleteTV;
    }
}
