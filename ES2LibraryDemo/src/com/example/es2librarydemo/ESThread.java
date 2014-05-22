package com.example.es2librarydemo;

import android.content.Context;
import android.os.Looper;

import com.ubhave.sensormanager.ESException;
import com.ubhave.sensormanager.ESSensorManager;

public class ESThread extends Thread
{
	Context context;
	
	public ESThread(Context context)
	{
		this.context = context;
	}

	public void run()
	{
		try
		{
			Looper.prepare();
			ESSensorManager.getSensorManager(context);
		}
		catch (ESException e)
		{
			e.printStackTrace();
		}
	}

}
