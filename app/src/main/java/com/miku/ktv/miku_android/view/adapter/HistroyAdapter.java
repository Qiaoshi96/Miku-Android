package com.miku.ktv.miku_android.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.miku.ktv.miku_android.R;
import com.miku.ktv.miku_android.model.bean.HistroyBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 焦帆 on 2017/10/16.
 */

public class HistroyAdapter extends BaseAdapter {
    public static final String TAG="HistroyAdapter";

    private Context context;
    private List<HistroyBean> histroyList=new ArrayList<>();
    ViewHolder holder=null;
    HashMap<Integer,View> map=new HashMap<>();

    public HistroyAdapter(Context context, List<HistroyBean> histroyList) {
        this.context = context;
        this.histroyList = histroyList;
    }

    @Override
    public int getCount() {
        return histroyList.size();
    }

    @Override
    public Object getItem(int position) {
        return histroyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (map.get(position)==null){
            convertView = View.inflate(context, R.layout.fragment_histroy_item, null);
            holder=new ViewHolder();
            holder.musicTV= (TextView) convertView.findViewById(R.id.Histroy_item_TextView_Music);
            holder.singerTV= (TextView) convertView.findViewById(R.id.Histroy_item_TextView_Singer);
            holder.paimaiTV= (TextView) convertView.findViewById(R.id.Histroy_item_TextView_Paimai);
            holder.paimaiTV.setOnClickListener(mOnClickListener);
            convertView.setTag(holder);

            map.put(position,convertView);
        }else {
            convertView=map.get(position);

            holder= (ViewHolder) convertView.getTag();
        }
        holder.paimaiTV.setTag(position);
        HistroyBean bean = histroyList.get(position);
        holder.musicTV.setText(bean.getName());
        holder.singerTV.setText(bean.getAuthor());


        return convertView;
    }

    public  interface  MyPaimaiClickListener {
        void onPaimaiClick(BaseAdapter adapter, View view, int position);
    }
    private MyPaimaiClickListener paimaiClickListener;
    public void setOnPaimaiClickListener(MyPaimaiClickListener listener) {
        paimaiClickListener = listener;
    }

    private View.OnClickListener mOnClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (paimaiClickListener!=null){
                int position = (int) v.getTag();
                switch (v.getId()) {
                    case R.id.Histroy_item_TextView_Paimai:
                        paimaiClickListener.onPaimaiClick(HistroyAdapter.this, v, position);
                        break;
                }
            }
        }
    };

    class ViewHolder{
        TextView musicTV;
        TextView singerTV;
        TextView paimaiTV;
    }
}
