package com.devyk.common.ext

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import org.reactivestreams.Subscription


fun <T> Observable<T>.subscribeIgnoreError(onNext: (T) -> Unit): Disposable?
    = subscribe(onNext, Throwable::printStackTrace)