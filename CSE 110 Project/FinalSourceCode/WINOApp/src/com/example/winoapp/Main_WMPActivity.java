package com.example.winoapp;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Main_WMPActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main__wmp);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main__wm, menu);
		return true;
	}

	//Start Meal Pairing Activity
	public void mealOnClick(View view) {
		Intent intent = new Intent(getApplicationContext(),
				Meal_WMPActivity.class);
		startActivity(intent);
	}
	
	//Start Wine Pairing Activity
	public void wineOnClick(View view) {
		Intent intent = new Intent(getApplicationContext(),
				Wine_WMPActivity.class);
		startActivity(intent);
	}

}
