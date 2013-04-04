package com.intakt.hilo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

/*
 * this is the main activity for playing the game - no score kept
 * cards are dealt then awaits click
 * then a new activity is started displaying the results
*/
public class PartyDeal extends Activity implements OnClickListener {
	
	//instance vars
	Card cardup;
	Card carddn;
	
	//view vars
	Button button;
	ImageView cardSlotL;
	ImageView cardSlotR;
	
	// setup the view. get a new hand. display faceup card. await click.
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamelayout);
        
        button = (Button) findViewById(R.id.buttonmid);
        cardSlotL = (ImageView) findViewById(R.id.cardleft);
    	cardSlotR = (ImageView) findViewById(R.id.cardright);
        
    	button.setBackgroundResource(R.drawable.btn);
    	
    	getNewHand();
        displayDeal();
	}    

	// display cardup and carddn. set button text. make button clickable
	private void displayDeal() {
		cardSlotL.setImageResource(cardup.cardImg);
		cardSlotR.setImageResource(R.drawable.front);
		Log.i("displayDeal","slots set to "+ cardSlotL + cardSlotR);
		button.setText("Reveal");
		button.setOnClickListener(this);
		
	}

	//generate two card objects from a list of cards
	private void getNewHand() {
		Card[] myHand = Card.getNewHand(1);
		cardup = myHand[0];
		carddn = myHand[1];
		Log.i("getNewHand","cardup = "+cardup.cardNum+" --- carddn = "+carddn.cardNum);
	}

	// start reveal activity. pass it data on card images. finish self.
	public void onClick(View v) {
		Intent reveal = new Intent(this, PartyReveal.class);
		Bundle b = new Bundle();
		b.putInt("cardup", cardup.cardImg);
		b.putInt("carddn", carddn.cardImg);
		reveal.putExtras(b);
		startActivity(reveal);
		overridePendingTransition(0,0);
		Log.i("onClick - GameSingle", "new activity started, finishing self");
		finish();
		
	}

	
}
