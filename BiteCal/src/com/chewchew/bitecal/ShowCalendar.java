package com.chewchew.bitecal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.caldroid.CaldroidListener;
import com.caldroid.CaldroidFragment;
import com.caldroid.CaldroidListener;
import com.caldroid.CalendarHelper;

public class ShowCalendar extends FragmentActivity {
	
	public final String[][] NoteArray = new String[12][31];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_calendar);
		
		final CaldroidFragment caldroidFragment = new CaldroidFragment();

		// This is to show customized fragment
		// **** If you want customized version, uncomment below line ****
//		final CaldroidSampleCustomFragment caldroidFragment = new CaldroidSampleCustomFragment();
		
		// Setup arguments
		CaldroidListener listener = new CaldroidListener()
		{
			@Override
			public void onSelectDate(Date date, View view)
			{
				showNote(CalendarHelper.convertDateToDateTime(date));
			}
		};
		Bundle args = new Bundle();
		Calendar cal = Calendar.getInstance();
		args.putInt("month", cal.get(Calendar.MONTH) + 1);
		args.putInt("year", cal.get(Calendar.YEAR));
		args.putBoolean("enableSwipe", true);
		args.putBoolean("fitAllMonths", false);
		caldroidFragment.setCaldroidListener(listener);
		
		// Comment this to customize startDayOfWeek
//		args.putInt("startDayOfWeek", 6); // Saturday
		caldroidFragment.setArguments(args);

		FragmentTransaction t = getSupportFragmentManager().beginTransaction();
		t.replace(R.id.calendar1, caldroidFragment);
		t.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_calendar, menu);
		return true;
	}
	
	public void showNote(final DateTime date)
	{
		final Dialog d = new Dialog(this);
		d.setContentView(R.layout.activity_note);
		d.setTitle("Note");
		d.setCancelable(true);
		Button b = (Button)d.findViewById(R.id.button1);
		d.show();
		b.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				EditText editor = (EditText)d.findViewById(R.id.editText2);
				String text = editor.getText().toString();
				NoteArray[date.getMonthOfYear()][date.getDayOfMonth()] = text;
				d.dismiss();
			}
		});
	}

}
