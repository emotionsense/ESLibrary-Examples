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

package com.ubhave.example.sensordatamanager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ubhave.datahandler.config.DataTransferConfig;
import com.ubhave.datahandler.loggertypes.AbstractDataLogger;
import com.ubhave.example.sensordatamanager.log.ExampleAsyncTransferLogger;
import com.ubhave.example.sensordatamanager.log.ExampleImmediateTransferLogger;
import com.ubhave.example.sensordatamanager.log.ExampleStoreOnlyLogger;
import com.ubhave.sensormanager.config.sensors.pull.PullSensorConfig;
import com.ubhave.sensormanager.sensors.SensorUtils;

public class MainActivity extends Activity
{
	private final static int[] DATA_TRANSFER_POLICIES = new int[]{
		DataTransferConfig.STORE_ONLY,
		DataTransferConfig.TRANSFER_IMMEDIATE,
		DataTransferConfig.TRANSFER_PERIODICALLY
	};
	private final static int CURRENT_POLICY_INDEX = 2;
	
	private boolean isSensing;
	private ExampleSensorListener sensor;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		isSensing = false;
		
		int currentPolicy = DATA_TRANSFER_POLICIES[CURRENT_POLICY_INDEX];
		AbstractDataLogger dataLogger = getDataLoggerForPolicy(currentPolicy);
		
		if (dataLogger != null)
		{
			sensor = new ExampleSensorListener(this, dataLogger, SensorUtils.SENSOR_TYPE_ACCELEROMETER);
			sensor.setSensorConfig(PullSensorConfig.SENSE_WINDOW_LENGTH_MILLIS, 5000L);
			sensor.setSensorConfig(PullSensorConfig.POST_SENSE_SLEEP_LENGTH_MILLIS, 10000L);
		}
	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
		if (isSensing)
		{
			sensor.stopSensing();
		}
	}
	
	private AbstractDataLogger getDataLoggerForPolicy(int currentPolicy)
	{
		if (currentPolicy == DataTransferConfig.STORE_ONLY)
		{
			return new ExampleStoreOnlyLogger(this);
		}
		else if (currentPolicy == DataTransferConfig.TRANSFER_IMMEDIATE)
		{
			return new ExampleImmediateTransferLogger(this);
		}
		else if (currentPolicy == DataTransferConfig.TRANSFER_PERIODICALLY)
		{
			return new ExampleAsyncTransferLogger(this);
		}
		else
		{
			System.err.println("No logger defined for policy: "+currentPolicy);
			return null;
		}
	}

	/*
	 * Listener for UI button
	 */
	public void switchSensing(final View view)
	{
		isSensing = !isSensing;
		if (switchSensing())
		{
			switchButtonText((Button) view);
		}
	}
	
	private void switchButtonText(final Button sensingButton)
	{
		if (isSensing)
		{
			sensingButton.setText(R.string.button_stopSensing);
		}
		else
		{
			sensingButton.setText(R.string.button_startSensing);
		}
	}
	
	private boolean switchSensing()
	{
		if (sensor != null)
		{
			if (isSensing)
			{
				return sensor.startSensing();
			}
			else
			{
				return sensor.stopSensing();
			}
		}
		else
		{
			return false;
		}
	}
}
