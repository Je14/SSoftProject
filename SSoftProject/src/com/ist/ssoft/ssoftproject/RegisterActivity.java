package com.ist.ssoft.ssoftproject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends Activity {

	public static final String USERNAME = "com.ist.ssoft.ssoftproject.USERNAME";
	private SQLiteDatabase db;
	private EditText editUsername;
	private EditText editEmail;
	private EditText editPassword;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		// Show the Up button in the action bar.
		setupActionBar();
		
		editUsername = (EditText)findViewById(R.id.usernameEditText);
		editEmail = (EditText)findViewById(R.id.emailEditText);
		editPassword = (EditText)findViewById(R.id.passwordEditText);
		
		db=openOrCreateDatabase("UserDB", Context.MODE_PRIVATE, null);
		db.execSQL("CREATE TABLE IF NOT EXISTS user(username VARCHAR,email VARCHAR,password VARCHAR);");
		db.execSQL("CREATE TABLE IF NOT EXISTS userPremium(username VARCHAR,premium BOOLEAN);");
		db.execSQL("CREATE TABLE IF NOT EXISTS userPoints(username VARCHAR,points BIGINT);");
		
		final Button registerButton = (Button) findViewById(R.id.register_button);
        registerButton.setOnClickListener(btnRegisterListener);
	}

	
	public OnClickListener btnRegisterListener=
			new View.OnClickListener() {		
		@Override
        public void onClick(View v) {
			
			Cursor c = db.rawQuery("SELECT * FROM user WHERE username='"+editUsername.getText()+"' OR email='"+editEmail.getText()+"'", null);
			if(c.getCount() == 0) {
				db.execSQL("INSERT INTO user VALUES('"+editUsername.getText()+"','"+
						editEmail.getText()+"','"+editPassword.getText()+"');");
				db.execSQL("INSERT INTO userPremium VALUES('"+editUsername.getText()+"','FALSE');");
				db.execSQL("INSERT INTO userPoints VALUES('"+editUsername.getText()+"','0');");
				
	        	Intent intent = new Intent(RegisterActivity.this, GameActivity.class);
				intent.putExtra(USERNAME, editUsername.getText());
	        	RegisterActivity.this.startActivity(intent);
			} else {
				AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
				 
				 alertDialog.setTitle("WRONG FIELDS!");
				 alertDialog.setMessage("User does not exist.");
				 alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				});
			}
        }
    };
	
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
		getMenuInflater().inflate(R.menu.register, menu);
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

}
