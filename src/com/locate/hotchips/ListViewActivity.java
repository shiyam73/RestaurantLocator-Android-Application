package com.locate.hotchips;

import java.util.ArrayList;





import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import com.locate.hotchips.R;

public class ListViewActivity extends Activity {
	
	private Button btn;
	 private Uri uri;
	 
	/** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_activity);
        btn = (Button)findViewById(R.id.route);
        
        ArrayList<HotelDetails> image_details = GetSearchResults();
        
        final ListView lv1 = (ListView) findViewById(R.id.listV_main);
        lv1.setAdapter(new HotelListBaseAdapter(this, image_details));
        
        lv1.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> a, View v, int position, long id) { 
        		Object o = lv1.getItemAtPosition(position);
            	HotelDetails obj_itemDetails = (HotelDetails)o;
        		//Toast.makeText(ListViewActivity.this, "You have chosen : " + " " + obj_itemDetails.getName(), Toast.LENGTH_LONG).show();
        	}  
        });
        if(!isNetworkAvailable())
        {
        	Toast.makeText(getBaseContext(), "Please connect to Internet..", Toast.LENGTH_LONG).show();
        	btn.setVisibility(View.GONE);
        	
        }
        
        System.out.println("link: http://maps.google.com/maps?&saddr="+Common.sourLat+","+Common.sourLng+"&daddr="+Common.destLat+","+Common.destLng);
        
        uri =Uri.parse("http://maps.google.com/maps?&saddr="+Common.sourLat+","+Common.sourLng+"&daddr="+Common.destLat+","+Common.destLng);
        
        if(Common.destLat.equals("") || Common.destLng.equals(""))
        {
        	Toast.makeText(getBaseContext(), "Please click the Start button in the first page", Toast.LENGTH_LONG).show();
        	btn.setVisibility(View.GONE);
        }
        
        btn.setOnClickListener(new OnClickListener() {
    		
    		public void onClick(View arg0) {
    			// TODO Auto-generated method stub
    			 Intent intent = new Intent(Intent.ACTION_VIEW, uri);
    			   startActivity(intent);
    			   finish();
    		}
    	});
        
    }
    
    
   
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager 
              = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return (activeNetworkInfo != null);
    }
  
    
    private ArrayList<HotelDetails> GetSearchResults(){
    	ArrayList<HotelDetails> results = new ArrayList<HotelDetails>();
    	
    	HotelDetails item_details = new HotelDetails();
    	item_details.setName("Velachery");
    	item_details.setItemDescription(" #3,Taramani Link Road, Baby Nagar,Velachery,Chennai-600042");
    	
    	results.add(item_details);
    	
    	item_details = new HotelDetails();
    	item_details.setName("Ampa Mall");
    	item_details.setItemDescription("#107/2,NelsonManaickam	Road , Aminjikkarai,Chennai-600 029");
    	results.add(item_details);
    	
    	item_details = new HotelDetails();
    	item_details.setName("Neelankarai");
    	item_details.setItemDescription("#2/546,ECR Road Neelankarai,Chennai-600 041");
    	results.add(item_details);
    	
    	item_details = new HotelDetails();
    	item_details.setName("Chrompet");
    	item_details.setItemDescription("#13,GST Road Near MIT Bridge,Chrompet,Chennai-600 045");
    	results.add(item_details);
    	
    	item_details = new HotelDetails();
    	item_details.setName("Tambaram");
    	item_details.setItemDescription("#67, Rajaji salai Venkantesan street, Tambaram 	Chennai-600 034");
    	results.add(item_details);
    	
    	item_details = new HotelDetails();
    	item_details.setName("Koyambedu");
    	item_details.setItemDescription("VG-1,Vegetable Market,Koyambedu Chennai-600 092");
    	results.add(item_details);
    	
    	item_details = new HotelDetails();
    	item_details.setName("Adayar");
    	item_details.setItemDescription("#52,Sardar Patel Road,Adayar Chennai-600 113");
    	results.add(item_details);
    	
    	item_details = new HotelDetails();
    	item_details.setName("CMBT");
    	item_details.setItemDescription("ShopNo1,Plot From No 3 Jawaharlal Nehru Salai Koyembedu");
    	results.add(item_details);
    	
    	item_details = new HotelDetails();
    	item_details.setName("Parrys Central");
    	item_details.setItemDescription("#225,NSC Bose road Parrys Corner Chennai-600 001");
    	results.add(item_details);
    	
    	item_details = new HotelDetails();
    	item_details.setName("Anna Nagar");
    	item_details.setItemDescription("#44,12th Main Road II Avenue,Anna Nagar,Chennai-600 040");
    	results.add(item_details);
    	
    	item_details = new HotelDetails();
    	item_details.setName("Thiruvanmayur");
    	item_details.setItemDescription("#68,Kalki Ka Krishnamurthy Salai,Triruvanmiyur,Chennai-600 041");
    	results.add(item_details);
    	
    	item_details = new HotelDetails();
    	item_details.setName("TNagar");
    	item_details.setItemDescription("#71,Sir Thyagaraya Road,PONDY BAZAAR,Chennai-600 017");
    	results.add(item_details);
    	
    	item_details = new HotelDetails();
    	item_details.setName("valasaravakam");
    	item_details.setItemDescription(" #20,Arcot Road,Valasaravakkam,Chennai-600 087");
    	results.add(item_details);
    	
    	item_details = new HotelDetails();
    	item_details.setName("Nungambakkam");
    	item_details.setItemDescription(" 1,Wheat Craft Road,Nungambakkam,Chennai-600 034");
    	results.add(item_details);
    	
    	item_details = new HotelDetails();
    	item_details.setName("Egmore");
    	item_details.setItemDescription("AAB Food Plaza,Egmore Railway Station,Chennai-600 007");
    	results.add(item_details);
    	
    	item_details = new HotelDetails();
    	item_details.setName("Ashok Nagar");
    	item_details.setItemDescription("#36/3.IAvenue,Balaji,Building Ashok Nagar,Chennai-600 083");
    	results.add(item_details);
    	 
    	
    	return results;
    }
	

}
