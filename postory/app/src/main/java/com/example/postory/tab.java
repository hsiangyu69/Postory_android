package com.example.postory;

import com.example.postory.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;

public class tab extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.tab);
	
		FragmentTabHost tabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
		tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		//1
		
		tabHost.addTab(tabHost.newTabSpec("Aroundyou")
	   			  .setIndicator("Aroundyou"), 
		  MainActivity.class, 
		  null);
		
		//2
		tabHost.addTab(tabHost.newTabSpec("Search")
	   			  .setIndicator("Search"), 
		  Search.class, 
		  null);
		
		//3
		tabHost.addTab(tabHost.newTabSpec("AboutUs")
			   				  .setIndicator("AboutUs"), 
   					  AppleFragment.class, 
   					  null);
	    
	
	}

	
	
}