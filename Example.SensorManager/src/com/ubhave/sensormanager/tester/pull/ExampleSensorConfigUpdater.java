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

import com.ubhave.sensormanager.ESException;
import com.ubhave.sensormanager.ESSensorManager;
import com.ubhave.sensormanager.ESSensorManagerInterface;
import com.ubhave.sensormanager.config.sensors.pull.PullSensorConfig;
import com.ubhave.sensormanager.sensors.SensorUtils;
import com.ubhave.sensormanager.tester.ApplicationContext;

public class ExampleSensorConfigUpdater
{
	private final int sensorType;
	private ESSensorManagerInterface sensorManager;

	public ExampleSensorConfigUpdater(int sensor)
	{
		this.sensorType = sensor;
		try
		{
			sensorManager = ESSensorManager.getSensorManager(ApplicationContext.getContext());
		}
		catch (ESException e)
		{
			e.printStackTrace();
		}
	}

	public String getSensorName()
	{
		try
		{
			return SensorUtils.getSensorName(sensorType);
		}
		catch (ESException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public void setSensorSampleWindow(long millis)
	{
		try
		{
			sensorManager.setSensorConfig(sensorType, PullSensorConfig.SENSE_WINDOW_LENGTH_MILLIS, millis);
		}
		catch (ESException e)
		{
			e.printStackTrace();
		}
	}

	public void setSensorSleepWindow(long millis)
	{
		try
		{
			sensorManager.setSensorConfig(sensorType, PullSensorConfig.POST_SENSE_SLEEP_LENGTH_MILLIS, millis);
		}
		catch (ESException e)
		{
			e.printStackTrace();
		}
	}

	public int getSensorSampleWindow()
	{
		try
		{
			Long sampleWindow = (Long) sensorManager.getSensorConfigValue(sensorType, PullSensorConfig.SENSE_WINDOW_LENGTH_MILLIS);
			return (int) (sampleWindow / 1000);
		}
		catch (ESException e)
		{
			e.printStackTrace();
			return 0;
		}
	}

	public int getSensorSleepWindow()
	{
		try
		{
			Long sampleWindow = (Long) sensorManager.getSensorConfigValue(sensorType, PullSensorConfig.POST_SENSE_SLEEP_LENGTH_MILLIS);
			return (int) (sampleWindow / 1000);
		}
		catch (ESException e)
		{
			e.printStackTrace();
			return 0;
		}
	}

}
