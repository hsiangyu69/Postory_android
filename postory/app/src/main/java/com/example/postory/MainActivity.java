package com.example.postory;
 
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView; 
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;






import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class MainActivity extends Fragment implements LocationListener {
 
	 
	GoogleMap googleMap;
	PhotoInfo[] result;
	 View aroundview;
	 WebView map;
	Boolean webviewReady;
	DisplayImageOptions options;
	ImageLoader imageLoader = ImageLoader.getInstance();

	@SuppressLint("SetJavaScriptEnabled")
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		aroundview= inflater.inflate(R.layout.activity_main,container, false);
		 final ProgressDialog dialog = ProgressDialog.show(getActivity(),
                 "Loading", "請稍等...",true);
         new Thread(new Runnable(){
             @Override
             public void run() {
                 try{
                     Thread.sleep(10000);
                 }
                 catch(Exception e){
                     e.printStackTrace();
                 }
                 finally{
                     dialog.dismiss();
                 }
             } 
        }).start();       
     // ImageLoader 
       options = new DisplayImageOptions.Builder()
        	.showStubImage(R.drawable.ic_stub)
        	.showImageForEmptyUri(R.drawable.ic_empty)
        	.showImageOnFail(R.drawable.ic_error)
			.cacheInMemory()
			.cacheOnDisc()
			.build();
        
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity().getApplicationContext())
        	.defaultDisplayImageOptions(options)
        	.build();
        imageLoader.init(config);
        
        map=(WebView)aroundview.findViewById(R.id.map);
        map.loadUrl("file:///android_asset/googlemap3.html");
        map.getSettings().setJavaScriptEnabled(true);
        
        map.setWebViewClient(new WebViewClient(){ 
            @Override 
            public void onPageFinished(WebView view, String url) 
            {
              //webView.loadUrl(centerURL);
             webviewReady = true;//webview is onload
            }

          });
    

            LocationManager locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
 
            // Creating a criteria object to retrieve provider
            Criteria criteria = new Criteria();
            
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            criteria.setAltitudeRequired(false);
            criteria.setBearingRequired(false);
            criteria.setCostAllowed(true);
            criteria.setPowerRequirement(Criteria.POWER_LOW);
 
            // Getting the name of the best provider
            String provider = locationManager.getBestProvider(criteria, true);
 
            // Getting Current Location
           // Location location = locationManager.getLastKnownLocation(provider);
            //onLocationChanged(location);
            locationManager.requestLocationUpdates(provider, 300000, 100, this);
            //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 300000, 100, this);

            
           /* if(location!=null){
               onLocationChanged(location);
            }*/
           
            GridView listView = (GridView)aroundview.findViewById(R.id.gridView1);
            
    		 listView.setOnItemClickListener(new OnItemClickListener() {
    				
    				@Override
    				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    					startImagePagerActivity(position);
    					
    				}
    			});
    		
           // locationManager.requestLocationUpdates(provider, 300000, 100, this);
	
		return aroundview;
    } 
    
	
	
	@SuppressLint("SetJavaScriptEnabled")
	@Override
    public void onLocationChanged(Location location) { 
 
		if(location!=null)
		{
        GridView listView = (GridView)aroundview.findViewById(R.id.gridView1);
       
        
        // Getting latitude of the current location
        double latitude = location.getLatitude(); 
       
        // Getting longitude of the current location
        double longitude = location.getLongitude(); 
 
 
        
        
        // Setting latitude and longitude in the TextView 
        String s_latitude = String.valueOf(latitude); //convert double to String
        String s_longitude = String.valueOf(longitude);//convert double to String
        //getlatitude.setText(s_latitude);
        //getlongitude.setText(s_longitude);
        String centerURL = "javascript:centerAt(" +latitude + "," + longitude+ ")";
        if (webviewReady) {
        	map.loadUrl(centerURL);
        }
        else
        {
        	map.loadUrl(centerURL);
        }
        
        
      	
        result = instagramparser.executeURL("https://api.instagram.com/v1/locations/search?lat="+s_latitude+"&lng="+s_longitude+"&distance=500&access_token=1297877652.5b9e1e6.c3e85ea9cf704ad391639d838a263534");
         listView.invalidate(); //refresh listview
        ((GridView) listView).setAdapter(new ImageAdapter(getActivity(), result));
		}
    }
	
	
	private void startImagePagerActivity(int position) {
		
		
		 
		Intent intent = new Intent(getActivity(), ImagePagerActivity.class);
		
		intent.putExtra("IMAGES", result[position]);
		intent.putExtra("postion",position);
		//intent.putExtra("location_name", location);
		//intent.putExtra("location_name", location_name.getText().toString());
		startActivity(intent);
	}
 
    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }
 
    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }
 
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }
 
    //@Override
   /* public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/
    
}