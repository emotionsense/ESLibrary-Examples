package com.ubhave.example.basicsensordataexample;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.ubhave.dataformatter.DataFormatter;
import com.ubhave.dataformatter.json.JSONFormatter;
import com.ubhave.sensormanager.ESException;
import com.ubhave.sensormanager.ESSensorManager;
import com.ubhave.sensormanager.config.GlobalConfig;
import com.ubhave.sensormanager.data.SensorData;
import com.ubhave.sensormanager.sensors.SensorUtils;

public class MainActivity extends Activity
{
	private final static int[] ALL_SENSORS = new int[]{
		SensorUtils.SENSOR_TYPE_ACCELEROMETER,
		SensorUtils.SENSOR_TYPE_APPLICATION,
		SensorUtils.SENSOR_TYPE_BATTERY,
		SensorUtils.SENSOR_TYPE_BLUETOOTH,
		SensorUtils.SENSOR_TYPE_CALL_CONTENT_READER,
//		SensorUtils.SENSOR_TYPE_CAMERA,
		SensorUtils.SENSOR_TYPE_CONNECTION_STATE,
		SensorUtils.SENSOR_TYPE_LOCATION,
		SensorUtils.SENSOR_TYPE_MICROPHONE,
		SensorUtils.SENSOR_TYPE_PHONE_STATE,
		SensorUtils.SENSOR_TYPE_PROXIMITY,
		SensorUtils.SENSOR_TYPE_SCREEN,
		SensorUtils.SENSOR_TYPE_SMS,
		SensorUtils.SENSOR_TYPE_SMS_CONTENT_READER,
		SensorUtils.SENSOR_TYPE_WIFI
	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		collectSensorData();
	}
	
	private void collectSensorData()
	{
		new AsyncTask<Void, Void, Void>()
		{
			@Override
			public Void doInBackground(Void ...voids)
			{
				try
				{
					Log.d("Sensor Data", " === Starting Sensor Thread ===");
					ESSensorManager esSensorManager = ESSensorManager.getSensorManager(MainActivity.this);
					esSensorManager.setGlobalConfig(GlobalConfig.PRINT_LOG_D_MESSAGES, false);
					for (int sensorType : ALL_SENSORS)
					{
						if (SensorUtils.isPullSensor(sensorType))
						{
							try
							{
								Log.d("Sensor Data", "Sensor type: " + SensorUtils.getSensorName(sensorType));
								JSONFormatter dataFormatter = DataFormatter.getJSONFormatter(MainActivity.this, sensorType);
								SensorData data = esSensorManager.getDataFromSensor(sensorType);
								JSONObject jsonData = dataFormatter.toJSON(data);
								Log.d("Sensor Time Stamp", "" + jsonData.getString("senseStartTime"));
								Log.d("Sensor Data", "" + jsonData.toString());
							}
							catch (JSONException e)
							{
								e.printStackTrace();
							}
						}
					}	
					Log.d("Sensor Data", " === Finished Sensor Thread ===");
				}
				catch (ESException e)
				{
					e.printStackTrace();
				}
				return null;
			}
		}.execute();
	}
}
