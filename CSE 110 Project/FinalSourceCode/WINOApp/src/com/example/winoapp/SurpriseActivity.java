package com.example.winoapp;

import java.util.List;
import java.util.Random;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class SurpriseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_surprise);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		displayRandomWine();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.surprise, menu);
		

		return true;
	}

	/*
	 * Method that chooses a wine randomly from user's inventory and displays it
	 */
	private void displayRandomWine() {
		WineDBHandler dbh = new WineDBHandler(this, WineDBHandler.INV_TABLE);
		List<Wine> wineList = dbh.getAllWine();
		int size = wineList.size();
		
		if (size == 0){
			setContentView(R.layout.logo_page);
			TextView label = (TextView) findViewById(R.id.blank_logo_label);
			label.setText("Please Grow Your Vineyard First!");
			return;
		}
		 
		Random generator = new Random();
		int ranWine = generator.nextInt(size);
		Wine wine = wineList.get(ranWine);

		TextView nameTV = (TextView) findViewById(R.id.surprise_wine_name_string);
		nameTV.setText(wine.getName());
		TextView varietalTV = (TextView) findViewById(R.id.surprise_wine_varietal_string);
		varietalTV.setText(wine.getVarietal());
		TextView vineyardTV = (TextView) findViewById(R.id.surprise_wine_vineyard_string);
		vineyardTV.setText(wine.getVineyard());
		TextView vintageTV = (TextView) findViewById(R.id.surprise_wine_vintage_string);
		vintageTV.setText(wine.getVintage());
		TextView storageTV = (TextView) findViewById(R.id.surprise_wine_storage_string);
		storageTV.setText(wine.getStorageLoc());
		TextView notesTV = (TextView) findViewById(R.id.surprise_wine_notes_string);
		notesTV.setText(wine.getNotes());

		RatingBar rateV = (RatingBar) findViewById(R.id.surprise_rating_bar);
		// update rating bar
		rateV.setRating((float) wine.getRating());
		// prevent user from changing the rating bar in view mode
		rateV.setIsIndicator(true);

		ImageView imageV = (ImageView) findViewById(R.id.surprise_image);

		if (wine.getImagePath().equals("")) {
			imageV.setImageResource(R.drawable.wino_bottle_transparent2);
		} else {

			try {
				Bitmap bmImg = BitmapFactory.decodeFile(wine.getImagePath());
				imageV.setImageBitmap(bmImg);
			} catch (OutOfMemoryError e) {
				Log.d("ANDRO_ASYNC", String.format("catch Out Of Memory error"));
				e.printStackTrace();
				System.gc();
			}
		}
	}

	// method to click on icon and return to main page
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// app icon in action bar clicked; go home
			Intent intent = new Intent(this, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
