package com.example.android.codelabs.networking

import kotlinx.coroutines.*

class CoroutineCancel {
}

suspend fun work(){
    val startTime = System.currentTimeMillis()
    var nextPrintTime = startTime
    var i = 0
    while (i < 5) {
        yield()
        // print a message twice a second
        if (System.currentTimeMillis() >= nextPrintTime) {
            println("Hello ${i++}")
            nextPrintTime += 500L
        }
    }
}
fun main(args: Array<String>) = runBlocking<Unit> {
    val job = launch (Dispatchers.Default) {
        try {
            work()
        } catch (e: CancellationException){
            println("Work cancelled!")
        } finally {
            println("Clean up!")
        }
    }
    delay(1000L)
    println("Cancel!")
    job.cancel()
    println("Done!")
}