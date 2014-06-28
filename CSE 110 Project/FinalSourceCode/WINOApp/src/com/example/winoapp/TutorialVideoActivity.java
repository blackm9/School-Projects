package com.example.winoapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class TutorialVideoActivity extends Activity {

	//define urls of the video tutorials we'll use as constants
	private final String WINE_101_VIDEO = "http://www.winetastetv.com/video/wine-101/education/1950/wine-tasting-101";
	private final String ORDER_RESTAURANT_WINE = "http://www.winetastetv.com/video/wine-101/education/1951/ordering-wine-at-a-restaurant-";
	private final String LEFTOVER_WINE = "http://www.winetastetv.com/video/wine-101/education/1841/what-to-do-with-leftover-wine-";
	private final String WINE_GADGETS = "http://www.winetastetv.com/video/wine-101/education/1834/ideal-wine-gadgets";
	private final String WINE_GLASSES = "http://www.winetastetv.com/video/wine-101/education/2539/wine-glasses";
	private final String WINE_CHEESE_PAIR = "http://www.winetastetv.com/video/wine-101/education/2332/wine-and-cheese-pairing-";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tutorial_video);
	}
	
	//display wine 101 tutorial in separate browser
	public void wine101Tut(View v){
		Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(WINE_101_VIDEO));
		startActivity(webIntent);
	}
	
	//display tutorial on ordering wine at a restaurant in separate browser
	public void restaurantWineTut(View v){
		Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ORDER_RESTAURANT_WINE));
		startActivity(webIntent);
	}
	
	//display leftover wine tutorial in separate browser
	public void leftoverWineTut(View v){
		Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(LEFTOVER_WINE));
		startActivity(webIntent);
	}
	
	//display necessary wine gear tutorial in separate browser
	public void wineGadgetsTut(View v){
		Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(WINE_GADGETS));
		startActivity(webIntent);
	}
	
	//display wine glass tutorial in separate browser
	public void wineGlassesTut(View v){
		Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(WINE_GLASSES));
		startActivity(webIntent);
	}
	
	//display wine cheese pairing tutorial in separate browser
	public void wineCheesePairTut(View v){
		Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(WINE_CHEESE_PAIR));
		startActivity(webIntent);
	}

}
