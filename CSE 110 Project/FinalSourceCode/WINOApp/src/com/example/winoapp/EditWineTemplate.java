package com.example.winoapp;

import java.io.File;
import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Display;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

public abstract class EditWineTemplate extends Activity {
	
	private final int MINWINE = 1;
	private final int MAXWINE = 100;

	private final int MINYEAR = 1960;
	
	private static final int REQUEST_CODE = 0;
	private static final double IMAGE_TO_SCREEN_RATIO = 0.4; //40%
	private double imageRatio; // for calculating image size
	
	// determine whether add wien or modify wine
	private final String EDIT = "edit";
	private final String ADD = "add";
	private String mode;

	private String folderPath = "";
	private String cameraImagePath = "";
	private String imagePath = "";
	
	private boolean isPhotoTaken;
	
	private ImageView wineImage;
	private Uri imageUri;
	
	private Bitmap b;
	private boolean hasCamera;
	
	protected String DBTableName;
	protected Class<?> listActivity;
	protected boolean hasQuantity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
     
        // set mode
        mode = getIntent().getStringExtra("mode");

        isPhotoTaken = false;

        // check if device has cameras
        int numCameras = Camera.getNumberOfCameras();
        if (numCameras > 0) {
            hasCamera = true;
        }
        
        // check if external storage is available/if sdcard is mounted
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
        	
        	// anything inside will get removed on uninstallation of app
	        // that is: /storage/emulated/#/Android/data/packageName/files
	        folderPath = this.getExternalFilesDir(null).getAbsolutePath();
	        
			// make directory if necessary(the first time running inventory)
	        File f = new File(folderPath);
	        if (!f.exists()) {
	        	f.mkdirs();
	        }
	        cameraImagePath = folderPath + File.separator + "cameraImage.jpg";
	        imageUri = Uri.fromFile(new File(cameraImagePath)); // photo path
        }
        Display display = getWindowManager().getDefaultDisplay(); 
        // using deprecated methods for backward compatibility
        double screenHeight = display.getHeight();

        // measured according to height: image height is 25% of screen height
        imageRatio = screenHeight/(screenHeight*IMAGE_TO_SCREEN_RATIO);
        
        initVars();
	}
	
	protected void setUpView () {
		// Set up spinner
		yearSpinnerHandler();
		if (hasQuantity) {
	        containerTypeSpinnerHandler();
	        quantitySpinnerHandler();
		}
		
        if (mode.equals(EDIT)) {
        	populateFields();
        }
        
        // initialize instance variables
        wineImage = (ImageView)findViewById(R.id.wineinfo_img);
	}
	
	protected abstract void initVars();
	
    public void startCamera(View view) {
    	if (hasCamera) {
	    	if (!cameraImagePath.equals("")) {
	    		// start camera intent
		        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		        startActivityForResult(intent, REQUEST_CODE);
	    	}
	    	else {
	    		Toast.makeText(this, "Please make sure your SD card is mounted for storing images!", 
	    				Toast.LENGTH_LONG).show();
	    	}
    	}
    	else {
    		Toast.makeText(this, "Your device does not support camera feature!", Toast.LENGTH_LONG).show();
    	}
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                b = ImageHandler.processCameraImage(cameraImagePath, imageRatio);
                if (b != null) {
                	wineImage.setImageBitmap(b);
                	isPhotoTaken = true;
                }
                else {
                	Toast.makeText(this, "Camera Error!", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    
	public void saveButtonClicked(View view) {

		saveWineInfo();
		
    	Intent intent = new Intent(this, listActivity);
		startActivity(intent);
	}
	
	protected Wine saveWineInfo() {
		Wine wine = getWineFromUI();

		// if image is modified(user takes another photo)
		// delete the original image
		if (isPhotoTaken && !imagePath.equals("")) {
			File f = new File(imagePath);
			if (f.exists())
			    f.delete();
			imagePath = "";
		}

		if (isPhotoTaken) {
			
			String fileName = "wineImg" + System.currentTimeMillis() + ".jpg";
			imagePath = folderPath + File.separator + fileName;
			
			//Bitmap b = imageViewToBitmap(wineImage);
			ImageHandler.storeBitmap(b, imagePath);
			b.recycle();
		}
		
		wine.setImagePath(imagePath);
		
		WineDBHandler dbhandler = new WineDBHandler(this, DBTableName);
		// handle data depends on mode
		if (mode.equals(ADD))
			dbhandler.addWine(wine);
		else // edit mode
			dbhandler.updateWine(wine);
		
		return wine;
	}
	
	private void populateFields() {

		int wineID = getIntent().getIntExtra("wineID", -1);
		Wine wine = (new WineDBHandler(this, DBTableName)).getWine(wineID);
		
		/*** GET UI ELEMENTS ***/
        EditText wineName = (EditText) findViewById(R.id.wine_name_input);
        EditText vineyard = (EditText) findViewById(R.id.vineyard_input);
        EditText varietal = (EditText) findViewById(R.id.varietal_input);
		EditText storageLoc = (EditText) findViewById(R.id.storage_location_input);
		EditText notes = (EditText) findViewById(R.id.notes_input);
		
		Spinner vintage = (Spinner) findViewById(R.id.vintage_input);

		RatingBar rating = (RatingBar) findViewById(R.id.wine_rating_bar);

		imagePath = wine.getImagePath();
		if (!imagePath.equals("")) {
			ImageView image = (ImageView) findViewById(R.id.wineinfo_img);
			
			/*** SET UI ELEMENTS ***/
			Bitmap bmImg = BitmapFactory.decodeFile(imagePath);
			image.setImageBitmap(bmImg);
		}
		
        wineName.setText(wine.getName());
        vineyard.setText(wine.getVineyard());
        varietal.setText(wine.getVarietal());
        storageLoc.setText(wine.getStorageLoc());
        notes.setText(wine.getNotes());
        rating.setRating((float)wine.getRating());

        setSpinner(vintage,   wine.getVintage());
        
        if (hasQuantity) {
    		Spinner quantity = (Spinner) findViewById(R.id.number_spinner);
    		Spinner container = (Spinner) findViewById(R.id.container_spinner);
    		
    		setSpinner(quantity, wine.getQuantity());
    		setSpinner(container, wine.getContainerType());
        }
	}

	private Wine getWineFromUI() {
		// get references to all the UI elements
		EditText  wineName		= (EditText)  findViewById(R.id.wine_name_input);
		EditText  vineyardName 	= (EditText)  findViewById(R.id.vineyard_input);
		EditText  varietal      = (EditText)  findViewById(R.id.varietal_input);
		EditText  storageLoc 	= (EditText)  findViewById(R.id.storage_location_input);
		EditText  notes 		= (EditText)  findViewById(R.id.notes_input);
		Spinner   vintage 		= (Spinner)   findViewById(R.id.vintage_input);
		RatingBar rating 		= (RatingBar) findViewById(R.id.wine_rating_bar);
		
		String quantity = "";
		String container = "";
		if (hasQuantity) {
			Spinner quantitySpinner  = (Spinner)   findViewById(R.id.number_spinner);
			Spinner containerSpinner = (Spinner)   findViewById(R.id.container_spinner);
			
			quantity = quantitySpinner.getSelectedItem().toString();
			container = containerSpinner.getSelectedItem().toString();
		}
		
		int wineID = getIntent().getIntExtra("wineID", -1);
		// create a new wine object from the fields of the UI elements
		Wine wine = new Wine(
						wineID,
				        wineName.getText().toString(),
				        vineyardName.getText().toString(),
				        varietal.getText().toString(),
				        vintage.getSelectedItem().toString(),
				        quantity,
				        container,
				        rating.getRating(),
				        storageLoc.getText().toString(),
				        notes.getText().toString(),
				        "");
		return wine;
	}
	
	private void setSpinner(Spinner spinner, String str) {
        ArrayAdapter adpt = (ArrayAdapter) spinner.getAdapter(); //cast to an ArrayAdapter
        if (adpt == null) System.out.println("yoyo");
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
