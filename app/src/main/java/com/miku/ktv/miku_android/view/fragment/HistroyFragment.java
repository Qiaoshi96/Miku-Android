package com.miku.ktv.miku_android.view.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.miku.ktv.miku_android.R;
import com.miku.ktv.miku_android.model.bean.AddBean;
import com.miku.ktv.miku_android.model.bean.AddListBean;
import com.miku.ktv.miku_android.model.bean.DeleteBean;
import com.miku.ktv.miku_android.model.bean.HistroyBean;
import com.miku.ktv.miku_android.model.utils.IsUtils;
import com.miku.ktv.miku_android.presenter.AddPresenter;
import com.miku.ktv.miku_android.view.adapter.HistroyAdapter;
import com.miku.ktv.miku_android.view.custom.RefreshListView;
import com.miku.ktv.miku_android.view.iview.IAddView;
import com.miku.ktv.miku_android.view.iview.OnRefreshListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by 焦帆 on 2017/10/11.
 */

public class HistroyFragment extends Fragment implements IAddView<AddBean, DeleteBean, AddListBean>, OnRefreshListener {
    public static final String TAG="HistroyFragment";

    private View inflateView;
    private RefreshListView refreshLVHistroy;
    private Context mContext;
    private HistroyAdapter adapter;
    private ArrayList<HistroyBean> listAll=new ArrayList<>();
    private SharedPreferences sp;
    private SharedPreferences.Editor edit;
    private AddPresenter addPresenter;
    private TextView dialogMusicTV;
    private TextView dialogTimeTV;
    private TextView dialogGiveupTV;
    private TextView dialogNowTV;
    private AlertDialog builder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext=getActivity();
        sp = getActivity().getSharedPreferences("config", MODE_PRIVATE);
        edit = sp.edit();
        addPresenter=new AddPresenter();
        addPresenter.attach(HistroyFragment.this);
        initView();
        return inflateView;
    }

    public void setData(ArrayList<HistroyBean> list) {
        for(int x=0; x< list.size(); x++){
            if (list.size() >0 && list != null) {
                Log.d(TAG, "setData: "+list.get(x).getName());
                Log.d(TAG, "setData: "+list.get(x).getLink());
                listAll.addAll(list);
                adapter = new HistroyAdapter(mContext,listAll);
                refreshLVHistroy.setAdapter(adapter);
                refreshLVHistroy.setOnRefreshListener(this);
                adapter.setOnPaimaiClickListener(new HistroyAdapter.MyPaimaiClickListener() {
                    @Override
                    public void onPaimaiClick(BaseAdapter adapter, View view, int position) {
                        IsUtils.showShort(getActivity(),"点了排麦");

                        edit.putString("musicname",listAll.get(position).getName());
                        edit.putString("musiclink", listAll.get(position).getLink());
                        edit.putString("singer", listAll.get(position).getAuthor());
                        edit.putString("lyric", listAll.get(position).getLrc());
                        edit.commit();

                        String name = System.currentTimeMillis() + "";
                        MessageDigest md5 = null;
                        try {
                            md5 = MessageDigest.getInstance("MD5");
                            name = new String(md5.digest(listAll.get(position).getLrc().getBytes()));
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        }

                        edit.putString("mp3Location", Environment.getExternalStorageDirectory() + "/MiDoDownUtil/" +  name + ".mp3");
                        edit.putString("lyricLocation", Environment.getExternalStorageDirectory() + "/MiDoDownUtil/" + name + listAll.get(position).getLrc().substring(listAll.get(position).getLrc().lastIndexOf(".")));
                        edit.commit();
                        Log.d(TAG, "歌名为："+listAll.get(position).getName());

                        HashMap<String,String> map=new HashMap<>();
                        map.put("token",sp.getString("LoginToken",""));
                        map.put("sid",listAll.get(position).getId()+"");
                        Log.d(TAG, "歌曲ID:  "+listAll.get(position).getId()+"");
                        Log.d(TAG, "登录的token:  "+sp.getString("LoginToken",""));
                        Log.d(TAG, "roomId:  "+sp.getString("JFmRoomName",""));
                        addPresenter.postAdd(sp.getString("JFmRoomName",""), map, AddBean.class);
                    }
                });
            }else {
                Log.d(TAG, "setData: + list为空");
            }
        }

    }


    //上麦成功
    @Override
    public void onSuccess(AddBean addBean) {
        if (addBean.getStatus()==1){
            //排麦成功dialog
            showSuccessDialog();
            Log.d(TAG, "onSuccess: "+addBean.getMsg());
        }else {
            //排麦重复
            if (addBean.getStatus()==6){
                showTwoDialog();
            }
            Log.d(TAG, "onSuccess: "+addBean.getMsg());
        }
    }

    private void showTwoDialog() {
        final AlertDialog.Builder builderTwo = new AlertDialog.Builder(getActivity());
        builderTwo.setMessage("唱完才能点歌哦~");
        builderTwo.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builderTwo.create().show();
    }

    private void showSuccessDialog() {
        AlertDialog.Builder builderBig = new AlertDialog.Builder(getActivity());
        Log.d(TAG, "showSuccessDialog: ");
        builderBig.setMessage("排麦成功");
        builderBig.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                builder = new AlertDialog.Builder(getActivity()).create();

                View dialogView=View.inflate(getActivity(), R.layout.paimai_dialog,null);
                dialogMusicTV = (TextView) dialogView.findViewById(R.id.PaimaiDialog_TextView_Music);
                dialogTimeTV = (TextView) dialogView.findViewById(R.id.PaimaiDialog_TextView_Time);
                dialogGiveupTV = (TextView) dialogView.findViewById(R.id.PaimaiDialog_TextView_Giveup);
                dialogNowTV = (TextView) dialogView.findViewById(R.id.PaimaiDialog_TextView_Now);

                dialogMusicTV.setText(sp.getString("musicname",""));
                final CountDownTimer timer=new CountDownTimer(15000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        dialogTimeTV.setText((millisUntilFinished / 1000)+"秒后不上麦则自动放弃");
                    }

                    @Override
                    public void onFinish() {
                        dialogTimeTV.setText("已自动放弃");
                        //请求下麦接口
                        HashMap<String,String> map=new HashMap<>();
                        map.put("token",sp.getString("LoginToken",""));
                        Log.d(TAG, "自动放弃"+sp.getString("JFmRoomName",""));
                        addPresenter.delete(sp.getString("JFmRoomName",""), map, DeleteBean.class);
                    }
                };
                timer.start();

                builder.setView(dialogView);
                builder.show();

                dialogGiveupTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //请求下麦接口
                        HashMap<String,String> map=new HashMap<>();
                        map.put("token",sp.getString("LoginToken",""));
                        Log.d(TAG, "手动放弃"+sp.getString("JFmRoomName",""));
                        addPresenter.delete(sp.getString("JFmRoomName",""), map, DeleteBean.class);
                        timer.cancel();
                    }
                });

                dialogNowTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        IsUtils.showShort(getActivity(),"立即上麦");

                        Intent intent=new Intent();

                        intent.putExtra("mp3Url",sp.getString("musiclink",""));
                        intent.putExtra("lyricUrl",sp.getString("lyric",""));

                        //本地地址
                        intent.putExtra("mp3Location",sp.getString("mp3Location",""));
                        intent.putExtra("lyricLocation",sp.getString("lyricLocation",""));

                        intent.putExtra("musicName",sp.getString("musicname",""));
                        intent.putExtra("singer",sp.getString("singer",""));

                        getActivity().setResult(2,intent);

                        builder.dismiss();
                        timer.cancel();
                        getActivity().finish();
                    }
                });
                dialog.dismiss();
            }

        });

        builderBig.create().show();
    }

    @Override
    public void onError(Throwable t) {
        Log.d(TAG, "onError: "+t.getMessage());
    }
    //删除成功
    @Override
    public void onDeleteSuccess(DeleteBean t) {
        if (t.getStatus()==1){
            builder.dismiss();
            IsUtils.showShort(getActivity(),"放弃");
        }else {
            IsUtils.showShort(getActivity(),"放弃失败");
        }
    }

    @Override
    public void onDeleteError(Throwable throwable) {
        Log.d(TAG, "onDeleteError: "+throwable.getMessage());

    }

    //下拉刷新
    @Override
    public void onDownPullRefresh() {
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                adapter.notifyDataSetChanged();
                refreshLVHistroy.hideHeaderView();
            }
        }.execute(new Void[]{});
    }
    //上拉加载
    @Override
    public void onLoadingMore() {
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                adapter.notifyDataSetChanged();
                refreshLVHistroy.hideFooterView();
            }
        }.execute(new Void[]{});
    }

    private void initView() {
        inflateView = View.inflate(mContext, R.layout.fragment_histroy, null);
        refreshLVHistroy = (RefreshListView) inflateView.findViewById(R.id.refreshLVHistroy);
        setData(listAll);
    }

    @Override
    public void onAddListSuccess(AddListBean t) {

    }

    @Override
    public void onAddListError(Throwable throwable) {

    }

}
