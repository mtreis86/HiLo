package com.intakt.hilo;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/*
 * displays scores from multiplayer activity
*/
public class Score extends Activity implements OnClickListener{
	
	// instance vars
	ArrayList<String> playerNames = new ArrayList<String>();
	ArrayList<Integer> playerScores = new ArrayList<Integer>();
	Bundle extras;
	
	// view vars
	TextView scoreText;
	Button finishBtn;
	
	// retrieve extras and setup view. display scores. await click
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoreslayout);
        extras = getIntent().getExtras();
        
        finishBtn = (Button) findViewById(R.id.buttonmid);
        scoreText = (TextView) findViewById(R.id.scoretext);
        
        finishBtn.setBackgroundResource(R.drawable.btn);
        finishBtn.setText("Next");
        
        finishBtn.setOnClickListener(this);
        
        playerNames = extras.getStringArrayList("playernames");
        playerScores = extras.getIntegerArrayList("playerscores");
        
        displayScores();
	}
	
	// display scores in edittext view
	public void displayScores() {
		// depending on number of digits in scores, setup edittext
		for(int i = 0; i < playerNames.size(); i++) {
			if (playerScores.get(i) < 10) {
				scoreText.append(playerNames.get(i) + " :    " + playerScores.get(i) + "  \n");
			} else if (10 <= playerScores.get(i) && playerScores.get(i) < 100) {
				scoreText.append(playerNames.get(i) + " :   " + playerScores.get(i) + "  \n");
			} else if (100 <= playerScores.get(i) && playerScores.get(i) < 1000) {
				scoreText.append(playerNames.get(i) + " :  " + playerScores.get(i) + "  \n");
			} else scoreText.append(playerNames.get(i) + " : " + playerScores.get(i) + "  \n");
		}
		
		// make scores scrollable
		scoreText.setMovementMethod(new ScrollingMovementMethod());
	}

	// onclick finish activity
	public void onClick(View v) {
		finish();
		
	}
}
