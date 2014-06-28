package com.example.winoapp;

import library.UserFunctions;

import org.json.JSONObject;

import android.util.Log;

public class LoginAction {

	private static String KEY_SUCCESS = "success";
	private String email, password;
	private JSONObject json;
	
	public LoginAction(String email, String password) {
		this.email = email;
		this.password = password;
	}
	public Boolean validateLogin() {
		UserFunctions userFunction = new UserFunctions();
		this.json = userFunction.loginUser(email, password);
		if (json == null) {
			return null;
		}
		return parseJSONObject(json);
	}
	public Boolean parseJSONObject(JSONObject json) {
		try {
			if (json.getString(KEY_SUCCESS) != null) {
				String res = json.getString(KEY_SUCCESS); 
				if(Integer.parseInt(res) == 1){
					return true;
				}
				else {
					return false;
				}
			}
		} catch (Exception e) {
			Log.e("Login", "Login Error", e);
		}
		return null;
	}
	public JSONObject getJSON(){
		return this.json;
	}
}
