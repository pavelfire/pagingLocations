package com.example.android.codelabs.paging.experimental.coroutines

import android.util.Log
import kotlinx.coroutines.*

class MyViewModelOld: CoroutineScope {

    private val exHandler = CoroutineExceptionHandler { _, _ ->

//        withContext(Dispatchers.Main){
//            //view.showError() // UI changing
//        }
    }

    private val job: Job = SupervisorJob()
    override val coroutineContext = job + Dispatchers.Main + exHandler

    private fun showCoupons(){
        launch(Dispatchers.IO){
            //val coupons = promoInteractor.loadCoupons()

            withContext(Dispatchers.Main){
                //view.showCoupons(coupons)
            }
        }
    }
}