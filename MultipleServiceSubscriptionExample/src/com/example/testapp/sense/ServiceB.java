package com.example.testapp.sense;

import com.ubhave.sensormanager.sensors.SensorUtils;


public class ServiceB extends AbstractService
{
	private final static String LOG_TAG = "ServiceB";

	@Override
	protected String getLogTag()
	{
		return LOG_TAG;
	}

	@Override
	protected void sense()
	{
		senseWithParameters(2000L, 2);
	}

	@Override
	protected int getSensorType()
	{
		return SensorUtils.SENSOR_TYPE_ACCELEROMETER;
	}
}
