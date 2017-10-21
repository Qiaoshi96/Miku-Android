package com.miku.ktv.miku_android.model.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.miku.ktv.miku_android.R;
import com.miku.ktv.miku_android.view.custom.ProgressDialogLoading;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/7/31.
 */

public class IsUtils {
    private static final int PASS_LOW_LIMIT = 6;
    private static final int PASS_HIGH_LIMIT = 16;

    //判空
    public static boolean isNull(String msg){
        if(msg==null||"".equals(msg)||msg.length()==0||"null".endsWith(msg.toLowerCase())){
            return false;
        }else{
            return true;
        }
    }

    //手机号
    public static boolean validatePhoneNumber(String mobiles) {
        String telRegex = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(147,145))\\d{8}$";
        return !TextUtils.isEmpty(mobiles) && mobiles.matches(telRegex);
    }

    /**
     * 验证密码是否合法　６－１６位
     */
    public static boolean validatePass(String password) {
        return password.length() >= PASS_LOW_LIMIT && password.length() <= PASS_HIGH_LIMIT;
    }

    //密码不能为汉字
    public static boolean checkChinese(String password){
        Pattern  p=Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(password);
        return m.matches();
    }

    //吐司
    public static void showShort(Context context, String s){
        Toast.makeText(context,s, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(Context context, String s){
        Toast.makeText(context,s, Toast.LENGTH_LONG).show();
    }

    public static void showCustomDialog(Context context){
        ProgressDialogLoading mLoading=new ProgressDialogLoading(context, R.style.CustomProgressDialog);
        mLoading.show();
    }

    public static void dismissCustomDialog(Context context){
        ProgressDialogLoading mLoading=new ProgressDialogLoading(context, R.style.CustomProgressDialog);
        mLoading.dismiss();
    }

    public static boolean IsSex(String sex){
        if(sex.equals("男")||sex.equals("女")){
            return true;
        }
        return false;
    }

    public static void setWindowStatusBarColor(Activity activity, int colorResId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(activity.getResources().getColor(colorResId));

                //底部导航栏
                window.setNavigationBarColor(activity.getResources().getColor(colorResId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setWindowStatusBarColor(Dialog dialog, int colorResId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = dialog.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(dialog.getContext().getResources().getColor(colorResId));

                //底部导航栏
                window.setNavigationBarColor(dialog.getContext().getResources().getColor(colorResId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
