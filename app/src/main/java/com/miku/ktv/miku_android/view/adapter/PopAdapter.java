package com.miku.ktv.miku_android.view.adapter;

import android.content.Context;
import android.content.SharedPreferences;
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
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    private Context context;
    private List<AddListBean.BodyBean.SingerListBean> list=new ArrayList<>();
    ViewHolder holder;


    public PopAdapter(Context context, List<AddListBean.BodyBean.SingerListBean> list) {

        sp = context.getSharedPreferences("config", context.MODE_PRIVATE);
        editor = sp.edit();

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
//展示排麦列表的listview
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
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

//        乔石： 添加接口中的测试数据

//        bean.getSong().get


        String fullnameMain = sp.getString("FullnameMain", "");
        if (bean.getCreator().getFullname().equals(fullnameMain)) {
            holder.deleteTV.setText("删除");
        }else {
            holder.deleteTV.setVisibility(View.GONE);
        }

        Log.d(TAG, "getView: "+bean.getSong().getName());
        Log.d(TAG, "getView: "+bean.getSong().getAuthor());
        Log.d(TAG, "getView: "+bean.getCreator().getNick());

        return convertView;
    }


    public interface MyClickListener {
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
