package com.ist.ssoft.ssoftproject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends Activity {

	private SQLiteDatabase db;
	public static final String USERNAME = "com.ist.ssoft.ssoftproject.USERNAME";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		// Show the Up button in the action bar.
		setupActionBar();
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void attemptToLoging(View view){
		String username;
		String password;
		Intent intent;
		
		EditText usernameEditText = (EditText) findViewById(R.id.usernameLogin);
		EditText passwordEditText = (EditText) findViewById(R.id.passwordLogin);
		username = usernameEditText.getText().toString();
		password = passwordEditText.getText().toString();
		
		this.db = openOrCreateDatabase("UserDB", Context.MODE_PRIVATE, null);
		db.execSQL("CREATE TABLE IF NOT EXISTS user(username VARCHAR,email VARCHAR,password VARCHAR);");
		
		 Cursor c = db.rawQuery("SELECT * FROM user WHERE username='"+username+"' AND password='"+password+"'", null);
		 
		 if(c.moveToFirst()){
			 intent = new Intent(this, GameActivity.class);
			 intent.putExtra(USERNAME, username);
			 startActivity(intent);
		 }else{
			 AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			 
			 alertDialog.setTitle("WRONG FIELDS!");
			 alertDialog.setMessage("User does not exist.");
			 alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
		 }
	}

}
