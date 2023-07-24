package com.example.android.codelabs.paging.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.android.codelabs.paging.databinding.ActivityThreeBinding
import kotlin.random.Random

class ActivityThree : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityThreeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        Log.d(Companion.TAG, "onCreate: ")
        binding.emptyList.text = TAG
        binding.linLayout.setBackgroundColor(
            Color.argb(
            255, Random(255).nextInt(), Random(255).nextInt(), Random(255).nextInt()))
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

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d(Companion.TAG, "onNewIntent: ")
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
        private const val TAG = "ActivityThree"
    }
}