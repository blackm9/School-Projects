package com.example.winoapp;

import android.os.Bundle;
import android.widget.TextView;

public class InventoryViewInfo extends ViewWineTemplate {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_view_wine);
        
        setUpView();

        // Wine in inventory should have two more fields
		TextView quantity = (TextView) findViewById(R.id.quantity);
		TextView container = (TextView) findViewById(R.id.container);
		
		quantity.setText(this.wine.getQuantity());
		container.setText(this.wine.getContainerType());
    }

	@Override
	protected void initVars() {
		
		this.DBTableName = WineDBHandler.INV_TABLE;
		
		this.defaultImageId = R.drawable.inv_sample_holder;
		
		this.editWineActivity = InventoryEditWine.class;
		this.ListActivity = InventoryActivity.class;
	}
}
