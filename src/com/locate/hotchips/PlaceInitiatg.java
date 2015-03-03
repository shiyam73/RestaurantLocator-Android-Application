package com.locate.hotchips;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


import com.locate.hotchips.Common;
import com.locate.hotchips.R;
import com.google.android.maps.GeoPoint;


import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class PlaceInitiatg extends Activity {

	private ProgressDialog dialog;
	private ArrayList<String> lat = new ArrayList<String>(); 
	private ArrayList<String> lng = new ArrayList<String>();
	
	private ArrayList<Double> dist = new ArrayList<Double>();
	
	private double temp3[] = new double[3];
  
	private int temp4[] = new int[]{1,2,3};
	
	GeoPoint routePoints [];
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_initiatg);
        
            
        lat.add("13.040349"); //Ashok Nagar
        lng.add("80.212727");
        
        lat.add("13.07836");//Egmore
        lng.add("80.260341");
        
        lat.add("12.988249"); // Velachery
        lng.add("80.219078");
        

lat.add("13.076071");//Ampa Mall
lng.add("80.221374");


lat.add("12.988249");//Neelankarai
lng.add("80.219078");

lat.add("13.071452");//Chrompet
lng.add("80.169983");


lat.add("12.975544");//Tambaram
lng.add("80.169386");


lat.add("21.125498");//Koyambedu
lng.add("81.914063");


lat.add("13.007485");//Adayar
lng.add("80.244398");


lat.add("13.069069");
lng.add("80.205173");//CMBT


lat.add("13.105561");
lng.add("80.292892");//Parrys


lat.add("13.085519");
lng.add("80.203199");//Anna Nagar


lat.add("12.989023");
lng.add("80.255513");//thiruvanmayur


lat.add("13.041561");
lng.add("80.235085");//Tnagar


lat.add("13.042564");
lng.add("80.178137");//Valasaravakam


lat.add("13.082822");
lng.add("80.241394");//Nungambakkam

       System.out.println("PlaceInitaing"+" "+Common.sourLat+","+Common.sourLng);
        
       dialog = ProgressDialog.show(PlaceInitiatg.this, "", 
               "Calculating distance to the nearest branch.. Please wait..", true);
       if(!isNetworkAvailable())
       {
       	Toast.makeText(getBaseContext(), "Please connect to Internet..", Toast.LENGTH_LONG).show();
       	finish();
       }
       else
       {
    	   final Handler handler=new Handler();
           final Runnable r = new Runnable()
           {
               public void run() 
               {
                  finish();
               }
           };
           
           Thread xml_thread = new Thread() {
           	@Override
               public void run() {
                   try {
                	   for(int i=0;i<3;i++)
                       {
                       
                		   /*Calculating distance */
                       
                       	 System.out.println(Common.sourLat+" "+i+" "+Common.sourLng);
                       	Common.URL = "http://maps.googleapis.com/maps/api/directions/xml?origin="+Common.sourLat+","+Common.sourLng+"&destination=";
                       	Common.URL += lat.get(i)+","+lng.get(i)+"&sensor=false";
                       	
                       	CalculateDistDur();
                       	Common.URL="";
                       	Common.dur.add(Common.duration);
                       	Common.dis.add(Common.distance);
                       	
                       }
                       String temp[] = Common.dis.toArray(new String[Common.dis.size()]);
                       
                     //  System.out.println("distance size "+Common.dis.size());
                       
                       for(int i=0;i<3;i++)
                       {
                       	String temp1 = temp[i];
                       	String temp2[] = temp1.split(" ");
                       	temp3[i]=Double.parseDouble(temp2[0]);
                       }
                       
                      // Collections.sort(dist);
                     
                       for (int i = 0 ; i <= 1 ; i++ )
                   	{
                   		for (int j = 0 ; j <= 1 - i ; j++ )
                   		{
                   			if ( temp3[j] > temp3[j + 1] )
                   			{
                   				double tem = temp3[j] ;
                   				temp3[j] = temp3[j + 1] ;
                   				temp3[j + 1] = tem ;
                   				
                   				int tem1 = temp4[j];
                   				temp4[j] = temp4[j + 1] ;
                   				temp4[j + 1] = tem1 ;

                   			}
                   		}
                   	}

                       
                     /*  for(int i=0;i<3;i++)
                       	System.out.println(Common.dur.get(i)+" dur dist "+temp3[i]+" "+temp4[i]);*/
                       
                       Common.destLat = lat.get(temp4[0]-1);
                       Common.destLng = lng.get(temp4[0]-1);
                       
                       PopulateLatLng();
                       Intent intent = new Intent(PlaceInitiatg.this,Googlemaps.class);
       				startActivity(intent);
       			 handler.post(r);
                   	
                   } catch (Exception e) {
                       e.printStackTrace();
                   }
               }
           };
           xml_thread.start();     
       
        
       }
    }
    // override on stop
    public void onStop()
    {
    	super.onStop();
    	dialog.dismiss();
    	
    }
    
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager 
              = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return (activeNetworkInfo != null);
    }
  
    private boolean CalculateDistDur(){
    	//HACK
    	
    	String xml = getXmlFromUrl(Common.URL);
    	Document doc = getDomElement(xml); 
    	
    	NodeList n3 = doc.getElementsByTagName("duration");
    	NodeList n4 = doc.getElementsByTagName("distance");
    	
    	
    	
    	
            Element item = (Element)n3.item(n3.getLength()-1);
            Common.duration = getValue(item, "text").trim();
            item = (Element)n4.item(n4.getLength()-1);
            Common.distance = getValue(item, "text").trim();
           // System.out.println("\n"+Common.duration+" "+Common.distance);
           
         
        
    	return true;
    }
    
    
    private boolean PopulateLatLng(){
    	//HACK
    	
    	Common.URL = "http://maps.googleapis.com/maps/api/directions/xml?origin="+Common.sourLat+","+Common.sourLng+"&destination=";
    	Common.URL += Common.destLat+","+Common.destLng+"&sensor=false";
    	
    	
    	String xml = getXmlFromUrl(Common.URL);
    	Document doc = getDomElement(xml); 
    	NodeList nl = doc.getElementsByTagName("start_location");
    	NodeList n2 = doc.getElementsByTagName("end_location");
    	
    	
    	
    	for (int i = 0; i < nl.getLength(); i++) {
            Element item = (Element)nl.item(i);
            String lat = getValue(item, "lat").trim();
            String lon = getValue(item, "lng").trim();
            Common.start.add(new Start_Dest(lat, lon));
            
         
        }
    	
    	for (int i = 0; i < n2.getLength(); i++) {
            Element item = (Element)n2.item(i);
            String lat = getValue(item, "lat").trim();
            String lon = getValue(item, "lng").trim();
            Common.end.add(new End_Dest(lat, lon));
        
         
        }
    	
    	/*for(int i=0;i<Common.start.size();i++)
    	{
    		System.out.println(Common.start.get(i).getLat()+" "+Common.start.get(i).getLng());
    		System.out.println(Common.end.get(i).getLat()+" "+Common.end.get(i).getLng());
    	}
   	
    	for(int i=0;i<Common.start.size();i++)
    	{
    		routePoints[i] = new GeoPoint(Integer.parseInt(Common.start.get(i).getLat()),Integer.parseInt(Common.start.get(i).getLng()));
    	}*/
    	              
            	return true;
    }
    
    /* get the XML from URL */
    private String getXmlFromUrl(String url) {
        String xml = null;
        try {
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
 
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            xml = EntityUtils.toString(httpEntity);
 
        } catch (UnsupportedEncodingException e) {
        	Toast.makeText(getBaseContext(), "Problem in connecting to server..", Toast.LENGTH_LONG).show();
        	finish();
            e.printStackTrace();
        } catch (ClientProtocolException e) {
        	Toast.makeText(getBaseContext(), "Problem in connecting to server..", Toast.LENGTH_LONG).show();
        	finish();
            e.printStackTrace();
        } catch (IOException e) {
        	Toast.makeText(getBaseContext(), "Problem in connecting to server..", Toast.LENGTH_LONG).show();
        	finish();
            e.printStackTrace();
        }
        return xml;
    }
    
    /* get DOM element */
    public Document getDomElement(String xml){
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
 
            DocumentBuilder db = dbf.newDocumentBuilder();
 
            InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(xml));
                doc = db.parse(is); 
 
            } catch (ParserConfigurationException e) {
                Log.e("Error: ", e.getMessage());
                return null;
            } catch (SAXException e) {
                Log.e("Error: ", e.getMessage());
                return null;
            } catch (IOException e) {
                Log.e("Error: ", e.getMessage());
                return null;
            }
            return doc;
    }
    
    public String getValue(Element item, String str) {
        NodeList n = item.getElementsByTagName(str);
        return this.getElementValue(n.item(0));
    }
     
    public final String getElementValue( Node elem ) {
             Node child;
             if( elem != null){
                 if (elem.hasChildNodes()){
                     for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
                         if( child.getNodeType() == Node.TEXT_NODE  ){
                             return child.getNodeValue();
                         }
                     }
                 }
             }
             return "";
      } 
    
    
}
