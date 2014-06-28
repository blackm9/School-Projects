package com.example.winoapp;

import library.DatabaseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public class LoginDBHandler {

	private Context context;
	private static String KEY_USERNAME = "username";
	private static String KEY_EMAIL = "email";
	private static String KEY_CREATED_AT = "created_at";
	
	public LoginDBHandler (Context context){
		this.context = context;
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