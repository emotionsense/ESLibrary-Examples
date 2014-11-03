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

package com.ubhave.example.capturemedia;

import android.content.Context;
import android.util.Log;

import com.ubhave.datahandler.config.DataStorageConfig;
import com.ubhave.datahandler.loggertypes.AbstractDataLogger;
import com.ubhave.sensormanager.ESException;
import com.ubhave.sensormanager.ESSensorManager;
import com.ubhave.sensormanager.SensorDataListener;
import com.ubhave.sensormanager.config.pull.CameraConfig;
import com.ubhave.sensormanager.config.pull.MicrophoneConfig;
import com.ubhave.sensormanager.config.pull.PullSensorConfig;
import com.ubhave.sensormanager.data.SensorData;
import com.ubhave.sensormanager.sensors.SensorUtils;

public class ExampleSensorListener implements SensorDataListener
{
	private ESSensorManager sensorManager;
	private AbstractDataLogger dataLogger;
	private int audioSubscriptionId, cameraSubscriptionId;
	
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
				/*
				 * Set microphone sensing params
				 */
				sensorManager.setSensorConfig(SensorUtils.SENSOR_TYPE_MICROPHONE, PullSensorConfig.SENSE_WINDOW_LENGTH_MILLIS, 2000L);
				sensorManager.setSensorConfig(SensorUtils.SENSOR_TYPE_MICROPHONE, PullSensorConfig.POST_SENSE_SLEEP_LENGTH_MILLIS, 5000L);
				
				/*
				 * Set camera 'sensing' params
				 */
				sensorManager.setSensorConfig(SensorUtils.SENSOR_TYPE_CAMERA, PullSensorConfig.POST_SENSE_SLEEP_LENGTH_MILLIS, 5000L);
				sensorManager.setSensorConfig(SensorUtils.SENSOR_TYPE_CAMERA, CameraConfig.CAMERA_TYPE, CameraConfig.CAMERA_TYPE_FRONT);
				
				
				String rootDirectory = (String) dataLogger.getDataManager().getConfig(DataStorageConfig.LOCAL_STORAGE_ROOT_DIRECTORY_NAME);
				
				/*
				 * Store audio files to /Sounds
				 */
				sensorManager.setSensorConfig(SensorUtils.SENSOR_TYPE_MICROPHONE, MicrophoneConfig.AUDIO_FILES_DIRECTORY, rootDirectory + "/Sounds");
				/*
				 * Store image files to /Images
				 */
				sensorManager.setSensorConfig(SensorUtils.SENSOR_TYPE_CAMERA, CameraConfig.IMAGE_FILES_DIRECTORY, rootDirectory + "/Images");
				
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public boolean startSensing()
	{
		try
		{
			Log.d("ExampleApp", "Subscribing to sensors.");
			audioSubscriptionId = sensorManager.subscribeToSensorData(SensorUtils.SENSOR_TYPE_MICROPHONE, this);
			cameraSubscriptionId = sensorManager.subscribeToSensorData(SensorUtils.SENSOR_TYPE_CAMERA, this);
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
			Log.d("ExampleApp", "Unsubscribing from sensors.");
			sensorManager.unsubscribeFromSensorData(audioSubscriptionId);
			sensorManager.unsubscribeFromSensorData(cameraSubscriptionId);
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
		try
		{
			Log.d("ExampleApp", "Data Received from: "+SensorUtils.getSensorName(data.getSensorType()));
			dataLogger.logSensorData(data);
		}
		catch (ESException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onCrossingLowBatteryThreshold(boolean isBelowThreshold)
	{
		// Nothing for example app
	}
}
