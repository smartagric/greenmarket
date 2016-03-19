package com.integra.dealcaller;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.integra.dealcaller.R;

public class MyMessagesListAdapter extends BaseAdapter {
	private Activity activity;
	private LayoutInflater inflater;
	private List<MovieMyMessages> movieItems;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	public MyMessagesListAdapter(Activity activity, List<MovieMyMessages> movieItems) {
		this.activity = activity;
		this.movieItems = movieItems;
	}

	
	

	public int getCount() {
		return movieItems.size();
	}

	public Object getItem(int location) {
		return movieItems.get(location);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		if (inflater == null)
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.mymessages_row, null);

		if (imageLoader == null)
			imageLoader = AppController.getInstance().getImageLoader();
		NetworkImageView thumbNail = (NetworkImageView) convertView
				.findViewById(R.id.thumbnailmy);
		TextView title = (TextView) convertView.findViewById(R.id.titlemy);
		TextView rating = (TextView) convertView.findViewById(R.id.ratingmy);
		TextView genre = (TextView) convertView.findViewById(R.id.genremy);
		TextView year = (TextView) convertView.findViewById(R.id.releaseYearmy);
	

		// getting movie data for the row
		MovieMyMessages m = movieItems.get(position);

		// thumbnail image
		thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);
	
		
		// title
		title.setText(m.getTitle());
		
		// rating
		rating.setText("Rating: " + String.valueOf(m.getRating()));
		
		// genre
		
		genre.setText(String.valueOf(m.getGenre()));
		
		// release year
		year.setText(String.valueOf(m.getYear()));

		return convertView;
	}

}