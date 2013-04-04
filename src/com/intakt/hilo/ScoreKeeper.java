package com.intakt.hilo;

import java.util.ArrayList;
import android.util.Log;

/*
 * generate new player scorekeepers with playername as input and initial score of 0
*/
public class ScoreKeeper{
	
	// static vars
	public static int numPlayers = 0;
	
	// instance vars
	public String playerName;
	public int playerScore;
	public int playerNumber;
	
	public ArrayList<Integer> playerScores = new ArrayList<Integer>();
	
	
	// take input of player name and generate new player with score of 0 and a playernumber starting at 0
	public ScoreKeeper(String name) {
		this.playerNumber = setPlayerNumber();
		Log.i("scorekeeper","player set to number: "+this.playerNumber);
		this.playerName = name;
		Log.i("scorekeeper","player name: "+this.playerName);
		this.playerScore = 0;
		Log.i("scorekeeper", "new player added. score set to: " + this.playerScore);
	}
	
	// set player score to a new score adding old score to it and keeping track of individual scores in a list
	public void setPlayerScore(Integer newScore) {
		Log.i("scorekeeper", "new score of " + newScore);
		this.playerScores.add(newScore);
		this.playerScore += newScore;
		Log.i("scorekeeper", "player score now " + this.playerScore);
	}
	
	// create player number based on who gets added first
	private int setPlayerNumber() {
		numPlayers++;
		return numPlayers;
	}
	
}
