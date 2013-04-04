package com.intakt.hilo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.content.SharedPreferences;


/*
 * main menu for game. allows entry into settings or gamelevel. loads prefs to determine which game to start
*/
public class StartScreen extends Activity implements OnClickListener {
	
	// instance vars	
	boolean partyMode;
	
	// view vars
	Button startbtn;
	Button settingsbtn;

	// set view. make buttons clickable. set button texts. await click
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startlayout);
        
        startbtn = (Button) findViewById(R.id.mainbtnstart);  
        settingsbtn = (Button) findViewById(R.id.mainbtnsettings);  
        Log.i("startscreen", "buttons set to: " + startbtn + " and: " + settingsbtn);
        
        startbtn.setOnClickListener(this); 
        settingsbtn.setOnClickListener(this);
        Log.i("startscreen", "oncreate finished; awaiting click");
	
        startbtn.setText("Start"); 
        settingsbtn.setText("Settings");
	
	}

	// on click start game or settings
	public void onClick( View v) {
		switch(v.getId()) {
			
			// player clicked start
			case R.id.mainbtnstart:{
				Log.i("startscreen","starting game");
				startGame();
				break;
			}
			
			// player clicked settings
			case R.id.mainbtnsettings:{
				Log.i("startscreen","starting settings");
				Intent isett = new Intent(this, Settings.class);
				startActivity(isett);
				overridePendingTransition(0,0);
				break;
			}

		}
	}
	
	// check prefs to see which games to start then initialize it. then finish self.
	private void startGame() {
		
		SharedPreferences prefs = getSharedPreferences("HiLoPrefs", MODE_PRIVATE);
		partyMode = prefs.getBoolean("partymode", true);
		Intent igame;
		Log.i("startscreen","starting partymode: " + partyMode);
		
		// partymode not checked in settings - start GameDeal
		if (!partyMode) {
			igame = new Intent(this,GameDeal.class);
			startActivity(igame);
			overridePendingTransition(0,0);
			Log.i("startscreen","starting multiplayer");
			finish();
		}
		
		// partymode checked - start PartyDeal
		if(partyMode) {
			igame = new Intent(this,PartyDeal.class);
			startActivity(igame);
			overridePendingTransition(0,0);
			Log.i("startscreen","starting singleplayer");
			finish();
		}
	}
}
