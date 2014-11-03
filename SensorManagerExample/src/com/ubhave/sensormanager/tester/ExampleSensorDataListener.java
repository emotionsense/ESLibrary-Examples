/* **************************************************
 Copyright (c) 2012, University of Cambridge
 Neal Lathia, neal.lathia@cl.cam.ac.uk

This demo application was developed as part of the EPSRC Ubhave (Ubiquitous and
Social Computing for Positive Behaviour Change) Project. For more
information, please visit http://www.emotionsense.org

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

package com.ubhave.sensormanager.tester;

import android.content.Context;
import android.util.Log;

import com.ubhave.dataformatter.DataFormatter;
import com.ubhave.dataformatter.json.JSONFormatter;
import com.ubhave.datahandler.except.DataHandlerException;
import com.ubhave.sensormanager.ESException;
import com.ubhave.sensormanager.ESSensorManager;
import com.ubhave.sensormanager.ESSensorManagerInterface;
import com.ubhave.sensormanager.SensorDataListener;
import com.ubhave.sensormanager.config.GlobalConfig;
import com.ubhave.sensormanager.config.pull.LocationConfig;
import com.ubhave.sensormanager.data.SensorData;
import com.ubhave.sensormanager.sensors.SensorUtils;

public class ExampleSensorDataListener implements SensorDataListener
{
	private final static String LOG_TAG = "SensorListener";
	
	private final int sensorType;
	private final SensorDataUI userInterface;

	private ESSensorManagerInterface sensorManager;
	private final JSONFormatter formatter;
//	private final CSVFormatter formatter;
	
	private int sensorSubscriptionId;
	private boolean isSubscribed;

	public ExampleSensorDataListener(int sensorType, SensorDataUI userInterface)
	{
		this.sensorType = sensorType;
		this.userInterface = userInterface;
		isSubscribed = false;
		
		Context context = ApplicationContext.getContext();
		formatter = DataFormatter.getJSONFormatter(context, sensorType);
		
		try
		{
			sensorManager = ESSensorManager.getSensorManager(context);
			sensorManager.setGlobalConfig(GlobalConfig.LOW_BATTERY_THRESHOLD, 25);
			
			if (sensorType == SensorUtils.SENSOR_TYPE_LOCATION)
			{
				sensorManager.setSensorConfig(SensorUtils.SENSOR_TYPE_LOCATION, LocationConfig.ACCURACY_TYPE, LocationConfig.LOCATION_ACCURACY_FINE);
			}
		}
		catch (ESException e)
		{
			e.printStackTrace();
		}
	}

	public void subscribeToSensorData()
	{
		try
		{
			sensorSubscriptionId = sensorManager.subscribeToSensorData(sensorType, this);
			isSubscribed = true;
		}
		catch (ESException e)
		{
			e.printStackTrace();
		}
	}

	public void unsubscribeFromSensorData()
	{
		try
		{
			sensorManager.unsubscribeFromSensorData(sensorSubscriptionId);
			isSubscribed = false;
		}
		catch (ESException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onDataSensed(SensorData data)
	{
		try
		{
			userInterface.updateUI(formatter.toJSON(data).toString());
		}
		catch (DataHandlerException e)
		{
			e.printStackTrace();
		}
	}

	public String getSensorName()
	{
		try
		{
			return SensorUtils.getSensorName(sensorType);
		}
		catch (ESException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public boolean isSubscribed()
	{
		return isSubscribed;
	}

	@Override
	public void onCrossingLowBatteryThreshold(boolean isBelowThreshold)
	{
		Log.d(LOG_TAG, "crossingLowBatteryThreshold: "+isBelowThreshold);
		try
		{
			if (isBelowThreshold)
			{
				userInterface.updateUI("Sensing stopped: low battery");
				sensorManager.pauseSubscription(sensorSubscriptionId);
			}
			else
			{
				userInterface.updateUI("Sensing unpaused: battery healthy");
				sensorManager.unPauseSubscription(sensorSubscriptionId);
			}
		}
		catch (ESException e)
		{
			e.printStackTrace();
		}
	}

}
