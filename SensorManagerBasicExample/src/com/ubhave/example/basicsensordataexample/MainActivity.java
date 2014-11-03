package com.ubhave.example.basicsensordataexample;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		startPull();
	}

	private void startPull()
	{
		new SenseFromAllPullSensorsTask(this)
		{
			@Override
			protected void onPostExecute(Void result)
			{
				super.onPostExecute(result);
				startEnvironment();
			}
		}.execute();
	}

	private void startEnvironment()
	{
		new SenseFromAllEnvSensorsTask(this)
		{
			@Override
			protected void onPostExecute(Void result)
			{
				super.onPostExecute(result);
				startPush();
			}
		}.execute();
	}

	private void startPush()
	{
		new SenseFromAllPushSensorsTask(this).execute();
	}
}
