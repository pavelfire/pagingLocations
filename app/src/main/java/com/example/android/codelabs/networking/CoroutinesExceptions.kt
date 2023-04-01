package com.example.android.codelabs.networking

import kotlinx.coroutines.*

suspend fun main(): Unit = runBlocking {
    val execHa = CoroutineExceptionHandler{
        coroutineContext, throwable ->
        println("throwable")
    }
    val parentJob = Job()

    launch {
        val child1 = launch {
            delay(900)
            println("finished 1 ${this.toString()}")
        }

        val child2 = launch {
            delay(900)
            throw RuntimeException()
            println("finished 2 ${this.toString()}")
        }

        val child3 = launch {
            delay(900)
            println("finished 3 ${this.toString()}")
        }

        child1.join()
        child2.join()
        child3.join()
    }
}