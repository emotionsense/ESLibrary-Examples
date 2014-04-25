package com.ubhave.example.sensordatamanager.log;

import java.util.HashMap;

import android.content.Context;

import com.ubhave.datahandler.loggertypes.AbstractAsyncTransferLogger;
import com.ubhave.example.sensordatamanager.HiddenConstants;

public class ExampleAsyncTransferLogger extends AbstractAsyncTransferLogger
{

	public ExampleAsyncTransferLogger(Context context)
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
		return "Your Server's Response";
	}

	@Override
	protected HashMap<String, String> getPostParameters()
	{
		/*
		 * Parameters to be used when POST-ing data
		 */
		return null;
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
