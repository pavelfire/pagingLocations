package com.example.android.codelabs.networking

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.android.codelabs.paging.databinding.ActivityLocationsBinding
import kotlinx.coroutines.*

class ActivityExceptionsExample : AppCompatActivity() {

    private lateinit var binding: ActivityLocationsBinding

    private val parentJob = SupervisorJob()
    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.d("TEST", "${coroutineContext[CoroutineName]}")
        Log.d("TEST", "$throwable")
    }
    private val scope = CoroutineScope(parentJob + exceptionHandler)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val child1 = scope.launch(CoroutineName("job1")) {
            delay(1000)
            Log.d("TEST", "1 finished")
        }
        val child2 = scope.launch(CoroutineName("job2")) {
            delay(900)
            Log.d("TEST", "2 finished")
        }
        val child3 = scope.launch(CoroutineName("job3")) {
            delay(800)
            throw (RuntimeException())
            Log.d("TEST", "3 finished")
        }
        Log.d("TEST", "child1 ${parentJob.children.contains(child1)}")
        Log.d("TEST", "child2 ${parentJob.children.contains(child2)}")
        Log.d("TEST", "child3 ${parentJob.children.contains(child3)}")
    }
}