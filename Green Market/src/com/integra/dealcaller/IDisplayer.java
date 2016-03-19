package com.integra.dealcaller;
/*w  w  w.j  av a  2 s . c  om*/
import android.graphics.Bitmap;
import android.view.View;

import com.integra.dealcaller.BitmapTools.BitmapDisplayConfig;

public interface IDisplayer {
    void loadCompletedisplay(View imageView, Bitmap bitmap, BitmapDisplayConfig config);

    void loadFailDisplay(View view, BitmapDisplayConfig config);

    void loadDefaultDisplay(View view, BitmapDisplayConfig config);
}