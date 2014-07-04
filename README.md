ES Library Example Apps
================================

These example apps demonstrate how to use the ES Libraries.

* Sensor Manager Library: [https://github.com/xsenselabs/SensorManager](https://github.com/xsenselabs/SensorManager)
* Sensor Data Manager Library: [https://github.com/xsenselabs/SensorDataManager](https://github.com/xsenselabs/SensorDataManager)

Questions
-------------------------------
* Read the [Sensor Manager library documentation](https://github.com/xsenselabs/SensorManager/tree/master/docs)
* Email [this google group](https://groups.google.com/forum/#!forum/es-library-developers)

Contents
-------------------------------
* [BasicSensorDataExample](https://github.com/xsenselabs/ESLibrary-Examples/tree/master/BasicSensorDataExample). Runs AsyncTasks that grab data from all the sensors, formats them as JSON, and prints the result to Log.d.
* [ES2LibraryDemo](https://github.com/xsenselabs/ESLibrary-Examples/tree/master/ES2LibraryDemo). Example/skeleton app used in the [Mobile and Sensor Systems](http://www.cl.cam.ac.uk/teaching/1314/MobSensSys/materials.html) course in the Computer Laboratory, University of Cambridge. 
* [Example.SensorManager](https://github.com/xsenselabs/ESLibrary-Examples/tree/master/Example.SensorManager). Simple/example app with tabs that lets you select a sensor and collect some data from it.
* [Example.SensorDataManager](https://github.com/xsenselabs/ESLibrary-Examples/blob/master/Example.SensorDataManager). Has examples for the 3 different data manager policies that the Data Manager library supports (store only, transfer-immediate, transfer-async).
* [Example.UploadMedia](https://github.com/xsenselabs/ESLibrary-Examples/blob/master/Example.UploadMedia). An example app that was developed for testing while extending the libraries to store/transfer media (e.g., audio) files.
* [MultipleServiceSubscriptionExample](https://github.com/xsenselabs/ESLibrary-Examples/tree/master/MultipleServiceSubscriptionExample). An example app where you have more than one Service that is sensing from the same sensor.
* [SensingWithAlarmsExample](https://github.com/xsenselabs/ESLibrary-Examples/tree/master/SensingWithAlarmsExample). An example app that uses the Android Alarm Manager to sense on a 60 second interval.
* [SubscribeAPI19](https://github.com/xsenselabs/ESLibrary-Examples/blob/master/SubscribeAPI19). An example/test app that was developed when extending the libraries to deal with changes introduced in Android API 19.

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
