package com.miku.ktv.miku_android.view.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.miku.ktv.miku_android.R;
import com.miku.ktv.miku_android.model.bean.AvatarBean;
import com.miku.ktv.miku_android.model.bean.HeartBean;
import com.miku.ktv.miku_android.model.utils.Constant;
import com.miku.ktv.miku_android.model.utils.IsUtils;
import com.miku.ktv.miku_android.model.utils.ZipImageUtil;
import com.miku.ktv.miku_android.presenter.HeartPresenter;
import com.miku.ktv.miku_android.presenter.UpdateAvatarPresenter;
import com.miku.ktv.miku_android.view.custom.CircleImage;
import com.miku.ktv.miku_android.view.iview.IHeartView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class EditActivity extends Activity implements IHeartView<HeartBean>, View.OnClickListener {
    public static final String TAG = "EditActivity";

    private ImageView edit_imageView_back;
    private CircleImage edit_circle_head;
    private TextView edit_textView_nick;
    private TextView edit_textView_id;
    private RelativeLayout edit_relativeLayout_head;
    private RelativeLayout edit_relativeLayout_nick;
    private RelativeLayout edit_relativeLayout_sign;
    private TextView dialog_textView_camera;
    private TextView dialog_textView_album;
    private TextView dialog_textView_cancel;
    private Dialog bottomDialog;
    private SharedPreferences sp;
    private SharedPreferences.Editor edit;
    private HeartPresenter heartPresenter;

    private File file;//存储拍摄图片的文件
    private static final int PICTURE_FROM_CAMERA = 0X32;
    private static final int PICTURE_FROM_GALLERY = 0X34;
    private static final int CROP_SMALL_PICTURE = 2;

    private Bitmap mBitmap;
    private UpdateAvatarPresenter updatePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        edit = sp.edit();
        initState();
        initView();
        initListener();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Edit_ImageView_Back:

                Bitmap bmp=drawableToBitamp(edit_circle_head.getDrawable());
                Intent intentToPersonal=new Intent();
                ByteArrayOutputStream baos=new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte [] bitmapByte =baos.toByteArray();
                intentToPersonal.putExtra("bitmapintentToPersonal", bitmapByte);

                String imageString=new String(Base64.encode(bitmapByte, 0));
                edit.putString("bitmapByte", imageString).commit();

                intentToPersonal.putExtra("newNickIntent",sp.getString("newNickToPersonal",""));
                setResult(6,intentToPersonal);
                finish();
                break;
            case R.id.Edit_RelativeLayout_Head:
                //底部弹出dialog
                bottomDialog();
                break;
            case R.id.Edit_RelativeLayout_Nick:
                startActivityForResult(new Intent(this,EditNickActivity.class),3);
                break;
            case R.id.Edit_RelativeLayout_Sign:
                startActivity(new Intent(this,EditSignActivity.class));
                finish();
                break;

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 4) {
            switch (requestCode) {
                case 3:
                    if (data == null) {
                        Log.d("EditActivity", "data为空");
                        return;
                    } else {
                        String newNick=data.getStringExtra("newNick");
                        Log.d("EditActivity", "onActivityResult: "+newNick);
                        edit_textView_nick.setText(newNick);
                        edit.putString("newNickToPersonal",edit_textView_nick.getText().toString());
                        edit.commit();
                    }
                    break;

                default:
                    break;
            }
        } else if (resultCode == RESULT_OK) {
            switch (requestCode) {
                //相机
                case PICTURE_FROM_CAMERA:
                    //对图片进行压缩
                    ZipImageUtil.zipImage(Uri.fromFile(file).getPath());
                    //将图片设置到ImageView中
                    edit_circle_head.setImageURI(Uri.fromFile(file));

                    edit_circle_head.setDrawingCacheEnabled(true);


                    //头像上传服务器
                    if (file!=null){
                        updatePresenter.postAvatar(sp.getString("LoginToken",""),file, AvatarBean.class);
                    }

                    Log.d(TAG, "onActivityResult: +相机");
                    break;
                //相册
                case PICTURE_FROM_GALLERY:
                    //通过返回的data数据，获取图片的路径信息，但是这个路径是Uri的
                    Uri uri = data.getData();
                    //进行压缩，首先要将Uri地址转换为真实路径
                    File file2 = getFilePath(uri);
                    this.file = file2;
                    //压缩图片
                    ZipImageUtil.zipImage(file2.getAbsolutePath());
                    edit_circle_head.setImageURI(Uri.fromFile(file2));

                    if (file!=null){
                        updatePresenter.postAvatar(sp.getString("LoginToken",""),file, AvatarBean.class);
                    }

                    Log.d(TAG, "onActivityResult: +相册");
                    break;
            }
        }

    }

    private Bitmap drawableToBitamp(Drawable drawable) {
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        // 取drawable的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ?Bitmap.Config.ARGB_8888:Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(w,h,config);
        Canvas canvas = new Canvas(bitmap);// 建立对应bitmap的画布
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);// 把drawable内容画到画布中
        return bitmap;
    }

    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void onAvatarSuccess(AvatarBean bean) {
        if (bean.getStatus()==1){
            Log.d(TAG, "onAvatarSuccess: "+bean.getBody().getAvatar());
            IsUtils.showShort(this,"上传成功");
        }else {
            IsUtils.showShort(this,"上传失败");
        }
    }


    @Override
    public void onAvatarError(Throwable t) {
        Log.d(TAG, "onAvatarError: "+t.getMessage());
    }

    private void bottomDialog() {
        bottomDialog = new Dialog(this, R.style.BottomDialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(this).inflate(
                R.layout.edit_dialog, null);
        root.findViewById(R.id.Dialog_TextView_Cancel).setOnClickListener(btnlistener);
        root.findViewById(R.id.Dialog_TextView_Camera).setOnClickListener(btnlistener);
        root.findViewById(R.id.Dialog_TextView_Album).setOnClickListener(btnlistener);
        bottomDialog.setContentView(root);
        Window dialogWindow = bottomDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.BottomDialog_Animation); // 添加动画
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.y = 20;//设置Dialog距离底部的距离
        dialogWindow.setAttributes(lp);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.show();
    }

    private View.OnClickListener btnlistener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                    //拍照
                case R.id.Dialog_TextView_Camera:
                    IsUtils.showShort(EditActivity.this,"拍照");
                    bottomDialog.dismiss();
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

                    break;
                    //相册
                case R.id.Dialog_TextView_Album:
                    IsUtils.showShort(EditActivity.this,"相册");
                    bottomDialog.dismiss();

                    Intent intent2 = new Intent();
                    //设置启动相册的Action
                    intent2.setAction(Intent.ACTION_GET_CONTENT);
                    //设置类型
                    intent2.setType("image/*");
                    //启动相册，这里使用有返回结果的启动
                    startActivityForResult(intent2, PICTURE_FROM_GALLERY);

                    break;
                    //取消
                case R.id.Dialog_TextView_Cancel:
                    if (bottomDialog != null) {
                        bottomDialog.dismiss();
                    }
                    break;
            }
        }
    };


    private File getFilePath(Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor actualimagecursor = managedQuery(uri, proj, null, null, null);
        int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        actualimagecursor.moveToFirst();
        String img_path = actualimagecursor.getString(actual_image_column_index);
        return new File(img_path);
    }


    private void initListener() {
        edit_imageView_back.setOnClickListener(this);
        edit_relativeLayout_head.setOnClickListener(this);
        edit_relativeLayout_nick.setOnClickListener(this);
        edit_relativeLayout_sign.setOnClickListener(this);
    }

    private void initView() {
        edit_imageView_back = (ImageView) findViewById(R.id.Edit_ImageView_Back);
        edit_circle_head = (CircleImage) findViewById(R.id.Edit_Circle_Head);
        edit_textView_nick = (TextView) findViewById(R.id.Edit_TextView_Nick);
        edit_textView_id = (TextView) findViewById(R.id.Edit_TextView_ID);
        edit_relativeLayout_head = (RelativeLayout) findViewById(R.id.Edit_RelativeLayout_Head);
        edit_relativeLayout_nick = (RelativeLayout) findViewById(R.id.Edit_RelativeLayout_Nick);
        edit_relativeLayout_sign = (RelativeLayout) findViewById(R.id.Edit_RelativeLayout_Sign);

        heartPresenter = new HeartPresenter();
        heartPresenter.attach(this);

        updatePresenter = new UpdateAvatarPresenter();
        updatePresenter.attach(this);

        HashMap<String,String> map=new HashMap<>();
        map.put("token",sp.getString("LoginToken",""));
        heartPresenter.getHeart(map,HeartBean.class);

        edit_textView_id.setText(sp.getString("FullnameMain",""));

        String s = Constant.BASE_PIC_URL + sp.getString("AvatarMain", "");
        Glide.with(this)
                .load(s)
                .placeholder(R.mipmap.bg9)
                .error(R.mipmap.bg9)
                .into(edit_circle_head);
    }

    private void initState() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void onSuccess(HeartBean heartBean) {
        if (heartBean.getStatus()==1){
            IsUtils.showShort(this,TAG+"请求成功");
            edit_textView_nick.setText(heartBean.getBody().getNick());
            String s2 = Constant.BASE_PIC_URL + heartBean.getBody().getAvatar()+"";
            Glide.with(this)
                    .load(s2)
                    .placeholder(R.mipmap.bg9)
                    .error(R.mipmap.bg9)
                    .into(edit_circle_head);

        }else {
            IsUtils.showShort(this,TAG+"请求失败");
        }
    }

}
