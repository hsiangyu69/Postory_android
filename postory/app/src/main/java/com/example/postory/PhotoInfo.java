package com.example.postory;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PhotoInfo implements Serializable{ //create photoInfo calss in order to use 
	private String photo_url;
	private String location_name;
	private String bigger_photo_url;
	private String author;
	
	
	public PhotoInfo(String photo_url,String location_name,String bigger_photo_url,String author)
	{
		this.photo_url=photo_url;
		this.location_name=location_name;
		this.bigger_photo_url=bigger_photo_url;
		this.author=author;
		
	}
	 public String getphoto_url() { 
	        return photo_url; 
	    }

	  public String getlocation_name() { 
	        return location_name; 
	   }
	  
	  public String getbigger_photo_url() { 
	        return bigger_photo_url; 
	   }
	  public String getauthor() { 
	        return author; 
	   }
	  
	
	
	
}