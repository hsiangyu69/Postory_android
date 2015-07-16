package com.example.postory;

import com.example.postory.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;


public class AppleFragment extends Fragment {
	
	private String value = "";
	private WebView wv;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 final View aboutus =inflater.inflate(R.layout.frg_apple, container, false);
		wv=(WebView)aboutus.findViewById(R.id.webView1);
		wv.setWebViewClient(mWebViewClient);
		wv.loadUrl("file:///android_asset/teampage.png");
		WebSettings settings = wv.getSettings(); 
		settings.setUseWideViewPort(true); 
        settings.setLoadWithOverviewMode(true);
		Log.d("=====>", "AppleFragment onCreateView");
		return aboutus;
	}
	 WebViewClient mWebViewClient = new WebViewClient() {
		  @Override
		  public boolean shouldOverrideUrlLoading(WebView view, String url) {
		   view.loadUrl(url);
		   return true;
		  }
		 };

	
	
}
