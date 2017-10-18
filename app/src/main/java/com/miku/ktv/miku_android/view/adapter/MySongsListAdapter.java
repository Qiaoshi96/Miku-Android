package com.miku.ktv.miku_android.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.miku.ktv.miku_android.R;
import com.miku.ktv.miku_android.model.bean.SongsListBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 焦帆 on 2017/10/12.
 */

public class MySongsListAdapter extends BaseAdapter {
    public static final String TAG="MySongsListAdapter";

    private Context context;
    private List<SongsListBean.BodyBean.SongListBean> songsListAll=new ArrayList<>();
    ViewHolder holder=null;
    HashMap<Integer,View> map=new HashMap<>();

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (map.get(position)==null){
            convertView = View.inflate(context, R.layout.fragment_hot_item, null);
            holder=new ViewHolder();
            holder.musicTV= (TextView) convertView.findViewById(R.id.HotFragment_item_TextView_Music);
            holder.singerTV= (TextView) convertView.findViewById(R.id.HotFragment_item_TextView_Singer);
            holder.downLoadTV= (TextView) convertView.findViewById(R.id.HotFragment_item_TextView_DownLoad);
            holder.paimaiTV= (TextView) convertView.findViewById(R.id.HotFragment_item_TextView_Paimai);
            convertView.setTag(holder);

            map.put(position,convertView);
        }else {
            convertView=map.get(position);

            holder= (ViewHolder) convertView.getTag();
        }
        try {
            final SongsListBean.BodyBean.SongListBean bean = songsListAll.get(position);
            holder.musicTV.setText(bean.getName());
            holder.singerTV.setText(bean.getAuthor());
            //设置按钮点击事件
            holder.downLoadTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemDeleteListener.onDeleteClick(position);
                }
            });
            holder.paimaiTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemDeleteListener.onDeleteClick(position);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

        return convertView;
    }

    public interface onItemDeleteListener {
        void onDeleteClick(int i);
    }

    private onItemDeleteListener mOnItemDeleteListener;

    public void setOnItemDeleteClickListener(onItemDeleteListener mOnItemDeleteListener) {
        this.mOnItemDeleteListener = mOnItemDeleteListener;
    }

    class ViewHolder{
        TextView musicTV;
        TextView singerTV;
        TextView downLoadTV;
        TextView paimaiTV;
    }
}
