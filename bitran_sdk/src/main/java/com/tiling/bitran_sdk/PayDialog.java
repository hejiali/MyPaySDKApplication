package com.tiling.bitran_sdk;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by hjl on 2018/9/27.
 */
public class PayDialog extends Dialog {

    public PayDialog(Context context) {
        super(context);
    }

    public PayDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder{

        private Context context;
        private boolean isCancelable=false;
        private boolean isCancelOutside=false;
        private String title;


        public Builder(Context context) {
            this.context = context;
        }

        /**
         * 设置是否可以按返回键取消
         * @param isCancelable
         * @return
         */

        public Builder setCancelable(boolean isCancelable){
            this.isCancelable=isCancelable;
            return this;
        }

        public Builder setTitle(String title){
            this.title= title;
            return this;
        }

        /**
         * 设置是否可以取消
         * @param isCancelOutside
         * @return
         */
        public Builder setCancelOutside(boolean isCancelOutside){
            this.isCancelOutside=isCancelOutside;
            return this;
        }

        public PayDialog create(){

            LayoutInflater inflater = LayoutInflater.from(context);
            View view=inflater.inflate(R.layout.dialog_ly_loading,null);
            PayDialog payDialog = new PayDialog(context,R.style.Custom_Progress);

            payDialog.setContentView(view);
            payDialog.setCancelable(isCancelable);
            payDialog.setCanceledOnTouchOutside(isCancelOutside);

            WindowManager.LayoutParams lp = payDialog.getWindow().getAttributes();
            // 设置背景层透明度
            lp.dimAmount = 0.2f;
            payDialog.getWindow().setAttributes(lp);

            return  payDialog;

        }


    }
}
