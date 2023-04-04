package com.example.android.codelabs.rxjava

import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class RxJavaExamples {
}

fun main(){

    val observableObject = Observable.create(ObservableOnSubscribe<Int>{

        if(!it.isDisposed){
            it.onNext(10)
        }
        if(!it.isDisposed){
            it.onNext(20)
        }
        if(!it.isDisposed){
            it.onNext(30)
            it.onComplete()
        }
    } )

    val observerInstance = object : io.reactivex.Observer<Int> {

        override fun onComplete() {
            println("onComplete")
        }

        override fun onSubscribe(d: Disposable) {
            println("onSubscribe")
        }

        override fun onNext(t: Int) {
            println("onNext $t")
        }

        override fun onError(e: Throwable) {
            println("onError "+e.localizedMessage)
        }

    }

    observableObject.subscribe(observerInstance)


}