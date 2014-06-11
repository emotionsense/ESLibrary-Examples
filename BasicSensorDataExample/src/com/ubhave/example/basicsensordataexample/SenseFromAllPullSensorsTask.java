package com.ubhave.example.basicsensordataexample;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ubhave.dataformatter.DataFormatter;
import com.ubhave.dataformatter.json.JSONFormatter;
import com.ubhave.sensormanager.ESException;
import com.ubhave.sensormanager.ESSensorManager;
import com.ubhave.sensormanager.config.GlobalConfig;
import com.ubhave.sensormanager.data.SensorData;
import com.ubhave.sensormanager.sensors.SensorUtils;

public class SenseFromAllPullSensorsTask extends AsyncTask<Void, Void, Void>
{
	private final static String LOG_TAG = "SenseFromAllPullSensorsTask";
	private final Context context;
	private ESSensorManager sensorManager;
	
	public SenseFromAllPullSensorsTask(final Context context)
	{
		this.context = context;
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
		try
		{
			Log.d("Sensor Data", " === Starting "+LOG_TAG+" ===");
			for (int sensorType : SensorUtils.ALL_SENSORS)
			{
				if (SensorUtils.isPullSensor(sensorType))
				{
					try
					{
						Log.d(LOG_TAG, "Sensor type: " + SensorUtils.getSensorName(sensorType));
						
						// Sense with default parameters
						SensorData data = sensorManager.getDataFromSensor(sensorType);
						
						// Dump the result
						JSONFormatter dataFormatter = DataFormatter.getJSONFormatter(context, sensorType);
						JSONObject jsonData = dataFormatter.toJSON(data);
						Log.d(LOG_TAG, "Sensor Time Stamp: " + jsonData.getString("senseStartTime"));
						Log.d(LOG_TAG, "Sensor Data: " + jsonData.toString());
					}
					catch (JSONException e)
					{
						e.printStackTrace();
					}
				}
			}	
			Log.d("Sensor Data", " === Finished "+LOG_TAG+" ===");
		}
		catch (ESException e)
		{
			e.printStackTrace();
		}
		return null;
	}

}
