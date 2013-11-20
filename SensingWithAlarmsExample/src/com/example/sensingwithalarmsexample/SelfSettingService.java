package com.example.sensingwithalarmsexample;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.ubhave.sensormanager.ESException;
import com.ubhave.sensormanager.ESSensorManager;
import com.ubhave.sensormanager.sensors.SensorUtils;

public class SelfSettingService extends Service
{
	private final int intentCode = 2;
	private final long serviceInterval = 45 * 1000L;
	private final static String LOG_TAG = "SelfSettingService";
	private final static int sensorType = SensorUtils.SENSOR_TYPE_ACCELEROMETER;
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		Log.d(LOG_TAG, "onCreate()");
		senseOnceAndSetAlarm();
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		Log.d(LOG_TAG, "onDestroy()");
	}

	private void senseOnceAndSetAlarm()
	{
		try
		{
			ESSensorManager sensorManager = ESSensorManager.getSensorManager(this);
			sensorManager.getDataFromSensor(sensorType);
			Log.d(LOG_TAG, "Sensed from: "+SensorUtils.getSensorName(sensorType));
			
			Intent intent = new Intent(getApplicationContext(), SelfSettingService.class);
			AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
			PendingIntent operation = PendingIntent.getService(getApplicationContext(), intentCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			
			alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+serviceInterval, operation);
			stopSelf();
		}
		catch (ESException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public IBinder onBind(Intent arg0)
	{
		return null;
	}

}
