package com.team.catswithhats;

import com.team.catswithhats.HatDatabase.HatType;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

public class GPSservice implements LocationListener {

	private LocationManager locationManager;
	private String latitude;
	private String longitude;
	private Criteria criteria;
	private String provider;
	public Context context;

	public GPSservice(Context context) {
		locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		provider = locationManager.getBestProvider(criteria, true);
	
		locationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, 0, 0, this);
		setMostRecentLocation(locationManager.getLastKnownLocation(provider));
		
		this.context = context;
		
		latitude = 0 + "";
		longitude = 0 + "";

	}

	private void setMostRecentLocation(Location lastKnownLocation) {
		//latitude = lastKnownLocation.getLatitude()+"";
		//longitude = lastKnownLocation.getLongitude()+"";
	}

	public String getLatitude() {
		//System.out.println("LAT IS: "+latitude);
		return latitude;
	}

	public String getLongitude() {
		//System.out.println("LONG IS: "+longitude);
		return longitude;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.location.LocationListener#onLocationChanged(android.location.
	 * Location)
	 */
	@Override
	public void onLocationChanged(Location location) {
		double lon = (double) (location.getLongitude());// / * 1E6);
		double lat = (double) (location.getLatitude());// * 1E6);

		latitude = lat + "";
		longitude = lon + "";
		
		HatType hat = HatDatabase.getHatArea(Float.parseFloat(latitude),Float.parseFloat(longitude));
		
		CharSequence text = "You are currently in " + hat.toString() +" Area!";
		int duration = Toast.LENGTH_SHORT;

		if (GameFile.notificationsEnabled()) {
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.location.LocationListener#onProviderDisabled(java.lang.String)
	 */
	@Override
	public void onProviderDisabled(String arg0) {
		int duration = Toast.LENGTH_SHORT;
		CharSequence text = "Please enable GPS to continue";
		if (GameFile.notificationsEnabled()) {
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		}
		
		latitude = 0+ "";
		longitude = 0 + "";

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.location.LocationListener#onProviderEnabled(java.lang.String)
	 */
	@Override
	public void onProviderEnabled(String arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.location.LocationListener#onStatusChanged(java.lang.String,
	 * int, android.os.Bundle)
	 */
	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		latitude = 0+ "";
		longitude = 0 + "";
	}

}