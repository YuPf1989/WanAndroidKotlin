package com.rain.wanandroidkotlin.mvp.presenter

import com.rain.wanandroidkotlin.base.PresenterImpl
import com.rain.wanandroidkotlin.mvp.contract.HotContract
import com.rain.wanandroidkotlin.mvp.model.api.ApiService
import com.rain.wanandroidkotlin.net.RetrofitHelper
import com.rain.wanandroidkotlin.net.exception.ExceptionHandle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Author:rain
 * Date:2019/1/2 16:55
 * Description:
 */
class HotPresenter:PresenterImpl<HotContract.View>(),HotContract.Presenter {
    private lateinit var view: HotContract.View

    override fun attachView(view: HotContract.View) {
        super.attachView(view)
        this.view = view
    }

    override fun getHotList() {
        val subscribe = RetrofitHelper.createApi(ApiService::class.java)
                .getHotList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.getHotResultOK(it)
                }, {
                    view.getHotResultErr(ExceptionHandle.handleException(it))
                })

        addSubscription(subscribe)
    }
}