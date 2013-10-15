package com.ubhave.example.sensordatamanager.log;

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
		// Transfer any files that are more than 30 seconds old
		return (1000L * 30);
	}

	@Override
	protected long getTransferAlarmLengthMillis()
	{
		// Try to transfer data every 1 minutes
		return (1000L * 60 * 1);
	}

	@Override
	protected String getDataPostURL()
	{
		return HiddenConstants.YOUR_SERVERS_FILE_POST_URL;
	}

	@Override
	protected String getPostPassword()
	{
		return HiddenConstants.YOUR_SERVERS_POST_PASSWORD_IF_ANY;
	}

	@Override
	protected String getLocalStorageDirectoryName()
	{
		return "ExampleSensorDataManager-AsyncData";
	}

	@Override
	protected String getUniqueUserId()
	{
		// Should be unique to this user, not a static string
		return "ExampleSensorDataManagerUser";
	}

}
