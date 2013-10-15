package com.ubhave.triggermanager.tester.ui.clock;

import java.util.Calendar;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ubhave.triggermanager.config.TriggerConfig;
import com.ubhave.triggermanager.tester.R;
import com.ubhave.triggermanager.tester.ui.ExampleAbstractActivity;

public class ClockTriggerActivity extends ExampleAbstractActivity
{
	private int delta = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		for (int i=0; i<10; i++)
		{
			delta += 1;
			super.subscribe();
		}
	}

	@Override
	protected int getInterfaceLayout()
	{
		return R.layout.activity_clock;
	}

	@Override
	protected TriggerConfig getParameters()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.SECOND, delta * 10);
		
//		TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);
//		calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
//		calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
//		calendar.set(Calendar.SECOND, 0);
//		
		long time = calendar.getTimeInMillis();
		if (time < System.currentTimeMillis())
		{
			Toast.makeText(this, "Adding 10 seconds..", Toast.LENGTH_SHORT).show();
			time = System.currentTimeMillis() + (10 * 1000);
		}
		
		TriggerConfig config = new TriggerConfig();
		config.addParameter(TriggerConfig.CLOCK_TRIGGER_DATE_MILLIS, time);
		config.addParameter(TriggerConfig.IGNORE_USER_PREFERENCES, true);
		
		Log.d("Clock trigger", "Setting trigger for: "+calendar.getTime().toString());
		return config;
	}

	@Override
	public void notificationTriggered()
	{
		Log.d("Clock trigger", "Notification triggered");
		runOnUiThread(new Runnable()
		{
			public void run()
			{
				triggerReceiver.unsubscribeFromTrigger("Clock caller");
			}
		});
	}

}
