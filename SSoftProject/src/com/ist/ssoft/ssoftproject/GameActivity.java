package com.ist.ssoft.ssoftproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class GameActivity extends Activity {

	private SQLiteDatabase db;
	private String username;
	private boolean doublePoints = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		
		db=openOrCreateDatabase("UserDB", Context.MODE_PRIVATE, null);
		db.execSQL("CREATE TABLE IF NOT EXISTS user(username VARCHAR,email VARCHAR,password VARCHAR);");
		db.execSQL("CREATE TABLE IF NOT EXISTS userPremium(username VARCHAR,premium BOOLEAN);");
		db.execSQL("CREATE TABLE IF NOT EXISTS userPoints(username VARCHAR,points BIGINT);");
		
		username = getIntent().getExtras().getString("username");
		
		 Cursor c = db.rawQuery("SELECT * FROM userPoints WHERE username='"+username+"'", null);
		 int points = c.getInt(1);
		 
		 TextView pointTextView = (TextView) findViewById(R.id.points);
		 
		 pointTextView.setText(points);
		 setContentView(pointTextView);


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}
	
	public void getPoint(View view)	{
		 Cursor c = db.rawQuery("SELECT * FROM userPoints WHERE username='"+username+"'", null);
		 int newPoints = doublePoints ? c.getInt(1) + 2 : c.getInt(1) + 1;
		 
		 db.execSQL("UPDATE userPoints SET points='"+newPoints+"' WHERE username='"+username+"'");
		 
		 //tentar q fique dinamico....
		 TextView pointTextView = (TextView) findViewById(R.id.points);
		 
		 pointTextView.setText(newPoints);
		 setContentView(pointTextView);
	}
	
	public void goToShop(View view){
		 Intent intent = new Intent(this, ShopActivity.class);
		 Bundle bundle = new Bundle();
		 bundle.putString("username", username);
		 intent.putExtras(bundle);
		 startActivity(intent);

	}

	public void bePremium(View view){
		 db.execSQL("UPDATE userPremium SET premium ='TRUE' WHERE username='"+username+"'");

	}
	
	public void doublePoints(View view) {
		 Cursor c = db.rawQuery("SELECT * FROM userPremium WHERE username='"+username+"'", null);
		 boolean premium = c.getInt(1)>0;
		 
		 this.doublePoints = premium ? true : false;

	}
}
