package com.locate.hotchips;

import java.util.List;


import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.locate.hotchips.Common;
import com.locate.hotchips.R;




import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import android.widget.Toast;


import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.location.Location;

import android.location.LocationManager;


public class Googlemaps extends MapActivity {

	 private MapView mapView;
	 private MapController mc;
	
	 LocationManager locMgr;
	 //MyLocationListener locLstnr;
	 Location mloc;
	 
	 private Button btn;
	 
	 
	 
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	 setContentView(R.layout.activity_googlemaps);

    	 btn = (Button)findViewById(R.id.button1);
    	 
    	 mapView = (MapView) findViewById(R.id.mapview1);
    	 mc = mapView.getController();
    	 mapView.setBuiltInZoomControls(true);

    	    	 double lat = Common.sourLat;
    	 double lng = Common.sourLng;
    	 
         GeoPoint p = new GeoPoint(
         (int) (lat * 1E6),
         (int) (lng * 1E6));

        mc.animateTo(p);
         mc.setZoom(17);
         MyMapOverlays marker = new MyMapOverlays(p) ;
    	 List listOfOverLays = mapView.getOverlays();
    	 listOfOverLays.clear();
    	 listOfOverLays.add(marker);
         mapView.invalidate();
       // uri =Uri.parse("http://maps.google.com/maps?&saddr="+Common.sourLat+","+Common.sourLng+"&daddr="+Common.destLat+","+Common.destLng);
        
         btn.setOnClickListener(new OnClickListener() {
     		
     		public void onClick(View arg0) {
     			// TODO Auto-generated method stub
     			 Intent intent = new Intent(Googlemaps.this,ListViewActivity.class);
     			   startActivity(intent);
     			   finish();
     		}
     	});
    	 }

    	 // Initiating Menu XML file (menu.xml)
    	 @Override
    	 public boolean onCreateOptionsMenu(Menu menu)
    	 {
    	 MenuInflater menuInflater = getMenuInflater();
    	 menuInflater.inflate(R.menu.menu, menu);
    	 return true;
    	 }

    	 /**
    	 * Event Handling for Individual menu item selected
    	 * Identify single menu item by it's id
    	 * */
    	 @SuppressWarnings("deprecation")
    	 @Override
    	 public boolean onOptionsItemSelected(MenuItem item)
    	 {

    	 switch (item.getItemId())
    	 {
    	 case R.id.my_location:
    	 // Single menu item is selected do something
    	 Toast.makeText(Googlemaps.this, "Moving To Current location", Toast.LENGTH_SHORT).show();
    	// locLstnr.gpsCurrentLocation();

    	 return true;

    	 case R.id.mapview_normal:
    	 Toast.makeText(Googlemaps.this, "Map Normal Street View", Toast.LENGTH_SHORT).show();
    	 if(mapView.isSatellite()==true){
    	 mapView.setSatellite(false);
    	 }
    	 return true;

    	 case R.id.mapview_satellite:
    	 Toast.makeText(Googlemaps.this, "Map Satallite View", Toast.LENGTH_SHORT).show();
    	 if(mapView.isSatellite()==false){
    	 mapView.setSatellite(true);
    	 }
    	 return true;

    	  default:
    	 return super.onOptionsItemSelected(item);
    	 }
    	 }
        
        
        
          

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

		
	
	
	
	/*My overlay Class starts*/
	 class MyMapOverlays extends com.google.android.maps.Overlay
	 {
	 GeoPoint location = null;

	public MyMapOverlays(GeoPoint location)
	 {
	 super();
	 this.location = location;
	 }

	@Override
	 public void draw(Canvas canvas, MapView mapView, boolean shadow)
	 {

	 super.draw(canvas, mapView, shadow);
	 //translate the screen pixels
	 Point screenPoint = new Point();
	 mapView.getProjection().toPixels(this.location, screenPoint);

	 //add the image
	 //canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete),
	 canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.pin),
	 screenPoint.x, screenPoint.y-50 , null); //Setting the image  location on the screen (x,y).
	 }
	 }
	 /*My overlay Class ends*/
  
    
}
