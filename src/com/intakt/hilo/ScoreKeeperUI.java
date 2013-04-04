package com.intakt.hilo;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/*
 * interface for adding players. returns a list of new player names to initializing activity
*/
public class ScoreKeeperUI extends Activity implements OnClickListener {
	
	// instance vars
	ArrayList<String> players = new ArrayList<String>();
	String name;
	
	//view vars
	Button setPlayer;
	TextView playerNames;
	EditText nameEntry;
	Button finishPlayers;

	// set the view. make player name textview scrollable. make input clickable. await click
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scorekeeperlayout);
	
        setPlayer = (Button) findViewById(R.id.setplayer);
    	playerNames = (TextView) findViewById(R.id.playernames);
    	nameEntry = (EditText) findViewById(R.id.nameentry);
    	finishPlayers = (Button) findViewById(R.id.finishplayers);
        setPlayer.setOnClickListener(this);
        finishPlayers.setOnClickListener(this);
    	
        playerNames.setMovementMethod(new ScrollingMovementMethod());
        
        Log.i("scorekeeperUI", "variables created");
	}

	// if user clicks add set the player name input as new player. if player clicks finish, finish scorekeeperui
	public void onClick(View v) {
		switch(v.getId()) {
			
			// user clicked add
			case R.id.setplayer:{
				Log.i("scorekeeperUI", "button add pressed");
				setPlayers();
				break;
			}
			
			// user clicked finished
			case R.id.finishplayers:{
				Log.i("scorekeeperUI", "button finish pressed");
				finishPlayers();
				break;
			}
		}
	}

	// if no players have been added, toast a warning and await input. otherwise set players in bundle and return with a result of 1
	public void finishPlayers() {
		if (!checkForEmpty()) {
			Bundle bundle = new Bundle();
			bundle.putStringArrayList("players", players);
			Intent intent = new Intent();
			intent.putExtras(bundle);
			setResult(1, intent);
			finish();
		}
	}

	// check to see if any player names have been added. toast if none in list
	private boolean checkForEmpty() {
		if (players.size() == 0) {
			Toast.makeText(getBaseContext(), "Add Player(s) First", Toast.LENGTH_SHORT).show();
			return true;
		}else return false;
	}

	// adds any strings inputted into textbox to the list of player names after removing newlines. displays them in textview
	public void setPlayers() {
		name = nameEntry.getText().toString();
		name = name.replace("\n", "");
		Log.i("scorekeeperUI", "name pulled from edittext: " + name);
		playerNames.append(name + "\n");
		nameEntry.setText("");
		players.add(name);
		Log.i("scorekeeperUI", "playerlist is now" + players);
	}


}
