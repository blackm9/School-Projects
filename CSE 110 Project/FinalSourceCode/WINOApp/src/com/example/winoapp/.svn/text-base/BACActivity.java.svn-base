package com.example.winoapp;

//import org.json.JSONObject;

import library.UserFunctions;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class BACActivity extends Activity {

	EditText num_drinks;
	EditText inputHrs;
	EditText inputMins;
	EditText inputWt;
	EditText inputSex;

	/*
	 * JSON response names private static String KEY_USERNAME = "username";
	 * private static String KEY_SEX = "sex"; private static String KEY_WEIGHT =
	 * "weight";
	 */

	static final int BAC_REQUEST = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bac);

		inputWt = (EditText) findViewById(R.id.weight_field);
		inputSex = (EditText) findViewById(R.id.gender_field);
		num_drinks = (EditText) findViewById(R.id.drinks);
		inputHrs = (EditText) findViewById(R.id.hours_field);
		inputMins = (EditText) findViewById(R.id.minutes_field);

		
		
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
	public void onBackPressed() {
		super.onBackPressed();
	}

/*
 * Method to calculate BAC
 */
	public void calcBAC(View v) {
		inputWt = (EditText) findViewById(R.id.weight_field);
		inputSex = (EditText) findViewById(R.id.gender_field);
		num_drinks = (EditText) findViewById(R.id.drinks);
		inputHrs = (EditText) findViewById(R.id.hours_field);
		inputMins = (EditText) findViewById(R.id.minutes_field);


		String weight = inputWt.getText().toString();
		String sex = inputSex.getText().toString();
		String numOfDrinks = num_drinks.getText().toString();
		String hours = inputHrs.getText().toString();
		String minutes = inputMins.getText().toString();


		int wt = 0;
		String gen = "Male";
		int numDrinks = 0;
		int hrs = 0;
		int mins = 0;

		//Checks user entries and makes sure all entries all valid
		if( weight.length() > 0 ) {
			wt = Integer.parseInt(weight);
			if( wt < 0 ) {
				Toast.makeText(getApplicationContext(), "Weight must be positive!", Toast.LENGTH_LONG)
				.show();
				return;
			}
		}
		else{
			Toast.makeText(getApplicationContext(), "Weight must be provided!", Toast.LENGTH_LONG)
			.show();
			wt = 0;
			return ;
		}
		if( gen.length() > 0) {
			gen = sex;
			if( !("Male".equals(gen)) && !("Female".equals(gen)) ) {
				Toast.makeText(getApplicationContext(), "Gender must be either \"Male\" or \"Female\"", Toast.LENGTH_LONG)
				.show();
				return;
			}
		}
		else {
			Toast.makeText(getApplicationContext(), "Gender must be provided!", Toast.LENGTH_LONG)
			.show();
			return;
		}
		if( numOfDrinks.length() > 0 ) {
			numDrinks = Integer.parseInt(numOfDrinks);
			if( numDrinks > 5 ) 
				numDrinks = 5;
		}
		if( hours.length() > 0 ) {
			hrs = Integer.parseInt(hours);		
		}
		if( minutes.length() > 0 ) {
			mins = Integer.parseInt(minutes);
			if( mins  < 0 || mins > 59 ) {
				Toast.makeText(getApplicationContext(), "Minutes must be within the range 0-59!", Toast.LENGTH_LONG)
				.show();
				return;
			}
		}

		UserFunctions userFunction = new UserFunctions();
		String bacResult = userFunction.processBAC(hrs, mins, numDrinks,wt,gen);
		Toast.makeText(getApplicationContext(), bacResult, Toast.LENGTH_LONG)
					.show();
		return;


		
	}

}
