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

import android.content.Context;
import android.util.Log;

import com.ubhave.dataformatter.json.JSONFormatter;
import com.ubhave.datahandler.except.DataHandlerException;
import com.ubhave.datahandler.loggertypes.AbstractDataLogger;
import com.ubhave.sensormanager.ESException;
import com.ubhave.sensormanager.ESSensorManager;
import com.ubhave.sensormanager.SensorDataListener;
import com.ubhave.sensormanager.data.SensorData;

public class ExampleSensorListener implements SensorDataListener
{
	private JSONFormatter formatter;
	private ESSensorManager sensorManager;
	private AbstractDataLogger dataLogger;
	private int subscriptionId, sensorType;
	
	public ExampleSensorListener(final Context context, final AbstractDataLogger logger, final int sensorType)
	{
		this.sensorType = sensorType;
		this.dataLogger = logger;
		try
		{
			sensorManager = ESSensorManager.getSensorManager(context);
			formatter = JSONFormatter.getJSONFormatter(context, sensorType);
		}
		catch (ESException e)
		{
			sensorManager = null;
			e.printStackTrace();
		}
	}
	
	public void setSensorConfig(String key, Object value)
	{
		if (sensorManager != null)
		{
			try
			{
				sensorManager.setSensorConfig(sensorType, key, value);
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
			subscriptionId = sensorManager.subscribeToSensorData(sensorType, this);
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
			Log.d("Sensor", "Unsubscribing id = "+subscriptionId);
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
		try
		{
			Log.d("Data Sensed", formatter.toJSON(data).toString());
		}
		catch (DataHandlerException e)
		{
			e.printStackTrace();
		}
		dataLogger.logSensorData(data);
	}

	@Override
	public void onCrossingLowBatteryThreshold(boolean isBelowThreshold)
	{
		// Nothing for example app
	}
}
