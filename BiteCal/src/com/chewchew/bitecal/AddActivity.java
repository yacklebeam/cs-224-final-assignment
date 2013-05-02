package com.chewchew.bitecal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.joda.time.DateTime;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.SQLException;
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

public class AddActivity extends Activity {
	
	private int meal_number;
	public static Meal[][] BreakfastArray = new Meal[13][32];
	public static Meal[][] LunchArray = new Meal[13][32];
	public static Meal[][] DinnerArray = new Meal[13][32];
	public static Meal[][] OtherArray = new Meal[13][32];
	private DateTime date = ShowCalendar.dateTime;
	
	public DatabaseHandler db_handler;
	
	private String bfast_file = "BiteCal_Breakfast_Info.txt";
	private String lunch_file = "BiteCal_Lunch_Info.txt";
	private String dinner_file = "BiteCal_Dinner_Info.txt";
	private String other_file = "BiteCal_Other_Info.txt";
	
	private ArrayList<Food> result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
		
		try {
			populateArrays();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Bundle extras = getIntent().getExtras();
		if (extras != null){
			meal_number = extras.getInt("meal_type");
		}
		setContentView(R.layout.activity_note);
		Button b = (Button)this.findViewById(R.id.button1);
		b.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				EditText editor = (EditText)findViewById(R.id.editText2);
				String text = editor.getText().toString();
				clickedQuery(v, text);
				//Food food = new Food(text, 1, 10);
				//addFoodToArray(meal_number, food);
				//finish();
			}
		});
	}
	
	protected void onDestroy(){
		super.onDestroy();
		try {
			writeInfoToFile(meal_number);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.add, menu);
		return true;
	}
	
	private void addFoodToArray(int mealNumber, Food food){
		if(mealNumber == 0){
			if(BreakfastArray[date.getMonthOfYear()][date.getDayOfMonth()] == null){
				Meal meal = new Meal();
				meal.addFood(food);
				System.out.println("New Meal Created.  New food being added to Breakfast: " + food.getFoodName() + " at index " + date.getMonthOfYear() + ", " + date.getDayOfMonth());
				BreakfastArray[date.getMonthOfYear()][date.getDayOfMonth()] = meal;
			}
			else{
				System.out.println("Meal already created.  New food being added to Breakfast: " + food.getFoodName());
				BreakfastArray[date.getMonthOfYear()][date.getDayOfMonth()].addFood(food);
			}
		}
		else if(mealNumber == 1){
			if(LunchArray[date.getMonthOfYear()][date.getDayOfMonth()] == null){
				Meal meal = new Meal();
				meal.addFood(food);
				System.out.println("New Meal Created.  New food being added to Lunch: " + food.getFoodName() + " at index " + date.getMonthOfYear() + ", " + date.getDayOfMonth());
				LunchArray[date.getMonthOfYear()][date.getDayOfMonth()] = meal;
			}
			else{
				System.out.println("Meal already created.  New food being added to Lunch: " + food.getFoodName());
				LunchArray[date.getMonthOfYear()][date.getDayOfMonth()].addFood(food);
			}
		}
		else if(mealNumber == 2){
			if(DinnerArray[date.getMonthOfYear()][date.getDayOfMonth()] == null){
				Meal meal = new Meal();
				meal.addFood(food);
				System.out.println("New Meal Created.  New food being added to Dinner: " + food.getFoodName() + " at index " + date.getMonthOfYear() + ", " + date.getDayOfMonth());
				DinnerArray[date.getMonthOfYear()][date.getDayOfMonth()] = meal;
			}
			else{
				System.out.println("Meal already created.  New food being added to Dinner: " + food.getFoodName());
				DinnerArray[date.getMonthOfYear()][date.getDayOfMonth()].addFood(food);
			}
		}
		else{
			if(OtherArray[date.getMonthOfYear()][date.getDayOfMonth()] == null){
				Meal meal = new Meal();
				meal.addFood(food);
				System.out.println("New Meal Created.  New food being addedto Other: " + food.getFoodName() + " at index " + date.getMonthOfYear() + ", " + date.getDayOfMonth());
				OtherArray[date.getMonthOfYear()][date.getDayOfMonth()] = meal;
			}
			else{
				System.out.println("Meal already created.  New food being added to Other: " + food.getFoodName());
				OtherArray[date.getMonthOfYear()][date.getDayOfMonth()].addFood(food);
			}
		}
	}
	
	private void writeInfoToFile(int mealNumber) throws IOException{
		if(mealNumber == 0){
			BufferedWriter bfastwriter = new BufferedWriter(new FileWriter(new File(getFilesDir() + File.separator + bfast_file), true));
			int i = date.getMonthOfYear();
			int j = date.getDayOfMonth();
			if(BreakfastArray[i][j] != null){
				System.out.print("Index: " + i + ", " + j + "  ");
				String month = Integer.toString(i);
				String day = Integer.toString(j);
				ArrayList <Food> foodList = BreakfastArray[i][j].getFoods();
				if(foodList.size() == 1){
					System.out.println("Size is 1, writing asterisk");
					bfastwriter.write("*;" + month + ";" + day + ";" + foodList.get(foodList.size()-1).getFoodName() + ";" + foodList.get(foodList.size()-1).getServings() + ";");
					System.out.println("WRITE: *;" + month + ";" + day + ";" + foodList.get(foodList.size()-1).getFoodName() + ";" + foodList.get(foodList.size()-1).getServings() + ";");
				}
				else{
					bfastwriter.write(foodList.get(foodList.size()-1).getFoodName() + ";" + foodList.get(foodList.size()-1).getServings() + ";");
					System.out.println("WRITE: " + foodList.get(foodList.size()-1).getFoodName() + ";" + foodList.get(foodList.size()-1).getServings() + ";");
				}
			}
			bfastwriter.close();
		}
		
		else if(mealNumber == 1){
			BufferedWriter lunchwriter = new BufferedWriter(new FileWriter(new File(getFilesDir() + File.separator + lunch_file), true));
			int i = date.getMonthOfYear();
			int j = date.getDayOfMonth();
			if(LunchArray[i][j] != null){
				System.out.print("Index: " + i + ", " + j + "  ");
				String month = Integer.toString(i);
				String day = Integer.toString(j);
				ArrayList <Food> foodList = LunchArray[i][j].getFoods();
				if(foodList.size() == 1){
					System.out.println("Size is 1, writing asterisk");
					lunchwriter.write("*;" + month + ";" + day + ";" + foodList.get(foodList.size()-1).getFoodName() + ";" + foodList.get(foodList.size()-1).getServings() + ";");
					System.out.println("WRITE: *;" + month + ";" + day + ";" + foodList.get(foodList.size()-1).getFoodName() + ";" + foodList.get(foodList.size()-1).getServings() + ";");
				}
				else{
					lunchwriter.write(foodList.get(foodList.size()-1).getFoodName() + ";" + foodList.get(foodList.size()-1).getServings() + ";");
					System.out.println("WRITE: " + foodList.get(foodList.size()-1).getFoodName() + ";" + foodList.get(foodList.size()-1).getServings() + ";");
				}
			}
			lunchwriter.close();
		}
		
		else if(mealNumber == 2){
			BufferedWriter dinnerwriter = new BufferedWriter(new FileWriter(new File(getFilesDir() + File.separator + dinner_file), true));
			int i = date.getMonthOfYear();
			int j = date.getDayOfMonth();
			if(DinnerArray[i][j] != null){
				System.out.print("Index: " + i + ", " + j + "  ");
				String month = Integer.toString(i);
				String day = Integer.toString(j);
				ArrayList <Food> foodList = DinnerArray[i][j].getFoods();
				if(foodList.size() == 1){
					System.out.println("Size is 1, writing asterisk");
					dinnerwriter.write("*;" + month + ";" + day + ";" + foodList.get(foodList.size()-1).getFoodName() + ";" + foodList.get(foodList.size()-1).getServings() + ";");
					System.out.println("WRITE: *;" + month + ";" + day + ";" + foodList.get(foodList.size()-1).getFoodName() + ";" + foodList.get(foodList.size()-1).getServings() + ";");
				}
				else{
					dinnerwriter.write(foodList.get(foodList.size()-1).getFoodName() + ";" + foodList.get(foodList.size()-1).getServings() + ";");
					System.out.println("WRITE: " + foodList.get(foodList.size()-1).getFoodName() + ";" + foodList.get(foodList.size()-1).getServings() + ";");
				}
			}
			dinnerwriter.close();
		}
		
		else if(mealNumber == 3){
			BufferedWriter otherwriter = new BufferedWriter(new FileWriter(new File(getFilesDir() + File.separator + other_file), true));
			int i = date.getMonthOfYear();
			int j = date.getDayOfMonth();
			if(OtherArray[i][j] != null){
				System.out.print("Index: " + i + ", " + j + "  ");
				String month = Integer.toString(i);
				String day = Integer.toString(j);
				ArrayList <Food> foodList = OtherArray[i][j].getFoods();
				if(foodList.size() == 1){
					System.out.println("Size is 1, writing asterisk");
					otherwriter.write("*;" + month + ";" + day + ";" + foodList.get(foodList.size()-1).getFoodName() + ";" + foodList.get(foodList.size()-1).getServings() + ";");
					System.out.println("WRITE: *;" + month + ";" + day + ";" + foodList.get(foodList.size()-1).getFoodName() + ";" + foodList.get(foodList.size()-1).getServings() + ";");
				}
				else{
					otherwriter.write(foodList.get(foodList.size()-1).getFoodName() + ";" + foodList.get(foodList.size()-1).getServings() + ";");
					System.out.println("WRITE: " + foodList.get(foodList.size()-1).getFoodName() + ";" + foodList.get(foodList.size()-1).getServings() + ";");
				}
			}
			otherwriter.close();
		}
	}
	
	public void populateArrays() throws IOException{
		populateBreakfastArray();
		populateLunchArray();
		populateDinnerArray();
		populateOtherArray();
	}
	
	private void populateBreakfastArray() throws IOException{
		BufferedReader bufferedReader = new BufferedReader(new FileReader(getFilesDir() + File.separator + bfast_file));	
		String read;
		StringTokenizer splitter;
		String nextToken;
		int month = 0;
		int day = 0;
		double serv;
		while((read = bufferedReader.readLine()) != null){
			System.out.println("READ: " + read);
			splitter = new StringTokenizer(read, ";");
			nextToken = splitter.nextToken();
			while(nextToken != null){
				System.out.println("In while loop");
				System.out.println("first token= " + nextToken);
				if(nextToken.equals("*")){
					System.out.println("In * loop");
					nextToken = splitter.nextToken();
					month = Integer.parseInt(nextToken);
					nextToken = splitter.nextToken();
					day = Integer.parseInt(nextToken);
					nextToken = splitter.nextToken();
				}
				else{
					serv = Double.parseDouble(splitter.nextToken());
					Food food = new Food(nextToken, serv, 1);
					if(BreakfastArray[month][day] == null){
						System.out.println("Index " + month + ", " + day + " is null, creating new meal and adding " + food.getFoodName());
						Meal meal = new Meal();
						meal.addFood(food);
						BreakfastArray[month][day] = meal;
					}
					else{
						System.out.println("Meal already exists, adding food, " + food.getFoodName() + " to it");
						BreakfastArray[month][day].addFood(food);
					}
					if(splitter.hasMoreTokens()){
						System.out.println("More tokens in the buffer");
						nextToken = splitter.nextToken();
					}
					else{
						System.out.println("No tokens left in the buffer");
						nextToken = null;
					}
				}
			}
		}
		bufferedReader.close();
	}
	
	private void populateLunchArray() throws IOException{
		BufferedReader bufferedReader = new BufferedReader(new FileReader(getFilesDir() + File.separator + lunch_file));	
		String read;
		StringTokenizer splitter;
		String nextToken;
		int month = 0;
		int day = 0;
		double serv;
		while((read = bufferedReader.readLine()) != null){
			System.out.println("READ: " + read);
			splitter = new StringTokenizer(read, ";");
			nextToken = splitter.nextToken();
			while(nextToken != null){
				System.out.println("In while loop");
				System.out.println("first token= " + nextToken);
				if(nextToken.equals("*")){
					System.out.println("In * loop");
					nextToken = splitter.nextToken();
					month = Integer.parseInt(nextToken);
					nextToken = splitter.nextToken();
					day = Integer.parseInt(nextToken);
					nextToken = splitter.nextToken();
				}
				else{
					serv = Double.parseDouble(splitter.nextToken());
					Food food = new Food(nextToken, serv, 1);
					if(LunchArray[month][day] == null){
						System.out.println("Index " + month + ", " + day + " is null, creating new meal and adding " + food.getFoodName());
						Meal meal = new Meal();
						meal.addFood(food);
						LunchArray[month][day] = meal;
					}
					else{
						System.out.println("Meal already exists, adding food, " + food.getFoodName() + " to it");
						LunchArray[month][day].addFood(food);
					}
					if(splitter.hasMoreTokens()){
						System.out.println("More tokens in the buffer");
						nextToken = splitter.nextToken();
					}
					else{
						System.out.println("No tokens left in the buffer");
						nextToken = null;
					}
				}
			}
		}
		bufferedReader.close();
	}
	
	private void populateDinnerArray() throws IOException{
		BufferedReader bufferedReader = new BufferedReader(new FileReader(getFilesDir() + File.separator + dinner_file));	
		String read;
		StringTokenizer splitter;
		String nextToken;
		int month = 0;
		int day = 0;
		double serv;
		while((read = bufferedReader.readLine()) != null){
			System.out.println("READ: " + read);
			splitter = new StringTokenizer(read, ";");
			nextToken = splitter.nextToken();
			while(nextToken != null){
				System.out.println("In while loop");
				System.out.println("first token= " + nextToken);
				if(nextToken.equals("*")){
					System.out.println("In * loop");
					nextToken = splitter.nextToken();
					month = Integer.parseInt(nextToken);
					nextToken = splitter.nextToken();
					day = Integer.parseInt(nextToken);
					nextToken = splitter.nextToken();
				}
				else{
					serv = Double.parseDouble(splitter.nextToken());
					Food food = new Food(nextToken, serv, 1);
					if(DinnerArray[month][day] == null){
						System.out.println("Index " + month + ", " + day + " is null, creating new meal and adding " + food.getFoodName());
						Meal meal = new Meal();
						meal.addFood(food);
						DinnerArray[month][day] = meal;
					}
					else{
						System.out.println("Meal already exists, adding food, " + food.getFoodName() + " to it");
						DinnerArray[month][day].addFood(food);
					}
					if(splitter.hasMoreTokens()){
						System.out.println("More tokens in the buffer");
						nextToken = splitter.nextToken();
					}
					else{
						System.out.println("No tokens left in the buffer");
						nextToken = null;
					}
				}
			}
		}
		bufferedReader.close();
	}
	
	private void populateOtherArray() throws IOException{
		BufferedReader bufferedReader = new BufferedReader(new FileReader(getFilesDir() + File.separator + other_file));	
		String read;
		StringTokenizer splitter;
		String nextToken;
		int month = 0;
		int day = 0;
		double serv;
		while((read = bufferedReader.readLine()) != null){
			System.out.println("READ: " + read);
			splitter = new StringTokenizer(read, ";");
			nextToken = splitter.nextToken();
			while(nextToken != null){
				System.out.println("In while loop");
				System.out.println("first token= " + nextToken);
				if(nextToken.equals("*")){
					System.out.println("In * loop");
					nextToken = splitter.nextToken();
					month = Integer.parseInt(nextToken);
					nextToken = splitter.nextToken();
					day = Integer.parseInt(nextToken);
					nextToken = splitter.nextToken();
				}
				else{
					serv = Double.parseDouble(splitter.nextToken());
					Food food = new Food(nextToken, serv, 1);
					if(OtherArray[month][day] == null){
						System.out.println("Index " + month + ", " + day + " is null, creating new meal and adding " + food.getFoodName());
						Meal meal = new Meal();
						meal.addFood(food);
						OtherArray[month][day] = meal;
					}
					else{
						System.out.println("Meal already exists, adding food, " + food.getFoodName() + " to it");
						OtherArray[month][day].addFood(food);
					}
					if(splitter.hasMoreTokens()){
						System.out.println("More tokens in the buffer");
						nextToken = splitter.nextToken();
					}
					else{
						System.out.println("No tokens left in the buffer");
						nextToken = null;
					}
				}
			}
		}
		bufferedReader.close();
	}
	
	public void clickedQuery (View v, String text) {
		System.out.println("Query clicked. " + text);
		result = db_handler.sendQuery(text);
		
		createTable(result);		
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
		Button add_new = (Button)result_dialog.findViewById(R.id.button1);
		
		result_dialog.show();
		begin_bt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick (View v) {
				result_dialog.dismiss();
			}
		});
		
		add_new.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick (View v) {
				showAddNewDialog();
			}
		});
		
	}
	
	private void showAddNewDialog() {
		final Dialog new_dialog = new Dialog(this, android.R.style.Theme_NoTitleBar_Fullscreen);
		new_dialog.setContentView(R.layout.addnewtodatabase);
		
		//EditText name_view = (EditText)new_dialog.findViewById(R.id.editText1);
		//EditText cal_view = (EditText)new_dialog.findViewById(R.id.editText2);
		
		Button begin_bt = (Button)new_dialog.findViewById(R.id.button1);
		new_dialog.show();
		begin_bt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick (View v) {
				View w = (View) v.getParent();
				EditText text_v = (EditText)w.findViewById(R.id.editText1);
				EditText text_i = (EditText)w.findViewById(R.id.editText2);
				String text = text_v.getText().toString();
				float cal_t = Float.parseFloat(text_i.getText().toString());
				
				System.out.println("Adding " + text + " with " + cal_t + " calories.");
				
				db_handler.insertRow(text, cal_t);
				finish();
			}
		});
		
		//add_dialog.show();
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
				EditText num_servs = (EditText)add_dialog.findViewById(R.id.editText1);
				int servs = Integer.parseInt(num_servs.getText().toString());
				TextView name_view = (TextView)add_dialog.findViewById(R.id.textView3);
				String text = name_view.getText().toString().substring(5);
				TextView cal_view = (TextView)add_dialog.findViewById(R.id.textView4);
				int cals = Integer.parseInt(cal_view.getText().toString().substring(18));
				Food food = new Food(text, servs, cals);
				addFoodToArray(meal_number, food);
				//Intent i = new Intent(getApplicationContext(), ShowCalendar.class);
				//Intent i = new Intent(this, TestDatabaseActivity.class);
				//startActivity(i); 
				finish();
			}
		});
		
		add_dialog.show();
	}
}
