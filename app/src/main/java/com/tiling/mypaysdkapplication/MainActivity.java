package com.tiling.mypaysdkapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.tiling.bitran_sdk.IntentBYManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_bitmap_compress)
    Button mBtnBitmapCompress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_bitmap_compress})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_bitmap_compress:
                // 去支付
                String mStrOrder = "你申请的订单号";
                IntentBYManager.startLaoYuanAppPay(MainActivity.this,mStrOrder);
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        IntentBYManager.destroy();
    }
}
