package com.rain.wanandroidkotlin.mvp.presenter

import android.util.Log
import com.rain.wanandroidkotlin.base.PresenterImpl
import com.rain.wanandroidkotlin.mvp.contract.HomeContract
import com.rain.wanandroidkotlin.mvp.model.api.ApiService
import com.rain.wanandroidkotlin.net.RetrofitHelper
import com.rain.wanandroidkotlin.net.exception.ExceptionHandle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Author:rain
 * Date:2018/11/19 16:00
 * Description:
 */
class HomePresenter : HomeContract.Presenter, PresenterImpl<HomeContract.View>() {
    var currentPage: Int = 0

    override fun attachView(view: HomeContract.View) {
        this.view = view
        super.attachView(view)
    }

    override fun autoRefresh() {
        currentPage = 0
        getBanner()
        getHomePageListData(currentPage)
    }

    override fun getBanner() {
        val disposable = RetrofitHelper.creatApi(ApiService::class.java)
                .getBannerList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    (view as HomeContract.View).getBannerOk(it)
                }, {
                    (view as HomeContract.View).getBannerErr(ExceptionHandle.handleException(it))
                })
        addSubscription(disposable)
    }

    override fun getHomePageListData(page: Int) {
        val disposable = RetrofitHelper.creatApi(ApiService::class.java)
                .getArticleList(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    (view as HomeContract.View).getHomePageListOk(it)
                }, {
                    (view as HomeContract.View).getHomePageListErr(ExceptionHandle.handleException(it))
                })
        addSubscription(disposable)
    }

    fun getHomePageListMoreData(page: Int) {
        val disposable = RetrofitHelper.creatApi(ApiService::class.java)
                .getArticleList(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (currentPage <= it.pageCount) {
                        (view as HomeContract.View).setLoadMoreData(it)
                        (view as HomeContract.View).loadComplete()
                    }
                    if (currentPage > it.pageCount) {
                        (view as HomeContract.View).loadEnd()
                    }
                }, {
                    (view as HomeContract.View).loadFail()
                })
        addSubscription(disposable)
    }

    override fun loadMore() {
        currentPage++
        getHomePageListMoreData(currentPage)
    }
}