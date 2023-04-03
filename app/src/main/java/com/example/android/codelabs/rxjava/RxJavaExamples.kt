package com.example.android.codelabs.rxjava

import io.reactivex.Observable
import org.reactivestreams.Subscriber



class RxJavaExamples {
}

fun main(){

    val myObservable: Observable<String> = Observable.create(
        object : Observable<String?>() {
            fun call(sub: Subscriber<in String?>) {
                sub.onNext("Hello, world!")
                sub.onCompleted()
            }
        }
    )

    val mySubscriber: Subscriber<String> = object : Subscriber<String?>() {
        fun onNext(s: String?) {
            println(s)
        }

        fun onCompleted() {}
        fun onError(e: Throwable?) {}
    }

    myObservable.subscribe(mySubscriber)


}