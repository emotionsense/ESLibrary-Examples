package com.ubhave.example.subscribeapi19;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.ubhave.dataformatter.DataFormatter;
import com.ubhave.dataformatter.json.JSONFormatter;
import com.ubhave.datahandler.except.DataHandlerException;
import com.ubhave.sensormanager.ESException;
import com.ubhave.sensormanager.ESSensorManager;
import com.ubhave.sensormanager.SensorDataListener;
import com.ubhave.sensormanager.config.pull.LocationConfig;
import com.ubhave.sensormanager.config.pull.PullSensorConfig;
import com.ubhave.sensormanager.data.SensorData;
import com.ubhave.sensormanager.sensors.SensorUtils;

public class MainActivity extends Activity implements SensorDataListener
{
	private final static String LOG_TAG = "MainActivity";
	private ESSensorManager sensorManager;
	private JSONFormatter dataFormatter;
	private int subscriptionId;
	private boolean isSensing;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		try
		{
			sensorManager = ESSensorManager.getSensorManager(this);
			sensorManager.setSensorConfig(SensorUtils.SENSOR_TYPE_LOCATION, LocationConfig.ACCURACY_TYPE, LocationConfig.LOCATION_ACCURACY_FINE);
			sensorManager.setSensorConfig(SensorUtils.SENSOR_TYPE_LOCATION, PullSensorConfig.SENSE_WINDOW_LENGTH_MILLIS, 10000L);
			sensorManager.setSensorConfig(SensorUtils.SENSOR_TYPE_LOCATION, PullSensorConfig.POST_SENSE_SLEEP_LENGTH_MILLIS, 3000L);
			dataFormatter = DataFormatter.getJSONFormatter(this, SensorUtils.SENSOR_TYPE_LOCATION);
			isSensing = false;
		}
		catch (ESException e)
		{
			Log.d(LOG_TAG, e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		if (!isSensing && sensorManager != null)
		{
			try
			{
				subscriptionId = sensorManager.subscribeToSensorData(SensorUtils.SENSOR_TYPE_LOCATION, this);
				isSensing = true;
			}
			catch (ESException e)
			{
				Log.d(LOG_TAG, e.getMessage());
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		if (isSensing && sensorManager != null)
		{
			try
			{
				sensorManager.unsubscribeFromSensorData(subscriptionId);
				isSensing = false;
			}
			catch (ESException e)
			{
				Log.d(LOG_TAG, e.getMessage());
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onDataSensed(SensorData data)
	{
		try
		{
			Log.d(LOG_TAG, dataFormatter.toJSON(data).toString());
		}
		catch (DataHandlerException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onCrossingLowBatteryThreshold(boolean isBelowThreshold)
	{
		Log.d(LOG_TAG, "onCrossingLowBatteryThreshold: "+isBelowThreshold);
	}
}
