package com.ubhave.example.basicsensordataexample;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.SparseIntArray;

import com.ubhave.dataformatter.DataFormatter;
import com.ubhave.dataformatter.json.JSONFormatter;
import com.ubhave.sensormanager.ESException;
import com.ubhave.sensormanager.ESSensorManager;
import com.ubhave.sensormanager.SensorDataListener;
import com.ubhave.sensormanager.config.GlobalConfig;
import com.ubhave.sensormanager.data.SensorData;
import com.ubhave.sensormanager.sensors.SensorUtils;

public class SenseFromAllPushSensorsTask extends AsyncTask<Void, Void, Void> implements SensorDataListener
{
	private final static String LOG_TAG = "SenseFromAllPushSensorsTask";
	private final SparseIntArray subscriptions;
	private final Context context;
	private ESSensorManager sensorManager;
	
	public SenseFromAllPushSensorsTask(final Context context)
	{
		this.context = context;
		subscriptions = new SparseIntArray();
		try
		{
			sensorManager = ESSensorManager.getSensorManager(context);
			sensorManager.setGlobalConfig(GlobalConfig.PRINT_LOG_D_MESSAGES, false);
		}
		catch (ESException e)
		{
			
		}
	}

	@Override
	protected Void doInBackground(Void... params)
	{
		try
		{
			Log.d(LOG_TAG, " === Starting "+LOG_TAG+" ===");
			for (int sensorType : SensorUtils.ALL_SENSORS)
			{
				if (SensorUtils.isPushSensor(sensorType))
				{
					Log.d(LOG_TAG, "Subscribe to: " + SensorUtils.getSensorName(sensorType));
					int subscriptionId = sensorManager.subscribeToSensorData(sensorType, SenseFromAllPushSensorsTask.this);
					subscriptions.put(sensorType, subscriptionId);
				}
			}
			
			Log.d(LOG_TAG, " === Waiting for 30 seconds ===");
			long waited = 0;
			while (waited < 30000L)
			{
				try
				{
					Thread.sleep(10);
					waited += 10;
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				
			}
			Log.d(LOG_TAG, " === Finished "+LOG_TAG+" ===");
		}
		catch (ESException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void onDataSensed(SensorData data)
	{
		try
		{
			int sensorType = data.getSensorType();
			JSONFormatter dataFormatter = DataFormatter.getJSONFormatter(context, sensorType);
			JSONObject jsonData = dataFormatter.toJSON(data);
			Log.d(LOG_TAG, "Sensor Time Stamp: " + jsonData.getString("senseStartTime"));
			Log.d(LOG_TAG, "Sensor Data: " + jsonData.toString());
			
			int subscriptionId = subscriptions.get(sensorType);
			sensorManager.unsubscribeFromSensorData(subscriptionId);
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		catch (ESException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onCrossingLowBatteryThreshold(boolean isBelowThreshold)
	{
		// Nothing in this example	
	}
}
