package com.example.android.codelabs.rxjava

import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Thread.currentThread

class RxExampleViewModel : ViewModel() {

    private val disposables: CompositeDisposable = CompositeDisposable()

    fun fetchShowsList() {
        Observable.just(1, 2, 3, 4, 5, 6)
            .doOnNext { data ->
                println("Emitting item " + data + " on: " + currentThread().name)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .subscribe ({ data ->
                println("Consuming item " + data + " on: " + currentThread().name)
            },{error ->
                println("Error Occured " + error.localizedMessage)
            }).let {
                disposables.add(it)
            }
    }

    override fun onCleared() {
        super.onCleared()
        if(!disposables.isDisposed) {
            disposables.dispose()
        }
        disposables.clear()
    }
}