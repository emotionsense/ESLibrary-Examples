package com.ubhave.example.sensordatamanager;

import android.content.Context;

import com.ubhave.datahandler.config.DataTransferConfig;
import com.ubhave.datahandler.loggertypes.AbstractDataLogger;
import com.ubhave.example.sensordatamanager.store.ExampleStoreOnlyLogger;
import com.ubhave.sensormanager.ESException;
import com.ubhave.sensormanager.ESSensorManager;
import com.ubhave.sensormanager.SensorDataListener;
import com.ubhave.sensormanager.config.sensors.pull.PullSensorConfig;
import com.ubhave.sensormanager.data.SensorData;
import com.ubhave.sensormanager.sensors.SensorUtils;

public class ExampleAccelerometerSensorListener implements SensorDataListener
{
	private ESSensorManager sensorManager;
	private AbstractDataLogger dataLogger;
	private int subscriptionId;
	
	public ExampleAccelerometerSensorListener(final Context context, final int dataTransferPolicy)
	{
		try
		{
			sensorManager = ESSensorManager.getSensorManager(context);
			setConfig();
			if (dataTransferPolicy == DataTransferConfig.STORE_ONLY)
			{
				dataLogger = new ExampleStoreOnlyLogger(context);
			}
		}
		catch (ESException e)
		{
			sensorManager = null;
			e.printStackTrace();
		}
	}
	
	private void setConfig()
	{
		try
		{
			sensorManager.setSensorConfig(SensorUtils.SENSOR_TYPE_ACCELEROMETER, PullSensorConfig.SENSE_WINDOW_LENGTH_MILLIS, 2000L);
			sensorManager.setSensorConfig(SensorUtils.SENSOR_TYPE_ACCELEROMETER, PullSensorConfig.POST_SENSE_SLEEP_LENGTH_MILLIS, 2000L);
		}
		catch (ESException e)
		{
			e.printStackTrace();
		}
	}
	
	public boolean startSensing()
	{
		try
		{
			subscriptionId = sensorManager.subscribeToSensorData(SensorUtils.SENSOR_TYPE_ACCELEROMETER, this);
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean stopSensing()
	{
		try
		{
			sensorManager.unsubscribeFromSensorData(subscriptionId);
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void onDataSensed(SensorData data)
	{
		dataLogger.logSensorData(data);
	}

	@Override
	public void onCrossingLowBatteryThreshold(boolean isBelowThreshold)
	{
		// Nothing for example app
	}
}
