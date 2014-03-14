/* **************************************************
 Copyright (c) 2012, University of Cambridge
 Neal Lathia, neal.lathia@cl.cam.ac.uk

For more information, please visit http://www.emotionsense.org

Permission to use, copy, modify, and/or distribute this software for any
purpose with or without fee is hereby granted, provided that the above
copyright notice and this permission notice appear in all copies.

THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR
IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 ************************************************** */

package com.ubhave.example.example.captureaudio;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.ubhave.datahandler.loggertypes.AbstractDataLogger;
import com.ubhave.sensormanager.ESException;
import com.ubhave.sensormanager.ESSensorManager;
import com.ubhave.sensormanager.SensorDataListener;
import com.ubhave.sensormanager.config.sensors.pull.MicrophoneConfig;
import com.ubhave.sensormanager.config.sensors.pull.PullSensorConfig;
import com.ubhave.sensormanager.data.SensorData;
import com.ubhave.sensormanager.sensors.SensorUtils;

public class ExampleSensorListener implements SensorDataListener
{
	private ESSensorManager sensorManager;
	private AbstractDataLogger dataLogger;
	private int subscriptionId;
	
	public ExampleSensorListener(final Context context, final AbstractDataLogger logger)
	{
		this.dataLogger = logger;
		try
		{
			sensorManager = ESSensorManager.getSensorManager(context);
			setSensorConfig();
		}
		catch (ESException e)
		{
			sensorManager = null;
			e.printStackTrace();
		}
	}
	
	private void setSensorConfig()
	{
		if (sensorManager != null)
		{
			try
			{
				sensorManager.setSensorConfig(SensorUtils.SENSOR_TYPE_MICROPHONE, PullSensorConfig.SENSE_WINDOW_LENGTH_MILLIS, 2000L);
				sensorManager.setSensorConfig(SensorUtils.SENSOR_TYPE_MICROPHONE, PullSensorConfig.POST_SENSE_SLEEP_LENGTH_MILLIS, 2000L);
				sensorManager.setSensorConfig(SensorUtils.SENSOR_TYPE_MICROPHONE, MicrophoneConfig.KEEP_AUDIO_FILES, true);
				
				String destinationDirectory = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ExampleSensorDataManager-AsyncData/Sounds";
				sensorManager.setSensorConfig(SensorUtils.SENSOR_TYPE_MICROPHONE, MicrophoneConfig.AUDIO_FILES_DIRECTORY, destinationDirectory);
			}
			catch (ESException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public boolean startSensing()
	{
		try
		{
			subscriptionId = sensorManager.subscribeToSensorData(SensorUtils.SENSOR_TYPE_MICROPHONE, this);
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
		Log.d("ExampleApp", "Data Received");
		dataLogger.logSensorData(data);
	}

	@Override
	public void onCrossingLowBatteryThreshold(boolean isBelowThreshold)
	{
		// Nothing for example app
	}
}
