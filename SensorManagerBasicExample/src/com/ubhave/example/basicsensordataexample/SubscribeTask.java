package com.ubhave.example.basicsensordataexample;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.SparseIntArray;

import com.ubhave.sensormanager.ESException;
import com.ubhave.sensormanager.ESSensorManager;
import com.ubhave.sensormanager.SensorDataListener;
import com.ubhave.sensormanager.config.GlobalConfig;
import com.ubhave.sensormanager.data.SensorData;
import com.ubhave.sensormanager.sensors.SensorUtils;

public abstract class SubscribeTask extends AsyncTask<Void, Void, Void> implements SensorDataListener
{
	private final static String LOG_TAG = "SubscribeTask";
	
	private final Context context;
	protected final SparseIntArray subscriptions;
	protected ESSensorManager sensorManager;

	public SubscribeTask(final Context context)
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
			e.printStackTrace();
		}
	}
	
	protected abstract void subscribe() throws ESException;
	
	private void unsubscribe() throws ESException
	{
		for (int i=0; i<subscriptions.size(); i++)
		{
			int sensorType = subscriptions.keyAt(i);
			int subscriptionId = subscriptions.get(sensorType);
			sensorManager.unsubscribeFromSensorData(subscriptionId);
		}
	}
	
	private void waitForData()
	{
		if (subscriptions.size() > 0)
		{
			long waited = 0;
			while (waited < 60000L)
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
		}
	}

	@Override
	protected Void doInBackground(Void... params)
	{
		try
		{
			Log.d(LOG_TAG, " === Starting " + LOG_TAG + " ===");
			subscribe();

			Log.d(LOG_TAG, " === Waiting for 60 seconds ===");
			waitForData();
			
			Log.d(LOG_TAG, " === Finished " + LOG_TAG + " ===");
			unsubscribe();
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
			Log.d(LOG_TAG, "Received from: " + SensorUtils.getSensorName(sensorType));
			// To format/store your data, check out the SensorDataManager library
			
//			JSONFormatter formatter = DataFormatter.getJSONFormatter(context, data.getSensorType());
//			Log.d(LOG_TAG, formatter.toJSON(data).toString());
		}
		catch (ESException e)
		{
			e.printStackTrace();
		}
//		catch (DataHandlerException e)
//		{
//			e.printStackTrace();
//		}
	}

	@Override
	public void onCrossingLowBatteryThreshold(boolean isBelowThreshold)
	{
		// Nothing in this example
	}
}
