package com.example.android.codelabs.paging.experimental.components

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android.codelabs.paging.databinding.ActivityServiceBinding
// https://www.youtube.com/watch?v=K13rJh6ufs4&list=PLtNLwG5hHZ-I7YBaPLGnqQxSYIQx6_wJQ&index=3
// 1:13
class ServiceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityServiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.btnPlay.setOnClickListener {

        }
    }

    private fun updateService() {
//        if (ServiceManager.isStarted) {
//            // stop service
//        } else {
//            // start service
//        }
    }
}