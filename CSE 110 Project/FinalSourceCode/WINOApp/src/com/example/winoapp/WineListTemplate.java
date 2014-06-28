package com.example.winoapp;

import java.io.File;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;

public abstract class WineListTemplate extends Activity {

	private WineListAdapter adapter;
	
    protected ListView list;
    protected String DBTableName;
    protected Class<?> viewWineActivity; // child of ViewWineTemplate
    protected Class<?> editWineActivity; // child of EditWineTemplate
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_winelist);
		// for the ability to go up a screen
		ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        
        // children of this template should implement this method
        initVars();
		
		list = (ListView) findViewById(R.id.winelistView);
		
		WineDBHandler dbh = new WineDBHandler(this, DBTableName);
		// only if database has entries
		if (dbh.getCount() > 0) {
			
			updateList();
			
			// listen to single click for viewing wine
			list.setOnItemClickListener(new OnItemClickListener() {
				// View Wine info page
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					
					Intent i = new Intent(WineListTemplate.this, viewWineActivity);
					i.putExtra("wineID", (int)id);
					startActivity(i);
				}
			});

			// listen to long press for wine removal
			list.setOnItemLongClickListener(new OnItemLongClickListener() {
				@Override
				public boolean onItemLongClick(AdapterView<?> parent, View view,
						int position, long id) {
					final int wineID = (int)id;
					AlertDialog.Builder builder=new AlertDialog.Builder(WineListTemplate.this);

					builder.setTitle("Confirm");
					builder.setMessage("Do you want to delete this wine?");
					
					builder.setPositiveButton("OK",new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							removeWine(wineID);
							updateList();
						}
					});
					builder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});

					AlertDialog alertdialog = builder.create();
					alertdialog.show();
					return true; // everything works fine
				}
			});
		}
        
        /*** Implment Search Feature ***/
        EditText inputSearch = (EditText) findViewById(R.id.winelist_searchfield);
        inputSearch.addTextChangedListener(new TextWatcher() {
			// when user deletes a char, reset the adapter and refilter it
			// the next step to keep the search dynamic
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (adapter != null) {
					if (count < before) {
						adapter.resetAdapter();
					}
					adapter.getFilter().filter(s.toString());
				}
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			}

			@Override
			public void afterTextChanged(Editable arg0) {
			}
		});
	}
	
	/**
	 * Should initialize the following variables:
	 *   viewWineClass
	 *   editWineClass
	 *   DBTableName
	 */
	protected abstract void initVars ();

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.winelist, menu);
		return true;
	}
	//method to click on icon and return to main page
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
	
	private void updateList() {
		WineDBHandler dbh = new WineDBHandler(this, DBTableName);
        List<Wine> wineList = dbh.getAllWine();

        // set adapter to change listView
        adapter = new WineListAdapter(this, wineList);

		list.setAdapter(adapter);
	}
	
	private void removeWine(int id) {
		WineDBHandler dbhandler = new WineDBHandler(this, DBTableName);
		Wine wine = dbhandler.getWine(id);

		// delete image file as well
		File f = new File(wine.getImagePath());
		if (f.exists())
			f.delete();
		
		dbhandler.deleteWine(id);
	}
	
	public void addButtonClicked(View view) {
    	Intent i = new Intent(this, editWineActivity);
    	i.putExtra("mode", "add");
		startActivity(i);
	}
	
	@Override
	public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
	}

}
