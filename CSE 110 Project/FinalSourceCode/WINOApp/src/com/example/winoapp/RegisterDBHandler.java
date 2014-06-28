package com.example.winoapp;

import org.json.JSONException;
import org.json.JSONObject;

import library.DatabaseHandler;
import library.UserFunctions;
import android.content.Context;

public class RegisterDBHandler {
	
	Context context;
	private static final String KEY_USERNAME = "username";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_CREATED_AT = "created_at";

	public RegisterDBHandler (Context context) {
		this.context = context;
	}
	public void logoutUser() {
		UserFunctions userFunction = new UserFunctions();
		userFunction.logoutUser(context);
	}
	public boolean loginUser (JSONObject json){
		DatabaseHandler db = new DatabaseHandler(context);
		JSONObject json_user;
		try {
			json_user = json.getJSONObject("user");
			db.addUser(json_user.getString(KEY_USERNAME), json_user.getString(KEY_EMAIL), json_user.getString(KEY_CREATED_AT));	
			return true;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;		

	}
}
