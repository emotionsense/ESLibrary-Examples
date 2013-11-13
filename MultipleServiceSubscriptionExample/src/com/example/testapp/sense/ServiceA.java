package com.example.testapp.sense;

import com.ubhave.sensormanager.sensors.SensorUtils;


public class ServiceA extends AbstractService
{
	private final static String LOG_TAG = "ServiceA";

	@Override
	protected String getLogTag()
	{
		return LOG_TAG;
	}

	@Override
	protected void sense()
	{
		senseWithParameters(0, 5);
	}
	
	@Override
	protected int getSensorType()
	{
		return SensorUtils.SENSOR_TYPE_ACCELEROMETER;
	}
}
