package com.chewchew.bitecal;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

@SuppressLint("NewApi")
public class MealSelectDialog extends DialogFragment{
	
	private int mealNumber;
	Activity addActiv;
	
	public Dialog onCreateDialog(Bundle savedInstanceState){
		
		addActiv = getActivity();
		AlertDialog.Builder builder = new AlertDialog.Builder(addActiv);
		builder.setTitle(R.string.pick_meal);
		builder.setItems(R.array.meal_select_array, new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int index){
			
				if(index == 0){
					breakfastClick();
				}
				else if (index == 1){
					lunchClick();
				}
				else if (index == 2){
					dinnerClick();
				}
				else{
					otherClick();
				}
			}
		});
		
		return builder.create();
		
	}

	public void breakfastClick(){
		mealNumber = 0;
		Intent intent = new Intent(addActiv, AddActivity.class);
		intent.putExtra("meal_type", mealNumber);
		addActiv.startActivity(intent);
		
	}
	
	public void lunchClick(){
		mealNumber = 1;
		Intent intent = new Intent(addActiv, AddActivity.class);
		intent.putExtra("meal_type", mealNumber);
		addActiv.startActivity(intent);
	}
	
	public void dinnerClick(){
		mealNumber = 2;
		Intent intent = new Intent(addActiv, AddActivity.class);
		intent.putExtra("meal_type", mealNumber);
		addActiv.startActivity(intent);
	}
	
	public void otherClick(){
		mealNumber = 3;
		Intent intent = new Intent(addActiv, AddActivity.class);
		intent.putExtra("meal_type", mealNumber);
		addActiv.startActivity(intent);
	}
	
}
