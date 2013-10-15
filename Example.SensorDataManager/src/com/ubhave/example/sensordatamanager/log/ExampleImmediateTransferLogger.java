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

package com.ubhave.example.sensordatamanager.log;

import android.content.Context;

import com.ubhave.datahandler.loggertypes.AbstractImmediateTransferLogger;
import com.ubhave.example.sensordatamanager.HiddenConstants;

public class ExampleImmediateTransferLogger extends AbstractImmediateTransferLogger
{

	public ExampleImmediateTransferLogger(Context context)
	{
		super(context);
	}

	@Override
	protected String getLocalStorageDirectoryName()
	{
		return "ExampleSensorDataManager-LocalData";
	}

	@Override
	protected String getUserId()
	{
		return "ExampleSensorDataManagerUser";
	}

	@Override
	protected String getDataPostURL()
	{
		return HiddenConstants.YOUR_SERVERS_POST_URL;
	}

	@Override
	protected String getPostPassword()
	{
		return HiddenConstants.YOUR_SERVERS_POST_PASSWORD_IF_ANY;
	}
}
