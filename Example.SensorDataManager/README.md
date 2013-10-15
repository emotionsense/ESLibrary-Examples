ES Sensor Data Manager Example
================================

This example app demonstrate how to use the following ES Library:
* ES Sensor Data Library: [https://github.com/nlathia/SensorDataManager](https://github.com/nlathia/SensorDataManager)

Although it also uses the:
* ES Sensor Library: [https://github.com/nlathia/SensorManager](https://github.com/nlathia/SensorManager)

Device Side
-------------------------------
The library has a number of abstract classes that you can extend to quickly set up your app. You can find them [here](https://github.com/nlathia/SensorDataManager/tree/master/src/com/ubhave/datahandler/loggertypes).

* [AbstractStoreOnlyLogger](https://github.com/nlathia/SensorDataManager/blob/master/src/com/ubhave/datahandler/loggertypes/AbstractStoreOnlyLogger.java) if you just want to store data on the phone. Requires the "android.permission.WRITE_EXTERNAL_STORAGE" permission.
* [AbstractImmediateTransferLogger](https://github.com/nlathia/SensorDataManager/blob/master/src/com/ubhave/datahandler/loggertypes/AbstractImmediateTransferLogger.java) if you want to immediately post data to your server. Requires the "android.permission.INTERNET" permission.
* [AbstractAsyncTransferLogger](https://github.com/nlathia/SensorDataManager/blob/master/src/com/ubhave/datahandler/loggertypes/AbstractAsyncTransferLogger.java) if you want to asynchronously transfer data to your server. Requires both of the above permissions, as well as "android.permission.ACCESS_NETWORK_STATE"

Server Side
-------------------------------
This library posts data to your server if you enable the 'transfer immediate' or 'transfer periodically' options. The ES SensorDataManager library currently expects your server to reply with the string "success" if it has received the data. If you transfer immediately, the data is sent as a JSON object of the form: {"ESDataManagerData":"your_data"}

Check the [WebConnection.java](https://github.com/nlathia/SensorDataManager/blob/master/src/com/ubhave/datahandler/http/WebConnection.java) to see how data is posted.


Questions
-------------------------------
Email [this google group](https://groups.google.com/forum/#!forum/es-library-developers)

Reading
-------------------------------
N. Lathia, K. Rachuri, C. Mascolo, G. Roussos. Open Source Smartphone Libraries for Computational Social Science
In 2nd ACM Workshop on Mobile Systems for Computational Social Science. Zurich, Switzerland. September 8, 2013. 

Download [here](http://www.cl.cam.ac.uk/~nkl25/publications/papers/lathia_mcss2013.pdf)

License
-------------------------------
Copyright (c) 2013, Neal Lathia, neal.lathia@cl.cam.ac.uk

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
