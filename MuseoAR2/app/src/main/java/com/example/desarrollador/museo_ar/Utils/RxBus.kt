package com.example.desarrollador.museo_ar.Utils

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.prefs.NodeChangeEvent

object RxBus{
    private val publisher = PublishSubject.create<Any>()

    fun publish(event: Any)
    {
        publisher.onNext(event)
    }

    fun <T> listen(eventType: Class<T>): Observable<T> = publisher.ofType(eventType)
}