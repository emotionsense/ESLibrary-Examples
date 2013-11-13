package com.example.testapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.testapp.sense.ServiceA;
import com.example.testapp.sense.ServiceB;

public class MainActivity extends Activity
{	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Intent intenta = new Intent(getApplicationContext(), ServiceA.class);
		Intent intentb = new Intent(getApplicationContext(), ServiceB.class);
		
		launchService(intenta);
		launchService(intentb);
	}
	
	private void launchService(final Intent intent)
	{
		new Thread()
		{
			@Override
			public void run()
			{
				stopService(intent);
				startService(intent);
			}
		}.start();
	}
}
