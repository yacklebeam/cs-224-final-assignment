package com.chewchew.bitecal;

import java.io.IOException;
import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TestDatabaseActivity extends Activity {
	
	public DatabaseHandler db_handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_database);
		
		//db_handler = new DatabaseHandler(getApplicationContext());
		db_handler = new DatabaseHandler(this);
 
		try {
        	db_handler.createDataBase();
        	//db_handler.copyAnyway();
	 	} catch (IOException ioe) {
	 		throw new Error("Unable to create database");
	 	}
	 	try {
	 		//db_handler.openDataBase();
	 	}catch(SQLException sqle){
	 		throw sqle;
	 	}
	}
	
	public void clickedQuery (View v) {
		EditText text_v = (EditText)findViewById(R.id.editText1);
		String text = text_v.getText().toString();
		
		ArrayList<Food> result = db_handler.sendQuery(text);
		Toast.makeText(this, "FOODS", Toast.LENGTH_SHORT).show();

		for (Food food: result) {
			Toast.makeText(this, food.getFoodName() + " : " + food.getFoodCalories(), Toast.LENGTH_SHORT).show();
		}
	}
	
	/*public void clickedQuery (View v) {
		try {
			EditText text_v = (EditText)findViewById(R.id.editText1);
			String text = text_v.getText().toString();
			//Toast.makeText(this, "Name=> "+text, Toast.LENGTH_LONG).show();
			//SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/com.chewchew.bitecal/databases/bitecal.sql", null, 1);
			SQLiteDatabase db = db_handler.getReadableDatabase();
					
			//Cursor c = db.query("bitecal",new String[] {"name"},null,null,null,null,null);
			Cursor c = db.rawQuery("SELECT name,cal FROM bitecal WHERE name LIKE ?;", new String[] {text});

			if (c.moveToFirst()) {
			    //for (int i = 0; i < 10; i++) {
			    Toast.makeText(this, c.getString(0)+ ": " + c.getString(1), Toast.LENGTH_SHORT).show();
			        //c.moveToNext();
			    //}
			}
		
		} catch (SQLiteException e) {
			e.printStackTrace();
		}
	}*/

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test_database, menu);
		return true;
	}

}
