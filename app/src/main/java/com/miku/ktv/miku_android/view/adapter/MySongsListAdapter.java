package com.miku.ktv.miku_android.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.miku.ktv.miku_android.R;
import com.miku.ktv.miku_android.model.bean.SongsListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 焦帆 on 2017/10/12.
 */

public class MySongsListAdapter extends BaseAdapter{

    private Context context;
    private List<SongsListBean.BodyBean.SongListBean> songsListAll=new ArrayList<>();

    public MySongsListAdapter(Context context, List<SongsListBean.BodyBean.SongListBean> songsListAll) {
        this.context = context;
        this.songsListAll = songsListAll;
    }

    @Override
    public int getCount() {
        return songsListAll.size();
    }

    @Override
    public Object getItem(int position) {
        return songsListAll.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            convertView = View.inflate(context, R.layout.fragment_hot_item_pulltorefreshlistview, null);
            holder=new ViewHolder();
            holder.musicTV= (TextView) convertView.findViewById(R.id.HotFragment_item_TextView_Music);
            holder.singerTV= (TextView) convertView.findViewById(R.id.HotFragment_item_TextView_Singer);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        holder.musicTV.setText(songsListAll.get(position).getName());
        holder.singerTV.setText(songsListAll.get(position).getAuthor());
        return convertView;
    }

    class ViewHolder{
        TextView musicTV;
        TextView singerTV;
    }

//    //添加数据
//    public void addItem(List<SongsListBean.BodyBean.SongListBean> songsListNew){
//        songsList.addAll(songsListNew);
//    }
}
