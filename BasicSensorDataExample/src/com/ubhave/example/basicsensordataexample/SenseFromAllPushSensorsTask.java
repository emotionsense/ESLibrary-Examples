package com.ubhave.example.basicsensordataexample;

import android.content.Context;
import android.util.Log;

import com.ubhave.sensormanager.ESException;
import com.ubhave.sensormanager.SensorDataListener;
import com.ubhave.sensormanager.sensors.SensorEnum;
import com.ubhave.sensormanager.sensors.SensorUtils;

public class SenseFromAllPushSensorsTask extends SubscribeTask implements SensorDataListener
{
	private final static String LOG_TAG = "SenseFromAllPushSensorsTask";
	
	public SenseFromAllPushSensorsTask(Context context)
	{
		super(context);
	}
	
	@Override
	protected void subscribe() throws ESException
	{
		for (SensorEnum s : SensorEnum.values())
		{
			if (s.isPush())
			{
				Log.d(LOG_TAG, "Subscribe to: " + SensorUtils.getSensorName(s.getType()));
				int subscriptionId = sensorManager.subscribeToSensorData(s.getType(), SenseFromAllPushSensorsTask.this);
				subscriptions.put(s.getType(), subscriptionId);
			}
		}
	}
}
