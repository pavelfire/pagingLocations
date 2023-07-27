package com.example.android.codelabs.paging.ui

import android.content.Intent

class App : android.app.Application() {

    override fun onCreate() {
        super.onCreate()

    }

    fun openMyActivity() {
        val intent = Intent(this, ActivityThree::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}