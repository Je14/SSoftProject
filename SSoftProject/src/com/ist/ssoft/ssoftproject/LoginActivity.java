package com.ist.ssoft.ssoftproject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private SQLiteDatabase db;
	EditText editUsername;
	EditText editPassword;
	public static final String USERNAME = "com.ist.ssoft.ssoftproject.USERNAME";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		// Show the Up button in the action bar.
		setupActionBar();
		
		editUsername = (EditText) findViewById(R.id.usernameLogin);
		editPassword = (EditText) findViewById(R.id.passwordLogin);

		
		this.db = openOrCreateDatabase("UserDB", Context.MODE_PRIVATE, null);
		db.execSQL("CREATE TABLE IF NOT EXISTS user(username VARCHAR,email VARCHAR,password VARCHAR);");
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
		
		Intent intent;
		
		// Values for username and password at the time of the login attempt.
		String mUsername;
		String mPassword;
					
					
		// Reset errors.
		editUsername.setError(null);
		editPassword.setError(null);

		// Store values at the time of the register attempt.
		mUsername = editUsername.getText().toString();
		mPassword = editPassword.getText().toString();
					
		boolean cancel = false;
		View focusView = null;
					
		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			editPassword.setError(getString(R.string.error_field_required));
			focusView = editPassword;
			cancel = true;
			} /*else if (mPassword.length() < 4) {
				mPasswordView.setError(getString(R.string.error_invalid_password));
				focusView = mPasswordView;
				cancel = true;
			} */

		// Check for a valid username.
		if (TextUtils.isEmpty(mUsername)) {
			editUsername.setError(getString(R.string.error_field_required));
			focusView = editUsername;
			cancel = true;
			} 

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			Cursor c = db.rawQuery("SELECT * FROM user WHERE username='"+mUsername+"' AND password='"+mPassword+"'", null);
			 
			 if(c.moveToFirst()){
				 intent = new Intent(this, GameActivity.class);
				 Bundle bundle = new Bundle();
				 bundle.putString("username", c.getString(0));
				 intent.putExtras(bundle);
				 startActivity(intent);
			} else {
				Toast toast = Toast.makeText(getApplicationContext(), "Wrong Username or Password!", Toast.LENGTH_LONG);
				TextView toastV = (TextView) toast.getView().findViewById(android.R.id.message);
				if( toastV != null){
					toastV.setGravity(Gravity.CENTER);
				}
				toast.show();
				editUsername.requestFocus();
			}

		}
					
	}

}
