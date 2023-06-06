package com.example.android.codelabs.paging.experimental.coroutines

import android.util.Log
import com.example.android.codelabs.paging.util.launchIO
import com.example.android.codelabs.paging.util.withMain
import kotlinx.coroutines.*

class MyViewModel : CoroutineScope {

    private val job: Job = SupervisorJob()
    override val coroutineContext = job

    private fun showCoupons() {
        launchIO(
            safeAction = {
                //val coupons = promoInteractor.loadCoupons()

                withMain {
                    //view.showCoupons(coupons)
                }
            },
            onError = throw java.lang.Exception()  //view::showError
        )
    }
}