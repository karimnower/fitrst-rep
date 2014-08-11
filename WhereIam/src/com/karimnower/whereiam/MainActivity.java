package com.karimnower.whereiam;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.karimnower.whereiam.MyLocation.LocationResult;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.os.Handler;

public class MainActivity extends Activity {

	 MyLocation myLocation = new MyLocation();
	 TextView myCurrLocation;
	 TextView myCurrAddress;
	 Button locateMe;
	 String currLoc;
	 double latitude;
	 double longitude;
	 String addressString = "No address found!";
	 
	 
    private Handler handler = new Handler();  
    Geocoder gc = new Geocoder(this, Locale.getDefault());
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//locationClick();
		myCurrLocation = (TextView) findViewById(R.id.CurrentLocationTextView);
		myCurrAddress = (TextView) findViewById(R.id.CurrentAddressTextView);
		locateMe = (Button) findViewById(R.id.findLocationButton);
		
		locateMe.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				locationClick();
				updateView(myCurrLocation,currLoc);
				updateView(myCurrAddress,addressString);

				
			}
		});
	}



	   private void locationClick() {
	    myLocation.getLocation(this, locationResult);	    
	   }

		public LocationResult locationResult = new LocationResult() {
			@Override
			public void gotLocation(Location location) {
				currLoc = "Lat: " + location.getLatitude() + "\nLong: " + location.getLongitude() + " on: " + 
						location.getTime();
				longitude = location.getLongitude();
				latitude = location.getLatitude();
				
				try {
					List<Address> addresses = gc.getFromLocation(latitude, longitude, 1);
					StringBuilder sb = new StringBuilder();
					//Log.i("address size", addresses.size() + "");
					Log.i("address", addresses.toString());
					if (addresses.size() > 0) {
						Address address = addresses.get(0);
						
						//for (int i = 0; i < address.getMaxAddressLineIndex(); i++)
							sb.append("getAddressLine: 0 " + address.getAddressLine(0)).append("\n");
							sb.append("getAddressLine: 1 " + address.getAddressLine(1)).append("\n");
							sb.append("getAddressLine: 2 " + address.getAddressLine(2)).append("\n");
							sb.append("getAddressLine: 3 " + address.getAddressLine(3)).append("\n");
							sb.append("getPostalCode: " + address.getPostalCode()).append("\n");
							sb.append("getSubLocality: " + address.getSubLocality()).append("\n");
							//sb.append(address.getCountryCode()).append("\n");
							sb.append("getAdminArea: " + address.getAdminArea()).append("\n"); // use this one
							//sb.append("getFeatureName: " + address.getFeatureName()).append("\n");
							sb.append("getSubAdminArea: " + address.getSubAdminArea()).append("\n");
							sb.append("getLocality: " + address.getLocality()).append("\n");
					}
					addressString = sb.toString();
				   	  handler.post(new Runnable(){
				  		  public void run(){
				  			  myCurrAddress.setText(addressString);
				  		  } 
				  	  });
				} catch (IOException e) {}


				}

			};

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void updateView(final TextView textview, final String text){
	   	  handler.post(new Runnable(){
	  		  public void run(){
	  			  textview.setText(text);
	  		  } 
	  	  }); 
		}
	
	
	
	

}
