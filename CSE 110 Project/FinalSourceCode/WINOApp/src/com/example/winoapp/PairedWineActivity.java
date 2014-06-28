package com.example.winoapp;

import library.UserFunctions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PairedWineActivity extends Activity {

	private String wine;
	public static JSONArray wine_jsonArray;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_paired_wine);

		TextView wineLabel = (TextView) findViewById(R.id.paired_wine_label);
		TextView wineDescription = (TextView) findViewById(R.id.paired_wine_description_label);
		TextView heading = (TextView) findViewById(R.id.paired_wine_heading);
		Button invSearchButton = (Button) findViewById(R.id.search_paired_wine_inventory_button);
		Button apiSearchButton = (Button) findViewById(R.id.search_paired_wine_api_button);
		Bundle extras = getIntent().getExtras();
		String dish = extras.getString("dish");
		wine = extras.getString("wine");

		heading.setText("Your Pairing for " + dish);
		wineLabel.setText(wine);
		invSearchButton.setText("Search My Vineyard for " + wine);
		apiSearchButton.setText("Search Database for " + wine);

		//Wine descriptions
		if (wine.equals("Cabernet Sauvignon")) {
			wineDescription.setText("Description: Cabernet Sauvignon can be astringent and flat, but over time, flavors of coffee-toffee, caramel and fragrant cigar box emerge, often coupled with a little sweet herb.");
		} else if (wine.equals("Chardonnay")) {
			wineDescription.setText("Description: Chardonnay is one of the most popular white wines in the world, and for good reason. Its crisp taste and delicious notes of vanilla, apple, and tropical fruits make it one of life�s great pleasures.");
		} else if (wine.equals("Chianti")) {
			wineDescription.setText("Description: The flavor profile of Chanti is fruity, with moderate to high natural acidity and generally a medium-body ranging from firm and elegant to assertive and robust and a finish that can tend towards bitterness.");
		} else if (wine.equals("Malbec")) {
			wineDescription.setText("Description: Malbec is typically a medium to full-bodied red wine. Ripe fruit flavors of plums and blackberry give it a jammy characteristic. The tannins are typically a bit tight and the earthy, wood-like appeal makes for a fairly rustic wine.");
		} else if (wine.equals("Merlot")) {
			wineDescription.setText("Description: Merlot can be light, easy drinking, and fruit-driven or more complex, with big, bold tannins, dark fruits and dark spices.");
		} else if (wine.equals("Muscadet")) {
			wineDescription.setText("Description: Muscadet is a light white wine with white flower, citrus & anise");
		} else if (wine.equals("Muscat")) {
			wineDescription.setText("Description: Muscat wines are characterized by pungent floral aromas and sweet, spicy flavors and are used as dessert wines.");
		} else if (wine.equals("Pinot Grigio")) {
			wineDescription.setText("Description: Pinot Grigio / Pinot Gris is usually delicately fragrant and mildly floral with lightly lemon-citrus flavors. Depending upon ripeness at harvest and vinification technique, Pinot Grigio can be tangy and light, or quite rich, round and full bodied.");
		} else if (wine.equals("Pinot Noir")) {
			wineDescription.setText("Description: A sweet and fruity Pinot Noir is typically fruit driven with notes of raspberries, cherries, and strawberries. A more savory and complex Pinot Noir will have more organic flavors as opposed to fruit flavors.");
		} else if (wine.equals("Port")) {
			wineDescription.setText("Description: Port wine is a Portuguese fortified wine produced exclusively in the Douro Valley in the northern provinces of Portugal. It is typically a sweet, red wine, often served as a dessert wine");
		} else if (wine.equals("Prosecco")) {
			wineDescription.setText("Description: Prosecco is an Italian wine � generally a dry, sweet, sparkling wine � made from a variety of white grape of the same name. Very food friendly.");
		} else if (wine.equals("Riesling")) {
			wineDescription.setText("Description: Riesling can produce a sleek and tangy or soft and fruity table wine or a viscous, honeyed dessert wine. Riesling is usually characterized by fruit-forward apricot floral aromas, and rich full-bodied citrus flavors with a fresh off dry to sweet finish.");
		} else if (wine.equals("Rioja")) {
			wineDescription.setText("Description: Rioja wines have characteristic flavors of plum, cherry, and strawberry often mixed with an earthy minerality.");
		} else if (wine.equals("Rose")) {
			wineDescription.setText("Description: Rosy pink bouquet that opens to a medium-bodied palate of fresh strawberry and raspberry flavors. The finish is pleasantly crisp and dry with a substantial length of red fruit.");
		} else if (wine.equals("Sauvignon Blanc")) {
			wineDescription.setText("Sauvignon Blanc is always tangy, tart, nervy, racy, or zesty, and this character pervades even sweet and dessert versions, keeping them from being cloying and sticky-tasting.");
		} else if (wine.equals("Sparkling Wine")) {
			wineDescription.setText("Description: Sparkling Wines can be reminiscent of fresh applesauce, spiced apple, ripe pear and �fresh baked bread� smells, compliments of the yeast that's added during the second fermentation.");
		} else if (wine.equals("Syrah")) {
			wineDescription.setText("Description: Simple Syrah/Shiraz can be quite fruit-driven, exhibiting blackberries, mulberries, loganberries and the like. Upper-end Syrah/Shiraz will have layers of flavor and express its fruit in conjunction with spice, herb and musky wood smoke");
		} else if (wine.equals("Zinfandel")) {
			wineDescription.setText("Description: Zinfandel is a heady, full-bodied red with ripe raspberry fruit accented by pepper and spice. In most cases its fruit is complemented by a dusky, briary, brambly undercurrent that hints of bay leaves and sweet thyme");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	//Searches inventory for selected wine
	public void searchInventoryOnClick(View view){
		WineDBHandler dbh = new WineDBHandler(this, WineDBHandler.INV_TABLE);
		String [] wineArray = dbh.getWineFromType(wine);
		Intent intent = new Intent(this, PairedMealActivity.class);
		intent.putExtra("wineNames", wineArray);
		intent.putExtra("fromMeal", true);
		startActivity(intent);
	}	
	
	//Searches database for the selected wine
	public void searchApiOnClick(View view) throws InterruptedException{
		String wineString = wine;
		wineString.replaceAll("\\s","+");
		wineString = wineString.toLowerCase();
		final String wine = wineString;
		
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					
					// Your code goes here
					UserFunctions userFunction = new UserFunctions();
					JSONObject json = userFunction.findWine(wine);
					try {
						if (json != null) {
							PairedWineActivity.wine_jsonArray = json.getJSONArray("wines");
							
						} else {
							Log.e("pair json", "error");
						}
					} catch (Exception e) {
						Log.e("pair", "error", e);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		thread.start();
		thread.join();
		
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
		intent.putExtra("fromMeal", true);
		startActivity(intent);
	}
}
