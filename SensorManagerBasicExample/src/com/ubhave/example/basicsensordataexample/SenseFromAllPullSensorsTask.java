package com.ubhave.example.basicsensordataexample;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ubhave.sensormanager.ESException;
import com.ubhave.sensormanager.ESSensorManager;
import com.ubhave.sensormanager.config.GlobalConfig;
import com.ubhave.sensormanager.data.SensorData;
import com.ubhave.sensormanager.sensors.SensorEnum;
import com.ubhave.sensormanager.sensors.SensorUtils;

public class SenseFromAllPullSensorsTask extends AsyncTask<Void, Void, Void>
{
	private final static String LOG_TAG = "SenseFromAllPullSensorsTask";
	private ESSensorManager sensorManager;

	public SenseFromAllPullSensorsTask(final Context context)
	{
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

	@Override
	protected Void doInBackground(Void... params)
	{
		Log.d("Sensor Data", " === Starting " + LOG_TAG + " ===");
		for (SensorEnum s : SensorEnum.values())
		{
			if (s.isPull())
			{
				try
				{
					// Sense with default parameters
					Log.d(LOG_TAG, "Sensing from: " + s.getName());
					SensorData data = sensorManager.getDataFromSensor(s.getType());
					Log.d(LOG_TAG, "Sensed from: " + SensorUtils.getSensorName(data.getSensorType()));
					// To store/format your data, check out the SensorDataManager library
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		Log.d("Sensor Data", " === Finished " + LOG_TAG + " ===");
		return null;
	}

}
