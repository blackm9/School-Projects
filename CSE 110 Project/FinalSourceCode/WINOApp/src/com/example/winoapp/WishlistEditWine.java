package com.example.winoapp;

import android.os.Bundle;

public class WishlistEditWine extends EditWineTemplate {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wishlist_edit);
		setUpView();
	}
	
	@Override
	protected void initVars() {
		this.DBTableName = WineDBHandler.WISHLIST_TABLE;
		this.listActivity = WishlistActivity.class;
		this.hasQuantity = false;
	}
}
