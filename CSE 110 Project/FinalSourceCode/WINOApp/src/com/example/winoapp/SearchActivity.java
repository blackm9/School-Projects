package com.example.winoapp;

import java.util.ArrayList;
import java.util.List;

import library.DatabaseHandler;
import library.UserFunctions;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;



public class SearchActivity extends Activity {

	private static String KEY_SUCCESS = "success";
	private static String KEY_RESULTS = "results";
	private ListView list;
	private WineListAdapter adapter;
	
	EditText inputSearch;
	int currentOffset;
	List<JSONObject> Results;
	List<Wine> wineList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        
        list = (ListView) findViewById(R.id.invListView);
        
        inputSearch = (EditText) findViewById(R.id.search_field);
        
        WineDBHandler dbh = new WineDBHandler(this, WineDBHandler.INV_TABLE);
        
        setListListeners();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            // app icon in action bar clicked; go home
	           finish();
	           return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	private void setListListeners() {
		// listen to single click for viewing wine
		list.setOnItemClickListener(new OnItemClickListener() {
			// View Wine info page
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Intent i = new Intent(SearchActivity.this, SearchViewInfo.class);
				i.putExtra("wine", Results.get((int)id).toString());
				startActivity(i);
			}
		});
	}
	
	private void updateList() {
		WineDBHandler dbh = new WineDBHandler(getApplicationContext(), WineDBHandler.INV_TABLE);
		
        // set adapter to change listView
        adapter = new WineListAdapter(this, wineList);

		list.setAdapter(adapter);
	}
	
	public void searchOnClick( View v){
		currentOffset = 0;
		final String search = inputSearch.getText().toString();
		
		Thread thread = new Thread(new Runnable(){
		    @Override
		    public void run() {
		    	Looper.prepare();
		        try {
		            //Your code goes here
		    		UserFunctions userFunction = new UserFunctions();
		    		JSONObject json = userFunction.searchAPI(search, currentOffset);
		    		
		    		try {
		    			if (json.getJSONObject("meta") != null) {
		    				JSONObject meta = json.getJSONObject("meta");
	    					JSONArray wines = json.getJSONArray("wines");
	    					JSONObject currWine;
	    					double rating;
		    				String res = meta.getString("status");
		    				System.out.println("json res = " + res );
		    				if(Integer.parseInt(res) == 1){
		    					
		    					
		    					
		    					meta.getString(KEY_RESULTS);
		    					wineList = new ArrayList<Wine>();
		    					Results = new ArrayList<JSONObject>();
		    					for(int i=0; i<wines.length(); i++) {
		    						currWine = wines.getJSONObject(i);
		    						if(currWine.getString("snoothrank").equals("n/a"))
		    							rating = 0;
		    						else
		    							rating = Double.parseDouble(currWine.getString("snoothrank"));
		    						wineList.add(
		    								new Wine(i,
		    										currWine.getString("name"),
		    										currWine.getString("winery"),
		    										currWine.getString("varietal"),
		    										currWine.getString("vintage"),
		    										currWine.getString("available"),
		    										null,
		    										rating,
		    										null,
		    										null,
		    										""));
		    										//currWine.getString("image")));
		    						Results.add(currWine);
		    					}
		    				}else{
		    					
		    				}
		    				Message msg = Message.obtain();
		    	            msg.what = 1;
		    	            handler.sendMessage(msg);
		    			}
		    		} catch (Exception e) {
		    			Log.e("Login", "Login Error", e);
		    		}
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		        Looper.loop();
		    }
		});
		
		thread.start();
	}
	
	private Handler handler = new Handler(){
	    @Override
	    public void handleMessage(Message msg) {
	        switch(msg.what){
	        case 1:
	            updateList();
	            break;
	        }
	    }
	};

}
