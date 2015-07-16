package com.example.postory;



import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


public class Search extends Fragment{
	 EditText location;
	 Button search;
	PhotoInfo[] result;
	
	DisplayImageOptions options;
	ImageLoader imageLoader = ImageLoader.getInstance();
   
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		final View searchview= inflater.inflate(R.layout.search,container, false);
		location = (EditText)searchview.findViewById(R.id.location);
        search=(Button)searchview.findViewById(R.id.search);
        //search.setOnClickListener(searchphoto);
       // GridView listView = (GridView)searchview.findViewById(R.id.gridView1);
	
        // ImageLoader 初始設定
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
		
       
        search.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	String location_name=location.getText().toString();
            	String encode_name = null;
    			try {
    				encode_name=URLEncoder.encode(location_name, "UTF-8"); //中文字需要編碼
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    				imm.hideSoftInputFromWindow(location.getWindowToken(), 0); //隱藏虛擬鍵盤
            	
            	String location_url="https://api.instagram.com//v1/tags/"+encode_name+"/media/recent?access_token=46723307.1fb234f.14251839703d4581950c9d26d0eb8b0b";
					
				
					// TODO Auto-generated catch block
					
    			result=searchparser.executeURL(location_url);
    			
    			if(result.length==0)
    			{
    				GridView listView = (GridView)searchview.findViewById(R.id.gridView1);
    				listView.setVisibility(View.INVISIBLE);
    				TextView message=(TextView)searchview.findViewById(R.id.message);
    				message.setVisibility(View.VISIBLE);
    				message.setText("找不到照片");
    			}
    			else
    			{
    			TextView message=(TextView)searchview.findViewById(R.id.message);
    			message.setVisibility(View.INVISIBLE);
    			
    			  GridView listView = (GridView)searchview.findViewById(R.id.gridView1);
    			  listView.setVisibility(View.VISIBLE);
    				((GridView) listView).setAdapter(new ImageAdapter(getActivity(), result));
    			
    				
    				 listView.setOnItemClickListener(new OnItemClickListener() {
    					 	@Override
    						public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    							startImagePagerActivity(position);
    							//Toast.makeText(getActivity(), "POS"+position, Toast.LENGTH_LONG).show();
    							//點了之後顯示圖片資訊
    						}
    					});
    			}
            	

            }
         });
        
		// Inflate the layout for this fragment
		return searchview;
	}
	
	
	
	
	
	
	private void startImagePagerActivity(int position) {
		
		
		 
		Intent intent = new Intent(getActivity(), ImagePagerActivity.class);
		//intent.putExtra("IMAGES", result[position].getphoto_url());
		intent.putExtra("IMAGES", result[position]);
		intent.putExtra("postion",position);
		//intent.putExtra("location_name", location);
		//intent.putExtra("location_name", location_name.getText().toString());
		startActivity(intent);
	}
	
	/* public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.main, menu);
	        return true;
	    }*/
	
	
	/*static class ViewHolder {
		ImageView imageView;
		ProgressBar progressBar;
		
	}
    
    public class ImageAdapter extends BaseAdapter {
		
    	Context context;
    	PhotoInfo[] result;
    	
    	ImageAdapter(Context context,PhotoInfo[] result)
    	{
    		this.context=context;
    		this.result=result;
    	}
    	
    	@Override
		public int getCount() {
			return result.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ViewHolder holder;
			View view = convertView;
			if (view == null) {
				LayoutInflater inflater = (LayoutInflater)context.getSystemService( Context.LAYOUT_INFLATER_SERVICE ); //2014/08/08 Debug
				view = inflater.inflate(R.layout.item_grid_image, parent, false);
				holder = new ViewHolder();
				assert view != null;
				holder.imageView = (ImageView) view.findViewById(R.id.image);
				holder.progressBar = (ProgressBar) view.findViewById(R.id.progress);
				
				
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			
			//TextView get_url=(TextView)view.findViewById(R.id.photo_url);
			///TextView get_location=(TextView)view.findViewById(R.id.location_name);
			//get_url.setText(result[position].getphoto_url());
			//get_location.setText(result[position].getlocation_name());
			//location=get_location.getText().toString();
			imageLoader.displayImage(result[position].getphoto_url(), holder.imageView, options);
			//imageLoader.displayImage(get_url.getText().toString(), holder.imageView, options);
			
			return view;
		}
	}*/
        
}


