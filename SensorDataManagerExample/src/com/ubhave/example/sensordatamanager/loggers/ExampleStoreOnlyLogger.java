/* **************************************************
 Copyright (c) 2012, University of Cambridge
 Neal Lathia, neal.lathia@cl.cam.ac.uk

For more information, please visit http://www.emotionsense.org

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

package com.ubhave.example.sensordatamanager.loggers;

import android.content.Context;

import com.ubhave.datahandler.except.DataHandlerException;
import com.ubhave.datahandler.loggertypes.AbstractStoreOnlyLogger;
import com.ubhave.sensormanager.ESException;

public class ExampleStoreOnlyLogger extends AbstractStoreOnlyLogger
{
	public ExampleStoreOnlyLogger(final Context context, int storageType) throws DataHandlerException, ESException
	{
		super(context, storageType);
	}

	@Override
	protected String getStorageName()
	{
		return "ExampleSensorDataManager-LocalDatabase";
	}

	@Override
	protected String getUniqueUserId()
	{
		/*
		 * Note: Should be unique to this user, not a static string
		 */
		return "ExampleSensorDataManagerUser";
	}
	
	@Override
	protected String getDeviceId()
	{
		/*
		 * Note: Should be unique to this device, not a static string
		 */
		return "ExampleSensorDataManagerDevice";
	}

	@Override
	protected boolean shouldPrintLogMessages()
	{
		/*
		 * Turn on/off Log.d messages
		 */
		return true;
	}
}
