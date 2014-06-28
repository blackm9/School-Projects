package com.example.winoapp;

import org.json.JSONArray;
import org.json.JSONException;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class PairedMealActivity extends Activity {
	
	private ListView listView;
	private Button back;
	private TextView heading;
	private String[] arrayVals, arrayId, mealLinks, mealNames;
	private ArrayAdapter<String> adapter;
	public JSONArray meal_jsonArray;
	private boolean recipies = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_paired_meal);
		int rowLayout = R.layout.pairings_row;
		
		arrayVals = getIntent().getStringArrayExtra("wineNames");
		arrayId = getIntent().getStringArrayExtra("wineId");
		boolean fromMeal = getIntent().getBooleanExtra("fromMeal", false);
		
		if (fromMeal) {
			rowLayout = R.layout.normal_row;
		}
		
		listView = (ListView) findViewById(R.id.pairedWine_listView);
		heading = (TextView) findViewById(R.id.pairedWine_wmp_heading);
		back = (Button) findViewById(R.id.pairedWine_wmp_back_button);
		
		adapter = new ArrayAdapter<String>(this, rowLayout,
				R.id.listText, arrayVals);
		listView.setAdapter(adapter);
		if (!fromMeal) {
			listView.setOnItemClickListener(new OnItemClickListener() {
				
				@Override
				public void onItemClick(AdapterView<?> listAdapter, View view,
						int position, long arg3) {
					if (!recipies) {
						wineClicked(position);
						heading.setText("Paired Meals");
						back.setText("Back to Wines");
						recipies = true;
						listView.setAdapter(adapter);
						listView.invalidate();
					}
					else {
						mealClicked(position);
					}
				}
			});
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.paired, menu);
		return true;
	}
	
	
	public void wineClicked(int position) {
		final String id = arrayId[position];
		
		PairMealAction action = new PairMealAction();
		action.pairWine(id);
		meal_jsonArray = action.getMealArray();
		
		mealNames = new String [meal_jsonArray.length()];
		mealLinks = new String [meal_jsonArray.length()];
		for (int i = 0; i < meal_jsonArray.length(); i++) {
			try {
				mealNames[i] = meal_jsonArray.getJSONObject(i).getString("name");
				mealLinks[i] = meal_jsonArray.getJSONObject(i).getString("source_link");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		adapter = new ArrayAdapter<String>(PairedMealActivity.this, R.layout.pairings_row,
				R.id.listText, mealNames);
	}
	
	/*
	 * Redirect app to web browser containing recipe for the selected meal
	 */
	public void mealClicked(int position) {
		String link = mealLinks[position];
		Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
		startActivity(webIntent);
	}
	
	/*
	 * Goes back to former category list when back button clicked
	 */
	public void backOnClick(View view) {
		if (recipies) {
			adapter = new ArrayAdapter<String>(PairedMealActivity.this, R.layout.pairings_row,
					R.id.listText, arrayVals);
			heading.setText("Wine");
			back.setText("Pick a Wine");
			recipies = false;
			listView.setAdapter(adapter);
			listView.invalidate();
		}
	}
}
