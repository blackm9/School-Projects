package com.example.winoapp;

import java.util.Calendar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	
	public boolean error = false;
	private static final int MINYEAR = 1930;
	Button submit_button;
	EditText inputUsername;
	EditText inputFirstName;
	EditText inputLastName;
	EditText inputEmail;
	EditText inputPassword;
	EditText inputDOB;
	EditText inputWeight;
	Spinner monthSpinner;
	Spinner daySpinner;
	Spinner yearSpinner;
	RadioGroup radioSex;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		submit_button = (Button) findViewById(R.id.register_submit_button);
		inputUsername = (EditText) findViewById(R.id.username_field);
		inputFirstName = (EditText) findViewById(R.id.first_name_field);
		inputLastName = (EditText) findViewById(R.id.last_name_field);
		inputEmail = (EditText) findViewById(R.id.email_field);
		inputPassword = (EditText) findViewById(R.id.password_field);
		inputWeight = (EditText) findViewById(R.id.weight_field);
		radioSex = (RadioGroup) findViewById(R.id.sex_radioGroup);
		monthSpinner = (Spinner) findViewById(R.id.dob_month_spinner);
		daySpinner = (Spinner) findViewById(R.id.dob_day_spinner);
		yearSpinner = (Spinner) findViewById(R.id.dob_year_spinner);
		
		monthSpinnerHandler();
		daySpinnerHandler();
		yearSpinnerHandler();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}
	
	@Override
	public void onBackPressed() {
	   // super.onBackPressed();
		this.setResult(RESULT_CANCELED);
		finish();
	}
	
	public void submit(View v) throws InterruptedException {
		final String username = inputUsername.getText().toString();
		final String firstName = inputFirstName.getText().toString();
		final String lastName = inputLastName.getText().toString();
		final String email = inputEmail.getText().toString();
		final String password = inputPassword.getText().toString();
		final String weight = inputWeight.getText().toString();
		String month = monthSpinner.getSelectedItem().toString();
		String day = monthSpinner.getSelectedItem().toString();
		String year = monthSpinner.getSelectedItem().toString();
		
		RegisterAction action = new RegisterAction(username, firstName, lastName, email, password, weight, month, day, year);
		action.parseFields(radioSex);
		Boolean registerResult = action.registerUser();
		
		RegisterDBHandler dbh = new RegisterDBHandler(this);

		
		if (registerResult == null) {
			Toast.makeText(this, "Error occured during Register", Toast.LENGTH_LONG).show();
		}
		else if (registerResult) {
			dbh.logoutUser();
			if (dbh.loginUser(action.getJSON())) {
				RegisterActivity.this.setResult(RESULT_OK);
				// Close Login Screen
				finish();
			}
			else {
				Toast.makeText(this, "Error Saving to SQLite", Toast.LENGTH_LONG).show();	
			}
		}
		else {
			Toast.makeText(this, "Invalid Information", Toast.LENGTH_LONG).show();
			Toast.makeText(this, action.getError(), Toast.LENGTH_LONG).show();			
		}
	}
	private void yearSpinnerHandler(){
		// get current year
		Calendar c = Calendar.getInstance(); 
		int currentYear = c.get(Calendar.YEAR);
		
		int totalYears = currentYear - MINYEAR;

		// store current year to previous year
		String[] years = new String[totalYears+1];
		years[0] = "YYYY"; // default string
		for(int i=1; i<=totalYears; i++)
			years[i]=Integer.toString(currentYear-i);

		setUpSpinner(R.id.dob_year_spinner, years);
	}
	private void daySpinnerHandler(){
		// store current year to previous year
		String[] day = new String[32];
		day[0] = "DD"; // default string
		for(int i=1; i<=31; i++) {
			day[i]=Integer.toString(i);
		}

		setUpSpinner(R.id.dob_day_spinner, day);
	}
	private void monthSpinnerHandler(){
		// store current year to previous year
		String[] month = new String[13];
		month[0] = "MM"; // default string
		for(int i=1; i<=12; i++) {
			month[i]=Integer.toString(i);
		}

		setUpSpinner(R.id.dob_month_spinner, month);
	}
	private void setUpSpinner(int id, String[] spinnerStrings){

		Spinner spinner = (Spinner) findViewById(id);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	                android.R.layout.simple_spinner_item, spinnerStrings);
		spinner.setAdapter(adapter);
	}
}