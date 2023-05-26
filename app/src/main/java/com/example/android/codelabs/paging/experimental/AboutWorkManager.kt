package com.example.android.codelabs.paging.experimental

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.work.WorkManager
import androidx.work.Worker
import java.util.concurrent.TimeUnit

class AboutWorkManager : AppCompatActivity(){

//
//    val workManager: WorkManager = WorkManager.getInstance(Context)
//    workManager.enqueue(Builder(MyWorker::class.java).build())
//    workManager.enqueue()
//
//
//
//}
//
//class MyWorker : Worker() {
//    fun doWork(): WorkerResult {
//        Log.d(Companion.TAG, "doWork: start")
//        try {
//            TimeUnit.SECONDS.sleep(10)
//        } catch (e: InterruptedException) {
//            e.printStackTrace()
//        }
//        Log.d(Companion.TAG, "doWork: end")
//        return WorkerResult.SUCCESS
//    }
//
//    companion object {
//        const val TAG = "workmng"
//    }
}