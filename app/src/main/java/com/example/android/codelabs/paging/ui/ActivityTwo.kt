package com.example.android.codelabs.paging.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.android.codelabs.paging.databinding.ActivityTwoBinding
import kotlin.random.Random

class ActivityTwo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTwoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        Log.d(TAG, "onCreate: ")
//        finish()
//        Log.d(TAG, "finish")
        binding.emptyList.text = TAG

        var color = Random(255)
        binding.linLayout.setBackgroundColor(
            Color.argb(
            255, color.nextInt(), color.nextInt(), color.nextInt()))
        binding.btnActOne.setOnClickListener {
            val intent = Intent(this, ActivityOne::class.java)
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
    }

    // тут поэкспериментируйте с noinline и crossinline и посмотрите, что будет выводиться в логи
    fun doSmth(action: () -> Unit) {
        println("start")
        action.invoke()
        println("finish doSmth")
    }

    fun doWork(){
        doSmth {
            println("action")
            return@doSmth
        }
        println("finish doWork")
    }

    fun main() {
        doWork()

    }

    override fun onStart() {
        super.onStart()
        Log.d(Companion.TAG, "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.d(Companion.TAG, "onResume: ")
    }

    override fun onPause() {
        super.onPause()
        Log.d(Companion.TAG, "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        Log.d(Companion.TAG, "onStop: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(Companion.TAG, "onDestroy: ")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(Companion.TAG, "onRestart: ")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Log.d(Companion.TAG, "onBackPressed: ")
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
        private const val TAG = "ActivityTwo"
    }
}