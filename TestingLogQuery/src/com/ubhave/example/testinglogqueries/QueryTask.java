package com.ubhave.example.testinglogqueries;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ubhave.sensormanager.ESSensorManager;
import com.ubhave.sensormanager.config.GlobalConfig;
import com.ubhave.sensormanager.config.pull.ContentReaderConfig;
import com.ubhave.sensormanager.data.pull.AbstractContentReaderEntry;
import com.ubhave.sensormanager.data.pull.AbstractContentReaderListData;
import com.ubhave.sensormanager.sensors.SensorUtils;

public class QueryTask extends AsyncTask<Void, Void, Void>
{
	private final static String TAG = "ContentReaderSensor";
	private final Context context;

	public QueryTask(final Context context)
	{
		this.context = context;
	}

	@Override
	protected Void doInBackground(Void... params)
	{
		try
		{
			ESSensorManager sensorManager = ESSensorManager.getSensorManager(context);
			sensorManager.setGlobalConfig(GlobalConfig.PRINT_LOG_D_MESSAGES, true);

			mostRecent(sensorManager, SensorUtils.SENSOR_TYPE_SMS_CONTENT_READER);
			mostRecent(sensorManager, SensorUtils.SENSOR_TYPE_CALL_CONTENT_READER);
			
			sinceYesterday(sensorManager, SensorUtils.SENSOR_TYPE_SMS_CONTENT_READER);
			sinceYesterday(sensorManager, SensorUtils.SENSOR_TYPE_CALL_CONTENT_READER);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	private void mostRecent(ESSensorManager sensorManager, int sensorType) throws Exception
	{
		String sensorName = SensorUtils.getSensorName(sensorType);
		Calendar calendar = Calendar.getInstance();

		ArrayList<AbstractContentReaderEntry> entries = query(sensorManager, sensorType, ContentReaderConfig.NO_TIME_LIMIT);
		Log.d(TAG, sensorName + ", first query: " + entries.size());

		AbstractContentReaderEntry mostRecent = getMostRecent(entries);
		sensorManager.setSensorConfig(sensorType, ContentReaderConfig.TIME_LIMIT_MILLIS, mostRecent.getTimestamp());
		
		calendar.setTimeInMillis(mostRecent.getTimestamp());
		Log.d(TAG, sensorName + ", most recent is: " + calendar.getTime().toString());

		assert(query(sensorManager, sensorType, mostRecent.getTimestamp()).size() == 1);
	}

	private void sinceYesterday(ESSensorManager sensorManager, int sensorType) throws Exception
	{
		String sensorName = SensorUtils.getSensorName(sensorType);
		long yesterday = System.currentTimeMillis() - (1000L * 60 * 60 * 24);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(yesterday);
		
		Log.d(TAG, sensorName + ", yesterday: " + calendar.getTime().toString());
		ArrayList<AbstractContentReaderEntry> entries = query(sensorManager, sensorType, yesterday);
		
		
		for (AbstractContentReaderEntry entry : entries)
		{
			calendar.setTimeInMillis(entry.getTimestamp());
			Log.d(TAG, sensorName+", event: "+calendar.getTime().toString());
		}
		Log.d(TAG, sensorName + ", events since yesterday: " + entries.size());
	}

	private ArrayList<AbstractContentReaderEntry> query(ESSensorManager sensorManager, int sensorType, long timeLimit) throws Exception
	{
		sensorManager.setSensorConfig(sensorType, ContentReaderConfig.TIME_LIMIT_MILLIS, timeLimit);
		AbstractContentReaderListData data = (AbstractContentReaderListData) sensorManager.getDataFromSensor(sensorType);
		return data.getContentList();
	}

	private AbstractContentReaderEntry getMostRecent(ArrayList<AbstractContentReaderEntry> entries)
	{
		AbstractContentReaderEntry mostRecent = null;
		for (AbstractContentReaderEntry entry : entries)
		{
			try
			{
				if (mostRecent == null || entry.getTimestamp() > mostRecent.getTimestamp())
				{
					mostRecent = entry;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return mostRecent;
	}

}
