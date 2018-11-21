package com.rain.wanandroidkotlin.net.myobserver

import com.rain.wanandroidkotlin.net.exception.ExceptionHandle
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Author:rain
 * Date:2017/11/13 14:20
 * Description:
 */

abstract class MyObserver<T> : Observer<T> {

    override fun onSubscribe(d: Disposable) {

    }

    abstract override fun onNext(t: T)

    override fun onError(e: Throwable) {
        // 异常，统一交给该处理的类去处理
        ExceptionHandle.handleException(e)
    }

    override fun onComplete() {

    }
}
