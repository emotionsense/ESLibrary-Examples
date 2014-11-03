package com.ubhave.example.capturemedia;

import com.ubhave.example.example.captureaudio.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity
{
	private boolean isSensing;
	private TextView statusText;
	private ExampleSensorListener sensorListener;
	private ExampleAsyncTransferLogger dataLogger;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		statusText = (TextView) findViewById(R.id.statusText);
		statusText.setText(R.string.notSensing);
		
		isSensing = false;
		
		try
		{
			dataLogger = new ExampleAsyncTransferLogger(this);
			sensorListener = new ExampleSensorListener(this, dataLogger);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
		if (isSensing)
		{
			sensorListener.stopSensing();
			isSensing = false;
		}
	}
	
	public void switchSensing(final View view)
	{
		if (isSensing)
		{
			sensorListener.stopSensing();
			isSensing = false;
			statusText.setText(R.string.notSensing);
		}
		else
		{
			sensorListener.startSensing();
			isSensing = true;
			statusText.setText(R.string.sensing);
		}
	}
}
