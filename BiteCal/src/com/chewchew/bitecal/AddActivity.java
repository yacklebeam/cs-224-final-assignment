package com.chewchew.bitecal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

import org.joda.time.DateTime;

import android.os.Bundle;
import android.app.Activity;
import android.database.SQLException;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends Activity {
	
	private int meal_number;
	public final Meal[][] BreakfastArray = new Meal[13][32];
	public final Meal[][] LunchArray = new Meal[13][32];
	public final Meal[][] DinnerArray = new Meal[13][32];
	public final Meal[][] OtherArray = new Meal[13][32];
	private DateTime date = ShowCalendar.dateTime;
	
	public DatabaseHandler db_handler;
	
	private String bfast_file = "BiteCal_Breakfast_Info.txt";
	private String lunch_file = "BiteCal_Lunch_Info.txt";
	private String dinner_file = "BiteCal_Dinner_Info.txt";
	private String other_file = "BiteCal_Other_Info.txt";

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
				Food food = new Food(text, 1, 10);
				addFoodToArray(meal_number, food);
				finish();
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
			BufferedWriter bfastwriter = new BufferedWriter(new FileWriter(new File(getFilesDir() + File.separator + bfast_file)));
			for(int i = 0; i < 13; i++){
				for(int j = 0; j < 32; j++){
					if(BreakfastArray[i][j] != null){
						System.out.print("Index: " + i + ", " + j + "  ");
						String month = Integer.toString(i);
						String day = Integer.toString(j);
						String meal = BreakfastArray[i][j].toString();
						bfastwriter.write("*;" + month + ";" + day + ";" + meal);
						System.out.println("WRITE: *;" + month + ";" + day + ";" + meal);
					}
				}
			}
			bfastwriter.close();
		}
		
		else if(mealNumber == 1){
			BufferedWriter lunchwriter = new BufferedWriter(new FileWriter(new File(getFilesDir() + File.separator + lunch_file)));
			for(int i = 0; i < 13; i++){
				for(int j = 0; j < 32; j++){
					if(LunchArray[i][j] != null){
						System.out.print("Index: " + i + ", " + j + "  ");
						String month = Integer.toString(i);
						String day = Integer.toString(j);
						String meal = LunchArray[i][j].toString();
						lunchwriter.write("*;" + month + ";" + day + ";" + meal);
						System.out.println("WRITE: *;" + month + ";" + day + ";" + meal);
					}
				}
			}
			lunchwriter.close();
		}
		
		else if(mealNumber == 2){
			BufferedWriter dinnerwriter = new BufferedWriter(new FileWriter(new File(getFilesDir() + File.separator + dinner_file)));
			for(int i = 0; i < 13; i++){
				for(int j = 0; j < 32; j++){
					if(DinnerArray[i][j] != null){
						System.out.print("Index: " + i + ", " + j + "  ");
						String month = Integer.toString(i);
						String day = Integer.toString(j);
						String meal = DinnerArray[i][j].toString();
						dinnerwriter.write("*;" + month + ";" + day + ";" + meal);
						System.out.println("WRITE: *;" + month + ";" + day + ";" + meal);
					}
				}
			}
			dinnerwriter.close();
		}
		
		else if(mealNumber == 3){
			BufferedWriter otherwriter = new BufferedWriter(new FileWriter(new File(getFilesDir() + File.separator + other_file)));
			for(int i = 0; i < 13; i++){
				for(int j = 0; j < 32; j++){
					if(OtherArray[i][j] != null){
						System.out.print("Index: " + i + ", " + j + "  ");
						String month = Integer.toString(i);
						String day = Integer.toString(j);
						String meal = OtherArray[i][j].toString();
						otherwriter.write("*;" + month + ";" + day + ";" + meal);
						System.out.println("WRITE: *;" + month + ";" + day + ";" + meal);
					}
				}
			}
			otherwriter.close();
		}
	}
	
	private void populateArrays() throws IOException{
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
}
