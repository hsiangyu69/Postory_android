package com.example.postory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import org.json.JSONArray;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.os.AsyncTask;
@SuppressLint("UseSparseArrays")
public class instagramparser {

public static PhotoInfo[] executeURL(String instagramurl)
 {
		
		 PhotoInfo[] photo_url =null; 
		 String[] location_id=null;
		
		 //ArrayList<PhotoInfo> myList = new ArrayList<PhotoInfo>();
		 HashMap<PhotoInfo,Integer> map=new HashMap<PhotoInfo,Integer>();
		 try {
			 getLocationid getid=new getLocationid();
			 location_id=getid.execute(instagramurl).get();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 
		 for(int i=0;i<20;i++)
		 {
			try {
				 
				getImageURL getURL=new getImageURL();
				String location_url="https://api.instagram.com/v1/locations/"+location_id[i]+"/media/recent?access_token=1297877652.5b9e1e6.c3e85ea9cf704ad391639d838a263534";
				photo_url=getURL.execute(location_url).get();
				
				for(int j=0;j<photo_url.length;j++)
				map.put(photo_url[j],j);
					
				
		
			}catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
			
		 PhotoInfo[] get_all_url=map.keySet().toArray(new PhotoInfo[map.size()]);
		//PhotoInfo[] get_all_url=myList.toArray(new PhotoInfo[myList.size()]);
			return get_all_url;
}




static class getLocationid extends AsyncTask<String, Void, String[]> { //get location ID
	protected String[] doInBackground(String... params) //啟動任務執行的輸入參數，比如HTTP請求的URL
    {
		try{
			URL myUrl = new URL(params[0]);
			URLConnection myConn = myUrl.openConnection();	//開啟連線
		
			String line;
			String sResult = ""; 
			
			
			BufferedReader in = new BufferedReader(new InputStreamReader(myConn.getInputStream())); //緩衝
			
			while ((line = in.readLine()) != null) {
				sResult += line;
			}
		
			

			
			JSONObject ob = new JSONObject(sResult); //抓parser json 資料先轉換成JSONObject物件
			JSONArray jsonOb = ob.getJSONArray("data"); 
			String[] Locationid=new String[jsonOb.length()];
			for(int i=0;i<jsonOb.length();i++)
			{
			JSONObject jo = (JSONObject) jsonOb.get(i);
			Locationid[i]= jo.getString("id");
			}
			
	
		    return Locationid;
		    
		}
		
			catch(Exception e){
				e.printStackTrace();
				return null;
			}
	
    }
	protected void onPostExecute(String[] result) 
    {
        super.onPostExecute(result);
    	
    }       
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
					
					while ((line = in.readLine()) != null) {
						sResult += line;
					}
				
					
					String get_url,getlocation_name,standard_resolution_url,author;
					JSONObject ob = new JSONObject(sResult);
					JSONArray jsonOb = ob.getJSONArray("data");
					int a=3;
					if(jsonOb.length()<a)
					{
						a=jsonOb.length();
					}
					//String[] caption=new String[jsonOb.length()]; //0718
					//ArrayList<PhotoInfo> myList = new ArrayList<PhotoInfo>();
				
						HashMap<PhotoInfo,Integer> map=new HashMap<PhotoInfo,Integer>();
						for(int i=0;i<a;i++)
						{
							JSONObject jo = (JSONObject) jsonOb.get(i);
							get_url=jo.getJSONObject("images").getJSONObject("thumbnail").getString("url"); //抓圖片URL
							getlocation_name=jo.getJSONObject("location").getString("name"); //get location name
							standard_resolution_url=jo.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
							author=jo.getJSONObject("user").getString("username");
							//image_url[i]=get_url;  
							//location_name[i]=getlocation_name;
							PhotoInfo photoinfo=new PhotoInfo(get_url,getlocation_name,standard_resolution_url,author);
							map.put(photoinfo, i);
							//myList.add(photoinfo);
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
	 


