package com.intakt.hilo;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/*
 * activity to display cards resulting from GameDeal
*/
public class GameReveal extends Activity implements OnClickListener {
	
	//instance vars
	Bundle extras;
	int cardup;
	int carddn;
	String currentPlayerName;
	ArrayList<String> playerNames = new ArrayList<String>();
	ArrayList<Integer> playerScores = new ArrayList<Integer>();
	
	//view vars
	View gameMulti;
	TextView playerName;
	ImageView cardSlotL;
	ImageView cardSlotR;
	Button buttonP;
	Button buttonE;
	
	// create the view, retreive extras, set player name, and reveal solution, then await click
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamelayout);
        
        extras = getIntent().getExtras();
        
        playerName = (TextView) findViewById(R.id.playertext);
    	cardSlotL = (ImageView) findViewById(R.id.cardleft);
    	cardSlotR = (ImageView) findViewById(R.id.cardright);
    	buttonP = (Button) findViewById(R.id.buttontop);
    	buttonE = (Button) findViewById(R.id.buttonmid);
        
    	buttonP.setBackgroundResource(R.drawable.btn);
    	buttonE.setBackgroundResource(R.drawable.btn);
    	
    	buttonP.setText("Next");
    	buttonE.setText("Scores");
    	
    	buttonP.setOnClickListener(this);
    	buttonE.setOnClickListener(this);
    	
    	getExtras();
    	setPlayerName();
    	revealSolution();
	}
	
	// pull extras from previous activity
	public void getExtras() {
		playerNames = extras.getStringArrayList("playernames");
		playerScores = extras.getIntegerArrayList("playerscores");
		
		cardup = extras.getInt("cardup");
		carddn = extras.getInt("carddn");
	
		currentPlayerName = extras.getString("currentplayer");
	}	
		
	// display player name on top of screen
	private void setPlayerName() {
		Log.i("gamedeal", "starting cleartable");
		playerName.setText(currentPlayerName);
	}
	
	// display both cards
	public void revealSolution() {
		cardSlotR.setImageResource(carddn);
		cardSlotL.setImageResource(cardup);
	}
	
	// on click either finish and go back to GameDeal, or start Scores
	public void onClick(View v) {
		switch(v.getId()) {
			case R.id.buttontop:{
				finish();
				break;
			}
		case R.id.buttonmid:{
				displayScores();
				break;
			}
		}
	}
		
	// passes data to new activity Scores then finishes self
	public void displayScores() {
		Intent i = new Intent(this,Score.class);
		Bundle extrasToPass = new Bundle();
		extrasToPass.putStringArrayList("playernames", playerNames);
		extrasToPass.putIntegerArrayList("playerscores", playerScores);
		i.putExtras(extrasToPass);
		startActivity(i);
		overridePendingTransition(0,0);
		finish();
	}

}
