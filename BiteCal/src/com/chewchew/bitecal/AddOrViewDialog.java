package com.chewchew.bitecal;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

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
		FileInputStream fis = new FileInputStream("BiteCal_BreakfastArray_Info");
		InputStreamReader isr = new InputStreamReader(fis);
		while(isr.ready()){
			System.out.println(isr.read());
		}
		
	}
	
	private void addClick(){
		MealSelectDialog md = new MealSelectDialog();
		md.show(getFragmentManager(), "meal");
	}

}
