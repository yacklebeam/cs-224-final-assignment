package com.chewchew.bitecal;

import java.io.IOException;
import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
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
		createTable(result);
		//Toast.makeText(this, "FOODS", Toast.LENGTH_SHORT).show();

		//for (Food food: result) {
			//Toast.makeText(this, food.getFoodName() + " : " + food.getFoodCalories(), Toast.LENGTH_SHORT).show();
		//}
	}
	
	public void insertClicked (View v) {
		EditText text_v = (EditText)findViewById(R.id.editText3);
		EditText text_i = (EditText)findViewById(R.id.editText2);
		String text = text_v.getText().toString();
		float cal_t = Float.parseFloat(text_i.getText().toString());
		
		db_handler.insertRow(text, cal_t);
	}
	
	public void createTable(ArrayList<Food> foods)
	{
		final Dialog result_dialog = new Dialog(this, android.R.style.Theme_NoTitleBar_Fullscreen);
		result_dialog.setContentView(R.layout.resultscreen);
		
		if(foods.isEmpty()) {
			foods.add(new Food("No Results", 0,0));
		}
		
		TableLayout td = (TableLayout)result_dialog.findViewById(R.id.tableLayout_result);
		for (Food food: foods) 
		{
			TextView t = new TextView(this);
			//t.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			TextView t2 = new TextView(this);
			//t2.setWidth(0);
			t2.setTextColor(Color.TRANSPARENT);
			t2.setText(food.getCalOneServing()+"");
			t.setText(food.getFoodName().subSequence(0,food.getFoodName().length()));
			t.setTextColor(Color.BLACK);
			t.setTextSize(15);
	        t.setBackgroundColor(Color.WHITE); 
	        t.setPadding(5, 0, 0, 0);
	        TableRow tr = new TableRow(this);
	        tr.setPadding(0, 1, 0, 1);
	        tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	        //t.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	        //tr.setBackgroundColor(Color.BLACK);
			tr.setClickable(true);
			tr.setOnClickListener(new OnClickListener()
			{
				public void onClick(View v)
				{
					TableRow t = (TableRow) v;
				    TextView firstTextView = (TextView) t.getChildAt(0);
				    TextView second = (TextView) t.getChildAt(1);
				    String name = (String)firstTextView.getText();
				    int cals = Integer.parseInt((String)second.getText());
				    showAddDialog(name,cals);
					//Toast.makeText(getApplicationContext(), firstTextView.getText(), Toast.LENGTH_SHORT).show();
				}
			}
			);
			
			tr.addView(t);
			tr.addView(t2);
			td.addView(tr);
		}
		
		
		Button begin_bt = (Button)result_dialog.findViewById(R.id.button2);
		result_dialog.show();
		begin_bt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick (View v) {
				result_dialog.dismiss();
			}
		});
		
	}
	
	private void showAddDialog(String name, int cals) {
		final Dialog add_dialog = new Dialog(this, android.R.style.Theme_NoTitleBar_Fullscreen);
		add_dialog.setContentView(R.layout.addfoodtomeal);
		
		TextView name_view = (TextView)add_dialog.findViewById(R.id.textView3);
		name_view.setText("Name: " + name);
		TextView cal_view = (TextView)add_dialog.findViewById(R.id.textView4);
		cal_view.setText("Calories/Serving: " + cals);
		
		Button begin_bt = (Button)add_dialog.findViewById(R.id.button1);
		add_dialog.show();
		begin_bt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick (View v) {
				//add_dialog.dismiss();
				//ADD a new food to the meal here.
			}
		});
		
		add_dialog.show();
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
