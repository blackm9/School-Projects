package com.example.winoapp;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import library.UserFunctions;

public class PairMealAction {

	private JSONObject json;

	public Boolean pairWine(String id) {
		UserFunctions userFunction = new UserFunctions();
		this.json = userFunction.pairWine(id);
		if (json == null) {
			return null;
		}
		return true;
	}

	public JSONArray getMealArray() {
		JSONArray myJsonArray = null;
		try {
			if (json != null) {
				JSONArray wines = json.getJSONArray("wines");
				myJsonArray = wines.getJSONObject(0).getJSONArray("recipes");
				
			} else {
				Log.e("pair json", "error");
			}
		} catch (Exception e) {
			Log.e("pair", "error", e);
		}
		return myJsonArray;
	}
}
