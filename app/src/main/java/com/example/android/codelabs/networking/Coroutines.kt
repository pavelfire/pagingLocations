package com.example.android.codelabs.networking

import kotlinx.coroutines.*

suspend fun main() = coroutineScope{
    launch(SupervisorJob()){
        for(i in 0..5){
            delay(400L)
            println(i)
        }
    }

    coroutineScope {
        doWork()

    }

    supervisorScope {

    }
    coroutineContext

    println("Hello Coroutines")

    val job = coroutineScope {
        // Родительский элемент новой корутины - CoroutineScope
        val result = async {
            // New coroutine that has the coroutine started by
            // launch as a parent
            //println("Hello Coroutines Job")
            "Job async"
        }//.await()
        println(result.await())
        result.cancel()
    }

}

suspend fun doWork(){
    for(i in 0..5){
        println("i $i")
        delay(400L)
    }
}

