package com.ubhave.triggermanager.tester.ui.clock;

import java.util.Calendar;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ubhave.triggermanager.config.TriggerConfig;
import com.ubhave.triggermanager.tester.R;
import com.ubhave.triggermanager.tester.ui.ExampleAbstractActivity;

public class IntervalTriggerActivity extends ExampleAbstractActivity
{
	private SeekBar bar;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		bar = (SeekBar) findViewById(R.id.seekBar);
		
		final TextView label = (TextView) findViewById(R.id.intervalText);
		final String prefix = getResources().getString(R.string.interval_prefix);
		bar.setOnSeekBarChangeListener(new OnSeekBarChangeListener()
		{

			@Override
			public void onProgressChanged(SeekBar arg0, int progress, boolean arg2)
			{
				label.setText(prefix+" "+progress+" minutes.");
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar)
			{}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar)
			{}
			
		});
	}
	
	@Override
	protected int getInterfaceLayout()
	{
		return R.layout.activity_interval;
	}

	@Override
	protected TriggerConfig getParameters()
	{
		Calendar calendar = Calendar.getInstance();
		TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);
		calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
		calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
		
		TriggerConfig config = new TriggerConfig();
		config.addParameter(TriggerConfig.IGNORE_USER_PREFERENCES, true);
		config.addParameter(TriggerConfig.INTERVAL_TRIGGER_START_DELAY, System.currentTimeMillis() - calendar.getTimeInMillis());
		
		int progress = bar.getProgress();
		long interval;
		if (progress == 0)
		{
			interval = 1000 * 10;
			Toast.makeText(this, "Setting interval to 10 seconds (it can't be zero!)", Toast.LENGTH_SHORT).show();
		}
		else
		{
			interval = progress * 1000 * 60;
		}
		
		config.addParameter(TriggerConfig.INTERVAL_TRIGGER_TIME_MILLIS, interval);
		
		Toast.makeText(this, "Setting trigger for: "+calendar.getTime().toString()+", every "+interval+" milliseconds.", Toast.LENGTH_SHORT).show();
		return config;
	}
}
