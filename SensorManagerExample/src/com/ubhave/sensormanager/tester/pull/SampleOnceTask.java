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

import android.os.AsyncTask;
import android.util.Log;

import com.ubhave.sensormanager.ESException;
import com.ubhave.sensormanager.ESSensorManager;
import com.ubhave.sensormanager.data.SensorData;
import com.ubhave.sensormanager.tester.ApplicationContext;

public class SampleOnceTask extends AsyncTask<Void, Void, SensorData>
{
	private final ESSensorManager sensorManager;
	private final int sensorType;
	protected String errorMessage;

	public SampleOnceTask(int sensorType) throws ESException
	{
		this.sensorType = sensorType;
		sensorManager = ESSensorManager.getSensorManager(ApplicationContext.getContext());
	}

	@Override
	protected SensorData doInBackground(Void... params)
	{
		try
		{
			Log.d("Sensor Task", "Sampling from Sensor");
			return sensorManager.getDataFromSensor(sensorType);
		}
		catch (ESException e)
		{
			e.printStackTrace();
			errorMessage = e.getMessage();
			return null;
		}
	}

}
