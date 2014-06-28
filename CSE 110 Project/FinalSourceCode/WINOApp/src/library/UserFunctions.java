package library;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;

public class UserFunctions {
	
	private JSONParser jsonParser;
	
	private static String loginURL = "http://cse110wino.net63.net/api/";
	private static String registerURL = "http://cse110wino.net63.net/api/";
	private static String wineAddURL = "http://cse110wino.net63.net/api/";
	private static String searchWineURL = "http://api.snooth.com/wines/?akey=tbcc0cce7spe1b8nzykzgi04cpeotoojtgwozpnwmprbdmnp";
	private static String pairWineURL = "http://api.snooth.com/wine/?akey=tbcc0cce7spe1b8nzykzgi04cpeotoojtgwozpnwmprbdmnp";
	
	private static String wineDeleteURL = "http://winoapp.net16.net/api/";

	
	private static String login_tag = "login";
	private static String apisearch_tag = "apisearch";
	private static String register_tag = "register";
	private static String addWine_tag = "addWine";
	private static String deleteWine_tag = "deleteWine";
	
	// constructor
	public UserFunctions(){
		jsonParser = new JSONParser();
	}
	
	/**
	 * function make Login Request
	 * @param email
	 * @param password
	 * */
	public JSONObject loginUser(String email, String password){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", login_tag));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));
		JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
		// return json
		// Log.e("JSON", json.toString());
		return json;
	}
	
	/**
	 * function make Login Request
	 * @param name
	 * @param email
	 * @param password
	 * @param gender 
	 * @param weight 
	 * @param DOB 
	 * @param lastName 
	 * @param firstName 
	 * */
	public JSONObject registerUser(String username, String email, String password, String firstName, String lastName, String DOB, String weight, String gender){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", register_tag));
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));
		params.add(new BasicNameValuePair("first_name", firstName));
		params.add(new BasicNameValuePair("last_name", lastName));
		params.add(new BasicNameValuePair("dob", DOB));
		params.add(new BasicNameValuePair("weight", weight));
		params.add(new BasicNameValuePair("sex", gender));
		
		
		// getting JSON Object
		JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
		// return json
		return json;
	}
	
	/*
	 * Function to add wine to the inventory database
	 */
	public JSONObject addWine(String inventoryId, String wineId, String name, String type, String vintage, String quantity, String location, double rating, String notes) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		params.add(new BasicNameValuePair("tag", addWine_tag));
		params.add(new BasicNameValuePair("inventoryId", inventoryId));
		params.add(new BasicNameValuePair("wineId", wineId));
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("type", type));
		params.add(new BasicNameValuePair("vintage", vintage));
		params.add(new BasicNameValuePair("quantity", quantity));
		params.add(new BasicNameValuePair("location", location));
		params.add(new BasicNameValuePair("rating", String.valueOf(rating)));
		params.add(new BasicNameValuePair("notes", notes));

		
		// getting JSON Object
		JSONObject json = jsonParser.getJSONFromUrl(wineAddURL, params);
		// return json
		return json;
	}	
	public JSONObject findWine(String wine) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		params.add(new BasicNameValuePair("q", wine));
		
		// getting JSON Object
		JSONObject json = jsonParser.getJSONFromUrl(searchWineURL, params);
		// return json
		return json;
	}
	public JSONObject pairWine(String id) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		params.add(new BasicNameValuePair("id", id));
		params.add(new BasicNameValuePair("food", "1"));
		
		// getting JSON Object
		JSONObject json = jsonParser.getJSONFromUrl(pairWineURL, params);
		// return json
		return json;
	}
	
	public JSONObject searchAPI(String search, int offset) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		//params.add(new BasicNameValuePair("tag", apisearch_tag));
		//params.add(new BasicNameValuePair("search", search));
		//params.add(new BasicNameValuePair("num", Integer.toString(offset)));
		JSONObject json = jsonParser.getJSONFromUrl(searchWineURL+"&q="+search+"&n=10&f="+offset+1, params);
		// return json
		// Log.e("JSON", json.toString());
		return json;
	}

	/*
	 * Function to delete wine from server
	 */
/*	public JSONObject deleteWine(String inventoryId, String wineName) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		params.add(new BasicNameValuePair("tag", deleteWine_tag));
		params.add(new BasicNameValuePair("inventoryId", inventoryId));
		params.add(new BasicNameValuePair("name", wineName));
		
		JSONObject json = jsonParser.getJSONFromUrl(wineDeleteURL, params);
		return json;
	}*/
	
	
	/**
	 * Function get Login status
	 * */
	public boolean isUserLoggedIn(Context context){
		DatabaseHandler db = new DatabaseHandler(context);
		int count = db.getRowCount();
		if(count > 0){
			// user logged in
			return true;
		}
		return false;
	}
	
	/**
	 * Function to logout user
	 * Reset Database
	 * */
	public boolean logoutUser(Context context){
		DatabaseHandler db = new DatabaseHandler(context);
		db.resetTables();
		return true;
	}
	
	/**
	 * function returns intoxication info and status
	 * @param startHours
	 * @param startMinutes
	 * @param num_drinks
	 * @return result
	 */
	@SuppressLint("DefaultLocale")
	public String processBAC(int startHours, int startMinutes, int numDrinks, int wt, String gen) {
		 
		// check for nonsense user input
		if (startHours < 0) {
			System.err.print("Hours value must be postive");
		}
		else if( (startMinutes < 0) || (startMinutes > 59) ) {
			System.err.print("Minutes value must be within the range 0-59");
		}
		else if( numDrinks < 0 ) {
			System.err.print("Number of drinks must be positive");
		}
		else if( wt < 0 ) {
			System.err.print("Weight must be positive");
		}
		
		  //get how many minutes ago user started drinking
		  int startTime = (startHours  * 60) + startMinutes;

		  //bool used for gender in BAC calculation
		  String gender = gen;										// need to get user's gender from SQLDatabse
		  boolean isMale ="Male".equals(gender);

		  //calculate the raw and current BAC's
		  int weight = wt;														// need to get user's weight from SQLDatabase
		  double BAC = calcBAC(isMale,weight,numDrinks);
		  double currBAC = BAC - ((startTime / 40) * .01);
		  if (currBAC < 0.0)
			  currBAC = 0.0;

		  //calculate minutes until sobriety 
		  //(for every 40 minutes subtract .01 BAC)
		  int timeEstimate = (int) (40 * (currBAC / .01));

		  //calculate estimations to hours and minutes
		  int hourEstimate = timeEstimate / 60;
		  int minuteEstimate = timeEstimate % 60;

		  //get user's impairment status (Ok, Impaired, or Intoxicated)
		  String status = determineImpairment(currBAC);
		  String curr_BAC = String.format("%.2f", currBAC);
		  
		  String result ="Your BAC is: " + curr_BAC + "\n You're " + status + "\n Time until sobreity: " + hourEstimate + " hours and " + minuteEstimate + " minutes.";
		  return result;
	}
	
	public String determineImpairment(double BAC)
	{
	  if(BAC >= .08)
	    return "INTOXICATED";
	  else if(BAC > 0.0)
	    return "IMPAIRED";
	  else
	    return "OKAY";
	}
	
	
	public double calcBAC(boolean isMale, double weight, int numDrinks)
	{
      double bac = 0.0;
	  if(weight <= 100)
	  {
	    switch(numDrinks) {
	      case 0: bac = 0.0; break;
	      case 1: if(isMale) bac = 0.06;
	      				else bac = 0.07; 
	                 	break;
	      case 2: if(isMale) bac = 0.12;
          				else bac = 0.13; 
	      				break;
	      case 3: if(isMale) bac = 0.18;
          				else bac = 0.20; 
          				break;
	      case 4: if(isMale) bac = 0.24;
          				else bac = 0.26; 
          				break;
	      case 5: if(isMale) bac = 0.30;
          				else bac = 0.33; 
	      				break;
	    }
	  }
	  else if(weight > 100 && weight <= 120)
	  {
	    switch(numDrinks) {
	      case 0: bac = 0.0; break;
	      case 1: if(isMale) bac = 0.05;
          	else bac = 0.06; 
          	break;
	      case 2: if(isMale) bac = 0.10;
          	else bac = 0.11; 
          	break;
	      case 3: if(isMale) bac = 0.15;
          	else bac = 0.17; 
          	break;
	      case 4: if(isMale) bac = 0.20;
          	else bac = 0.22; 
          	break;
	      case 5: if(isMale) bac = 0.25;
          	else bac = 0.28; 
          	break;
	    }
	  }
	  else if(weight > 120 && weight <= 140)
	  {
	    switch(numDrinks) {
	      case 0: bac = 0.0; break;
	      case 1: if(isMale) bac = 0.04;
			else bac = 0.05; 
			break;
	      case 2: if(isMale) bac = 0.09;
			else bac = 0.09; 
			break;
	      case 3: if(isMale) bac = 0.13;
			else bac = 0.14; 
			break;
	      case 4: if(isMale) bac = 0.17;
			else bac = 0.19; 
			break;
	      case 5: if(isMale) bac = 0.21;
			else bac = 0.24; 
			break;
	    }
	  }
	  else if(weight > 140 && weight <= 160)
	  {
	    switch(numDrinks) {
	      case 0: bac = 0.0; break;
	      case 1: if(isMale) bac = 0.04;
			else bac = 0.04; 
			break;
	      case 2: if(isMale) bac = 0.07;
			else bac = 0.08; 
			break;
	      case 3: if(isMale) bac = 0.11;
			else bac = 0.12; 
			break;
	      case 4: if(isMale) bac = 0.15;
			else bac = 0.17; 
			break;
	      case 5: if(isMale) bac = 0.19;
			else bac = 0.21; 
			break;
	    }
	  }
	  else if(weight > 160 && weight <= 180)
	  {
	    switch(numDrinks) {
	      case 0: bac = 0.0; break;
	      case 1: if(isMale) bac = 0.03;
			else bac = 0.04; 
			break;
	      case 2: if(isMale) bac = 0.07;
			else bac = 0.07; 
			break;
	      case 3: if(isMale) bac = 0.10;
			else bac = 0.11; 
			break;
	      case 4: if(isMale) bac = 0.13;
			else bac = 0.15; 
			break;
	      case 5: if(isMale) bac = 0.17;
			else bac = 0.18; 
			break;
	    }
	  }
	  else if(weight > 180 && weight <= 200)
	  {
	    switch(numDrinks) {
	      case 0: bac = 0.0; break;
	      case 1: if(isMale) bac = 0.03;
			else bac = 0.03; 
			break;
	      case 2: if(isMale) bac = 0.06;
			else bac = 0.07; 
			break;
	      case 3: if(isMale) bac = 0.09;
			else bac = 0.10; 
			break;
	      case 4: if(isMale) bac = 0.12;
			else bac = 0.13; 
			break;
	      case 5: if(isMale) bac = 0.15;
			else bac = 0.17; 
			break;
	    }
	  }
	  else if(weight > 200 && weight <= 220)
	  {
	    switch(numDrinks) {
	      case 0: bac = 0.0; break;
	      case 1: if(isMale) bac = 0.03;
			else bac = 0.03; 
			break;
	      case 2: if(isMale) bac = 0.05;
			else bac = 0.06; 
			break;
	      case 3: if(isMale) bac = 0.08;
			else bac = 0.09; 
			break;
	      case 4: if(isMale) bac = 0.11;
			else bac = 0.12; 
			break;
	      case 5: if(isMale) bac = 0.14;
			else bac = 0.15; 
			break;
	    }
	  }
	  else //220 lbs or greater
	  {
	    switch(numDrinks) {
	      case 0: bac = 0.0; break;
	      case 1: if(isMale) bac = 0.02;
			else bac = 0.03; 
			break;
	      case 2: if(isMale) bac = 0.05;
			else bac = 0.06; 
			break;
	      case 3: if(isMale) bac = 0.07;
			else bac = 0.08; 
			break;
	      case 4: if(isMale) bac = 0.10;
			else bac = 0.11; 
			break;
	      case 5: if(isMale) bac = 0.12;
			else bac = 0.14; 
			break;
	    }
	  }
      return bac;
	}
	
	
}
