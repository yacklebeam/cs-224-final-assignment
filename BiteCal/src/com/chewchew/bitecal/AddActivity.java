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
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends Activity {
	
	private int meal_number;
	public final Meal[][] BreakfastArray = new Meal[12][31];
	public final Meal[][] LunchArray = new Meal[12][31];
	public final Meal[][] DinnerArray = new Meal[12][31];
	public final Meal[][] OtherArray = new Meal[12][31];
	private DateTime date = ShowCalendar.dateTime;
	
	private String bfast_file = "BiteCal_Breakfast_Info.txt";
	private String lunch_file = "BiteCal_Lunch_Info.txt";
	private String dinner_file = "BiteCal_Dinner_Info.txt";
	private String other_file = "BiteCal_Other_Info.txt";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
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
				BreakfastArray[date.getMonthOfYear()][date.getDayOfMonth()] = meal;
			}
			else{
				BreakfastArray[date.getMonthOfYear()][date.getDayOfMonth()].addFood(food);
			}
		}
		else if(mealNumber == 1){
			//LunchArray[date.getMonthOfYear()][date.getDayOfMonth()] = meal;
		}
		else if(mealNumber == 2){
			//DinnerArray[date.getMonthOfYear()][date.getDayOfMonth()] = meal;
		}
		else{
			//OtherArray[date.getMonthOfYear()][date.getDayOfMonth()] = meal;
		}
	}
	
	private void writeInfoToFile(int mealNumber) throws IOException{
		if(mealNumber == 0){
			BufferedWriter bfastwriter = new BufferedWriter(new FileWriter(new File(getFilesDir() + File.separator + bfast_file)));
			for(int i = 0; i < 12; i++){
				for(int j = 0; j < 31; j++){
					if(BreakfastArray[i][j] != null){
						String month = Integer.toString(i);
						String day = Integer.toString(j);
						String meal = BreakfastArray[i][j].toString();
						bfastwriter.write("*" + month + ";" + day + ";" + meal);
						System.out.println("*" + month + ";" + day + ";" + meal);
					}
				}
			}
			bfastwriter.close();
		}
		
		else if(mealNumber == 1){
			BufferedWriter lunchwriter = new BufferedWriter(new FileWriter(new File(getFilesDir() + File.separator + lunch_file)));
			for(int i = 0; i < 12; i++){
				for(int j = 0; j < 31; j++){
					if(LunchArray[i][j] != null){
						String month = Integer.toString(i);
						String day = Integer.toString(j);
						String meal = LunchArray[i][j].toString();
						lunchwriter.write(month + "," + day + "," + meal + "\n");
					}
				}
			}
			lunchwriter.close();
		}
		
		else if(mealNumber == 2){
			BufferedWriter dinnerwriter = new BufferedWriter(new FileWriter(new File(getFilesDir() + File.separator + dinner_file)));
			for(int i = 0; i < 12; i++){
				for(int j = 0; j < 31; j++){
					if(DinnerArray[i][j] != null){
						String month = Integer.toString(i);
						String day = Integer.toString(j);
						String meal = DinnerArray[i][j].toString();
						dinnerwriter.write(month + "," + day + "," + meal + "\n");
					}
				}
			}
			dinnerwriter.close();
		}
		
		else if(mealNumber == 3){
			BufferedWriter otherwriter = new BufferedWriter(new FileWriter(new File(getFilesDir() + File.separator + other_file)));
			for(int i = 0; i < 12; i++){
				for(int j = 0; j < 31; j++){
					if(OtherArray[i][j] != null){
						String month = Integer.toString(i);
						String day = Integer.toString(j);
						String meal = OtherArray[i][j].toString();
						otherwriter.write(month + "," + day + "," + meal + "\n");
					}
				}
			}
			otherwriter.close();
		}
	}
	
	private void populateArrays() throws IOException{
		BufferedReader bufferedReader = new BufferedReader(new FileReader(getFilesDir() + File.separator + bfast_file));	
		String read;
		StringTokenizer splitter;
		String nextToken;
		int month = 0;
		int day = 0;
		double serv;
		while((read = bufferedReader.readLine()) != null){
			System.out.println(read);
			splitter = new StringTokenizer(read, ";");
			nextToken = splitter.nextToken();
			while(nextToken != null){
				if(nextToken == "*"){
					nextToken = splitter.nextToken();
					month = Integer.parseInt(nextToken);
					nextToken = splitter.nextToken();
					day = Integer.parseInt(nextToken);
					nextToken = splitter.nextToken();
				}
				serv = Double.parseDouble(splitter.nextToken());
				Food food = new Food(nextToken, serv, 1);
				if(BreakfastArray[month][day] == null){
					Meal meal = new Meal();
					meal.addFood(food);
					BreakfastArray[month][day] = meal;
				}
				else{
					BreakfastArray[month][day].addFood(food);
				}
				if(splitter.hasMoreTokens()){
					nextToken = splitter.nextToken();
				}
				else{
					nextToken = null;
				}
			}
		}
		bufferedReader.close();
	}
}
