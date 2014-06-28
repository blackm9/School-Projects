package com.example.winoapp;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class WineDBHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "WineDB";

	// Table name
	public static final String INV_TABLE = "WineInventory";
	public static final String WISHLIST_TABLE = "WineWishlist";

	// Table column tags
	private static final String KEY_ID		= "Id";
	private static final String WINE_NAME 	= "Name";
	private static final String VINEYARD 	= "Vineyard";
	private static final String VINTAGE 	= "Vintage";
	private static final String VARIETAL	= "Varietal";
	private static final String QUANTITY 	= "Quantity";
	private static final String CONTAINER 	= "ContainerType";
	private static final String RATING 		= "Rating";
	private static final String STORAGE 	= "StorageLocation";
	private static final String NOTES 		= "Notes";
	private static final String IMAGE		= "Image";
	
	private String currentTable;
	
	// Constructor
	public WineDBHandler(Context context, String tableToHandle) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.currentTable = tableToHandle;
	}

	/** Creating Table
	 * 
	 *  CAUTIOUS: Any change to this part requires manual uninstallation of the
	 *  app before running the app again due to the change of the structure of 
	 *  the database
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		String createInvTable = createTable(INV_TABLE);
		db.execSQL(createInvTable);
		
		String createWishlistTable = createTable(WISHLIST_TABLE);
		db.execSQL(createWishlistTable);
	}
	
	private String createTable (String tableName) {
		String  tableString = "CREATE TABLE " + tableName + "("
				+ KEY_ID 	+ " INTEGER PRIMARY KEY autoincrement," 
				+ WINE_NAME + " TEXT,"
				+ VINEYARD  + " TEXT,"
				+ VARIETAL  + " TEXT,"
				+ VINTAGE 	+ " TEXT,"
				+ QUANTITY  + " TEXT,"
				+ CONTAINER + " TEXT,"
				+ RATING 	+ " DOUBLE,"
				+ STORAGE 	+ " TEXT,"
				+ NOTES 	+ " TEXT," 
				+ IMAGE		+ " TEXT" + ")";
		
		return tableString;
	}

	// Upgrading database: it's called when the properties of the db is modified 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + INV_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + WISHLIST_TABLE);

		// Create tables again
		onCreate(db);
	}

	public void addWine(Wine wine) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = extractValues(wine);
		// Inserting Row
		db.insert(currentTable, null, values);
		db.close();
	}
	
	public Wine getWine(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(currentTable, new String[] { KEY_ID,
				WINE_NAME, VINEYARD, VARIETAL, VINTAGE, QUANTITY, CONTAINER,
				RATING, STORAGE, NOTES, IMAGE}, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Wine wine = new Wine(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2),
				cursor.getString(3), cursor.getString(4),
				cursor.getString(5), cursor.getString(6),
				cursor.getDouble(7), cursor.getString(8),
				cursor.getString(9), cursor.getString(10));

		return wine;
	}
	
	public List<Wine> getAllWine() {
        List<Wine> wineList = new ArrayList<Wine>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + currentTable;
 
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	Wine wine = new Wine(Integer.parseInt(cursor.getString(0)),
        				cursor.getString(1), cursor.getString(2),
        				cursor.getString(3), cursor.getString(4),
        				cursor.getString(5), cursor.getString(6),
        				cursor.getDouble(7), cursor.getString(8),
        				cursor.getString(9), cursor.getString(10));
            	
            	wineList.add(wine);
            } while (cursor.moveToNext());
        }
 		db.close();
 		
        return wineList;
    }

	public int updateWine(Wine wine) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = extractValues(wine);

		// updating row
		int row = db.update(currentTable, values, KEY_ID+"="+wine.getID(), null);
		db.close();
		return row;
	}

	public void deleteWine(int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(currentTable, KEY_ID+"="+id, null);
		db.close();
	}

	public long getCount() {
		SQLiteDatabase db = this.getReadableDatabase();
		long count = DatabaseUtils.queryNumEntries(db,currentTable);
		db.close();
		
		return count;
	}
	
	public String[] getWineFromType(String type) {
		List<Wine> wineList = new ArrayList<Wine>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + currentTable + " WHERE Varietal = '" + type + "'";
 
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	Wine wine = new Wine(Integer.parseInt(cursor.getString(0)),
        				cursor.getString(1), cursor.getString(2),
        				cursor.getString(3), cursor.getString(4),
        				cursor.getString(5), cursor.getString(6),
        				cursor.getDouble(7), cursor.getString(8),
        				cursor.getString(9), cursor.getString(10));
            	
            	wineList.add(wine);
            } while (cursor.moveToNext());
        }
 		db.close();
 		
 		String [] wineArray = new String[wineList.size()];
 		
 		for (int i = 0; i < wineList.size(); i++) {
 			wineArray[i] = wineList.get(i).getName();
 		}
 		
 		if (wineList.size() == 0) {
 			wineArray = new String[1];
 			wineArray[0] = "No Matches";
 		}

        return wineArray;
	}
	
	private ContentValues extractValues(Wine wine) {
		ContentValues values = new ContentValues();
		values.put(WINE_NAME, wine.getName()); 
		values.put(VINEYARD,  wine.getVineyard());
		values.put(VARIETAL,  wine.getVarietal());
		values.put(VINTAGE,   wine.getVintage());
		values.put(QUANTITY,  wine.getQuantity());
		values.put(CONTAINER, wine.getContainerType());
		values.put(RATING,    wine.getRating());
		values.put(STORAGE,   wine.getStorageLoc());
		values.put(NOTES,     wine.getNotes());
		values.put(IMAGE,	  wine.getImagePath());
		
		return values;
	}
	
	// used for debugging
	public void logAllWines(WineDBHandler dbh) {
        Log.d("Loading: ", "Loading all wines..");
        List<Wine> wines = dbh.getAllWine();       
         
        for (Wine w : wines) {
            String log = "Id: "+w.getID()+" ,Name: " + w.getName() + " ,Vineyard: " + w.getVineyard()
            		         + " ,Varietal: " + w.getVarietal()
            				 + " ,Vintage: " + w.getVintage() + " ,Quantity: " + w.getQuantity() 
            		         + " ,Container: " + w.getContainerType() + " ,Rating: " + w.getRating()
            		         + " ,StorageLoc: " + w.getStorageLoc() + " ,Notes: " + w.getNotes()
            		         + " ,ImagePath: " + w.getImagePath();
            Log.d("Wine: ", log);
        }
	}

}
