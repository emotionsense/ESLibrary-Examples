package com.example.es2librarydemo;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class DemoActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_demo);

		initialise();

		Button startButton = (Button) findViewById(R.id.start_button);
		startButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				onButtonClicked();
			}
		});
	}

	private double captureSensorData()
	{	
		// Blank: you have to implement this!
		return 0.0;
	}

	private void onButtonClicked()
	{

		TextView textView = (TextView) findViewById(R.id.status_textView);
		textView.setText("Sensing...");

		new AsyncTask<Void, Void, Void>()
		{
//			float stdDev = 0.0f;
//			final TextView textView = (TextView) findViewById(R.id.status_textView);

			@Override
			protected Void doInBackground(Void... params)
			{
//				stdDev = (float) captureSensorData();
				return null;
			}

			@Override
			protected void onPostExecute(Void params)
			{
			}

		}.execute();
	}

	public static float mean(float[] input)
	{
		float sum = 0.0f;
		for (float i : input)
		{
			sum += i;
		}
		return sum / (float) input.length;
	}

	public static float stdDev(float[] input)
	{
		float means = mean(input);
		float stddev = 0.0f;
		for (int j = 0; j < input.length; j++)
		{
			stddev += (means - input[j]) * (means - input[j]);
		}
		stddev /= (float) input.length;
		stddev = (float) Math.sqrt(stddev);
		return stddev;
	}

	private void initialise()
	{
		// initialise
		ESThread thread = new ESThread(this);
		thread.start();
		try
		{
			Thread.sleep(2000);
		}
		catch (InterruptedException e1)
		{
		}
	}
}
