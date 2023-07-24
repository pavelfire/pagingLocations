package com.example.android.codelabs.paging.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.android.codelabs.paging.databinding.ActivityOneBinding
import kotlin.random.Random

class ActivityOne : AppCompatActivity() {

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

    companion object {
        private const val TAG = "ActivityOne"
    }
}
