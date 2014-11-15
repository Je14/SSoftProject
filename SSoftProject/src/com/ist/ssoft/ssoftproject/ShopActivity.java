package com.ist.ssoft.ssoftproject;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ShopActivity extends Activity {

	private SQLiteDatabase db;
	private String username;
	TextView pointTextView;
	int points;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop);
		
		pointTextView = (TextView) findViewById(R.id.points);
		
		db=openOrCreateDatabase("UserDB", Context.MODE_PRIVATE, null);
		db.execSQL("CREATE TABLE IF NOT EXISTS userPoints(username VARCHAR,points BIGINT);");

		username = getIntent().getExtras().getString("username");
		
		Cursor c = db.rawQuery("SELECT * FROM userPoints WHERE username='"+username+"'", null);
		if(c.moveToFirst()) {
			int points = c.getInt(1);
			 		 
			pointTextView.setText(Integer.toString(points));
			this.points = points;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.shop, menu);
		return true;
	}
	
	public void buy(View view) {
		int pointsToRemove = Integer.parseInt(((Button)view).getText().toString().split(" ")[0]);
		
		if(points >= pointsToRemove) {
			points -= pointsToRemove;
			db.execSQL("UPDATE userPoints SET points='"+points+"' WHERE username='"+username+"'");
			pointTextView.setText(Integer.toString(points));
			
			TextView tView = (TextView)((ViewGroup)view.getParent()).getChildAt(0);
			
			
			
			Toast toast = Toast.makeText(getApplicationContext(), "You just got the " + tView.getText().toString() + "!\n AWESOME!!!",
					   Toast.LENGTH_LONG);
			TextView toastV = (TextView) toast.getView().findViewById(android.R.id.message);
			if( toastV != null){
				toastV.setGravity(Gravity.CENTER);
			}
			toast.show();
		}
		
		
	}

}
