package com.ubhave.example.basicsensordataexample;

import android.content.Context;
import android.util.Log;

import com.ubhave.sensormanager.ESException;
import com.ubhave.sensormanager.sensors.SensorEnum;
import com.ubhave.sensormanager.sensors.SensorUtils;

public class SenseFromAllEnvSensorsTask extends SubscribeTask
{
	private final static String LOG_TAG = "SenseFromAllEnvSensorsTask";

	public SenseFromAllEnvSensorsTask(final Context context)
	{
		super(context);
	}

	@Override
	protected void subscribe() throws ESException
	{
		for (SensorEnum s : SensorEnum.values())
		{
			if (s.isEnvironment())
			{
				Log.d(LOG_TAG, "Subscribe to: " + SensorUtils.getSensorName(s.getType()));
				int subscriptionId = sensorManager.subscribeToSensorData(s.getType(), SenseFromAllEnvSensorsTask.this);
				subscriptions.put(s.getType(), subscriptionId);
			}
		}
	}
}
