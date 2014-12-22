package com.ubhave.example.sensordatamanager.loggers;

import java.util.HashMap;

import android.content.Context;

import com.ubhave.datahandler.except.DataHandlerException;
import com.ubhave.datahandler.loggertypes.AbstractAsyncTransferLogger;
import com.ubhave.sensormanager.ESException;

public class ExampleAsyncTransferLogger extends AbstractAsyncTransferLogger
{
	public ExampleAsyncTransferLogger(final Context context, int storageType) throws DataHandlerException, ESException
	{
		super(context, storageType);
	}

	@Override
	protected long getDataLifeMillis()
	{
		/*
		 *  Transfer data that is more than 10 seconds old
		 */
		return (1000L * 10);
	}

	@Override
	protected long getTransferAlarmLengthMillis()
	{
		/*
		 *  Try to transfer data every 30 seconds
		 */
		return (1000L * 30);
	}

	@Override
	protected String getDataPostURL()
	{
		return HiddenConstants.YOUR_SERVERS_FILE_POST_URL;
	}

	@Override
	protected String getStorageName()
	{
		return "ExampleSensorDataManager-AsyncData";
	}

	@Override
	protected String getUniqueUserId()
	{
		/*
		 * Note: Should be unique to this user, not a static string
		 */
		return "ExampleSensorDataManagerUser";
	}
	
	@Override
	protected String getDeviceId()
	{
		/*
		 * Note: Should be unique to this device, not a static string
		 */
		return "ExampleSensorDataManagerDevice";
	}

	@Override
	protected String getSuccessfulPostResponse()
	{
		return HiddenConstants.ON_SUCCESS_RESPONSE;
	}

	@Override
	protected HashMap<String, String> getPostParameters()
	{
		/*
		 * Parameters to be used when POST-ing data
		 */
		HashMap<String, String> params = new HashMap<String, String>();
		params.put(HiddenConstants.KEY_API_KEY, HiddenConstants.KEY_API_VALUE);
		return params;
	}

	@Override
	protected boolean shouldPrintLogMessages()
	{
		/*
		 * Turn on/off Log.d messages
		 */
		return true;
	}
}
