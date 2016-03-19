package com.integra.dealcaller;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;
import com.integra.dealcaller.R;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

public class MultiScrollView extends ScrollView {

private boolean isScrollable;

public MultiScrollView(Context context)
{
    super(context);
    isScrollable=true;
}

public MultiScrollView(Context context, AttributeSet attrs) {
    super(context, attrs);
    // TODO Auto-generated constructor stub
    isScrollable=true;
}
public MultiScrollView(Context context, AttributeSet attrs,int defStyle)
{
    super(context, attrs, defStyle);
    isScrollable=true;
}
public boolean isScrollable()
{
    return isScrollable;
}
public boolean setScrollable(boolean mScrollable)
{
    return mScrollable=isScrollable;
}
}