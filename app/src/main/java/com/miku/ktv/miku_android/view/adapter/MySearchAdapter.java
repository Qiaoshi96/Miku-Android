package com.miku.ktv.miku_android.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.miku.ktv.miku_android.R;
import com.miku.ktv.miku_android.model.bean.SearchBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 焦帆 on 2017/10/17.
 */

public class MySearchAdapter extends BaseAdapter {

    private Context context;
    private List<SearchBean.BodyBean.SongListBean> list=new ArrayList<>();
    ViewHolder holder= null;
    HashMap<Integer,View> map=new HashMap<>();

    public MySearchAdapter(Context context, List<SearchBean.BodyBean.SongListBean> list) {
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
        if (map.get(position)==null){
            convertView = View.inflate(context, R.layout.activity_search_item, null);
            holder=new ViewHolder();
            holder.musicTV= (TextView) convertView.findViewById(R.id.Search_item_TextView_Music);
            holder.singerTV= (TextView) convertView.findViewById(R.id.Search_item_TextView_Singer);
            holder.downTV= (TextView) convertView.findViewById(R.id.Search_item_TextView_DownLoad);
            holder.paimaiTV= (TextView) convertView.findViewById(R.id.Search_item_TextView_Paimai);
            convertView.setTag(holder);

            map.put(position,convertView);
        }else {
            convertView=map.get(position);

            holder= (ViewHolder) convertView.getTag();

            SearchBean.BodyBean.SongListBean bean = list.get(position);
            holder.musicTV.setText(bean.getName());
            holder.singerTV.setText(bean.getAuthor());
        }

        return convertView;
    }

    class ViewHolder{
        TextView musicTV;
        TextView singerTV;
        TextView downTV;
        TextView paimaiTV;
    }
}
