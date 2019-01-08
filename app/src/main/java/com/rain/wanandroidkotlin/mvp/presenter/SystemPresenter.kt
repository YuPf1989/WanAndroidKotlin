package com.rain.wanandroidkotlin.mvp.presenter

import com.rain.wanandroidkotlin.base.PresenterImpl
import com.rain.wanandroidkotlin.mvp.contract.SystemContract
import com.rain.wanandroidkotlin.mvp.model.api.ApiService
import com.rain.wanandroidkotlin.net.RetrofitHelper
import com.rain.wanandroidkotlin.net.exception.ExceptionHandle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Author:rain
 * Date:2018/12/25 16:11
 * Description:
 */
class SystemPresenter:PresenterImpl<SystemContract.View>(),SystemContract.Presenter {
    var view:SystemContract.View? = null

    override fun attachView(view: SystemContract.View) {
        super.attachView(view)
        this.view = view
    }


    // 下拉刷新的时候调用
    override fun autoRefresh() {
        getSystemList()
    }


    override fun getSystemList() {
        val subscribe = RetrofitHelper.createApi(ApiService::class.java)
                .getSystemList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view!!.getSystemListOk(it)
                }, {
                    view!!.getSystemListErr(ExceptionHandle.handleException(it))
                })

        addSubscription(subscribe)
    }
}