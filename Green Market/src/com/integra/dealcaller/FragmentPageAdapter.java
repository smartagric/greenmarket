package com.integra.dealcaller;

import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.MotionEvent;

public class FragmentPageAdapter extends FragmentPagerAdapter  {

	private boolean enabled;
	
	private final String[] TITLES = {"Showcases"};
	
	public FragmentPageAdapter(android.support.v4.app.FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
		this.enabled = true;
	}

	public  Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		
		switch (arg0) {
		
		case 0:
			return new LineupFragment();
			
			
			
		//case 1:
			//return new TimeLineFragment();
			
			
		//case 2:
			
			//return new LineupFragment();
			
		//case 3:
			
			//return new FriendsFragment();
			
			
		//case 4:
			
			
			//return new CommitmentFragment();
			

		default:
			break;
		}
		return null;
	}
	
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 1;
	}


	@Override
	public CharSequence getPageTitle(int position) {
		return TITLES[position];
	}

	


    public void setPagingEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}



