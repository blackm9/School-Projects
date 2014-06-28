package com.example.winoapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class TutorialResourceActivity extends Activity {

	private final String WINERY_LOCATOR = "http://www.allamericanwineries.com/AAWMain/locate.htm";
	private final String WINE_REVIEWS = "http://www.wineography.com/wine_reviews.htm";
	private final String WINE_NEWS = "http://greatwinenews.com/";
	private final String WINE_BUSINESS_NEWS = "http://www.winebusiness.com/";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tutorial_resource);
	}

/*	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tutorial_resource, menu);
		return true;
	}*/
	
	public void wineryLocator(View v){
		Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(WINERY_LOCATOR));
		startActivity(webIntent);
	}

	public void wineReviews(View v){
		Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(WINE_REVIEWS));
		startActivity(webIntent);
	}
	
	public void wineNews(View v){
		Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(WINE_NEWS));
		startActivity(webIntent);
	}
	
	public void wineBusinessNews(View v){
		Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(WINE_BUSINESS_NEWS));
		startActivity(webIntent);
	}
}
