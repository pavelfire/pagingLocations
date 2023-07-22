package com.example.android.codelabs.paging.experimental.observers

import android.util.Log

data class Man(
    val name: String
): ManBehavior{
    override fun getCloth(degree: Int) {
        if (degree > 200){
            degreeReaction("Сегодня тепло", degree)
        }else{
            degreeReaction("Холодно на улице", degree)
        }
    }

    private fun degreeReaction(howFeel: String, degree: Int){
        println(howFeel + degree)
        Log.d(Man::class.java.simpleName, howFeel + degree)
    }

}
