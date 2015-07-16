package com.example.postory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.postory.instagramparser.getImageURL;
import com.example.postory.instagramparser.getLocationid;

import android.os.AsyncTask;

public class searchparser {
	 //依 Location ID 抓照片
	public static PhotoInfo[] executeURL(String instagramurl)
	 {
			
			 PhotoInfo[] photo_url =null; 
		     
			
			 //ArrayList<PhotoInfo> myList = new ArrayList<PhotoInfo>();
			 HashMap<PhotoInfo,Integer> map=new HashMap<PhotoInfo,Integer>();
			 getImageURL getURL=new getImageURL();
				try {
					photo_url=getURL.execute(instagramurl).get();
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				for(int j=0;j<photo_url.length;j++)
					map.put(photo_url[j],j);
				PhotoInfo[] get_all_url=map.keySet().toArray(new PhotoInfo[map.size()]);
			//PhotoInfo[] get_all_url=myList.toArray(new PhotoInfo[myList.size()]);
				return get_all_url;
	}
	
	
	 static class getImageURL extends AsyncTask<String, Void, PhotoInfo[]> { //依 Location ID 抓照片
			
			protected PhotoInfo[] doInBackground(String... params) //啟動任務執行的輸入參數，比如HTTP請求的URL
	        {
				try{
					URL myUrl = new URL(params[0]);
					URLConnection myConn = myUrl.openConnection();	//開啟連線
				
					String line;
					String sResult = ""; 
					
					
					BufferedReader in = new BufferedReader(new InputStreamReader(myConn.getInputStream())); //緩衝
					
					while ((line = in.readLine()) != null) { //逐行存取
						sResult += line;
					}
				
					
					
					String get_url,standard_resolution_url,author;
					JSONObject ob = new JSONObject(sResult);
					JSONArray jsonOb = ob.getJSONArray("data");
					
					
					
					int a=30;
					if(jsonOb.length()<a)
					{
						a=jsonOb.length();
					}
					
				
						HashMap<PhotoInfo,Integer> map=new HashMap<PhotoInfo,Integer>();
						
							for(int i=0;i<a;i++)
							{
								JSONObject jo = (JSONObject) jsonOb.get(i);
								get_url=jo.getJSONObject("images").getJSONObject("thumbnail").getString("url"); //抓圖片URL小圖
								//getlocation_name=jo.getJSONObject("location").getString("name"); //get location name
								standard_resolution_url=jo.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
								//抓照片URL(大圖)
								author=jo.getJSONObject("user").getString("username");//抓author id

								PhotoInfo photoinfo=new PhotoInfo(get_url,null,standard_resolution_url,author);
								//new一個新的photoinfo物件，存入資料
								map.put(photoinfo, i); //map
					
							}
					
					
				
						
						return map.keySet().toArray(new PhotoInfo[map.size()]);
					//return myList.toArray(new PhotoInfo[myList.size()]);
				    //return photoinfo;
					

				}
				
					catch(Exception e){
						e.printStackTrace();
						return null;
					}
			
	        }
	                  
	        protected void onPostExecute(PhotoInfo[] result) 
	        {
	            super.onPostExecute(result);
	        	//WebView image = (WebView)findViewById(R.id.webView1);
	        	//image.loadUrl(result);
	            
	        }       

	 }
}
