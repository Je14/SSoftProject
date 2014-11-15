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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {

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
		
		final Button registerButton = (Button) findViewById(R.id.attempt_register_button);
        registerButton.setOnClickListener(btnRegisterListener);
	}

	
	public OnClickListener btnRegisterListener=
			new View.OnClickListener() {		
		@Override
        public void onClick(View v) {
			
			
			// Values for username and password at the time of the login attempt.
			String mUsername;
			String mPassword;
			String mEmail;
			
			
			// Reset errors.
			editUsername.setError(null);
			editPassword.setError(null);
			editEmail.setError(null);

			// Store values at the time of the register attempt.
			mUsername = editUsername.getText().toString();
			mPassword = editPassword.getText().toString();
			mEmail = editEmail.getText().toString();
			
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

			if (TextUtils.isEmpty(mEmail)) {
				editEmail.setError(getString(R.string.error_field_required));
				focusView = editEmail;
				cancel = true;
			} /*else if (!mEmail.contains("@")) {
				mEmailView.setError(getString(R.string.error_invalid_email));
				focusView = mEmailView;
				cancel = true;
			} */

			if (cancel) {
				// There was an error; don't attempt login and focus the first
				// form field with an error.
				focusView.requestFocus();
			} else {
				Cursor c = db.rawQuery("SELECT * FROM user WHERE username='"+mUsername+"' OR email='"+editEmail.getText()+"'", null);
				if(c.getCount() == 0) {
					db.execSQL("INSERT INTO user VALUES('"+mUsername+"','"+
							editEmail.getText()+"','"+editPassword.getText()+"');");
					db.execSQL("INSERT INTO userPremium VALUES('"+mUsername+"','0');");
					db.execSQL("INSERT INTO userPoints VALUES('"+mUsername+"','0');");
					
		        	Intent intent = new Intent(RegisterActivity.this, GameActivity.class);
		        	Bundle bundle = new Bundle();
		        	bundle.putString("username", editUsername.getText().toString());
					intent.putExtras(bundle);
		        	RegisterActivity.this.startActivity(intent);
				} else {
					Toast toast = Toast.makeText(getApplicationContext(), "Username or Email already exists!",
							   Toast.LENGTH_LONG);
					TextView toastV = (TextView) toast.getView().findViewById(android.R.id.message);
					if( toastV != null){
						toastV.setGravity(Gravity.CENTER);
					}
					toast.show();
					editUsername.requestFocus();
				}
				
				
				
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
