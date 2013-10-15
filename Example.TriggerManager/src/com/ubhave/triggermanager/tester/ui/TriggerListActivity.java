/* **************************************************
 Copyright (c) 2012, University of Cambridge
 Neal Lathia, neal.lathia@cl.cam.ac.uk

This demo application was developed as part of the EPSRC Ubhave (Ubiquitous and
Social Computing for Positive Behaviour Change) Project. For more
information, please visit http://www.emotionsense.org

Permission to use, copy, modify, and/or distribute this software for any
purpose with or without fee is hereby granted, provided that the above
copyright notice and this permission notice appear in all copies.

THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR
IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 ************************************************** */

package com.ubhave.triggermanager.tester.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.ubhave.triggermanager.TriggerException;
import com.ubhave.triggermanager.tester.ExampleTriggerReceiver;
import com.ubhave.triggermanager.tester.R;
import com.ubhave.triggermanager.tester.ui.clock.ClockTriggerActivity;
import com.ubhave.triggermanager.tester.ui.clock.IntervalTriggerActivity;
import com.ubhave.triggermanager.tester.ui.clock.RandomTriggerActivity;
import com.ubhave.triggermanager.triggers.TriggerUtils;

public class TriggerListActivity extends Activity
{
	public final static String LIST_TYPE = "ListType";
	public final static int CLOCK_TRIGGERS = 0;
	public final static int SENSOR_TRIGGERS = 1;

	private final static int[] clockTriggers = new int[] { TriggerUtils.CLOCK_TRIGGER_ONCE, TriggerUtils.CLOCK_TRIGGER_ON_INTERVAL, TriggerUtils.CLOCK_TRIGGER_DAILY_RANDOM, };

	private final static int[] sensorTriggers = new int[] { TriggerUtils.SENSOR_TRIGGER_ACCELEROMETER, TriggerUtils.SENSOR_TRIGGER_MICROPHONE, TriggerUtils.SENSOR_TRIGGER_CALLS, TriggerUtils.SENSOR_TRIGGER_SMS, TriggerUtils.SENSOR_TRIGGER_SCREEN };

	private final static String TITLE = "title";
	private final static String DESCRIPTION = "description";
	private final static String[] from = new String[] { TITLE, DESCRIPTION };
	private final static int[] to = new int[] { R.id.title, R.id.description };

	private int triggerListType;
	private List<HashMap<String, String>> triggerList;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_main);

		Intent intent = getIntent();
		triggerListType = intent.getIntExtra(LIST_TYPE, 0);
		setListContent(triggerListType);

		ListView sensorListView = (ListView) findViewById(R.id.sensorListView);
		sensorListView.setAdapter(new SimpleAdapter(this, triggerList, R.layout.listitem, from, to));
		sensorListView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
			{
				if (triggerListType == CLOCK_TRIGGERS)
				{
					switch (position)
					{
					case 0:
						launch(clockTriggers[position], ClockTriggerActivity.class);
						break;
					case 1:
						launch(clockTriggers[position], IntervalTriggerActivity.class);
						break;
					case 2:
						launch(clockTriggers[position], RandomTriggerActivity.class);
					default:
						break;
					}
				}
			}
		});
	}

	@Override
	public void onResume()
	{
		super.onResume();
		if (Context.NOTIFICATION_SERVICE != null)
		{
			String ns = Context.NOTIFICATION_SERVICE;
			NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
			nMgr.cancel(ExampleTriggerReceiver.NOTIFICATION_ID);
		}
	}

	private void launch(int triggerType, Class<?> target)
	{
		Intent intent = new Intent(this, target);
		intent.putExtra(ExampleAbstractActivity.TRIGGER_TYPE_ID, triggerType);
		startActivity(intent);
	}

	private void setListContent(int triggerListType)
	{
		triggerList = new ArrayList<HashMap<String, String>>();
		int[] selectedTriggers;
		String[] sensorDescriptions;

		if (triggerListType == CLOCK_TRIGGERS)
		{
			selectedTriggers = clockTriggers;
			sensorDescriptions = getResources().getStringArray(R.array.clock_trigger_descriptions);
		}
		else
		{
			selectedTriggers = sensorTriggers;
			sensorDescriptions = getResources().getStringArray(R.array.sensor_trigger_descriptions);
		}

		for (int i = 0; i < selectedTriggers.length; i++)
		{
			try
			{
				HashMap<String, String> entry = new HashMap<String, String>();
				entry.put(TITLE, TriggerUtils.getTriggerName(selectedTriggers[i]));
				entry.put(DESCRIPTION, sensorDescriptions[i]);
				triggerList.add(entry);
			}
			catch (TriggerException e)
			{
				e.printStackTrace();
			}
		}
	}
}
