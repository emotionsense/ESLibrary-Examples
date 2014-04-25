package com.ubhave.example.subscribeapi19;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.util.Log;

public class BatterySensor extends BroadcastReceiver
{
	private final static String LOG_TAG = "BatterySensor";
	private final static String CONNECTED = "android.intent.action.ACTION_POWER_CONNECTED";
	private final static String DISCONNECTED = "android.intent.action.ACTION_POWER_DISCONNECTED";
	private final static String LOW = "android.intent.action.ACTION_BATTERY_LOW";
	private final static String OK = "android.intent.action.ACTION_BATTERY_OKAY";
	private final Context context;

	public BatterySensor(final Context context)
	{
		this.context = context;
	}

	public void sense()
	{
		IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		Intent batteryStatus = context.registerReceiver(null, ifilter);

		// Are we charging / charged?
		int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
		boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL;

		// How are we charging?
		int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
		boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
		boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;

		Log.d(LOG_TAG, "isCharging: " + isCharging);
		Log.d(LOG_TAG, "charge USB: " + usbCharge + ", charge AC: " + acCharge);
		
		IntentFilter[] filters = new IntentFilter[]{
				new IntentFilter(CONNECTED),
				new IntentFilter(DISCONNECTED)
		};
		
		for (IntentFilter filter : filters)
		{
			context.registerReceiver(this, filter);
		}
	}

	@Override
	public void onReceive(Context context, Intent intent)
	{
		int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                            status == BatteryManager.BATTERY_STATUS_FULL;
    
        int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
        boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
        
        Log.d(LOG_TAG, "isCharging: " + isCharging);
		Log.d(LOG_TAG, "charge USB: " + usbCharge + ", charge AC: " + acCharge);
	}
}
