package com.rain.wanandroidkotlin.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Author:rain
 * Date:2018/11/16 10:35
 * Description:
 */
open class PresenterImpl<V : IView> : IPresenter<V> {
    protected var view: IView? = null
    protected var compositeDisposable: CompositeDisposable? = null

    override fun checkViewAttached(): Boolean {
        if (view != null) {
            return true
        } else {
            throw RuntimeException("Please call IPresenter.attachView(IBaseView) before" + " requesting data to the IPresenter")
        }
    }

    fun addSubscription(disposable: Disposable) {
        compositeDisposable?.add(disposable)
    }

    override fun attachView(view: V) {
        this.view = view
        compositeDisposable = CompositeDisposable()
    }

    override fun detachView() {
        view = null
        if (!compositeDisposable!!.isDisposed) {
            compositeDisposable!!.clear()
        }
    }

}