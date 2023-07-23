package com.example.android.codelabs.paging.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.android.codelabs.paging.databinding.ActivityTwoBinding

class ActivityTwo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTwoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        Log.d(Companion.TAG, "onCreate: ")

        binding.btnActThree.setOnClickListener {
            val intent = Intent(this, ActivityTwo::class.java)
            startActivity(intent)
        }
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

    companion object {
        private const val TAG = "ActivityTwo"
    }
}