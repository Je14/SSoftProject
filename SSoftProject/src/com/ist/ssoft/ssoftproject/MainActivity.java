package com.ist.ssoft.ssoftproject;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.ist.ssoft.ssoftproject.crypto.CryptoTool;

public class MainActivity extends Activity {
	
	private static final String pathToDB = "/data/data/com.ist.ssoft.ssoftproject/databases/UserDB";
	private static final String pathToEncryptedDB = "/data/data/com.ist.ssoft.ssoftproject/databases/EncryptedUserDB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        decryptDB();
        
        final Button loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
            	MainActivity.this.startActivity(myIntent);
            }
        });
        
        
        final Button registerButton = (Button) findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent myIntent = new Intent(MainActivity.this, RegisterActivity.class);
            	MainActivity.this.startActivity(myIntent);
            }
        });
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	encryptDB();
    }

    
    
    private void encryptDB() {
    	File DBFile = new File(pathToDB);
    	
    	if(DBFile.exists()) {
    		CryptoTool tool = new CryptoTool();
    		tool.encrypt(pathToDB, pathToEncryptedDB);
    		DBFile.delete();
    	}  	
    }
    
    
    
    

    private void decryptDB() {
    	File encryptedDBFile = new File(pathToEncryptedDB);
    	
    	if(encryptedDBFile.exists()) {
    		CryptoTool tool = new CryptoTool();
    		tool.decrypt(pathToEncryptedDB, pathToDB);
    		//encryptedDBFile.delete();
    	}
    }
    
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    
    
    
    
}
