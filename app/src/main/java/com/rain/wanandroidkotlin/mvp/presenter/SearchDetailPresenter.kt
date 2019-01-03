package com.rain.wanandroidkotlin.mvp.presenter

import com.rain.wanandroidkotlin.base.PresenterImpl
import com.rain.wanandroidkotlin.mvp.contract.SearchDetailContract
import com.rain.wanandroidkotlin.mvp.model.api.ApiService
import com.rain.wanandroidkotlin.net.RetrofitHelper
import com.rain.wanandroidkotlin.net.exception.ExceptionHandle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Author:rain
 * Date:2019/1/3 14:29
 * Description:
 */
class SearchDetailPresenter:PresenterImpl<SearchDetailContract.View>(),SearchDetailContract.Presenter {
    private lateinit var view: SearchDetailContract.View
    private  var currentPage:Int = -1
    private  var key:String = ""
    override fun attachView(view: SearchDetailContract.View) {
        super.attachView(view)
        this.view = view
    }
    override fun getSearechResult(page: Int, key: String) {
        currentPage = page
        this.key = key
        val subscribe = RetrofitHelper.creatApi(ApiService::class.java)
                .getSearechResult(page, key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.getSearechResultOk(it)
                }, {
                    view.getSearechResultErr(ExceptionHandle.handleException(it))
                })
        addSubscription(subscribe)
    }

    override fun autoRefresh() {
        if (currentPage != -1 && key.isNotEmpty()) {
            getSearechResult(0,key)
        }
    }

    override fun loadMore() {
        if (currentPage != -1 && key.isNotEmpty()) {
            currentPage++
            val subscribe = RetrofitHelper.creatApi(ApiService::class.java)
                    .getSearechResult(currentPage, key)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if (currentPage <= it.pageCount) {
                            view.setLoadMoreData(it)
                            view.loadComplete()
                        }
                        if (currentPage > it.pageCount) {
                            view.loadEnd()
                        }
                    }, {
                        view.loadFail()
                    })

            addSubscription(subscribe)
        }

    }
}