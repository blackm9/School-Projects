package com.example.winoapp;

import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Wine_WMPActivity extends Activity {
	public static JSONArray wine_jsonArray;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wine__wmp);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wine__wm, menu);
		return true;
	}

	public void pairWineOnClick(View v) throws InterruptedException {
		EditText wineInput = (EditText) findViewById(R.id.find_wine_search_field);
		String wineString = wineInput.getText().toString();
		wineString.replaceAll("\\s","+");
		wineString = wineString.toLowerCase(Locale.getDefault());
		final String wine = wineString;
		
		Wine_WMPAction action = new Wine_WMPAction();
		Boolean wineResult = action.findWine(wine);
		if (wineResult == null) {
			Toast.makeText(this, "Error in Snooth Server", Toast.LENGTH_LONG).show();
			return;
		}
		else if (action.noResults()){
			wine_jsonArray = null;
		}
		else {
			wine_jsonArray = action.getWineArray();
		}
		
		if (wine_jsonArray == null) {
			String [] wineNames = new String [1];
			wineNames[0] = "No Matches";
			Intent intent = new Intent(this, PairedMealActivity.class);
			intent.putExtra("wineNames", wineNames);
			intent.putExtra("fromMeal", true);
			startActivity(intent);
			return;
		}
		
		String [] wineNamesDup = new String [wine_jsonArray.length()];
		String [] wineIdDup = new String [wine_jsonArray.length()];
		int index = 0, count = 0;
		boolean ignore;
		
		for (; index < wine_jsonArray.length(); index++) {
			try {
				ignore = false;
				for (int j = 0; j < count; j++) {
					if (wineNamesDup[j].equals(wine_jsonArray.getJSONObject(index).getString("name"))){
						ignore = true;
						break;
					}
				}
				if(!ignore) {
					wineNamesDup[count] = wine_jsonArray.getJSONObject(index).getString("name");
					wineIdDup[count] = wine_jsonArray.getJSONObject(index).getString("code");
					System.out.println(wineNamesDup[count]);
					count++;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			index++;
		}
		String [] wineNames = new String [count];
		String [] wineId = new String [count];
		for (int i = 0; i < count; i++) {
			wineNames[i] = wineNamesDup[i];
			wineId[i] = wineIdDup[i];
		}
		
		Intent intent = new Intent(this, PairedMealActivity.class);
		intent.putExtra("wineNames", wineNames);
		intent.putExtra("wineId", wineId);
		startActivity(intent);
	}
}
