package com.chewchew.bitecal;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class StartScreenActivity extends Activity 
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_screen);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start_screen, menu);
		return true;
	}

	public void startClick(View v)
	{
		Intent i = new Intent(this, ShowCalendar.class);
		//Intent i = new Intent(this, TestDatabaseActivity.class);
		startActivity(i); 
	}
	
	public void settingClicked(View v)
	{
		showSettingsButton();
	}
	
	private void showSettingsButton() {
		final Dialog start_dialog = new Dialog(this, android.R.style.Theme_NoTitleBar_Fullscreen); 
		start_dialog.setContentView(R.layout.settings);
		start_dialog.setTitle("Settings");
		start_dialog.setCancelable(true);
		Button begin_bt = (Button)start_dialog.findViewById(R.id.closeSettings);
		start_dialog.show();
		begin_bt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick (View v) {
				start_dialog.dismiss();
			}
		});
	}	
		
	public void aboutClicked(View v)
	{
		showAboutButton();
	}
	
	private void showAboutButton() {
		final Dialog start_dialog = new Dialog(this, android.R.style.Theme_NoTitleBar_Fullscreen); 
		start_dialog.setContentView(R.layout.about_us);
		start_dialog.setTitle("About Us");
		start_dialog.setCancelable(true);
		Button begin_bt = (Button)start_dialog.findViewById(R.id.closeAbout);
		start_dialog.show();
		begin_bt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick (View v) {
				start_dialog.dismiss();
			}
		});
	}
}

