package com.example.winoapp;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;

import library.DatabaseHandler;
import library.UserFunctions;

import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;


public class SearchViewInfo extends Activity {

	// Spinner Constants
	private final int MINWINE = 1;
	private final int MAXWINE = 100;
	private static String KEY_SUCCESS = "success";
		
	private final int MINYEAR = 1960;
	
	private static final double IMAGE_TO_SCREEN_RATIO = 0.4; //40%
	private double imageRatio; // for calculating image size
	
	// folder name for storing images
	private String folderPath = "";
	private String imagePath = "";
	
	private ImageView wineImage;
	
	Wine wine;
	
	private Bitmap b;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_inventory_edit);
    	// for the ability to go up a screen
    	ActionBar actionBar = getActionBar();
    	actionBar.setDisplayHomeAsUpEnabled(true);
    	
    	// Set up spinners
    	yearSpinnerHandler();
    	containerTypeSpinnerHandler();
    	quantitySpinnerHandler();
    	
		try {
			/*** GET ALL THE UI ELEMENTS ***/
			
			EditText wineName = (EditText) findViewById(R.id.wine_name_input);
	        EditText vineyard = (EditText) findViewById(R.id.vineyard_input);
	        EditText varietal = (EditText) findViewById(R.id.varietal_input);
			EditText storageLoc = (EditText) findViewById(R.id.storage_location_input);
			EditText notes = (EditText) findViewById(R.id.notes_input);
	        Spinner vintage = (Spinner) findViewById(R.id.vintage_input);
			Spinner quantity = (Spinner) findViewById(R.id.number_spinner);
			Spinner container = (Spinner) findViewById(R.id.container_spinner);
			RatingBar rating = (RatingBar) findViewById(R.id.wine_rating_bar);
			ImageView image = (ImageView) findViewById(R.id.wineinfo_img);
			
			wineImage = (ImageView)findViewById(R.id.wineinfo_img);
			
			// Get wine id to display
			JSONObject json_wine = new JSONObject(getIntent().getStringExtra("wine"));
			double wine_rating;
			if(json_wine.getString("snoothrank").equals("n/a"))
				wine_rating = -1;
			else
				wine_rating = Double.parseDouble(json_wine.getString("snoothrank"));
			wine = new Wine(-1,
								json_wine.getString("name"),
								json_wine.getString("winery"),
								json_wine.getString("varietal"),
								json_wine.getString("vintage"),
								json_wine.getString("available"),
								null,
								wine_rating,
								null,
								null,
								json_wine.getString("image"));
			/*** SET UP ALL THE UI ELEMENTS ***/
			
			folderPath = this.getExternalFilesDir(null).getAbsolutePath();
			
			Display display = getWindowManager().getDefaultDisplay(); 
            
            // using deprecated methods for backward compatibility
            double screenHeight = display.getHeight();
            imageRatio = screenHeight/(screenHeight*IMAGE_TO_SCREEN_RATIO);
			
			// set image
			if (wine.getImagePath().equals("")) {
				image.setImageResource(R.drawable.inv_sample);
			}
			else {
				int sampleSize = 1;
            	
            	BitmapFactory.Options o = new BitmapFactory.Options();
            	
            	// image size will = original size / inSampleSize
            	// sampleSize has to be 2^x where x > 0
                o.inSampleSize = sampleSize;
                o.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(new java.net.URL(wine.getImagePath()).openStream(), null, o);
                
                
                int h = (int)(o.outHeight*imageRatio);
                int w = (int)(o.outWidth*imageRatio);
                
                Log.d("ANDRO_ASYNC",String.format("going in h=%d w=%d resample = %d",h,w,o.inSampleSize));
                
                o.inJustDecodeBounds = false;
                try{
                	b = Bitmap.createScaledBitmap(BitmapFactory.decodeStream(new java.net.URL(wine.getImagePath()).openStream(), null, o), w, h, true);
                    wineImage.setImageBitmap(b);
                }catch(OutOfMemoryError e){
                    Log.d("ANDRO_ASYNC",String.format("catch Out Of Memory error"));
                    e.printStackTrace();
                    System.gc();
                }
			}
			
			// update textview
			
			wineName.setText(wine.getName());
			vineyard.setText(wine.getVineyard());
			varietal.setText(wine.getVarietal());
			setSpinner(vintage,   wine.getVintage());
	        setSpinner(quantity,  ""+wine.getQuantity());
	        setSpinner(container, wine.getContainerType());
			storageLoc.setText(wine.getStorageLoc());
			notes.setText(wine.getNotes());
			
			// update rating bar
			if(wine.getRating()!=-1)
				rating.setRating((float)wine.getRating());
			// prevent user from changing the rating bar in view mode
			rating.setIsIndicator(true);
		} catch (Exception e) {
			Log.e("Loading Exception", "Error when loading wine info");
			e.printStackTrace();
		}
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_wine, menu);
		return true;
	}
	//method to click on icon and return to main page
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            // app icon in action bar clicked
	        	finish();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	public void startCamera(View view) {
		// tapping on the image should do nothing
	}
	
	public void saveButtonClicked(View view) {
		
		if (b != null) {
			String fileName = "wineImg" + System.currentTimeMillis() + ".jpg";
			imagePath = folderPath + File.separator + fileName;
			
			ImageHandler.storeBitmap(b, imagePath);
		}
		//b.recycle();
		
		wine.setImagePath(imagePath);
		
		WineDBHandler dbhandler = new WineDBHandler(this, WineDBHandler.INV_TABLE);
		
		EditText  wineName		= (EditText)  findViewById(R.id.wine_name_input);
		EditText  vineyardName 	= (EditText)  findViewById(R.id.vineyard_input);
		EditText  varietal     = (EditText)  findViewById(R.id.varietal_input);
		EditText  storageLoc 	= (EditText)  findViewById(R.id.storage_location_input);
		EditText  notes 		= (EditText)  findViewById(R.id.notes_input);
		Spinner   vintage 		= (Spinner)   findViewById(R.id.vintage_input);
		Spinner   quantity 	    = (Spinner)   findViewById(R.id.number_spinner);
		Spinner   containerType = (Spinner)   findViewById(R.id.container_spinner);
		RatingBar rating 		= (RatingBar) findViewById(R.id.wine_rating_bar);
		
		int wineID = getIntent().getIntExtra("wineID", -1);
		// create a new wine object from the fields of the UI elements
		wine = new Wine(wineID,
				        wineName.getText().toString(),
				        vineyardName.getText().toString(),
				        varietal.getText().toString(),
				        vintage.getSelectedItem().toString(),
				        quantity.getSelectedItem().toString(),
				        containerType.getSelectedItem().toString(),
				        rating.getRating(),
				        storageLoc.getText().toString(),
				        notes.getText().toString(),
				        imagePath);
		
		dbhandler.addWine(wine);
		

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
					JSONObject json = userFunction.addWine(inventoryId,
							Integer.toString(threadWine.getID()),
							threadWine.getName(),
							threadWine.getVarietal(),
							threadWine.getVintage(),
							threadWine.getQuantity(),
							threadWine.getStorageLoc(),
							threadWine.getRating(),
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
		
		//Prevents pressing the 'back' button from re-loading the edit wine page after changes have been made.
		finish();
	}
	
	private void setSpinner(Spinner spinner, String str) {
        ArrayAdapter adpt = (ArrayAdapter) spinner.getAdapter(); //cast to an ArrayAdapter
        int sp = adpt.getPosition(str);
		spinner.setSelection(sp);
	}
	
	/**
	 * sets up the spinner for vintage
	 */
	private void yearSpinnerHandler(){
		// get current year
		Calendar c = Calendar.getInstance(); 
		int currentYear = c.get(Calendar.YEAR);
		
		int totalYears = currentYear - MINYEAR;

		// store current year to previous year
		String[] years = new String[totalYears+1];
		years[0] = ""; // default string
		for(int i=1; i<=totalYears; i++)
			years[i]=Integer.toString(currentYear-i);

		setUpSpinner(R.id.vintage_input, years);
	}
	
	/**
	 * sets up the spinner for the quantity of wine
	 */
	private void quantitySpinnerHandler(){
		
		// private Adapters for setting values to UI
		String[] quantity = new String[MAXWINE+1];
		quantity[0] = ""; // default string
		for(int i=0;i<MAXWINE;i++)
			quantity[i+1]=Integer.toString(MINWINE+i);
		setUpSpinner(R.id.number_spinner, quantity);
	}
	
	/**
	 * sets up the spinner for types of containers of wine
	 */
	private void containerTypeSpinnerHandler(){
		String[] containerType = {
			"", // default string
			"Bottle(s)",
			"Case(s)",
		};
        setUpSpinner(R.id.container_spinner, containerType);
	}
	
	/**
	 * sets up spinners for spinnerHandlers
	 */
	private void setUpSpinner(int id, String[] spinnerStrings){

		Spinner spinner = (Spinner) findViewById(id);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	                android.R.layout.simple_spinner_item, spinnerStrings);
		spinner.setAdapter(adapter);
	}
	
}
