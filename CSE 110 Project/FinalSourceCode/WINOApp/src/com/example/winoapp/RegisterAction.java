package com.example.winoapp;

import org.json.JSONException;
import org.json.JSONObject;

import library.UserFunctions;
import android.util.Log;
import android.widget.RadioGroup;

public class RegisterAction {

	private static final String KEY_SUCCESS = "success";
	private static final String KEY_ERROR_MSG = "error_msg";
	private JSONObject json;
	private String username, firstName, lastName, email, password, weight,
			gender, month, day, year, DOB;

	public RegisterAction(String username, String firstName, String lastName,
			String email, String password, String weight, String month,
			String day, String year) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.weight = weight;
		this.month = month;
		this.day = day;
		this.year = year;
	}

	public void parseFields(RadioGroup radioSex) {
		if (radioSex.getCheckedRadioButtonId() == R.id.male_radio_button) {
			this.gender = "m";
		} else if (radioSex.getCheckedRadioButtonId() == R.id.male_radio_button) {
			this.gender = "f";
		}
		else {
			this.gender = "n";
		}

		this.DOB = month + "-" + day + "-" + year;
	}

	public Boolean registerUser() {
		UserFunctions userFunction = new UserFunctions();
		this.json = userFunction.registerUser(username, email, password,
				firstName, lastName, DOB, weight, gender);
		if (json == null) {
			return null;
		}
		return parseJSONObject();
	}

	private Boolean parseJSONObject() {
		try {
			if (json.getString(KEY_SUCCESS) != null) {
				String res = json.getString(KEY_SUCCESS);
				if (Integer.parseInt(res) == 1) {
					return true;
				} else {
					return false;
				}
			}
		} catch (Exception e) {
			Log.e("Register", "Register Error", e);
		}
		return null;
	}

	public JSONObject getJSON() {
		return this.json;
	}
	public String getError() {
		String error;
		try {
			error = json.getString(KEY_ERROR_MSG);
		} catch (JSONException e) {
			error = "Error with Server";
			e.printStackTrace();
		}
		return error;
	}
}
