package com.ubhave.triggermanager.tester.ui.clock;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.ubhave.triggermanager.ESTriggerManager;
import com.ubhave.triggermanager.TriggerException;
import com.ubhave.triggermanager.config.TriggerConfig;
import com.ubhave.triggermanager.tester.R;
import com.ubhave.triggermanager.tester.ui.ExampleAbstractActivity;

public class RandomTriggerActivity extends ExampleAbstractActivity
{
	private SeekBar minBar, maxBar, numBar, intBar, limBar;
	private TextView minText, maxText, numText, intText, limText;
	private Button resetButton;
	
	private Resources resources;
	private ESTriggerManager triggerManager;
	private TriggerConfig parameters;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		resources = getResources();
		try
		{
			triggerManager = ESTriggerManager.getTriggerManager(getApplicationContext());
		}
		catch (TriggerException e)
		{
			e.printStackTrace();
		}

		minText = (TextView) findViewById(R.id.minText);
		maxText = (TextView) findViewById(R.id.maxText);
		numText = (TextView) findViewById(R.id.numText);
		intText = (TextView) findViewById(R.id.intText);
		limText = (TextView) findViewById(R.id.allowedText);
		
		minBar = (SeekBar) findViewById(R.id.minSeekBar);
		maxBar = (SeekBar) findViewById(R.id.maxSeekBar);
		numBar = (SeekBar) findViewById(R.id.numSeekBar);
		intBar = (SeekBar) findViewById(R.id.intSeekBar);
		limBar = (SeekBar) findViewById(R.id.allowedSeekBar);
		
		OnSeekBarChangeListener listener = getListener();
		minBar.setOnSeekBarChangeListener(listener);
		maxBar.setOnSeekBarChangeListener(listener);
		numBar.setOnSeekBarChangeListener(listener);
		intBar.setOnSeekBarChangeListener(listener);
		limBar.setOnSeekBarChangeListener(listener);
		
		resetButton = (Button) findViewById(R.id.resetTriggerButton);
		resetButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Toast.makeText(RandomTriggerActivity.this, "Daily cap reset", Toast.LENGTH_LONG).show();
				triggerManager.resetCap();
			}
		});
		
		parameters = initParameters();
	}
	
	private OnSeekBarChangeListener getListener()
	{
		return new OnSeekBarChangeListener()
		{
			
			private String toTime(int value)
			{
				int hours = value / 2;
				String minute = (value % 2 ==  1) ? "30" : "00";
				return hours+":"+minute;
			}

			@Override
			public void onProgressChanged(SeekBar bar, int progress, boolean arg2)
			{
				int id = bar.getId();
				boolean parametersUpdated = false;
				if (id == R.id.minSeekBar)
				{
					minText.setText(resources.getString(R.string.before_prefix)+" "+toTime(progress));
					parametersUpdated |= update(TriggerConfig.DO_NOT_DISTURB_BEFORE_MINUTES, toMinutes(minBar.getProgress()));
				}
				else if (id == R.id.maxSeekBar)
				{
					maxText.setText(resources.getString(R.string.after_prefix)+" "+toTime(progress));
					parametersUpdated |= update(TriggerConfig.DO_NOT_DISTURB_AFTER_MINUTES, toMinutes(maxBar.getProgress()));
				}
				else if (id == R.id.numSeekBar)
				{
					numText.setText(resources.getString(R.string.num_prefix)+" "+progress+" notifications.");
					parametersUpdated |= update(TriggerConfig.NUMBER_OF_NOTIFICATIONS, numBar.getProgress());
				}
				else if (id == R.id.intSeekBar)
				{
					intText.setText(resources.getString(R.string.min_interval_prefix)+" "+progress+" "+resources.getString(R.string.min_interval_postfix));
					parametersUpdated |= update(TriggerConfig.MIN_TRIGGER_INTERVAL_MINUTES, intBar.getProgress());
				}
				else if (id == R.id.allowedSeekBar)
				{
					limText.setText(resources.getString(R.string.allowed_prefix)+" "+progress);
					parametersUpdated |= update(TriggerConfig.MAX_DAILY_NOTIFICATION_CAP, limBar.getProgress());
				}
				
				if (parametersUpdated)
				{
					resetTrigger();
				}
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar)
			{}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar)
			{}	
		};
	}
	
	private void resetTrigger()
	{
		if (super.isSubscribed())
		{
			super.unsubscribe("called by reset");
			super.subscribe();
		}
	}
	
	private boolean update(String key, int value)
	{
		int oldValue = (Integer) parameters.getParameter(key);
		if (value == oldValue)
		{
			return false;
		}
		else
		{
			parameters.addParameter(key, value);
			return true;
		}
	}
	
	@Override
	protected int getInterfaceLayout()
	{
		return R.layout.activity_random;
	}
	
	private int toMinutes(int progress)
	{
		return (progress * 30);
	}

	@Override
	protected TriggerConfig getParameters()
	{
		triggerManager.setNotificationCap(maxBar.getProgress());
		Toast.makeText(this, "Scheduling "+((Integer)parameters.getParameter(TriggerConfig.NUMBER_OF_NOTIFICATIONS))+" notifications", Toast.LENGTH_LONG).show();
		return parameters;
	}
	
	protected TriggerConfig initParameters()
	{
		long interval = 1000 * 60 * 60;
		TriggerConfig config = new TriggerConfig();
		config.addParameter(TriggerConfig.NUMBER_OF_NOTIFICATIONS, numBar.getProgress());
		config.addParameter(TriggerConfig.DO_NOT_DISTURB_BEFORE_MINUTES, toMinutes(minBar.getProgress()));
		config.addParameter(TriggerConfig.DO_NOT_DISTURB_AFTER_MINUTES, toMinutes(maxBar.getProgress()));
		config.addParameter(TriggerConfig.MIN_TRIGGER_INTERVAL_MINUTES, intBar.getProgress());
		config.addParameter(TriggerConfig.INTERVAL_TRIGGER_TIME_MILLIS, interval); // TODO set from ui
		
		triggerManager.setNotificationCap(limBar.getProgress());
		return config;
	}
}
