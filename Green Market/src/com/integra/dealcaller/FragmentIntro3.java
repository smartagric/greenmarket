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
 
public class FragmentIntro3 extends Fragment implements OnClickListener {
 
      ImageView ivIcon;
   
      private View view;
      private Button buy;
      
     int progress = 0;
     ImageLoader imageLoader = AppController.getInstance().getImageLoader();
     private NetworkImageView imgview;
     private ProgressBar pb;
     private TextView cost,des;
    
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        // Inflate the layout for this fragment
 
   	    
   	    
  	 view = inflater.inflate(R.layout.fragment_intro3, container, false);
  	imgview = (NetworkImageView)view.findViewById(R.id.imageButton122);
  	 buy = (Button)view.findViewById(R.id.btnAddExpense2);
  	cost = (TextView)view.findViewById(R.id.lblExpenseCancel);
  	 des = (TextView)view.findViewById(R.id.teks4);
  	 
  	 pb = (ProgressBar)view.findViewById(R.id.progressBarcpd);
  	 
  	 
  	 if (ShopListActivity.foto3.matches("")){
  		pb.setVisibility(View.GONE);
  	 }else{
  		 cost.setText("GH¢ "+ShopListActivity.foto3_name+".00");
  		 des.setText(ShopListActivity.foto3_des);
  		 imgview.setImageUrl(ShopListActivity.foto3, imageLoader);
  		 pb.setVisibility(View.GONE);
  	 }
  	 
  	 
      buy.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			CustomDialogClass cdd=new CustomDialogClass(getActivity());
			cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			cdd.show();
		}
	});
       
   	return view;
      }

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
}