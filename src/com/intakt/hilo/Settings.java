package com.intakt.hilo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.content.SharedPreferences;
import android.view.View.OnClickListener;

/*
 * activity to setup settings; only have option for starting in party mode or scorekeeping mode
 * plan to add: ability to change background, number of decks option, ability to turn shuffling sound on and off, and ability to have results from guessing right or wrong (such as a toast or a sound)
 * need to make checkbox a custom style
*/
public class Settings extends Activity implements OnClickListener {
	
	//view vars
	Button save;
	Button cancel;
	CheckBox partyModeBox;
	
	// setup view. set button texts. make buttons clickable. retrieve saved prefs. await click
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settingslayout);
        
        save = (Button)findViewById(R.id.settingssave);
        cancel = (Button)findViewById(R.id.settingscancel);
        partyModeBox = (CheckBox) findViewById(R.id.ispartymode);
        
        save.setText("Save");
        cancel.setText("Cancel");
        
        save.setOnClickListener(this);
        cancel.setOnClickListener(this);
        
        SharedPreferences prefs = getSharedPreferences("HiLoPrefs", MODE_PRIVATE);
        
        boolean partyMode = prefs.getBoolean("partymode", true);
        partyModeBox.setChecked(partyMode);
	}

	// store new prefs to file
	private void savePrefs() {
		SharedPreferences prefs = getSharedPreferences("HiLoPrefs", MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean("partymode", partyModeBox.isChecked());
    	editor.commit();
	}
	
	// if save clicked, store prefs. regardless finish
	public void onClick(View v) {
		if(v.getId() == R.id.settingssave) savePrefs();
		finish();
	}
}
