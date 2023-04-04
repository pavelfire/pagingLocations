package com.example.android.codelabs.rxjava

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.android.codelabs.paging.R

class RxExampleActivity : AppCompatActivity() {

    lateinit var viewModel: RxExampleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(RxExampleViewModel::class.java)
        initListeners()
    }

    private fun initListeners() {
        findViewById<Button>(R.id.btn_badge).setOnClickListener {
            viewModel.fetchShowsList()
                //.sampleObservable()
        }
    }

}