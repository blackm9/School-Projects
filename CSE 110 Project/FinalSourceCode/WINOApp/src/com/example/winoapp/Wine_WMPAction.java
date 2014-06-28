package com.example.winoapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import library.UserFunctions;

public class Wine_WMPAction {
	
	private JSONObject json;
	
	public Boolean findWine(String wine) {
		UserFunctions userFunction = new UserFunctions();
		this.json = userFunction.findWine(wine);
		if (json == null) {
			return null;
		}
		return true;
	}
	public boolean noResults() {
		try {
			if (this.json.getJSONObject("meta").getInt("results") == 0) {
				return true;
			}
			else {
				return false;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return true;
	}
	public JSONArray getWineArray() {
		try {
			return json.getJSONArray("wines");
		} catch (JSONException e) {
			Log.e("pair wineArray", "error");
		}
		return null;
	}

}
