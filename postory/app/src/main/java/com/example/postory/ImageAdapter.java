package com.example.postory;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;



class ViewHolder {
	ImageView imageView;
	ProgressBar progressBar;
	
}

public class ImageAdapter extends BaseAdapter {
	
	DisplayImageOptions options;
	ImageLoader imageLoader = ImageLoader.getInstance();
	
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

	@SuppressWarnings("static-access")
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
		
		
		imageLoader.displayImage(result[position].getphoto_url(), holder.imageView, options);
		
		
		
		return view;
	}
}