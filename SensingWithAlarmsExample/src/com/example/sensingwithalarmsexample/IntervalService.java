package com.example.sensingwithalarmsexample;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.ubhave.sensormanager.ESException;
import com.ubhave.sensormanager.ESSensorManager;
import com.ubhave.sensormanager.sensors.SensorUtils;

public class IntervalService extends Service
{
	private final static String LOG_TAG = "IntervalService";
	private final static int sensorType = SensorUtils.SENSOR_TYPE_ACCELEROMETER;
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		Log.d(LOG_TAG, "onCreate()");
		senseOnceAndStop();
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		Log.d(LOG_TAG, "onDestroy()");
	}

	private void senseOnceAndStop()
	{
		try
		{
			ESSensorManager sensorManager = ESSensorManager.getSensorManager(this);
			sensorManager.getDataFromSensor(sensorType);
			Log.d(LOG_TAG, "Sensed from: "+SensorUtils.getSensorName(sensorType));
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
