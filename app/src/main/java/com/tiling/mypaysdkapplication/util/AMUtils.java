package com.tiling.mypaysdkapplication.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tiling.mypaysdkapplication.R;

/**
 * Created by hjl on 18/12/4.
 * Company tiling
 */
public class AMUtils {

    public static void glideLoadImage(Context context, String uri, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.load_default)// 正在加载中的图片
                .error(R.drawable.load_default); // 加载失败的图片
        Glide.with(context)
                .load(uri)
                .apply(options)
                .into(imageView);
    }

}
