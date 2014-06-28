package com.example.winoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class WishlistViewInfo extends ViewWineTemplate {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist_view_wine);
        
        setUpView();
    }

	@Override
	protected void initVars() {
		
		this.DBTableName = WineDBHandler.WISHLIST_TABLE;
		
		this.defaultImageId = R.drawable.inv_sample;
		
		this.editWineActivity = WishlistEditWine.class;
		this.ListActivity = WishlistActivity.class;
	}
	
	public void boughtWine(View view) {
		
		// add wine to inventory
        WineDBHandler wineDBHandler = new WineDBHandler(this, WineDBHandler.INV_TABLE);
        wineDBHandler.addWine(this.wine);
		
		// delete wine from wishlist
        WineDBHandler wishDBHandler = new WineDBHandler(this, WineDBHandler.WISHLIST_TABLE);
		wishDBHandler.deleteWine(this.wine.getID());
		
		// go back to wishlist
		Intent intent = new Intent(getApplicationContext(), this.ListActivity);
		startActivity(intent);
	}
}
