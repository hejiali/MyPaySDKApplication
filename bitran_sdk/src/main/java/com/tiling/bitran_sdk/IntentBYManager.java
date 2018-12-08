package com.tiling.bitran_sdk;

import android.app.Activity;
import android.app.Fragment;
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
import android.widget.ImageView;

/**
 * @author hjl
 * date 2018/10/20
 * time 8:52
 * des: 封装的跳转钱包管理类
 */
public class IntentBYManager {

    public static final int ACTIVITY_START_LY_APP = 6001;
    private static PayDialog mPayDialog;

    /**
     * 仿支付宝显示加载dialog
     */
    private static void showPayDialog(final Activity activity, final String mStrOrder) {
        if (activity == null) {
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
                dismissProgressDialog();
                Intent intent1 = new Intent();
                intent1.setAction(activity.getString(R.string.ly_pay_action));
                intent1.addCategory(activity.getString(R.string.ly_pay_category));
                Bundle bundle = new Bundle();
                intent1.putExtra(activity.getString(R.string.params_json), mStrOrder);
                intent1.putExtras(bundle);
                activity.startActivityForResult(intent1, ACTIVITY_START_LY_APP);
            }
        }, 2000);
    }

    private static void dismissProgressDialog() {
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
    private static boolean checkPackInfo(Context context, String packageName) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo != null;
    }

    public static void startLaoYuanAppPay(final Fragment fragment, String mStrOrder) {
        startLaoYuanAppPay(fragment.getActivity(), mStrOrder);
    }

    // 去支付
    public static void startLaoYuanAppPay(final Activity activity, String mStrOrder) {

        String packageName = activity.getString(R.string.package_name);

        if (checkPackInfo(activity, packageName)) {
            showPayDialog(activity,mStrOrder);

        } else {
            new AlertDialog.Builder(activity)
                    .setTitle(R.string.tip)
                    .setMessage(activity.getString(R.string.has_not_loading_ly))
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            goToMarket(activity);
                        }
                    })
                    .create().show();
        }
    }

    //这里是进入应用商店，下载指定APP的方法。
    private static void goToMarket(Context context) {
        Uri uri = Uri.parse(context.getString(R.string.load_apk_url));
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(goToMarket);
        } catch (Exception e) {
        }
    }

    public static void destroy(){
        if (mPayDialog != null){
            mPayDialog.dismiss();
            mPayDialog = null;
        }

    }



}
