package com.rain.wanandroidkotlin.mvp.presenter

import android.content.Context
import com.rain.wanandroidkotlin.base.PresenterImpl
import com.rain.wanandroidkotlin.mvp.contract.SearchContract
import com.rain.wanandroidkotlin.mvp.model.SearchModel
import com.rain.wanandroidkotlin.mvp.model.api.ApiService
import com.rain.wanandroidkotlin.net.RetrofitHelper
import com.rain.wanandroidkotlin.net.exception.ExceptionHandle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Author:rain
 * Date:2019/1/3 10:44
 * Description:
 */
class SearchPresenter:PresenterImpl<SearchContract.View>(),SearchContract.Presenter {
    private lateinit var view: SearchContract.View
    private lateinit var model:SearchModel
    override fun attachView(view: SearchContract.View) {
        super.attachView(view)
        this.view = view
        model = SearchModel()
    }
    override fun getHotListResult() {
        val subscribe = RetrofitHelper.createApi(ApiService::class.java)
                .getHitKeyBean()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.getHotListOk(it)
                }, {
                    view.getHotListErr(ExceptionHandle.handleException(it))
                })

        addSubscription(subscribe)
    }

    override fun saveHistory(context: Context, historyList: List<String>) {
        model.saveHistory(context,historyList)
    }

    override fun getHistoryList(context: Context): List<String> {
        return model.getHistoryList(context)
    }
}