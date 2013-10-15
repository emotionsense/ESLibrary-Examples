package com.ubhave.example.sensordatamanager.store;

import android.content.Context;

import com.ubhave.datahandler.loggertypes.AbstractStoreOnlyLogger;

public class ExampleStoreOnlyLogger extends AbstractStoreOnlyLogger
{

	public ExampleStoreOnlyLogger(Context context)
	{
		super(context);
	}

	@Override
	protected String getLocalStorageDirectoryName()
	{
		return "ExampleSensorDataManager-LocalData";
	}

	@Override
	protected String getUserId()
	{
		return "ExampleSensorDataManagerUser";
	}
}
