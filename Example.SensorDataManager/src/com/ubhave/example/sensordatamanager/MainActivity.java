package com.ubhave.example.sensordatamanager;

import com.ubhave.datahandler.config.DataTransferConfig;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity
{
	private boolean isSensing;
	private ExampleAccelerometerSensorListener sensor;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		isSensing = false;
		sensor = new ExampleAccelerometerSensorListener(this, DataTransferConfig.STORE_ONLY);
	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
		if (isSensing)
		{
			sensor.stopSensing();
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
		if (isSensing)
		{
			return sensor.startSensing();
		}
		else
		{
			return sensor.stopSensing();
		}
	}
}
