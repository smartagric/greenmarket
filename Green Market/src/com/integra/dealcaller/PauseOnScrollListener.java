package com.integra.dealcaller;
// w w  w.  j  a va2s  . c o  m
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

import com.integra.dealcaller.BitmapTools;

public class PauseOnScrollListener implements OnScrollListener {

    private BitmapTools bitmapTools;

    private final boolean pauseOnScroll;
    private final boolean pauseOnFling;
    private final OnScrollListener externalListener;

    public PauseOnScrollListener(BitmapTools imageLoader, boolean pauseOnScroll, boolean pauseOnFling) {
        this(imageLoader, pauseOnScroll, pauseOnFling, null);
    }

    public PauseOnScrollListener(BitmapTools bitmapTools, boolean pauseOnScroll, boolean pauseOnFling,
            OnScrollListener customListener) {
        this.bitmapTools = bitmapTools;
        this.pauseOnScroll = pauseOnScroll;
        this.pauseOnFling = pauseOnFling;
        externalListener = customListener;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
        case OnScrollListener.SCROLL_STATE_IDLE:
            bitmapTools.resume();
            break;
        case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
            if (pauseOnScroll) {
                bitmapTools.pause();
            }
            break;
        case OnScrollListener.SCROLL_STATE_FLING:
            if (pauseOnFling) {
                bitmapTools.pause();
            }
            break;
        }
        if (externalListener != null) {
            externalListener.onScrollStateChanged(view, scrollState);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (externalListener != null) {
            externalListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }
}