package com.example.testapp.sense;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import com.ubhave.sensormanager.ESException;
import com.ubhave.sensormanager.ESSensorManager;
import com.ubhave.sensormanager.SensorDataListener;
import com.ubhave.sensormanager.data.SensorData;

public abstract class AbstractService extends Service implements SensorDataListener
{
	private int subscriptionId;
	private ESSensorManager sensorManager;

	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}
	
	protected abstract String getLogTag();

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		Log.d(getLogTag(), "onStartCommand");
		return super.onStartCommand(intent, flags, startId);
	}
	
	protected abstract void sense();

	@Override
	public void onCreate()
	{
		super.onCreate();
		Log.d(getLogTag(), "onCreate");
		try
		{
			sensorManager = ESSensorManager.getSensorManager(this);
			sense();
		}
		catch (ESException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		Log.d(getLogTag(), "destroying");
	}

	@Override
	public void onDataSensed(SensorData data)
	{
		Log.d(getLogTag(), "onDataSensed");
	}

	@Override
	public void onCrossingLowBatteryThreshold(boolean isBelowThreshold)
	{
		Log.d(getLogTag(), "onCrossingLowBatteryThreshold");
	}
	
	protected void subscribe(final int sensorType)
	{
		try
		{
			this.subscriptionId = sensorManager.subscribeToSensorData(sensorType, this);
			Log.d(getLogTag(), "Subscribed with: "+subscriptionId);
		}
		catch (ESException e)
		{
			e.printStackTrace();
		}
	}
	
	protected void unsubscribe()
	{
		try
		{
			Log.d(getLogTag(), "Unsubscribe from: "+subscriptionId);
			sensorManager.unsubscribeFromSensorData(subscriptionId);
		}
		catch (ESException e)
		{
			e.printStackTrace();
		}
	}
	
	protected abstract int getSensorType();
	
	protected void senseWithParameters(final long initialWait, final int numberOfLoops)
	{
		new AsyncTask<Void, Void, Void>()
		{
			@Override
			protected Void doInBackground(Void... params)
			{
				try
				{
					if (initialWait > 0)
					{
						Log.d(getLogTag(), "Sleeping before starting.");
						Thread.sleep(initialWait);
					}
					
					Log.d(getLogTag(), "Starting");
					for (int i = 0; i < numberOfLoops; i++)
					{
						Log.d(getLogTag(), "Subscribing for 20 seconds ("+(i+1)+"/"+numberOfLoops+").");
						subscribe(getSensorType());
						long waited = 0;
						while (waited < 20000L)
						{
							Thread.sleep(100);
							waited += 100;
						}
						unsubscribe();
						Thread.sleep(1000L);
					}
					
					Thread.sleep(5000L);
					Log.d(getLogTag(), "Stopping");
					AbstractService.this.stopSelf();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				return null;
			}
		}.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	}
}
