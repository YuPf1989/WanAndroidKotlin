package com.rain.wanandroidkotlin.base

import io.reactivex.disposables.CompositeDisposable

/**
 * Author:rain
 * Date:2018/11/16 10:35
 * Description:
 */
class PresenterImpl<V : IView> : IPresenter<V> {
    protected var view: IView? = null
    private var disposable: CompositeDisposable? = null
    override fun checkViewAttached(): Boolean {
        if (view != null) {
            return true
        } else {
            throw RuntimeException("Please call IPresenter.attachView(IBaseView) before" + " requesting data to the IPresenter")
        }
    }

    override fun attachView(view: V) {
        this.view = view
        disposable = CompositeDisposable()
    }

    override fun detachView() {
        view = null
        // TODO(disposable 不知道是否有可能为null)
        if (!disposable!!.isDisposed) {
            disposable!!.clear()
        }
    }

}