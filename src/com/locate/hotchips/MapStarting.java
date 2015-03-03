package com.locate.hotchips;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.locate.hotchips.R;




import android.speech.tts.TextToSpeech;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import android.os.Bundle;
import android.provider.Settings;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.util.Log;

import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import android.widget.ProgressBar;

import android.widget.Toast;


public class MapStarting extends Activity implements OnClickListener, android.content.DialogInterface.OnClickListener ,
TextToSpeech.OnInitListener {

	private LocationManager locationMangaer=null;
	private LocationListener locationListener=new MyLocationListener();	
	
	 private TextToSpeech tts;
	 
	private Button btnGetLocation = null;
	private Button hotelList = null;
		
	private ProgressBar pb =null;
	
	private static final String TAG = "Debug";
	private Boolean flag = false;

	private boolean gps_enabled = false;
	private boolean network_enabled = false;
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_starting);
		
		 tts = new TextToSpeech(this, this);
		//if you want to lock screen for always Portrait mode  
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		tts.setLanguage(Locale.ENGLISH);
		tts.setSpeechRate((float) 0.5);
		 speakOut();
		
		pb = (ProgressBar) findViewById(R.id.progressBar1);
		pb.setVisibility(View.INVISIBLE);
		
		hotelList = (Button) findViewById(R.id.list);
		hotelList.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MapStarting.this,ListViewActivity.class);
				startActivity(i);
			}
		});

		btnGetLocation = (Button) findViewById(R.id.btnLocation);
		btnGetLocation.setOnClickListener(this);
		
		locationMangaer = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		Criteria criteria = new Criteria();
		 String bestProvider = locationMangaer.getBestProvider(criteria, false);
		 Location loc = locationMangaer.getLastKnownLocation(bestProvider);
		
		 if(loc != null)
		 {
		
	
	    Common.sourLat = loc.getLatitude();
	    Common.sourLng = loc.getLongitude();
		//System.out.println(Common.sourLat+" Map Starting "+Common.sourLng);
		 }

	}

	public void onClick(View v) {
		flag = displayGpsStatus();
		locationListener = new MyLocationListener();
		if (flag) {
			
			Log.v(TAG, "onClick");		
			
			
			pb.setVisibility(View.VISIBLE);
			
			try {
				gps_enabled = locationMangaer.isProviderEnabled(LocationManager.GPS_PROVIDER);
			} catch (Exception ex) {
			}
			try {
				network_enabled = locationMangaer.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
			} catch (Exception ex) {
			}

			// don't start listeners if no provider is enabled
			if (!gps_enabled && !network_enabled) {
				AlertDialog.Builder builder = new Builder(this);
				builder.setTitle("Attention!");
				builder.setMessage("Sorry, location is not determined. Please enable location providers");
				builder.setPositiveButton("OK", this);
				builder.setNeutralButton("Cancel", this);
				builder.create().show();
				pb.setVisibility(View.GONE);
			}

			if (gps_enabled) {
				locationMangaer.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
			}
			if (network_enabled) {
				locationMangaer.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
			}
			
			
			

			locationMangaer.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
	                locationListener);
			
			/*Intent i = new Intent(MapStarting.this,PlaceInitiatg.class);
			startActivity(i);*/

			
			} else {
			alertbox("Gps Status!!", "Your GPS is: OFF");
		}

	}

	/*----------Method to Check GPS is enable or disable ------------- */
	private Boolean displayGpsStatus() {
		ContentResolver contentResolver = getBaseContext().getContentResolver();
		boolean gpsStatus = Settings.Secure.isLocationProviderEnabled(
				contentResolver, LocationManager.GPS_PROVIDER);
		if (gpsStatus) {
			return true;

		} else {
			return false;
		}
	}

	/*----------Method to create an AlertBox ------------- */
	protected void alertbox(String title, String mymessage) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Your Device's GPS is Disable")
				.setCancelable(false)
				.setTitle("** Gps Status **")
				.setPositiveButton("Gps On",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// finish the current activity
								// AlertBoxAdvance.this.finish();
								Intent myIntent = new Intent(
										Settings.ACTION_SECURITY_SETTINGS);
								startActivity(myIntent);
								dialog.cancel();
							}
						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// cancel the dialog box
								dialog.cancel();
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	/*----------Listener class to get coordinates ------------- */
	private class MyLocationListener implements LocationListener {
        public void onLocationChanged(Location loc) {
          
        	if (loc != null) {
        		locationMangaer.removeUpdates(locationListener);
            	
            	pb.setVisibility(View.INVISIBLE);
            	
            	
            	
            	    Common.sourLat = loc.getLatitude();
            	    Common.sourLng = loc.getLongitude();
           
           
            	
             //   Toast.makeText(getBaseContext(),"Location changed : Lat: " + loc.getLatitude()
                  //              + " Lng: " + loc.getLongitude(),Toast.LENGTH_SHORT).show();
                
                Intent i = new Intent(MapStarting.this,PlaceInitiatg.class);
    			startActivity(i);
    			
                String longitude = "Longitude: " +loc.getLongitude();  
    			Log.v(TAG, longitude);
    		    String latitude = "Latitude: " +loc.getLatitude();
    		    Log.v(TAG, latitude);
    		    
    		    /*----------to get City-Name from coordinates ------------- */
    		    String cityName=null;      		      
    		    Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());      		     
    		    List<Address>  addresses;  
    		    try {  
    		     addresses = gcd.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);  
    		     if (addresses.size() > 0)  
    		      System.out.println(addresses.get(0).getLocality());  
    		     cityName=addresses.get(0).getLocality();  
    		    } catch (IOException e) {    		      
    		     e.printStackTrace();  
    		    } 
    		    
    		   /* String s = longitude+"\n"+latitude +"\n\nMy Currrent City is: "+cityName;
     		    editLocation.setText(s);*/
        }
        }

        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub        	
        }

        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub        	
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            // TODO Auto-generated method stub        	
        }
        
        
    }

	public void onClick(DialogInterface arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	public void onInit(int status) {
		// TODO Auto-generated method stub
		if (status == TextToSpeech.SUCCESS) {
			 
            int result = tts.setLanguage(Locale.ENGLISH);
 
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
               
                speakOut();
            }
 
        } else {
            Log.e("TTS", "Initilization Failed!");
        }
 
		
	}
	
	 private void speakOut() {
		 
	        String text ="Click the start button, if network is present.Else click branches button for restaurants list.";
	        
	 
	        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
	        
	       
	    }

	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) 
	    {
	        MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.menu.exit_menu, menu);
	        return true;
	    }
	    
	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        // Handle item selection
	        switch (item.getItemId())
	        {
		       
		        case R.id.exit:
		        	android.os.Process.killProcess(android.os.Process.myPid());
		        	finish();
		            return true;
		        
		        default:
		            return super.onOptionsItemSelected(item);
	        }
	    }
	  
	    
	   
	 public void onDestroy() {
	        // Don't forget to shutdown tts!
	        if (tts != null) {
	            tts.stop();
	            tts.shutdown();
	        }
	        if(locationMangaer != null) {
		    	locationMangaer.removeUpdates(locationListener);
		    }
		    locationListener = null;
		    locationMangaer = null;
	        super.onDestroy();
	    }
    
}
