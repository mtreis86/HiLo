package com.intakt.hilo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/*
 * initial activity. displays loading screen. not needed as it loads too fast to even be displayed. loads StartScreen activity then finishes self
*/
public class Loader extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loader);
        Log.i("loader", "hilo loaded, init startscreen");
        Intent i = new Intent(this,StartScreen.class);
        startActivity(i);
        finish();
	}
}
