package com.miku.ktv.miku_android.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.miku.ktv.miku_android.R;
import com.miku.ktv.miku_android.model.bean.AvatarBean;
import com.miku.ktv.miku_android.model.bean.RegisterInfoBean;
import com.miku.ktv.miku_android.model.utils.IsUtils;
import com.miku.ktv.miku_android.model.utils.ZipImageUtil;
import com.miku.ktv.miku_android.presenter.RegisterInfoPresenter;
import com.miku.ktv.miku_android.view.custom.CircleImage;
import com.miku.ktv.miku_android.view.iview.IRegisterInfoView;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class RegisterSettingActivity extends AppCompatActivity implements IRegisterInfoView<RegisterInfoBean>, View.OnClickListener {

    public static final String TAG="RegisterSettingActivity";
    private SharedPreferences sp;
    private SharedPreferences.Editor edit;
    private LinearLayout rs_linearLayout_head;
    private EditText rs_editText_nick;
    private RadioButton rs_radioButton_man;
    private RadioButton rs_radioButton_woMan;
    private TextView rs_textView_register;
    private RadioGroup rs_radioGroup;
    private TextView rs_textView_man;
    private TextView rs_textView_woMan;
    private LinearLayout rs_back;
    private CircleImage rs_imageView_head;
    private File file;//存储拍摄图片的文件
    private static final int PICTURE_FROM_CAMERA = 0X32;
    private static final int PICTURE_FROM_GALLERY = 0X34;
    private String[] items={"拍照","相册"};
    private String sexName;
    private RegisterInfoPresenter infoPresenter;
    private int it;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_setting);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        edit = sp.edit();
        initView();
        initRadio();
        bindPresenter();
    }

    private void bindPresenter() {
        infoPresenter = new RegisterInfoPresenter();
        infoPresenter.attach(this);
    }

    private void initRadio() {
        rs_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (rs_radioButton_man.getId()==checkedId){
                    sexName=rs_textView_man.getText().toString();
                }else {
                    sexName=rs_textView_woMan.getText().toString();
                }

                try {
                    it = Integer.parseInt(sexName);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                edit.putInt("sex", it);
                edit.commit();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.RS_Back:
                finish();
                break;
            //注册
            case R.id.RS_TextView_Register:

                if (rs_imageView_head.getDrawable().getCurrent().getConstantState()
                        .equals(getResources().getDrawable(R.mipmap.mine_titlebar_normal)
                                .getConstantState())){
                    IsUtils.showShort(this,"请设置图片~");
                }else {
                    if (TextUtils.isEmpty(rs_editText_nick.getText().toString())){
                        IsUtils.showShort(this,"请输入昵称~");
                    }else {
                        if (rs_radioButton_man.isChecked() ==false && rs_radioButton_woMan.isChecked()==false){
                            IsUtils.showShort(this,"请选择性别~");
                        }else {

                            HashMap<String,String> map=new HashMap<>();
                            map.put("phone",sp.getString("phoneEdit","null"));
                            map.put("nick",rs_editText_nick.getText().toString());
                            map.put("sex",it+"");
                            infoPresenter.postInfo(map,RegisterInfoBean.class);

                        }
                    }
                }
                break;
            //设置头像
            case R.id.RS_LinearLayout_Head:
                setDialog();
                break;

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PICTURE_FROM_CAMERA://相机
                    //对图片进行压缩
                    ZipImageUtil.zipImage(Uri.fromFile(file).getPath());
                    //将图片设置到ImageView中
                    rs_imageView_head.setImageURI(Uri.fromFile(file));

                    break;
                case PICTURE_FROM_GALLERY://相册
                    //通过返回的data数据，获取图片的路径信息，但是这个路径是Uri的
                    Uri uri = data.getData();
                    //进行压缩，首先要将Uri地址转换为真实路径
                    File file = getFilePath(uri);
                    this.file = file;
                    //压缩图片
                    ZipImageUtil.zipImage(file.getAbsolutePath());
                    rs_imageView_head.setImageURI(Uri.fromFile(file));

                    break;
            }
        }
    }

    @Override
    public void onSuccess(RegisterInfoBean registerInfoBean) {
        if (registerInfoBean.getStatus()==1){
            edit.putString("tokenKey",registerInfoBean.getBody().getToken());
            edit.commit();
            IsUtils.showShort(this,"注册成功");
            //头像上传服务器
            if(file!=null){
                infoPresenter.postAvatar(registerInfoBean.getBody().getToken(), file, AvatarBean.class);
            }
        }else {
            IsUtils.showShort(this,"注册失败");
            Log.e(TAG,"onError  "+registerInfoBean.getMsg());
        }
    }

    @Override
    public void onError(RegisterInfoBean registerInfoBean) {

    }

    private void setDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        //调用相机
                        setCamera();
                        break;
                    case 1:
                        //调用相册
                        setAlbum();
                        break;

                    default:
                        break;
                }
            }
        });
        builder.create();
        builder.show();
    }

    private void setAlbum() {
        Intent intent = new Intent();
        //设置启动相册的Action
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //设置类型
        intent.setType("image/*");
        //启动相册，这里使用有返回结果的启动
        startActivityForResult(intent, PICTURE_FROM_GALLERY);
    }

    private void setCamera() {
        Intent intent = new Intent();
        //启动相机的Action
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        //文件的保存位置
        file = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //设置图片拍摄后保存的位置
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        //启动相机，这里使用有返回结果的启动
        startActivityForResult(intent, PICTURE_FROM_CAMERA);
    }

    private File getFilePath(Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor actualimagecursor = managedQuery(uri, proj, null, null, null);
        int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        actualimagecursor.moveToFirst();
        String img_path = actualimagecursor.getString(actual_image_column_index);
        return new File(img_path);
    }

    private void initView() {
        rs_imageView_head = (CircleImage) findViewById(R.id.RS_ImageView_Head);
        rs_linearLayout_head = (LinearLayout) findViewById(R.id.RS_LinearLayout_Head);
        rs_editText_nick = (EditText) findViewById(R.id.RS_EditText_Nick);
        rs_back = (LinearLayout) findViewById(R.id.RS_Back);

        rs_textView_man = (TextView) findViewById(R.id.RS_TextView_Man);
        rs_radioButton_man = (RadioButton) findViewById(R.id.RS_RadioButton_Man);
        rs_textView_woMan = (TextView) findViewById(R.id.RS_TextView_WoMan);
        rs_radioButton_woMan = (RadioButton) findViewById(R.id.RS_RadioButton_WoMan);

        rs_radioGroup = (RadioGroup) findViewById(R.id.RS_RadioGroup);
        rs_textView_register = (TextView) findViewById(R.id.RS_TextView_Register);

        rs_back.setOnClickListener(this);
        rs_linearLayout_head.setOnClickListener(this);
        rs_textView_register.setOnClickListener(this);

    }

    @Override
    public void onAvatarSuccess(AvatarBean bean) {
        Log.e(TAG,"onAvatarSuccess:"+bean.getBody().getAvatar());
        IsUtils.showShort(this,"上传成功");
//        edit.putString("nick",bean.getBody().getNick());
//        edit.putString("id",bean.getBody().getFullname());
//        edit.putString("avatar",bean.getBody().getAvatar());
//        edit.commit();
        Intent intent=new Intent(this,HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onAvatarError(Throwable t) {
        Log.e(TAG,"onAvatarError:"+t.toString());
    }
}
