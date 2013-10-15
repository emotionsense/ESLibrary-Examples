/* **************************************************
 Copyright (c) 2012, University of Cambridge
 Neal Lathia, neal.lathia@cl.cam.ac.uk
 Kiran Rachuri, kiran.rachuri@cl.cam.ac.uk

This library was developed as part of the EPSRC Ubhave (Ubiquitous and
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

import android.content.Context;
import android.content.SharedPreferences;

public class SubscriptionIds
{
	private static final String subscriptionIds = "subscriptionPreferences";
	
	public static void setId(String triggerType, int subscriptionId)
	{
		SharedPreferences preferences = ApplicationContext.getContext().getSharedPreferences(subscriptionIds, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt(triggerType, subscriptionId);
		editor.commit();
	}
	
	public static boolean isSubscribed(String triggerType)
	{
		SharedPreferences preferences = ApplicationContext.getContext().getSharedPreferences(subscriptionIds, Context.MODE_PRIVATE);
		return preferences.contains(triggerType);
	}
	
	public static void removeSubscription(String triggerType)
	{
		SharedPreferences preferences = ApplicationContext.getContext().getSharedPreferences(subscriptionIds, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.remove(triggerType);
		editor.commit();
	}
	
	public static int getId(String triggerType)
	{
		SharedPreferences preferences = ApplicationContext.getContext().getSharedPreferences(subscriptionIds, Context.MODE_PRIVATE);
		return preferences.getInt(triggerType, 0);
	}
}
