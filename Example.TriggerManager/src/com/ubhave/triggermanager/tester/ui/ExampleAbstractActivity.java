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

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.ubhave.triggermanager.config.TriggerConfig;
import com.ubhave.triggermanager.tester.ExampleTriggerReceiver;
import com.ubhave.triggermanager.tester.R;

public abstract class ExampleAbstractActivity extends Activity
{
	public final static String TRIGGER_TYPE_ID = "triggerTypeId";

	protected ExampleTriggerReceiver triggerReceiver;
	protected int selectedTriggerType, currentStatus;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		/*
		 * Instantiate the trigger receiver
		 */
		try
		{
			selectedTriggerType = getIntent().getIntExtra(TRIGGER_TYPE_ID, -1);
			triggerReceiver = new ExampleTriggerReceiver(selectedTriggerType, this);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		/*
		 * Create the user interface
		 */
		setContentView(getInterfaceLayout());
		enableStartTriggerButton();
		enableStopTriggerButton();
	}

	public void notificationTriggered()
	{
		// Do nothing
	}

	protected abstract int getInterfaceLayout();

	private void enableStartTriggerButton()
	{
		Button button = (Button) findViewById(R.id.enableTriggerButton);
		button.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				subscribe();
			}
		});
	}

	private void enableStopTriggerButton()
	{
		Button button = (Button) findViewById(R.id.disableTriggerButton);
		button.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				unsubscribe("called manually");
			}
		});
	}
	
	protected boolean isSubscribed()
	{
		return triggerReceiver.isSubscribed();
	}

	protected void subscribe()
	{
		triggerReceiver.subscribeToTrigger(getParameters());
	}

	protected abstract TriggerConfig getParameters();

	protected void unsubscribe(String caller)
	{
		triggerReceiver.unsubscribeFromTrigger(caller);
	}

}
