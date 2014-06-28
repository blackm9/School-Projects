package com.example.winoapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.webkit.WebView;

public class RecipiesActivity extends Activity {

	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipies);
		webView = (WebView) findViewById(R.id.recipies_webView);
		webView.getSettings().setJavaScriptEnabled(true);
		String link = getIntent().getStringExtra("url");
		webView.loadUrl(link);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.recipies, menu);
		return true;
	}

}
