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
 * this is the main activity for playing the game while keeping score
 * initialization starts with bringing up a scorekeeper ui which allows for users to input their names
 * cards are then dealt after which the user is presented with the option to guess
 * then a new activity is started displaying the results
 * after that finishes this activity continues dealing a new hand
*/
public class GameDeal extends Activity implements OnClickListener{
	// instance vars
	Card cardup;
	Card carddn;
	int cardValComp;
	int numPlayers;
	int currentPlayer = 0;
	int playerGuess;
	ArrayList<ScoreKeeper> players = new ArrayList<ScoreKeeper>();
	ArrayList<String> playerNames = new ArrayList<String>();
	ArrayList<Integer> playerScores = new ArrayList<Integer>();
	
	//view vars
	View gameMulti;
	TextView playerName;
	ImageView cardSlotL;
	ImageView cardSlotR;
	Button buttonP;
	Button buttonE;
	Button buttonM;
	
	//create the view. set button backgrounds, clickable, and texts. start scorekeeper	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamelayout);

    	playerName = (TextView) findViewById(R.id.playertext);
    	cardSlotL = (ImageView) findViewById(R.id.cardleft);
    	cardSlotR = (ImageView) findViewById(R.id.cardright);
    	buttonP = (Button) findViewById(R.id.buttontop);
    	buttonE = (Button) findViewById(R.id.buttonmid);
    	buttonM = (Button) findViewById(R.id.buttonlow);

    	buttonP.setBackgroundResource(R.drawable.btn);
    	buttonE.setBackgroundResource(R.drawable.btn);
    	buttonM.setBackgroundResource(R.drawable.btn);

    	buttonP.setOnClickListener(this);
    	buttonE.setOnClickListener(this);
    	buttonM.setOnClickListener(this);

    	buttonP.setText("+");
    	buttonE.setText("=");
    	buttonM.setText("-");
    	
    	addnewplayers();
        
	}
	
	//starts scorekeeper with request code 1 waiting for result
	private void addnewplayers() {
		Log.i("gamedeal","starting scorekeeper");
		Intent startScoreKeeper = new Intent(this,ScoreKeeperUI.class);
		startActivityForResult(startScoreKeeper, 1);
		overridePendingTransition(0,0);
	}
	
	/*
	 * if returning from scoreKeeperUI retrives player names from scoreKeeperUI and generates scoreKeeper instances
	 * if returning from scores or from gameReveal starts next player
	*/
	@Override
    public void onActivityResult(int requestCode,int resultCode,Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.i("gamedeal","returned from scorekeeper with requestCode: " + requestCode + " and resultCode: " + resultCode);
		
		// returning from scoreKeeperUI		
		if (requestCode == 1) {
			
			//if sucessful generate a new scoreKeeper with player name then start game		
			if (resultCode == 1) {
				Log.i("gamedeal", "returned to game, init...");
				Bundle extras = (Bundle) data.getExtras();
				playerNames = extras.getStringArrayList("players");
				for (int i = 0; i < playerNames.size();i++) {
					ScoreKeeper player = new ScoreKeeper(playerNames.get(i));
					players.add(player);
				}
				numPlayers = playerNames.size();
				playGame();
			}
			
			// if player backs out of scorekeeper
			if (resultCode != 1) finish();
		}
		
		// if returning from scores or gamereveal
		else nextPlayer();
	}
	
	// starts game - sets player name on top of screen, deals hand, and displays it. then awaits click.
	private void playGame() {
		Log.i("gamedeal", "starting playgame");
		Log.i("gamedeal", "current player is number: " + currentPlayer);
		setPlayerName();
		dealHand();
		displayHand();
			
	}
	
	// set player name to top of screen
	private void setPlayerName() {
		Log.i("gamedeal", "starting cleartable");
		playerName.setText(players.get(currentPlayer).playerName);
	}
	
	// deals new hand and sets carddn to the first card returned and cardup to the second
	private void dealHand() {
		Log.i("gamedeal", "starting dealhand");
		Card[] myhand = Card.getNewHand(1);
		cardup = myhand[0];
		carddn = myhand[1];
	}
	
	// displays left slot as cardup
	private void displayHand() {
		Log.i("gamedeal", "starting displayhand");
		cardSlotL.setImageResource(cardup.cardImg);
	}
	
	//sets value of cardValComp -1,0,1 where 1 indicates cardup is greater than carddn
	private void determineResult() {
		Log.i("gamedeal", "starting determineresult");
		if (carddn.cardVal==cardup.cardVal){
			cardValComp = 0;
		}
		if (carddn.cardVal<cardup.cardVal){
			cardValComp = -1;
		}
		if (carddn.cardVal>cardup.cardVal){
			cardValComp = 1;
		}
	}

	// compares user input to cardcomp value to determine of points should be awarded
	private void compareUIToResult() {
		Log.i("gamedeal", "starting compareuitoresult");
		if (playerGuess == cardValComp) {
			players.get(currentPlayer).setPlayerScore(1);
			Log.i("gamedeal", "player scored.");
		}
	}
	
	/*
	 * called when a button is clicked
	 * set user input to -1, 0, or 1 depending on if user clicks -, =, or +
	 * then determine result, compare it user guess, and reveal result
	*/
	
	public void onClick(View v) {
		Log.i("gamedeal", "starting onclick");
		switch(v.getId()) {
			
			// player clicked =
			case R.id.buttonmid: {
				playerGuess = 0;
				break;
			}
			
			// player clicked +
			case R.id.buttontop: {
				playerGuess = 1;
				break;
			}
			
			// player clicked -
			case R.id.buttonlow: {
				playerGuess = -1;
				break;
			}
		}
		determineResult();
		compareUIToResult();
		displayReveal();
		
	}
	
	// starts new game with next player (or first player if none is next)
	private void nextPlayer() {
		Log.i("gamedeal", "starting nextplayer");
		if (currentPlayer == (numPlayers - 1)) {
			currentPlayer = 0;
		}else currentPlayer++;
		
		Log.i("gamedeal", "nextplayer set to: " + currentPlayer);
		Log.i("gamedeal", "total number of players being: " + numPlayers);
		playGame();
	}

	// packages data to pass to GameReveal then starts activity, GameReveal
	private void displayReveal() {
		Log.i("gamedeal", "starting displayreveal");
		Intent i = new Intent(this,GameReveal.class);
		Bundle extrasToPass = new Bundle();
		makeLists();
		extrasToPass.putStringArrayList("playernames", playerNames);
		Log.i("gamedeal", "set to pass: " + playerNames);
		extrasToPass.putIntegerArrayList("playerscores", playerScores);
		Log.i("gamedeal", "set to pass: " + playerScores);
		extrasToPass.putInt("cardup", cardup.cardImg);
		Log.i("gamedeal", "set to pass: " + cardup.cardImg);
		extrasToPass.putInt("carddn", carddn.cardImg);
		Log.i("gamedeal", "set to pass: " + carddn.cardImg);
		extrasToPass.putString("currentplayer", players.get(currentPlayer).playerName);
		Log.i("gamedeal", "set to pass: " + players.get(currentPlayer).playerName);
		i.putExtras(extrasToPass);
		startActivityForResult(i, 2);
		overridePendingTransition(0,0);
		Log.i("gamedeal", "starting new activity - reveal");
	}

	//generates a list of player scores in same order as playernames is listed
	public void makeLists() {
		Log.i("gamedeal", "starting makelists");
		playerScores.clear();
		for(int i=0; i < players.size(); i++) {
			playerScores.add(players.get(i).playerScore);
		}
	}
}









