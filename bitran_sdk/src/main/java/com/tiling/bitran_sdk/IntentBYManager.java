package com.tiling.bitran_sdk;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.tiling.bitran_sdk.dialog.LoadingDialog;
import com.tiling.bitran_sdk.dialog.PayDialog;

/**
 * @author hjl
 * date 2018/10/20
 * time 8:52
 * des: 封装的跳转钱包管理类
 */
public class IntentBYManager {

    public static final int ACTIVITY_START_LY_APP = 1003;
    public static final String LY_PAY ="ly_pay";

    private static IntentBYManager mIntentBYManager;

    private IntentBYManager(){}

    public static IntentBYManager getInstance(){

        if (mIntentBYManager == null){
            synchronized (IntentBYManager.class) {
                if (mIntentBYManager == null){
                    mIntentBYManager = new IntentBYManager();
                }
            }
        }
        return mIntentBYManager;
    }

    /**
     * 仿支付宝显示加载dialog
     */


    //加载框变量
    private ProgressDialog progressDialog;
    private void showProgressDialog(Activity activity, String mStrOrder) {
        if (activity == null) {
            Log.d("hjl", "activity == null");
            return ;
        }


        if (progressDialog == null) {
            progressDialog = new ProgressDialog(activity);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        progressDialog.setMessage("跳转支付中...");    //设置内容
        progressDialog.setCancelable(false);//点击屏幕和按返回键都不能取消加载框
        if (!activity.isFinishing()) {
            progressDialog.show();
        }



    }

    private PayDialog mPayDialog;
    private void showPayDialog(final Activity activity, final String mStrOrder) {
        if (activity == null) {
            Log.d("hjl", "activity == null");
            return ;
        }

        if (mPayDialog == null) {
            mPayDialog = new PayDialog.Builder(activity)
                    .setCancelable(false)
                    .create();
        }
        mPayDialog.show();
        ImageView ivBg = mPayDialog.findViewById(R.id.spinnerImageView);
        ((AnimationDrawable) ivBg.getBackground()).start();
        //设置两秒后自动消失
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //取消加载框
                IntentBYManager.this.dismissProgressDialog();
                Intent intent1 = new Intent();
                intent1.setAction("com.example.intent.action.LAOYUAN_PAY_ACTION");
                intent1.addCategory("com.example.intent.category.LAOYUAN_PAY_CATEGORY");
                Bundle bundle = new Bundle();
                intent1.putExtra("flag", LY_PAY);
                intent1.putExtra("paramsJson", mStrOrder);
                intent1.putExtras(bundle);
                activity.startActivityForResult(intent1, ACTIVITY_START_LY_APP);
            }
        }, 2000);
    }

    private void dismissProgressDialog() {
        if (mPayDialog != null){
            if (mPayDialog.isShowing()) {
                mPayDialog.dismiss();
            }
        }
    }


    /**
     * 检查包是否存在
     *
     * @param packageName
     * @return
     */
    private boolean checkPackInfo(Context context, String packageName) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo != null;
    }

    // 去支付
    public void startLaoYuanAppPay(final Activity activity, String mStrOrder) {
        mStrOrder = "{\"tradeAmount\":1000,\"charset\":\"utf-8\",\"orderNo\":\"20181203201029154383902998622\",\"openId\":\"20181201001\",\"callBack\":\"http://excwww.houge666.com/api/v1/trade/recharge\",\"sign\":\"09e33fde420eeff4b1c6ed24d0d5df68\",\"version\":\"1.0\",\"timestamp\":1543839029988}";
        String packageName = "com.laoyuan.bitcoinwallet";

        if (checkPackInfo(activity, packageName)) {
       //     showProgressDialog(activity,mStrOrder);
            showPayDialog(activity,mStrOrder);

        } else {
            new AlertDialog.Builder(activity)
                    .setTitle("提示")
                    .setMessage("你还未下载Bitran Pay，是否立即去下载？")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            goToMarket(activity);
                        }
                    })
                    .create().show();
        }
    }


    private void startLaoYuanPay(String mStrOrder) {



    }

    //这里是进入应用商店，下载指定APP的方法。
    private void goToMarket(Activity activity) {
        Uri uri = Uri.parse("https://lybwww.houge666.com/upload/lyb-ui/20181129/8f1e931f-8fd6-4cd7-862d-4987f3c8b89d.apk");
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            activity.startActivity(goToMarket);
        } catch (Exception e) {
        }
    }

    public void destroy(){
        if (progressDialog != null){
            progressDialog.dismiss();
            progressDialog = null;
        }
        if (mPayDialog != null){
            mPayDialog.dismiss();
            mPayDialog = null;
        }

    }



}
