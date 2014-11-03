package com.ubhave.example.capturemedia;

import java.util.HashMap;

import android.content.Context;

import com.ubhave.datahandler.except.DataHandlerException;
import com.ubhave.datahandler.loggertypes.AbstractAsyncTransferLogger;
import com.ubhave.sensormanager.ESException;

public class ExampleAsyncTransferLogger extends AbstractAsyncTransferLogger
{
	public ExampleAsyncTransferLogger(Context context) throws DataHandlerException, ESException
	{
		super(context);
	}

	@Override
	protected long getFileLifeMillis()
	{
		/*
		 *  Transfer any files that are more than 30 seconds old
		 */
		return (1000L * 30);
	}

	@Override
	protected long getTransferAlarmLengthMillis()
	{
		/*
		 *  Try to transfer data every 1 minutes
		 */
		return (1000L * 60 * 1);
	}

	@Override
	protected String getDataPostURL()
	{
		return HiddenConstants.YOUR_SERVERS_FILE_POST_URL;
	}

	@Override
	protected String getLocalStorageDirectoryName()
	{
		return "ExampleSensorDataManager-AsyncData";
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
	protected String getUniqueUserId()
	{
		/*
		 * Note: Should be unique to this user, not a static string
		 */
		return "ExampleSensorDataManagerUser";
	}

	@Override
	protected String getSuccessfulPostResponse()
	{
		return HiddenConstants.YOUR_SERVERS_RESPONSE_ON_SUCCESSFUL_POST;
	}

	@Override
	protected HashMap<String, String> getPostParameters()
	{
		/*
		 * Parameters to be used when POST-ing data
		 */
		HashMap<String, String> params = new HashMap<String, String>();
		params.put(HiddenConstants.YOUR_POST_PARAM_KEY, HiddenConstants.YOUR_POST_PARAM_VALUE);
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
