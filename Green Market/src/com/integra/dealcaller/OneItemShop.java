package com.integra.dealcaller;



import java.io.UnsupportedEncodingException;




import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Cache;
import com.android.volley.Cache.Entry;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;


import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
 
public class OneItemShop extends ActionBarActivity implements OnClickListener {
 
      ImageView ivIcon;
      private Button b4, buy;
      
      private String photo, description,sme_id,price;
      
     int progress = 0;
     ImageLoader imageLoader = AppController.getInstance().getImageLoader();
     TextView teks,cost;
     private NetworkImageView imgview;
     private ProgressBar pb;
    
     @Override
     protected void onCreate(Bundle savedInstanceState) {
  	   //TODO Auto-generated method stub
  	   super.onCreate(savedInstanceState);
  	   getSupportActionBar().setTitle("Buy Item");
  	   setContentView(R.layout.one_item_shop);
  	   
  	   
  	 Bundle i = getIntent().getExtras();
     if (i != null) {
   	  
     photo = i.getString("photo");
     description = i.getString("description");
     sme_id = i.getString("sme_id");
     price = i.getString("price");
     
          }
  	   
  	 imgview = (NetworkImageView)findViewById(R.id.imageButton122);
  	 pb = (ProgressBar)findViewById(R.id.progressBarcpd);
  	 buy = (Button)findViewById(R.id.btnAddExpense2);
  	 teks = (TextView)findViewById(R.id.teks4);
  	 cost = (TextView)findViewById(R.id.lblExpenseCancel);
  	 
  	 if (photo.matches("")){
  		pb.setVisibility(View.GONE);
  	 }else{
  		 imgview.setImageUrl(photo, imageLoader);
  		 teks.setText(description);
  		cost.setText("GH¢ "+price+".00");
  		 pb.setVisibility(View.GONE);
  	 }
  	 
  	 
  	 
  	 
     buy.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			CustomDialogClass cdd=new CustomDialogClass(OneItemShop.this);
			cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			cdd.show();
		}
	});    
   
      }

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
}