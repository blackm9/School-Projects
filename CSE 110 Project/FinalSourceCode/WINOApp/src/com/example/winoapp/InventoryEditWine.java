package com.example.winoapp;

import java.util.HashMap;

import library.DatabaseHandler;
import library.UserFunctions;

import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;

public class InventoryEditWine extends EditWineTemplate {

	private static String KEY_SUCCESS = "success";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inventory_edit);

		setUpView();
	}
	
	@Override
	protected void initVars() {
		this.DBTableName = WineDBHandler.INV_TABLE;
		this.listActivity = InventoryActivity.class;
		this.hasQuantity = true;
	}
	
	/**
	 * Add button calls this method to pass information to database handler
	 */
	@Override
	protected Wine saveWineInfo() {

		Wine wine = super.saveWineInfo();

		// upload wine to server-side database
		DatabaseHandler db = new DatabaseHandler(getApplicationContext());
		HashMap<String, String> user = db.getUserDetails();
		final String inventoryId = "inventory_" + user.get("username");
		final Wine threadWine = wine;

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					// Your code goes here
					UserFunctions userFunction = new UserFunctions();
					JSONObject json = userFunction.addWine(inventoryId, ""
							+ threadWine.getID(), threadWine.getName(), threadWine.getVarietal(),
							threadWine.getVintage(), threadWine.getQuantity(),
							threadWine.getStorageLoc(), threadWine.getRating(),
							threadWine.getNotes());

					try {
						if (json.getString(KEY_SUCCESS) != null) {
							String res = json.getString(KEY_SUCCESS);
							if (Integer.parseInt(res) != 1) {

							}
						}
					} catch (Exception e) {
						Log.e("reg", "error", e);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		thread.start();

		return wine;
	}
}
