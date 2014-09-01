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

package com.ubhave.sensormanager.tester.pull;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.ubhave.dataformatter.DataFormatter;
import com.ubhave.dataformatter.json.JSONFormatter;
import com.ubhave.datahandler.except.DataHandlerException;
import com.ubhave.sensormanager.ESException;
import com.ubhave.sensormanager.data.SensorData;
import com.ubhave.sensormanager.tester.ExampleAbstractActivity;
import com.ubhave.sensormanager.tester.R;

public abstract class AbstractPullSensorExampleActivity extends ExampleAbstractActivity
{
	private final int SAMPLING = 2;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		/*
		 * Additional UI button for pull sensor
		 */
		enableSenseOnceButton();
		enableUpdateConfigButton();
	}

	protected int getInterfaceLayout()
	{
		return R.layout.pull_sensor_layout;
	}

	/*
	 * UI Components (Buttons, Text Fields)
	 */
	private void enableSenseOnceButton()
	{
		Button button = (Button) findViewById(R.id.pullOnceButton);
		button.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				pullDataOnce();
			}
		});
	}

	protected abstract void enableUpdateConfigButton();

	@Override
	protected String getStatusString()
	{
		Resources r = getResources();
		if (currentStatus == SAMPLING)
		{
			return r.getString(R.string.sampling);
		}
		else
		{
			return super.getStatusString();
		}
	}

	@Override
	protected void subscribe()
	{
		if (currentStatus == SAMPLING)
		{
			Toast.makeText(this, "Cannot subscribe while pulling data.", Toast.LENGTH_SHORT).show();
		}
		else
		{
			Log.d("Pull Sensor", "Subscribing");
			super.subscribe();
		}
	}

	private void pullDataOnce()
	{
		if (sensorDataListener.isSubscribed())
		{
			Toast.makeText(AbstractPullSensorExampleActivity.this, "Cannot pull data while sensor listener is subscribed.", Toast.LENGTH_SHORT).show();
		}
		else
		{
			try
			{
				new SampleOnceTask(selectedSensorType)
				{
					@Override
					public void onPreExecute()
					{
						setSensorStatusField(SAMPLING);
					}

					@Override
					public void onPostExecute(SensorData data)
					{
						if (data != null)
						{
							try
							{
								JSONFormatter formatter = DataFormatter.getJSONFormatter(AbstractPullSensorExampleActivity.this, selectedSensorType);
								updateUI(formatter.toJSON(data).toString());
							}
							catch (DataHandlerException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						else updateUI("Null (e.g., sensor off). Error message:\n"+errorMessage);
						setSensorStatusField(UNSUBSCRIBED);
					}

				}.execute();
			}
			catch (ESException e)
			{
				e.printStackTrace();
				setSensorDataField(e.getMessage());
			}

		}
	}
}
