package com.rain.wanandroidkotlin.mvp.presenter

import android.util.Log
import com.rain.wanandroidkotlin.base.IView
import com.rain.wanandroidkotlin.base.PresenterImpl
import com.rain.wanandroidkotlin.mvp.contract.WxContract
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
class WxPresenter:WxContract.Presenter,PresenterImpl<WxContract.View>() {
    var view:WxContract.View?  = null

    override fun attachView(view: WxContract.View) {
        super.attachView(view)
        this.view = view
    }
    override fun getWxListData() {
        val subscribe = RetrofitHelper.createApi(ApiService::class.java)
                .getWXList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view!!.getWxResultOk(it)
                }, {
                    Log.e("eee",it.toString())
                    view!!.getWxResultErr(ExceptionHandle.handleException(it))
                })
        addSubscription(subscribe)
    }

}