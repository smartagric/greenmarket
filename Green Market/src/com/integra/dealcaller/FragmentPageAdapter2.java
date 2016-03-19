package com.integra.dealcaller;

import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.MotionEvent;

public class FragmentPageAdapter2 extends FragmentPagerAdapter  {

	private boolean enabled;
	
	private final String[] TITLES = { "Intro1", "Intro2","Intro3", "Intro4"};
	
	public FragmentPageAdapter2(android.support.v4.app.FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
		this.enabled = true;
	}

	public  Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		
		switch (arg0) {
		
		case 0:
			
			return new FragmentIntro1();
			
		case 1:
			return new FragmentIntro2();
			
			
		case 2:
			return new FragmentIntro3();
			
			
		case 3:
			
			return new FragmentIntro4();
			
			
		case 4:
			
			
			return new FragmentIntro5();
			

		default:
			break;
		}
		return null;
	}
	
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 5;
	}


	@Override
	public CharSequence getPageTitle(int position) {
		return TITLES[position];
	}

	


    public void setPagingEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}



