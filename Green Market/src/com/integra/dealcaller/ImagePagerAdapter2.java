package com.integra.dealcaller;

import java.io.ByteArrayOutputStream;
import java.net.URI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.integra.dealcaller.R;
import com.integra.dealcaller.FeedListAdapter2.ShareMethod;

public class ImagePagerAdapter2 extends PagerAdapter {
    
    
    
    
    private int res[] = { R.drawable.lep1, R.drawable.lep2,
			R.drawable.lep3, R.drawable.lep4,
			R.drawable.lep5, R.drawable.lep6,
			R.drawable.lep7, R.drawable.lep8 };
    
  
    int NumberOfPages = res.length;
    LayoutInflater mInflater;
    
    ImagePagerAdapter2(Context c) {
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() { return NumberOfPages; }
    @Override
    public boolean isViewFromObject(View view, Object object) { return view == object; }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v = mInflater.inflate(R.layout.image2, container, false);
        ImageView imageView = (ImageView) v.findViewById(R.id.imageViewb);
        imageView.setImageResource(res[position]);
        container.addView(v);
        return v;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}