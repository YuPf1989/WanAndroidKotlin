package com.rain.wanandroidkotlin.mvp.presenter

import com.rain.wanandroidkotlin.base.PresenterImpl
import com.rain.wanandroidkotlin.mvp.contract.DemoContract
import com.rain.wanandroidkotlin.mvp.model.api.ApiService
import com.rain.wanandroidkotlin.net.RetrofitHelper
import com.rain.wanandroidkotlin.net.exception.ExceptionHandle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Author:rain
 * Date:2018/12/26 9:49
 * Description:
 */
class DemoPresenter: DemoContract.Presenter,PresenterImpl<DemoContract.View>() {

    var view:DemoContract.View? = null

    override fun attachView(view: DemoContract.View) {
        super.attachView(view)
        this.view = view
    }

    override fun getDemoListData() {
        val subscribe = RetrofitHelper.creatApi(ApiService::class.java)
                .getDemoTitleList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view!!.getDemoResultOk(it)
                }, {
                    view!!.getDemoResultErr(ExceptionHandle.handleException(it))
                })

        addSubscription(subscribe)
    }

}