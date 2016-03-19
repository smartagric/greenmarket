package com.integra.dealcaller;



import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;




import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
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
import com.integra.dealcaller.Response.Listener;


import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
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
 
public class FragmentIntro1 extends Fragment implements OnClickListener {
		private static String username,password,token;
      ImageView ivIcon;
      int progress = 0;
      private View view;
      private static  Map<String, String> params;
      public static final String url_voda_request= "http://testplay.vodafonecash.com/SendSMS.php";
      public static final String url_voda_request2= "http://activetalkgh.com/android_connect/vodafone_api.php";
      ImageLoader imageLoader = AppController.getInstance().getImageLoader();
      private NetworkImageView imgview;
      private ProgressBar pb;
      private Button buy;
      ProgressDialog pDialog;
      private TextView cost, des;
     
     @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
         Bundle savedInstanceState) {
         // Inflate the layout for this fragment
  
    	    
    	    
   	 view = inflater.inflate(R.layout.fragment_into1, container, false);
   	 imgview = (NetworkImageView)view.findViewById(R.id.imageButton122);
   	 buy = (Button)view.findViewById(R.id.btnAddExpense2);
   	 cost = (TextView)view.findViewById(R.id.lblExpenseCancel);
   	 des = (TextView)view.findViewById(R.id.teks4);
   	pDialog= new ProgressDialog(getActivity());
   	 
   	 pb = (ProgressBar)view.findViewById(R.id.progressBarcpd);
   	 
   	 
   	 if (ShopListActivity.foto.matches("")){
   		pb.setVisibility(View.GONE);
   	 }else{
   		 cost.setText("GH¢ "+ShopListActivity.foto_name+".00");
   		 des.setText(ShopListActivity.foto_des);
   		 imgview.setImageUrl(ShopListActivity.foto, imageLoader);
   		 pb.setVisibility(View.GONE);
   	 }
   	 
   	 
       buy.setOnClickListener(new OnClickListener() {
		
    	   
		@Override
		public void onClick(View arg0) {
			
			 username = "711504";
	    	  password = "hackathon2";
	    	   token = "abc1234";
	    	
	    	  //new VodaRequest().execute();
	    	   
	    	   startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("file:///android_asset/vodafone.html")));
	    	  
	    	   //Intent tt = new Intent(getActivity(), WebPaymentLocal.class);
	    	   //startActivity(tt);
		}
	});
       
       
       
    		   
    		   
   	return view;
      }

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
	
	

	

	  
	
	
	
	 private class VodaRequest extends AsyncTask<Void, Void, String[]> {
		 
		 
		  @Override
		    protected void onPreExecute() {
		        
		        pDialog.setMessage("Preparing payment platform...");
	            pDialog.setIndeterminate(false);
	            pDialog.setCancelable(true);
	            pDialog.show();
		        
		        }

	        @Override
	        protected String[] doInBackground(Void... params) {
	            // Simulates a background job.
	        	byte[] result = null;
		         
	        	HttpClient client = new DefaultHttpClient();
		        HttpPost post = new HttpPost(url_voda_request);// in this case, params[0] is URL
		        try {
		            // set up post data
		            ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
		           
		             
		                nameValuePair.add(new BasicNameValuePair("username", "711504"));
		        	    nameValuePair.add(new BasicNameValuePair("password", "hackathon2"));
		        	    nameValuePair.add(new BasicNameValuePair("token", "abc1234"));
		        	    nameValuePair.add(new BasicNameValuePair("amount", cost.getText().toString()));
		            

		            post.setEntity(new UrlEncodedFormEntity(nameValuePair, "UTF-8"));
		            HttpResponse response = client.execute(post);
		            StatusLine statusLine = response.getStatusLine();
		            if(statusLine.getStatusCode() == HttpURLConnection.HTTP_OK){
		                result = EntityUtils.toByteArray(response.getEntity());
		                String  str = new String(result, "UTF-8");
		                
		                

		         Intent rr = new Intent(getActivity(), WebPaymentLocal.class);
		        rr.putExtra("response", str.toString());
		        startActivity(rr);
		        
		        pDialog.dismiss();
		        
		            }
		        }
		        catch (UnsupportedEncodingException e) {
		            e.printStackTrace();
		        }
		        catch (Exception e) {
		        }
				return null;
		     
		    }

	        @Override
	        protected void onPostExecute(String[] result) {
	            Toast.makeText(getActivity(), "async ending", Toast.LENGTH_LONG).show();

	          

	            super.onPostExecute(result);
	        }
	    }
}