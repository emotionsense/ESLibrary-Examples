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

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.ubhave.triggermanager.tester.R;

public class MainActivity extends TabActivity
{

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		TabHost tabHost = getTabHost();
		
        TabSpec clockTriggers = tabHost.newTabSpec("Clock Triggers");
        clockTriggers.setIndicator("Clock Triggers", null);
        Intent pullSensorIntent = new Intent(this, TriggerListActivity.class);
        pullSensorIntent.putExtra(TriggerListActivity.LIST_TYPE, TriggerListActivity.CLOCK_TRIGGERS);
        clockTriggers.setContent(pullSensorIntent);
 
        tabHost.addTab(clockTriggers);
        
        TabSpec sensorTriggers = tabHost.newTabSpec("Sensor Triggers");
        sensorTriggers.setIndicator("Sensor Triggers", null);
        Intent pushSensorIntent = new Intent(this, TriggerListActivity.class);
        pushSensorIntent.putExtra(TriggerListActivity.LIST_TYPE, TriggerListActivity.SENSOR_TRIGGERS);
        sensorTriggers.setContent(pushSensorIntent);
 
        tabHost.addTab(sensorTriggers);
	}

}
