package com.example.android.codelabs.paging.experimental

import java.lang.reflect.Field


fun main(args: Array<String>) {

    var someText = "Go!"

    val result = mutableListOf<Field>()

    for ( i in 0 until someText.javaClass.declaredFields.size){
        result.add(someText.javaClass.declaredFields[i])
    }

    for (i in 0 until result.size) println("$i - ${result[i]}")

    println(result[0].isAccessible)
    println(result[0].type)

    //println("Спор закончен! $result")




}