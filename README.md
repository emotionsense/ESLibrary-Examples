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
Basic library usage examples:
* [SensorManagerBasicExample](https://github.com/xsenselabs/ESLibrary-Examples/tree/master/SensorManagerBasicExample). Runs AsyncTasks that grab data from all the sensors, formats them as JSON, and prints the result to Log.d.
* [SensorManagerExample](https://github.com/xsenselabs/ESLibrary-Examples/tree/master/SensorManagerExample). Simple/example app with tabs that lets you select a sensor and collect some data from it.
* [SensorManagerLectureDemo](https://github.com/xsenselabs/ESLibrary-Examples/tree/master/SensorManagerLectureDemo). Example/skeleton app used in the [Mobile and Sensor Systems](http://www.cl.cam.ac.uk/teaching/1314/MobSensSys/materials.html) course in the Computer Laboratory, University of Cambridge.
* [SensorDataManagerExample](https://github.com/xsenselabs/ESLibrary-Examples/tree/master/SensorDataManagerExample). Has examples for the 3 different data manager policies that the Data Manager library supports (store only, transfer-immediate, transfer-async).

Advanced library usage examples:
* [SensingWithAlarmsExample](https://github.com/xsenselabs/ESLibrary-Examples/tree/master/SensingWithAlarmsExample). An example app that uses the Android Alarm Manager to sense on a 60 second interval.
* [SensingWithServiceSubscriptions](https://github.com/xsenselabs/ESLibrary-Examples/tree/master/SensingWithServiceSubscriptionsExample). An example app where you have more than one Service that is sensing from the same sensor.
* [SensingWithMediaCapture](https://github.com/xsenselabs/ESLibrary-Examples/tree/master/SensingWithMediaCapture). An example app that was developed for testing while extending the libraries to store/transfer media (e.g., audio) files.

Test apps created while updating the libraries:
* [TestingAPI19Subscription](https://github.com/xsenselabs/ESLibrary-Examples/tree/master/TestingAPI19Subscription). An example/test app that was developed when extending the libraries to deal with changes introduced in Android API 19.
* [TestingLogQuery](https://github.com/xsenselabs/ESLibrary-Examples/tree/master/TestingLogQuery). An example/test app that shows how to only sense for call/sms logs that are more recent than a pre-defined date.

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
