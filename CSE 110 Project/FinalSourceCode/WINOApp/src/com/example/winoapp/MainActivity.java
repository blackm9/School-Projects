package com.example.winoapp;

import java.util.HashMap;

import library.DatabaseHandler;
import library.UserFunctions;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private View toplayout;
	private View sublayout;
	private View fakeLayout;
	private int screenWidth;
	private int animToPosition;
	private boolean menuOpen = false;
	private int oldLeft;
	private int oldTop;
	private int newleft;
	private int newTop;
	private Button inventoryB;
	private Button wishlistB;
	private Button pairItB;
	private Button searchB;
	private Button socialB;
	private Button BACB;
	private Button surpriseB;
	private Button helpB;
	private Button logoutB;
	private Button menuB;
	private Button quickAddB;
	private AnimationListener AL;
	private DisplayMetrics metrics;
	private Display display;
	public static TextView welcomeTV;

	static final int LOGIN_REQUEST = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
		      StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		      StrictMode.setThreadPolicy(policy);
		    }
		
		UserFunctions userFunctions = new UserFunctions();
		
		//Checks if user is logged in. If not, begin app at Login Page
		if(!userFunctions.isUserLoggedIn(getApplicationContext())) {
			Intent intent = new Intent(this, LoginActivity.class);
			startActivityForResult(intent, LOGIN_REQUEST); 
		}
		 

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		welcomeTV = (TextView) findViewById(R.id.welcome_message);
		toplayout = (View) findViewById(R.id.main_layout);
		sublayout = (View) findViewById(R.id.menu_layout);
		fakeLayout = (View) findViewById(R.id.fake_layout);
		inventoryB = (Button) findViewById(R.id.inventory_button);
		wishlistB = (Button) findViewById(R.id.wishlist_button);
		pairItB = (Button) findViewById(R.id.pairIt_button);
		searchB = (Button) findViewById(R.id.search_button);
		socialB = (Button) findViewById(R.id.social_button);
		BACB = (Button) findViewById(R.id.BAC_button);
		surpriseB = (Button) findViewById(R.id.surprise_button);
		helpB = (Button) findViewById(R.id.help_button);
		logoutB = (Button) findViewById(R.id.logout_button);
		menuB = (Button) findViewById(R.id.menu_button);
		quickAddB = (Button) findViewById(R.id.quick_add_button);
		metrics = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		display = getWindowManager().getDefaultDisplay();
		screenWidth = display.getWidth();
		int calcAnimatePosition = (screenWidth / 4);
		animToPosition = screenWidth - calcAnimatePosition;
		RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(
				animToPosition, RelativeLayout.LayoutParams.FILL_PARENT);
		sublayout.setLayoutParams(parms);
		
		DatabaseHandler sqlDBHandler = new DatabaseHandler(this);
		HashMap<String,String> user = sqlDBHandler.getUserDetails();
		String userName = user.get("username");
		if(userName==null)
		{
			userName = "Wino";
		}
		changeWelcome(userName);
				
		/** Animation Listener */

		AL = new AnimationListener() {

			public void onAnimationStart(Animation animation) {
				menuB.setClickable(false);
				toplayout.setEnabled(false);
			}

			public void onAnimationRepeat(Animation animation) {

			}

			public void onAnimationEnd(Animation animation) {
				if (menuOpen) {

					toplayout.layout(oldLeft, oldTop,
							oldLeft + toplayout.getMeasuredWidth(), oldTop
									+ toplayout.getMeasuredHeight());
					menuOpen = false;
					// sublayout.setEnabled(false);
					menuB.setClickable(true);
					toplayout.setEnabled(true);

				} else if (!menuOpen) {

					toplayout.layout(newleft, newTop,
							newleft + toplayout.getMeasuredWidth(), newTop
									+ toplayout.getMeasuredHeight());
					menuB.setClickable(true);

					menuOpen = true;
					toplayout.setEnabled(true);
				}

			}
		};

		// Menu button
		menuB.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (!menuOpen) {
					animSlideRight();
				} else if (menuOpen) {

					animSlideLeft();
				}
			}
		});

		// Quick Add button
		quickAddB.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
					Intent intent = new Intent(getApplicationContext(),
							QuickAddActivity.class);
					startActivity(intent);
			
			}
		});

		// Inventory button
		inventoryB.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// Go to inventory
				if (menuOpen) {
					Intent intent = new Intent(getApplicationContext(),
							InventoryActivity.class);
					startActivity(intent);
				}
			}
		});

		// Wishlist button
		wishlistB.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// Go to wishlist
				if (menuOpen) {
					Intent intent = new Intent(getApplicationContext(),
							WishlistActivity.class);
					startActivity(intent);
				}
			}
		});

		// Pairing button
		pairItB.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (menuOpen) {
					Intent intent = new Intent(getApplicationContext(),
							Main_WMPActivity.class);
					startActivity(intent);
				}
			}
		});

		// Search button
		searchB.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// Go to search page
				if (menuOpen) {
					Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
					startActivity(intent);
				}
			}
		});

		// Social button
		socialB.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (menuOpen) {
					Intent intent = new Intent(getApplicationContext(),
							SocialActivity.class);
					startActivity(intent);
				}
			}
		});

		// Blood Alcohol Content button
		BACB.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (menuOpen) {
					Intent intent = new Intent(getApplicationContext(),
							BACActivity.class);
					startActivity(intent);
				}
			}
		});

		// Recommend button
		surpriseB.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// Go to recommendation page
				if (menuOpen) {
					Intent intent = new Intent(getApplicationContext(),
							SurpriseActivity.class);
					startActivity(intent);
				}
			}
		});

		// Help button
		helpB.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// Go to help page
				if (menuOpen) {
					Intent intent = new Intent(getApplicationContext(),
							TutorialActivity.class);
					startActivity(intent);
				}
			}
		});

		// Logout button
		logoutB.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// Logout user
				if (menuOpen) {
					UserFunctions userFunction = new UserFunctions();
					userFunction.logoutUser(getApplicationContext());
					//go back out to login page
					Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
					startActivity(intent);
				}
			}
		});
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == LOGIN_REQUEST) {
			if (resultCode == RESULT_OK) {
				// DoNothing
			} else if (resultCode == RESULT_CANCELED) {
				// Do nothing
			} else {
				Toast.makeText(getApplicationContext(),
						"An Unknown Error Occur", Toast.LENGTH_SHORT).show();
			}
		}
	}
	public void onPause() {
		menuOpen = false;
		super.onPause();
	}
	
	public void onResume() {
		
		DatabaseHandler sqlDBHandler = new DatabaseHandler(this);
		HashMap<String,String> user = sqlDBHandler.getUserDetails();
		String userName = user.get("username");
		if(userName==null)
		{
			userName = "Wino";
		}
		changeWelcome(userName);
		
		super.onResume();
	}
	/** Animation right */

	private void animSlideRight() {

		fakeLayout.setVisibility(View.VISIBLE);
		newleft = toplayout.getLeft() + animToPosition;

		newTop = toplayout.getTop();
		TranslateAnimation slideRight = new TranslateAnimation(0, newleft, 0, 0);
		slideRight.setDuration(500);
		slideRight.setFillEnabled(true);
		slideRight.setAnimationListener(AL);
		toplayout.startAnimation(slideRight);

	}

	/** Animation left */

	private void animSlideLeft() {
		fakeLayout.setVisibility(View.GONE);
		oldLeft = toplayout.getLeft() - animToPosition;

		oldTop = toplayout.getTop();
		TranslateAnimation slideLeft = new TranslateAnimation(newleft, oldLeft,
				0, 0);
		slideLeft.setDuration(500);
		slideLeft.setFillEnabled(true);
		slideLeft.setAnimationListener(AL);
		toplayout.setAnimation(slideLeft);

	}
	
	@Override
	public void onBackPressed() {
		//If for whatever reason the main menu is NOT the root activity:
		//disables the back button from re-launching a previously loaded activity.
		// super.onBackPressed();
	}

	public static void changeWelcome(String username) {
		welcomeTV.setText("Welcome " + username + "!");
	}

}
