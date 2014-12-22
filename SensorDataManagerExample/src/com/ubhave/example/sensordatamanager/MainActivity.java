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
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ubhave.datahandler.config.DataStorageConfig;
import com.ubhave.datahandler.config.DataTransferConfig;
import com.ubhave.datahandler.except.DataHandlerException;
import com.ubhave.datahandler.loggertypes.AbstractDataLogger;
import com.ubhave.example.sensordatamanager.loggers.ExampleAsyncTransferLogger;
import com.ubhave.example.sensordatamanager.loggers.ExampleImmediateTransferLogger;
import com.ubhave.example.sensordatamanager.loggers.ExampleStoreOnlyLogger;
import com.ubhave.sensormanager.ESException;
import com.ubhave.sensormanager.sensors.SensorUtils;

public class MainActivity extends Activity
{
	/*
	 * The library supports three transfer policies:
	 * None - data is stored locally
	 * Immediate - the device tries to send the data immediately
	 * Periodically - the device is stored and bulk transferred
	 */
	private final static int[] DATA_TRANSFER_POLICIES = new int[]
	{
		DataTransferConfig.STORE_ONLY,
		DataTransferConfig.TRANSFER_IMMEDIATE,
		DataTransferConfig.TRANSFER_PERIODICALLY
	};
	
	/*
	 * The library supports three storage policies:
	 * None - data is stored locally
	 * Immediate - the device tries to send the data immediately
	 * Periodically - the device is stored and bulk transferred
	 */
	private final static int[] DATA_STORAGE_POLICIES = new int[]
	{
		DataStorageConfig.STORAGE_TYPE_NONE,
		DataStorageConfig.STORAGE_TYPE_FILES,
		DataStorageConfig.STORAGE_TYPE_DB
	};
	
	/*
	 * Index of the values used in this example
	 * Change these to try different storage/transfer combinations
	 */
	private final static int TRANSFER_POLICY_INDEX = 2;
	private final static int STORAGE_POLICY_INDEX = 2;
	private final static int SENSOR = SensorUtils.SENSOR_TYPE_PROXIMITY;
	
	private boolean isSensing;
	private ExampleSensorListener sensor;
	private AbstractDataLogger dataLogger;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		isSensing = false;
		
		int transferPolicy = DATA_TRANSFER_POLICIES[TRANSFER_POLICY_INDEX];
		int storagePolicy = DATA_STORAGE_POLICIES[STORAGE_POLICY_INDEX];
		Log.d("ESDataManager", "Transfer = "+transferPolicy+", Storage = "+storagePolicy);
		dataLogger = getDataLoggerForPolicy(transferPolicy, storagePolicy);
		sensor = new ExampleSensorListener(this, dataLogger, SENSOR);
	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
		if (isSensing)
		{
			switchSensing((Button) findViewById(R.id.sensing_button));
		}
	}
	
	private AbstractDataLogger getDataLoggerForPolicy(int transferPolicy, int storagePolicy)
	{
		try
		{
			if (transferPolicy == DataTransferConfig.STORE_ONLY)
			{
				return new ExampleStoreOnlyLogger(this, storagePolicy);
			}
			else if (transferPolicy == DataTransferConfig.TRANSFER_IMMEDIATE)
			{
				return new ExampleImmediateTransferLogger(this, storagePolicy);
			}
			else if (transferPolicy == DataTransferConfig.TRANSFER_PERIODICALLY)
			{
				return new ExampleAsyncTransferLogger(this, storagePolicy);
			}
			else
			{
				throw new NullPointerException("No logger defined for: "+transferPolicy);
			}
		}
		catch (ESException e)
		{
			Log.d("ESDataManager", "Return null / ESException");
			e.printStackTrace();
			return null;
		}
		catch (DataHandlerException e)
		{
			Log.d("ESDataManager", "Return null / DataHandlerException");
			e.printStackTrace();
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
