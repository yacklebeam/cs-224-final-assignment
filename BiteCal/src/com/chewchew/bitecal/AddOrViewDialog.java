package com.chewchew.bitecal;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class AddOrViewDialog extends DialogFragment{
	
	public Dialog onCreateDialog(Bundle savedInstanceState){
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setItems(R.array.add_or_view_array, new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int index){
			
				if(index == 0){
					try {
						viewClick();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else{
					addClick();
				}
			}
		});
		
		return builder.create();
		
	}

	private void viewClick() throws IOException{

		final Dialog view_dialog = new Dialog(this.getActivity(), android.R.style.Theme_NoTitleBar_Fullscreen);
		ArrayList<Food> bfastList = null;
		int bfastCounter = 0;
		ArrayList<Food> lunchList = null;
		int lunchCounter = 0;
		ArrayList<Food> dinnerList = null;
		int dinnerCounter = 0;
		ArrayList<Food> otherList = null;
		int otherCounter = 0;
		view_dialog.setContentView(R.layout.viewday);
		TableLayout td = (TableLayout)view_dialog.findViewById(R.id.tableLayout);
		
		if(AddActivity.BreakfastArray[ShowCalendar.dateTime.getMonthOfYear()][ShowCalendar.dateTime.getDayOfMonth()] != null){
			bfastList = AddActivity.BreakfastArray[ShowCalendar.dateTime.getMonthOfYear()][ShowCalendar.dateTime.getDayOfMonth()].getFoods();
			TextView h = new TextView(this.getActivity());
			h.setText("\t\tBREAKFAST");
			TableRow tr1 = new TableRow(this.getActivity());
			tr1.setPadding(0, 1, 0, 1);
			tr1.addView(h);
			td.addView(tr1);
			for(int z = 0; z < bfastList.size(); z++){
				if(z >= bfastCounter){
				Food food = bfastList.get(z);
				TextView t = new TextView(this.getActivity());
				t.setText(food.getFoodName() + "\t\t" + food.getServings() + "\t\t" + food.getFoodCalories());
				t.setTextColor(Color.BLACK);
		        t.setBackgroundColor(Color.WHITE);
		        t.setPadding(5, 0, 0, 0);
		        TableRow tr = new TableRow(this.getActivity());
		        tr.setPadding(0, 1, 0, 1); 
		        tr.setBackgroundColor(Color.BLACK);
		        tr.addView(t);
		        td.addView(tr);
		        //System.out.println(food.getFoodName());
		        bfastCounter++;
				}
			}
		}
		if(AddActivity.LunchArray[ShowCalendar.dateTime.getMonthOfYear()][ShowCalendar.dateTime.getDayOfMonth()] != null){
			lunchList = AddActivity.LunchArray[ShowCalendar.dateTime.getMonthOfYear()][ShowCalendar.dateTime.getDayOfMonth()].getFoods();
			TextView h = new TextView(this.getActivity());
			h.setText("\t\tLUNCH");
			TableRow tr1 = new TableRow(this.getActivity());
			tr1.setPadding(0, 1, 0, 1);
			tr1.addView(h);
			td.addView(tr1);
			for(int z = 0; z < lunchList.size(); z++){
				if(z >= lunchCounter){
				Food food = lunchList.get(z);
				TextView t = new TextView(this.getActivity());
				t.setText(food.getFoodName() + "\t\t" + food.getServings() + "\t\t" + food.getFoodCalories());
				t.setTextColor(Color.BLACK);
		        t.setBackgroundColor(Color.WHITE);
		        t.setPadding(5, 0, 0, 0);
		        TableRow tr = new TableRow(this.getActivity());
		        tr.setPadding(0, 1, 0, 1); 
		        tr.setBackgroundColor(Color.BLACK);
		        tr.addView(t);
		        td.addView(tr);
		        //System.out.println(food.getFoodName());
		        lunchCounter++;
				}
			}
		}
		if(AddActivity.DinnerArray[ShowCalendar.dateTime.getMonthOfYear()][ShowCalendar.dateTime.getDayOfMonth()] != null){
			dinnerList = AddActivity.DinnerArray[ShowCalendar.dateTime.getMonthOfYear()][ShowCalendar.dateTime.getDayOfMonth()].getFoods();
			TextView h = new TextView(this.getActivity());
			h.setText("\t\tDINNER");
			TableRow tr1 = new TableRow(this.getActivity());
			tr1.setPadding(0, 1, 0, 1);
			tr1.addView(h);
			td.addView(tr1);
			for(int z = 0; z < dinnerList.size(); z++){
				if(z >= dinnerCounter){
				Food food = dinnerList.get(z);
				TextView t = new TextView(this.getActivity());
				t.setText(food.getFoodName() + "\t\t" + food.getServings() + "\t\t" + food.getFoodCalories());
				t.setTextColor(Color.BLACK);
		        t.setBackgroundColor(Color.WHITE);
		        t.setPadding(5, 0, 0, 0);
		        TableRow tr = new TableRow(this.getActivity());
		        tr.setPadding(0, 1, 0, 1); 
		        tr.setBackgroundColor(Color.BLACK);
		        tr.addView(t);
		        td.addView(tr);
		        //System.out.println(food.getFoodName());
		        dinnerCounter++;
				}
			}
		}
		if(AddActivity.OtherArray[ShowCalendar.dateTime.getMonthOfYear()][ShowCalendar.dateTime.getDayOfMonth()] != null){
			otherList = AddActivity.OtherArray[ShowCalendar.dateTime.getMonthOfYear()][ShowCalendar.dateTime.getDayOfMonth()].getFoods();
			TextView h = new TextView(this.getActivity());
			h.setText("\t\tOTHER");
			TableRow tr1 = new TableRow(this.getActivity());
			tr1.setPadding(0, 1, 0, 1);
			tr1.addView(h);
			td.addView(tr1);
			for(int z = 0; z < otherList.size(); z++){
				if(z >= otherCounter){
				Food food = otherList.get(z);
				TextView t = new TextView(this.getActivity());
				t.setText(food.getFoodName() + "\t\t" + food.getServings() + "\t\t" + food.getFoodCalories());
				t.setTextColor(Color.BLACK);
		        t.setBackgroundColor(Color.WHITE);
		        t.setPadding(5, 0, 0, 0);
		        TableRow tr = new TableRow(this.getActivity());
		        tr.setPadding(0, 1, 0, 1); 
		        tr.setBackgroundColor(Color.BLACK);
		        tr.addView(t);
		        td.addView(tr);
		        //System.out.println(food.getFoodName());
		        otherCounter++;
				}
			}
		}
		
		view_dialog.show();
	}
	
	private void addClick(){
		MealSelectDialog md = new MealSelectDialog();
		md.show(getFragmentManager(), "meal");
	}

}

