package com.example.android.codelabs.paging.experimental.components

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android.codelabs.paging.R
import com.example.android.codelabs.paging.databinding.ActivityLocationsBinding
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
            updateService()
        }
    }

    private fun updateService() {
        val intent = Intent(this, PlayerService::class.java)
        if (ServiceManager.isStarted) {
            stopService(intent)
            binding.btnPlay.text = getString(R.string.btn_play)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                binding.btnPlay.icon = getDrawable(R.drawable.baseline_play_arrow_24)
            }
        } else {
            startService(intent)
            binding.btnPlay.text = getString(R.string.btn_pause)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                binding.btnPlay.icon = getDrawable(R.drawable.baseline_pause_24)
            }
        }
    }
}