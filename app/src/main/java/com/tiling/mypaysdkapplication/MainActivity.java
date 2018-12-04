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
    @BindView(R.id.btn_bitmap_factory)
    Button mBtnBitmapFactory;

    private IntentBYManager mIntentBYManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_bitmap_compress, R.id.btn_bitmap_factory})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_bitmap_compress:
                // 去支付
                mIntentBYManager = IntentBYManager.getInstance();
                mIntentBYManager.startLaoYuanAppPay(MainActivity.this,"");
                break;
            case R.id.btn_bitmap_factory:
                // 去充值

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mIntentBYManager!=null){
            mIntentBYManager.destroy();
        }

    }
}
