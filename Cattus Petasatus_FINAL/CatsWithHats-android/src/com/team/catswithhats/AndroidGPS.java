package com.team.catswithhats;

import android.content.Context;

public class AndroidGPS implements GPS {
	
	  private final GPSservice service;

	   public AndroidGPS(Context mainActivity) {
	      // Assuming we can instantiate it like this
		  service = new GPSservice(mainActivity);

	   }

	   public double getLatitude() {
		   Double latitude = Double.parseDouble(service.getLatitude());
		   return latitude;
	   }
	   
	   public double getLongitude(){
		   Double longi = Double.parseDouble(service.getLongitude());
		   return longi;
	   }
}
