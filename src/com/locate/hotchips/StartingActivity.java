package com.locate.hotchips;

import java.util.Locale;





import android.os.Bundle;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.sax.StartElementListener;
import android.speech.tts.TextToSpeech;
import com.locate.hotchips.R;
public class StartingActivity extends Activity implements OnClickListener, android.content.DialogInterface.OnClickListener ,
TextToSpeech.OnInitListener {

	 private TextToSpeech tts;
	 private ImageButton btn;
	 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);
        btn = (ImageButton)findViewById(R.id.start);
        tts = new TextToSpeech(this, this);
		//if you want to lock screen for always Portrait mode  
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		tts.setLanguage(Locale.ENGLISH);
		tts.setSpeechRate((float) 0.5);
		 speakOut();
		 
		 btn.setOnClickListener(this);

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
		 
	        String text ="Welcome to the hot chips locator app.The appication will give route to the nearest hot chips restaurant branche in chennai from your current location. Network availability is necessary for this app to show the route.Else each restaurant address list can be viewed. Get started by clicking the hotel logo";
	        		
	        
	 
	        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
	        
	       
	    }
	public void onClick(DialogInterface arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(StartingActivity.this,MapStarting.class);
		startActivity(intent);
		finish();
		
	}

   

    
}
