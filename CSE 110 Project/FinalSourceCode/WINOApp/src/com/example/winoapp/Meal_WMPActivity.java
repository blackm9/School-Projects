package com.example.winoapp;

import java.util.Arrays;
import java.util.Stack;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Meal_WMPActivity extends Activity {

	private ListView listView;
	private Button back;
	private TextView heading;
	private String[] arrayVals;
	private String previous, dish, category = "";
	private ArrayAdapter<String> adapter;
	private Stack<String> track;
	private boolean terminal = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meal__wmp);

		listView = (ListView) findViewById(R.id.listView);
		heading = (TextView) findViewById(R.id.meal_wmp_heading);
		back = (Button) findViewById(R.id.meal_wmp_back_button);
		track = new Stack<String>();
		previous = "Categories";

		arrayVals = this.getResources().getStringArray(R.array.CategoriesArray);

		adapter = new ArrayAdapter<String>(this, R.layout.pairings_row,
				R.id.listText, arrayVals);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> listAdapter, View view,
					int position, long arg3) {

				TextView listText = (TextView) view.findViewById(R.id.listText);
				listView = (ListView) findViewById(R.id.listView);

				String text = listText.getText().toString();

				menuClicked(text);
				if (!terminal) {
					track.push(previous);
					previous = text;
					heading.setText(text);
					listView.setAdapter(adapter);
					back.setText("Back to " + track.peek());
					listView.invalidate();
				}
			}

		});
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				listView = (ListView) findViewById(R.id.listView);
				if (!track.empty()) {
					previous = track.peek();
					menuClicked(track.pop());
					heading.setText(previous);
					listView.setAdapter(adapter);
					listView.invalidate();
				}
				if (track.empty()) {
					back.setText("Pick a Category");
				} else {
					back.setText("Back to " + track.peek());
				}
			}
		});

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	// method to click on icon and return to main page
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// app icon in action bar clicked; go home
			Intent intent = new Intent(this, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.meal__wm, menu);
		return true;
	}

	public void menuClicked(String text) {
		terminal = false;
		if (text.equals("Categories")) {
			category = "";
			arrayVals = this.getResources().getStringArray(
					R.array.CategoriesArray);
			adapter = new ArrayAdapter<String>(this, R.layout.pairings_row,
					R.id.listText, arrayVals);
		} else if (text.equals("Meat")) {
			arrayVals = this.getResources().getStringArray(R.array.MeatArray);
			adapter = new ArrayAdapter<String>(this, R.layout.pairings_row,
					R.id.listText, arrayVals);
		} else if (text.equals("Seafood")) {
			arrayVals = this.getResources()
					.getStringArray(R.array.SeafoodArray);
			adapter = new ArrayAdapter<String>(this, R.layout.pairings_row,
					R.id.listText, arrayVals);
		} else if (text.equals("Cheese")) {
			arrayVals = this.getResources().getStringArray(R.array.CheeseArray);
			adapter = new ArrayAdapter<String>(this, R.layout.pairings_row,
					R.id.listText, arrayVals);
		} else if (text.equals("Appetizers, Soup / Salad")) {
			category = "";
			arrayVals = this.getResources().getStringArray(
					R.array.AppetizerSSArray);
			adapter = new ArrayAdapter<String>(this, R.layout.pairings_row,
					R.id.listText, arrayVals);
		} else if (text.equals("Pizza / Pasta")) {
			arrayVals = this.getResources().getStringArray(
					R.array.PizzaPastaArray);
			adapter = new ArrayAdapter<String>(this, R.layout.pairings_row,
					R.id.listText, arrayVals);
		} else if (text.equals("Ethnic Cuisine")) {
			arrayVals = this.getResources().getStringArray(R.array.EthnicArray);
			adapter = new ArrayAdapter<String>(this, R.layout.pairings_row,
					R.id.listText, arrayVals);
		} else if (text.equals("Desserts")) {
			arrayVals = this.getResources()
					.getStringArray(R.array.DessertArray);
			adapter = new ArrayAdapter<String>(this, R.layout.pairings_row,
					R.id.listText, arrayVals);
		} else if (text.equals("Beef")) {
			category = "";
			arrayVals = this.getResources().getStringArray(R.array.BeefArray);
			adapter = new ArrayAdapter<String>(this, R.layout.pairings_row,
					R.id.listText, arrayVals);
		} else if (text.equals("Steaks")) {
			arrayVals = this.getResources().getStringArray(R.array.SteaksArray);
			adapter = new ArrayAdapter<String>(this, R.layout.pairings_row,
					R.id.listText, arrayVals);
		} else if (text.equals("Burgers")) {
			arrayVals = this.getResources()
					.getStringArray(R.array.BurgersArray);
			adapter = new ArrayAdapter<String>(this, R.layout.pairings_row,
					R.id.listText, arrayVals);
		} else if (text.equals("Seasonings")) {
			category = " Beef";
			arrayVals = this.getResources().getStringArray(
					R.array.SeasoningsArray);
			adapter = new ArrayAdapter<String>(this, R.layout.pairings_row,
					R.id.listText, arrayVals);
		} else if (text.equals("Sauces")) {
			category = " Beef";
			arrayVals = this.getResources().getStringArray(R.array.SaucesArray);
			adapter = new ArrayAdapter<String>(this, R.layout.pairings_row,
					R.id.listText, arrayVals);
		} else if (text.equals("Poultry")) {
			arrayVals = this.getResources()
					.getStringArray(R.array.PoultryArray);
			adapter = new ArrayAdapter<String>(this, R.layout.pairings_row,
					R.id.listText, arrayVals);
		} else if (text.equals("Chicken")) {
			arrayVals = this.getResources()
					.getStringArray(R.array.ChickenArray);
			adapter = new ArrayAdapter<String>(this, R.layout.pairings_row,
					R.id.listText, arrayVals);
		} else if (text.equals("Herbs/Sauce")) {
			arrayVals = this.getResources().getStringArray(R.array.HerbsArray);
			adapter = new ArrayAdapter<String>(this, R.layout.pairings_row,
					R.id.listText, arrayVals);
		} else if (text.equals("Turkey")) {
			arrayVals = this.getResources().getStringArray(R.array.TurkeyArray);
			adapter = new ArrayAdapter<String>(this, R.layout.pairings_row,
					R.id.listText, arrayVals);
		} else if (text.equals("Duck")) {
			arrayVals = this.getResources().getStringArray(R.array.DuckArray);
			adapter = new ArrayAdapter<String>(this, R.layout.pairings_row,
					R.id.listText, arrayVals);
		} else if (text.equals("Pork")) {
			arrayVals = this.getResources().getStringArray(R.array.PorkArray);
			adapter = new ArrayAdapter<String>(this, R.layout.pairings_row,
					R.id.listText, arrayVals);
		} else if (text.equals("Veal")) {
			arrayVals = this.getResources().getStringArray(R.array.VealArray);
			adapter = new ArrayAdapter<String>(this, R.layout.pairings_row,
					R.id.listText, arrayVals);
		} else if (text.equals("Lamb")) {
			arrayVals = this.getResources().getStringArray(R.array.LambArray);
			adapter = new ArrayAdapter<String>(this, R.layout.pairings_row,
					R.id.listText, arrayVals);
		} else if (text.equals("Ham")) {
			arrayVals = this.getResources().getStringArray(R.array.HamArray);
			adapter = new ArrayAdapter<String>(this, R.layout.pairings_row,
					R.id.listText, arrayVals);
		} else if (text.equals("White Fish")) {
			arrayVals = this.getResources().getStringArray(
					R.array.WhiteFishArray);
			adapter = new ArrayAdapter<String>(this, R.layout.pairings_row,
					R.id.listText, arrayVals);
		} else if (text.equals("Dark Fish")) {
			arrayVals = this.getResources().getStringArray(
					R.array.DarkFishArray);
			adapter = new ArrayAdapter<String>(this, R.layout.pairings_row,
					R.id.listText, arrayVals);
		} else if (text.equals("Shellfish")) {
			arrayVals = this.getResources().getStringArray(
					R.array.ShellfishArray);
			adapter = new ArrayAdapter<String>(this, R.layout.pairings_row,
					R.id.listText, arrayVals);
		} else if (text.equals("Sushi")) {
			arrayVals = this.getResources().getStringArray(R.array.SushiArray);
			adapter = new ArrayAdapter<String>(this, R.layout.pairings_row,
					R.id.listText, arrayVals);
		} else if (text.equals("Soft Cheese")) {
			arrayVals = this.getResources().getStringArray(
					R.array.SoftCheeseArray);
			adapter = new ArrayAdapter<String>(this, R.layout.pairings_row,
					R.id.listText, arrayVals);
		} else if (text.equals("Hard Cheese")) {
			arrayVals = this.getResources().getStringArray(
					R.array.HardCheeseArray);
			adapter = new ArrayAdapter<String>(this, R.layout.pairings_row,
					R.id.listText, arrayVals);
		} else if (text.equals("Soup")) {
			category = " Soup";
			arrayVals = this.getResources().getStringArray(R.array.SoupArray);
			adapter = new ArrayAdapter<String>(this, R.layout.pairings_row,
					R.id.listText, arrayVals);
		} else if (text.equals("Veggies")) {
			arrayVals = this.getResources()
					.getStringArray(R.array.VegSoupArray);
			adapter = new ArrayAdapter<String>(this, R.layout.pairings_row,
					R.id.listText, arrayVals);
		} else if (text.equals("Cream Based")) {
			arrayVals = this.getResources().getStringArray(
					R.array.CreamSoupArray);
			adapter = new ArrayAdapter<String>(this, R.layout.pairings_row,
					R.id.listText, arrayVals);
		} else if (text.equals("Seafood Based")) {
			arrayVals = this.getResources().getStringArray(
					R.array.SeafoodSoupArray);
			adapter = new ArrayAdapter<String>(this, R.layout.pairings_row,
					R.id.listText, arrayVals);
		} else if (text.equals("Salad")) {
			arrayVals = this.getResources().getStringArray(R.array.SaladArray);
			adapter = new ArrayAdapter<String>(this, R.layout.pairings_row,
					R.id.listText, arrayVals);
		} else if (text.equals("Hors d\'oeuvres")) {
			arrayVals = this.getResources().getStringArray(R.array.HorsDArray);
			adapter = new ArrayAdapter<String>(this, R.layout.pairings_row,
					R.id.listText, arrayVals);
		} else if (text.equals("Snacks")) {
			arrayVals = this.getResources().getStringArray(R.array.SnacksArray);
			adapter = new ArrayAdapter<String>(this, R.layout.pairings_row,
					R.id.listText, arrayVals);
		} else if (text.equals("Sandwiches")) {
			arrayVals = this.getResources().getStringArray(
					R.array.SandwichesArray);
			adapter = new ArrayAdapter<String>(this, R.layout.pairings_row,
					R.id.listText, arrayVals);
		} else if (text.equals("Wings")) {
			arrayVals = this.getResources().getStringArray(R.array.WingsArray);
			adapter = new ArrayAdapter<String>(this, R.layout.pairings_row,
					R.id.listText, arrayVals);
		} else if (text.equals("Pizza")) {
			arrayVals = this.getResources().getStringArray(R.array.PizzaArray);
			adapter = new ArrayAdapter<String>(this, R.layout.pairings_row,
					R.id.listText, arrayVals);
		} else if (text.equals("Pasta")) {
			arrayVals = this.getResources().getStringArray(R.array.PastaArray);
			adapter = new ArrayAdapter<String>(this, R.layout.pairings_row,
					R.id.listText, arrayVals);
		} else if (text.equals("Indian")) {
			arrayVals = this.getResources().getStringArray(R.array.IndianArray);
			adapter = new ArrayAdapter<String>(this, R.layout.pairings_row,
					R.id.listText, arrayVals);
		} else if (text.equals("Chinese")) {
			arrayVals = this.getResources()
					.getStringArray(R.array.ChineseArray);
			adapter = new ArrayAdapter<String>(this, R.layout.pairings_row,
					R.id.listText, arrayVals);
		} else if (text.equals("Italian")) {
			arrayVals = this.getResources()
					.getStringArray(R.array.ItalianArray);
			adapter = new ArrayAdapter<String>(this, R.layout.pairings_row,
					R.id.listText, arrayVals);
		} else if (text.equals("Mexican")) {
			arrayVals = this.getResources()
					.getStringArray(R.array.MexicanArray);
			adapter = new ArrayAdapter<String>(this, R.layout.pairings_row,
					R.id.listText, arrayVals);
		} else if (text.equals("Pastries")) {
			arrayVals = this.getResources().getStringArray(
					R.array.PastriesArray);
			adapter = new ArrayAdapter<String>(this, R.layout.pairings_row,
					R.id.listText, arrayVals);
		} else if (text.equals("Chocolates")) {
			arrayVals = this.getResources().getStringArray(
					R.array.ChocolatesArray);
			adapter = new ArrayAdapter<String>(this, R.layout.pairings_row,
					R.id.listText, arrayVals);
		} else if (text.equals("Cake")) {
			arrayVals = this.getResources().getStringArray(R.array.CakeArray);
			adapter = new ArrayAdapter<String>(this, R.layout.pairings_row,
					R.id.listText, arrayVals);
		} else if (text.equals("Fruity Desserts")) {
			arrayVals = this.getResources()
					.getStringArray(R.array.FruityDArray);
			adapter = new ArrayAdapter<String>(this, R.layout.pairings_row,
					R.id.listText, arrayVals);
		} else if (text.equals("Nutty Desserts")) {
			arrayVals = this.getResources().getStringArray(R.array.NuttyDArray);
			adapter = new ArrayAdapter<String>(this, R.layout.pairings_row,
					R.id.listText, arrayVals);
		} else if (text.equals("Creamy Desserts")) {
			arrayVals = this.getResources()
					.getStringArray(R.array.CreamyDArray);
			adapter = new ArrayAdapter<String>(this, R.layout.pairings_row,
					R.id.listText, arrayVals);
		} else {
			matchWine(text);
			terminal = true;
		}
	}

	public void matchWine(String text) {
		String wine = "";
		
		dish = text + category;
		if (text.equals("Clams")) {
			wine = "Muscadet";
		}

		String[] Cabernet_Sauvignon = { "Asiago", "Au Jus",
				"Beef w/ sauteed Onions", "Bittersweet Chocolate", "Blue",
				"Burger w/ Strong cheese", "Chocolate Cake",
				"Chocolates w/ Fruits", "Duck w/ Mushrooms",
				"Filet Mignon / Tenderloin", "Lamb Chops / Steaks",
				"Montreal or Peppercorn", "Mustard Chicken", "Pizza w/ Cheese",
				"Rib Eye", "Roast Beef", "Veal Chops", "Worcestershire",
				"Chocolates w/ Nuts" };
		String[] Chardonnay = { "Brie", "Broccoli", "California Roll",
				"Clam Chowder", "Corn", "Cream Chicken", "Garden", "Gruyere", "Honey",
				"Lime/Lemon Chicken", "Lobster", "Onion Chicken", "Pasta w/ Cheese",
				"Pesto Sauce", "Poultry Based", "Pudding", "Roast Turkey",
				"Salad w/ Pasta", "Salad w/ Seafood", "Salmon", "Sea Bass",
				"Shrimp", "Snapper", "Swordfish", "Vanilla Cake",
				"Veal Cutlets" };
		String[] Chianti = { "Butter / Cream Sauce", "Marinara Sauce",
				"Mortadella" };
		String[] Malbec = { "BBQ Chicken", "Beef w/ Broccoli", "Roast Lamb",
				"Teriyaki Marinade" };
		String[] Merlot = { "BBQ Duck", "Beef w/ sauteed Mushrooms",
				"Burger w/ Mustard or Relish", "Garlic & Pepper sauce",
				"Goose", "Hamburger", "Hen", "Minestrone", "Monterey Jack",
				"Mushroom & Onion Gravy", "Original", "Parmesan", "Pork Chops",
				"Quesadillas", "Sirloin", "Stuffed Mushrooms", "Swiss",
				"T-Bone", "Tomato", "Veal Marsala", "Veal Parmigiana",
				"Baby Swiss", "Salad w/ Red Meat" };
		String[] Muscat = { "Cheesecake", "Crème Brulee", "Pecan",
				"Coffee Cake" };
		String[] Pinot_Grigio = { "Alfredo Sauce", "Chicken Salad", "Cod",
				"Eel", "Garlic Chicken", "Gouda", "Grilled", "Ham w/ Honey",
				"Milanese Risotto", "Mozzarella", "Oysters",
				"Pasta w/ Seafood", "Salad w/ Chicken", "Salami", "Sole",
				"Strawberry", "Sweet/Sour Pork", "Trenette col Pesto", "Pizza w/ White Sauce" };
		String[] Pinot_Noir = { "Baked Ham", "Chicken Chow Mein", "Dark Meat",
				"Duck w/ Fruit", "Feta", "Garlic/Rosemary Duck", "Grilled",
				"Ground Turkey", "Ham w/ Mustard", "Lamb Stew", "Marsala Chicken",
				"Mushroom", "Peanut", "Peppercorn Turkey", "Pizza w/ Chicken",
				"Salmon", "Tandoori Chicken", "Turkey w/ Gravy", "Veal Shanks",
				"Veggie Pizza" };
		String[] Port = { "Almond", "Biscotti", "Brownies", "Hazelnut", "Milk Chocolate",
				"Walnut" };
		String[] Prosecco = { "Deviled Eggs", "Macadamia Nut",
				"Trofie al Pesto" };
		String[] Riesling = { "Apple", "Buffalo", "Coconut",
				"Ginger / Spice Cake", "Moo-Shu Pork", "Pumpkin",
				"Spicy / Peppery", "Spicy Chicken", "Spicy/Buffalo Chicken",
				"Turkey Sausage" };
		String[] Rioja = { "Burger w/ Onions or Mushrooms", "Enchiladas",
				"Huevos Rancheros", "Mexican Paella", "Pork w/ Mustard",
				"Roast Duck", "Spicy" };
		String[] Rose = { "BBQ Wings", "Chicken Tikka Masala", "Corned Beef",
				"Fish Soup", "General Tsao Chicken", "Spicy Ham",
				"Veal Piccata" };
		String[] Sauvignon_Blanc = { "Asian Chicken", "Canederil", "Catfish",
				"Cheddar", "Flounder", "Goat", "Ham w/ Pineapple", "Harvati",
				"Korma", "Lemon Cake", "Mackerel", "Mustard Seasoning", "Parmesan",
				"Salad w/ Citrus", "Samosas", "Scallops", "Spicy Tuna",
				"Spider Roll", "Trout", "Tuna", "Yellowtail Sashimi" };
		String[] Sparkling_Wine = { "Crab", "Lemon", "Peking Duck",
				"Raspberry", "Tunafish", "White Chocolate" };
		String[] Syrah = { "BBQ Pork", "Beef/Meat Based", "Burger w/ Ketchup",
				"Curry", "French Onion", "Meat Sauce", "Pheasant",
				"Porterhouse", "Prime Rib", "Provolone", "Smoked Turkey",
				"Teriyaki" };
		String[] Zinfandel = { "BBQ Turkey", "BBQ Rub", "Burger w/ Bacon",
				"Carne Asada", "Fajitas", "Gorgonzola", "Lamb Curry",
				"Lamb Shanks", "NY Strip", "Pizza w/ Meat", "Pork w/ Fruit",
				"Spicy", "Taco", "Tomato Sauce" };
		if (Arrays.asList(Cabernet_Sauvignon).contains(text)) {
			wine = "Cabernet Sauvignon";
		} else if (Arrays.asList(Chardonnay).contains(text)) {
			wine = "Chardonnay";
		} else if (Arrays.asList(Chianti).contains(text)) {
			wine = "Chianti";
		} else if (Arrays.asList(Malbec).contains(text)) {
			wine = "Malbec";
		} else if (Arrays.asList(Merlot).contains(text)) {
			wine = "Merlot";
		} else if (Arrays.asList(Muscat).contains(text)) {
			wine = "Muscat";
		} else if (Arrays.asList(Pinot_Grigio).contains(text)) {
			wine = "Pinot Grigio";
		} else if (Arrays.asList(Pinot_Noir).contains(text)) {
			wine = "Pinot Noir";
		} else if (Arrays.asList(Port).contains(text)) {
			wine = "Port";
		} else if (Arrays.asList(Prosecco).contains(text)) {
			wine = "Prosecco";
		} else if (Arrays.asList(Riesling).contains(text)) {
			wine = "Riesling";
		} else if (Arrays.asList(Rioja).contains(text)) {
			wine = "Rioja";
		} else if (Arrays.asList(Rose).contains(text)) {
			wine = "Rose";
		} else if (Arrays.asList(Sauvignon_Blanc).contains(text)) {
			wine = "Sauvignon Blanc";
		} else if (Arrays.asList(Sparkling_Wine).contains(text)) {
			wine = "Sparkling Wine";
		} else if (Arrays.asList(Syrah).contains(text)) {
			wine = "Syrah";
		} else if (Arrays.asList(Zinfandel).contains(text)) {
			wine = "Zinfandel";
		}
		
		Intent intent = new Intent(this, PairedWineActivity.class);
		intent.putExtra("dish", dish);
		intent.putExtra("wine", wine);
		startActivity(intent);
	}
}
