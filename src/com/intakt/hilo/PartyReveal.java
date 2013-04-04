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
 * activity to display cards resulting from PartyDeal
*/
public class PartyReveal extends Activity implements OnClickListener {
	
	// view vars
	Button button;
	ImageView cardSlotL;
	ImageView cardSlotR;
	int cardupLoc;
	int carddnLoc;
	
	// set view. retreive extras and populate vars. reveal solution. await click
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamelayout);
        
        Bundle b = getIntent().getExtras();
        carddnLoc = b.getInt("carddn",-1);
        cardupLoc = b.getInt("cardup",-1);
        if (carddnLoc == -1) Log.i("SingleDealed onCreate", "carddnloc not found");
        if (carddnLoc == -1) Log.i("SingleDealed onCreate", "carduploc not found");
        
        button = (Button) findViewById(R.id.buttonmid);
        cardSlotL = (ImageView) findViewById(R.id.cardleft);
        cardSlotR = (ImageView) findViewById(R.id.cardright);
    	
        button.setBackgroundResource(R.drawable.btn);
        
        displayReveal();
	}    

	//reveal solution
	private void displayReveal() {
		cardSlotL.setImageResource(cardupLoc);
		cardSlotR.setImageResource(carddnLoc);
		button.setText("Deal");
		button.setOnClickListener(this);
	}

	// on click start new activity PartyDeal and finish self
	public void onClick(View v) {
		Intent deal = new Intent(this, PartyDeal.class);
		startActivity(deal);
		overridePendingTransition(0,0);
		Log.i("onClick - SingleDeal", "new activity started, finishing self");
		finish();
		
	}
}
