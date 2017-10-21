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
import java.util.List;

/**
 * Created by 焦帆 on 2017/10/16.
 */

public class PopAdapter extends BaseAdapter {
    public static final String TAG="PopAdapter";

    private Context context;
    private List<AddListBean.BodyBean.SingerListBean> list=new ArrayList<>();
    ViewHolder holder;
//    HashMap<Integer,View> map=new HashMap<>();


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
    public View getView(final int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "getView: ");
        if (convertView==null){
            convertView = View.inflate(context, R.layout.ktv_pop_item, null);
            holder=new ViewHolder();
            holder.musicTV= (TextView) convertView.findViewById(R.id.Pop_item_TextView_Music);
            holder.singerTV= (TextView) convertView.findViewById(R.id.Pop_item_TextView_Singer);
            holder.nickTV= (TextView) convertView.findViewById(R.id.Pop_item_TextView_Nick);
            holder.deleteTV= (TextView) convertView.findViewById(R.id.Pop_item_TextView_Delete);
            holder.deleteTV.setOnClickListener(mOnClickListener);
            convertView.setTag(holder);

        }else {

            holder= (ViewHolder) convertView.getTag();
        }

        holder.deleteTV.setTag(position);

        AddListBean.BodyBean.SingerListBean bean = list.get(position);
        holder.musicTV.setText(bean.getSong().getName());
        holder.singerTV.setText(bean.getSong().getAuthor());
        holder.nickTV.setText(bean.getCreator().getNick());

        Log.d(TAG, "getView: "+bean.getSong().getName());
        Log.d(TAG, "getView: "+bean.getSong().getAuthor());
        Log.d(TAG, "getView: "+bean.getCreator().getNick());

//                holder.deleteTV.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onItemDeleteListener.onDeleteClick(position);
//                }
//            });
        return convertView;
    }

//    public interface onItemDeleteListener {
//        void onDeleteClick(int i);
//    }
//    private onItemDeleteListener onItemDeleteListener;
//    public void setOnItemDeleteListener(onItemDeleteListener onItemDeleteListener){
//        this.onItemDeleteListener=onItemDeleteListener;
//    }

    public  interface  MyClickListener {
        void onItemDeleteClick(BaseAdapter adapter, View view, int position);
    }
    private MyClickListener mListener;
    public void setOnItemDeleteClickListener(MyClickListener listener) {
        mListener = listener;
    }

    class ViewHolder{
        TextView musicTV;
        TextView singerTV;
        TextView nickTV;
        TextView deleteTV;
    }

    private View.OnClickListener mOnClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mListener!=null){
                int position = (int) v.getTag();
                switch (v.getId()) {
                    case R.id.Pop_item_TextView_Delete:
                        mListener.onItemDeleteClick(PopAdapter.this, v, position);
                        break;

                    default:
                        break;
                }
            }
        }
    };

}
