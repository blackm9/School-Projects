package com.example.winoapp;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	
	EditText inputEmail;
	EditText inputPassword;
	
	
	static final int REGISTER_REQUEST = 0;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		inputEmail = (EditText) findViewById(R.id.email_field);
		inputPassword = (EditText) findViewById(R.id.password_field);
		
	}
	
	@Override
	public void onBackPressed() {
	   // super.onBackPressed();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REGISTER_REQUEST) {
			if (resultCode == RESULT_OK) {
				finish();
			}
			else if(resultCode == 1){
				Toast.makeText(getApplicationContext(), "Registration Failed", Toast.LENGTH_SHORT).show();
			}
			else if(resultCode == RESULT_CANCELED) {
				//Do nothing
			}
			else {
				Toast.makeText(getApplicationContext(), "An Unknown Error Occur", Toast.LENGTH_SHORT).show();
			}
		}
	}

	//Switch to Register Activity
	public void regOnClick( View v){
		Intent intent = new Intent(this,RegisterActivity.class );
		startActivityForResult(intent, REGISTER_REQUEST);
	}
	
	//Makes checks and logs in user
	public void loginOnClick( View v){
		
		inputEmail = (EditText) findViewById(R.id.email_textView);
		inputPassword = (EditText) findViewById(R.id.password_textView);
		
		final String email = inputEmail.getText().toString();
		final String password = inputPassword.getText().toString();
		
		LoginAction action = new LoginAction(email, password);
		Boolean loginResult = action.validateLogin();
		
		LoginDBHandler dbh = new LoginDBHandler(this);
		
		if (loginResult == null) {
			Toast.makeText(this, "Error occured during Login", Toast.LENGTH_LONG).show();
		}
		else if (loginResult) {
			if (dbh.loginUser(action.getJSON())) {
				LoginActivity.this.setResult(RESULT_OK);
				// Close Login Screen
				finish();
			}
			else {
				Toast.makeText(this, "Error Saving to SQLite", Toast.LENGTH_LONG).show();	
			}
		}
		else {
			Toast.makeText(this, "Invalid Login", Toast.LENGTH_LONG).show();
		}
	}
}
