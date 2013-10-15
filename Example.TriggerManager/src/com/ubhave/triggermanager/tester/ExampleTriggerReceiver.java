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

package com.ubhave.triggermanager.tester;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.ubhave.triggermanager.ESTriggerManager;
import com.ubhave.triggermanager.TriggerException;
import com.ubhave.triggermanager.TriggerReceiver;
import com.ubhave.triggermanager.config.TriggerConfig;
import com.ubhave.triggermanager.tester.ui.ExampleAbstractActivity;
import com.ubhave.triggermanager.tester.ui.MainActivity;
import com.ubhave.triggermanager.triggers.TriggerUtils;

public class ExampleTriggerReceiver implements TriggerReceiver
{
	private final static String LOG_TAG = "Trigger Receiver";
	public final static int NOTIFICATION_ID = 1234;

	private final String triggerName;

	private final ESTriggerManager triggerManager;
	private ExampleAbstractActivity userInterface;

	private int triggerType, triggerSubscriptionId;
	private boolean isSubscribed;

	public ExampleTriggerReceiver(int triggerType, ExampleAbstractActivity ui) throws TriggerException
	{
		this.triggerType = triggerType;
		this.userInterface = ui;
		this.triggerName = TriggerUtils.getTriggerName(triggerType);
		this.triggerSubscriptionId = SubscriptionIds.getId(triggerName);
		this.triggerManager = ESTriggerManager.getTriggerManager(ApplicationContext.getContext());
	}

	public void subscribeToTrigger(final TriggerConfig params)
	{
		try
		{
			triggerSubscriptionId = triggerManager.addTrigger(triggerType, this, params);
			SubscriptionIds.setId(triggerName, triggerSubscriptionId);
			isSubscribed = true;
			
			Log.d("TriggerReceiver", "Trigger subscribed: " + triggerSubscriptionId);
			Toast.makeText(ApplicationContext.getContext(), "Trigger subscribed: " + triggerSubscriptionId, Toast.LENGTH_SHORT).show();
		}
		catch (TriggerException e)
		{
			Toast.makeText(ApplicationContext.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}
	
	public boolean isSubscribed()
	{
		return isSubscribed;
	}

	public void unsubscribeFromTrigger(String caller)
	{
		try
		{
			triggerManager.removeTrigger(triggerSubscriptionId);
			SubscriptionIds.removeSubscription(triggerName);
			isSubscribed = false;
			
			Log.d("TriggerReceiver", "Trigger removed: " + triggerSubscriptionId+", "+caller);
			Toast.makeText(ApplicationContext.getContext(), "Trigger removed: " + triggerSubscriptionId, Toast.LENGTH_SHORT).show();
		}
		catch (TriggerException e)
		{
			Toast.makeText(ApplicationContext.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onNotificationTriggered(int triggerId)
	{
		Log.d(LOG_TAG, "onNotificationTriggered");
		Context context = ApplicationContext.getContext();
		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		String message;
		try
		{
			message = "Trigger: " + TriggerUtils.getTriggerName(triggerType);
		}
		catch (TriggerException e)
		{
			message = "Unknown trigger";
		}

		Notification notification = new Notification(android.R.drawable.star_on, message, System.currentTimeMillis());

		notification.defaults |= Notification.DEFAULT_LIGHTS;
		notification.defaults |= Notification.DEFAULT_SOUND;

		Intent notificationIntent = new Intent(context, MainActivity.class);
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		notification.setLatestEventInfo(context, "Trigger Notification", message, PendingIntent.getActivity(context, 1, notificationIntent, 0));

		mNotificationManager.notify(NOTIFICATION_ID, notification);
		userInterface.notificationTriggered();
	}

}
