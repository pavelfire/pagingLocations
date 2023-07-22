package com.example.android.codelabs.paging.experimental.observers

import android.os.Handler
import java.util.LinkedList
import java.util.Random

class WeatherStation {
    private val people: MutableList<ManBehavior> = LinkedList()
    var degrees = 0

    fun addMan(manBehavior: ManBehavior){
        people.add(manBehavior)
    }

    fun removeMan(manBehavior: ManBehavior){
        people.remove(manBehavior)
    }

    fun updateWeather(){
        degrees = Random().nextInt(400)
        people.forEach{
            it.getCloth(degree = degrees)
        }

        val handler = Handler()
        handler.postDelayed({
            updateWeather()
        }, 2000)
    }
}