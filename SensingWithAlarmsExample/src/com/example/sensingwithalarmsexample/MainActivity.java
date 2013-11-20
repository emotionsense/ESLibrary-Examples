package com.example.sensingwithalarmsexample;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity
{
	private final long serviceInterval = 60 * 1000L;
	private final int intentCode = 1;
	private AlarmManager alarmManager;
	private PendingIntent operation;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Intent intent = new Intent(getApplicationContext(), IntervalService.class);
		operation = PendingIntent.getService(getApplicationContext(), intentCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		onStopAlarm(null); // in case it is already running
	}
	
	public void onStopAlarm(final View view)
	{
		alarmManager.cancel(operation);
		
		Intent intent = new Intent(getApplicationContext(), SelfSettingService.class);
		alarmManager.cancel(PendingIntent.getService(getApplicationContext(), intentCode, intent, PendingIntent.FLAG_UPDATE_CURRENT));
		
		Toast.makeText(this, "Alarms stopped.", Toast.LENGTH_LONG).show();
	}
	
	public void onStartAlarm(final View view)
	{
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), serviceInterval, operation);
		
		startService(new Intent(getApplicationContext(), SelfSettingService.class));
		
		Toast.makeText(this, "Alarms started.", Toast.LENGTH_LONG).show();
	}

}
