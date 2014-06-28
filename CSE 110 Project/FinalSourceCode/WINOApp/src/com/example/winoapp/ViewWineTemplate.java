package com.example.winoapp;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public abstract class ViewWineTemplate extends Activity{
	
	// children need to initialize the following vars
	protected Class<?> ListActivity; // child of WineListTemplate
	protected Class<?> editWineActivity; // child of EditWineTemplate
	protected int defaultImageId;
	protected String DBTableName;

	protected Wine wine;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // for the ability to go up a screen
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        
        initVars();
        
		int wineID = getIntent().getIntExtra("wineID", -1);
		wine = (new WineDBHandler(this, DBTableName)).getWine(wineID);
    }
    
    /**
     * Child shall implement this to initialize:
     *   DBTableName
     *   defaultImageId
     *   ListActivity
     *   editWineActivity
     */
    protected abstract void initVars();
    
    // children need to call this manually since this class
    // can't set content view for children
    protected void setUpView () {
    	
		/*** GET ALL THE UI ELEMENTS ***/
		TextView wineName   = (TextView) findViewById(R.id.wine_name);
		TextView vineyard   = (TextView) findViewById(R.id.vineyard);
		TextView varietal   = (TextView) findViewById(R.id.varietal);
		TextView vintage    = (TextView) findViewById(R.id.vintage);
		TextView storageLoc = (TextView) findViewById(R.id.storage_location);
		TextView notes      = (TextView) findViewById(R.id.notes);
		RatingBar rating    = (RatingBar) findViewById(R.id.wine_rating_bar);
		ImageView image     = (ImageView) findViewById(R.id.viewwine_img);
  
		/*** SET UP ALL THE UI ELEMENTS ***/
		// set image
		if (wine.getImagePath().equals("")) {
			image.setImageResource(defaultImageId);
		}
		else {
			try {
				Bitmap bmImg = BitmapFactory.decodeFile(wine.getImagePath());
				image.setImageBitmap(bmImg);
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
		vintage.setText(wine.getVintage());
		storageLoc.setText(wine.getStorageLoc());
		notes.setText(wine.getNotes());
		rating.setRating((float)wine.getRating());
		
		// prevent user from changing the rating bar in view mode
		rating.setIsIndicator(true);
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
	        	Intent intent = new Intent(this, ListActivity);
	            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            startActivity(intent);
	            return true;
	        case R.id.edit:
	        	// edit button on option menu
	    		int wineID = getIntent().getIntExtra("wineID", -1);
	    		
	        	Intent i = new Intent(this, editWineActivity);
	        	i.putExtra("mode", "edit");
	        	i.putExtra("wineID", wineID);
	    		startActivity(i);
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
}
