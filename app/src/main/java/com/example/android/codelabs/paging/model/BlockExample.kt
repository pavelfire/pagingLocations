package com.example.android.codelabs.paging.model

import com.example.android.codelabs.paging.model.counterMine.sharedCounter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.runBlocking

object counterMine {
    @Volatile
    var sharedCounter = 0
}
fun main() = runBlocking {

    var sharedCounter = 0

    val scope = CoroutineScope(newFixedThreadPoolContext(4, "synchronizationPool"))
    scope.launch {
        val coroutines = 1.rangeTo(1000).map {
            launch {
                for(i in 1..1000){
                        sharedCounter++
                }
            }
        }

        coroutines.forEach {
                corotuine->
            corotuine.join()
        }
    }.join()

    println("The number of shared counter should be 10000000, but actually is $sharedCounter")
}

/*
Лучшие практики Java
https://proglib.io/p/25-java-tricks

Volatile
https://proglib.io/p/kotlin-java-tips

Изолированные классы котлин
https://proglib.io/p/kotlin-tricks-1

Популярные функции Котлин
https://proglib.io/p/kotlin-tricks-2
 */