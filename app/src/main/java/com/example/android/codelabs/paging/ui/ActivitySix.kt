package com.example.android.codelabs.paging.ui

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.android.codelabs.paging.databinding.ActivityOneBinding
import kotlin.random.Random

class ActivitySix : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityOneBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        Log.d(TAG, "onCreate: ")
        binding.emptyList.text = TAG

        val color = Random(255)
        binding.linLayout.setBackgroundColor(Color.argb(
            255, color.nextInt(255), color.nextInt(), color.nextInt()))
        binding.btnActOne.setOnClickListener {
            val intent = Intent(baseContext, ActivitySix::class.java)
            startActivity(intent)
        }
        binding.btnActTwo.setOnClickListener {
            val intent = Intent(this, ActivityTwo::class.java)
            startActivity(intent)
        }
        binding.btnActThree.setOnClickListener {
            val intent = Intent(this, ActivityThree::class.java)
            startActivity(intent)
        }
        binding.btnActFour.setOnClickListener {
            val intent = Intent(this, ActivityFour::class.java)
            startActivity(intent)
        }
        binding.btnActFive.setOnClickListener {
            val intent = Intent(this, ActivityFive::class.java)
            startActivity(intent)
        }
        binding.btnServiceStart.setOnClickListener {
            val intent = Intent(this, RSSPullService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent)
            }else{
                startService(intent)
            }
        }
        binding.btnServiceStop.setOnClickListener {
            val intent = Intent(this, RSSPullService::class.java)
            stopService(intent)
        }
    }

    inline fun <reified T> filterList(list: List<Any>, noinline action: ()-> Unit): List<T> {
        val resultList = mutableListOf<T>()
        for (element in list) {
            if (element is T) {
                resultList.add(element)
            } else {
                saveResult(action)
            }
        }
        return resultList
    }

    fun saveResult(action: ()-> Unit){
        action.invoke()
    }

//    fun <T> filterList(list: List<Any>, action: ()-> Unit): List<T> {
//        val resultList = mutableListOf<T>()
//        for (element in list) {
//            if (element is T) {
//                resultList.add(element)
//            } else {
//                saveResult(action)
//            }
//        }
//        return resultList
//    }
//
//    fun saveResult(action: ()-> Unit){
//        action.invoke()
//    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart: ")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Log.d(TAG, "onBackPressed: ")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState: ")
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?
    ) {
        super.onRestoreInstanceState(savedInstanceState, persistentState)
        Log.d(TAG, "onRestoreInstanceState: ")
    }

    companion object {
        private const val TAG = "ActivitySix"
    }
}
