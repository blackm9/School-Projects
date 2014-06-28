package com.example.winoapp;

public class WishlistActivity extends WineListTemplate {
    @Override
	protected void initVars() {
		this.DBTableName = WineDBHandler.WISHLIST_TABLE;
		this.viewWineActivity = WishlistViewInfo.class;
		this.editWineActivity = WishlistEditWine.class;
	}
}