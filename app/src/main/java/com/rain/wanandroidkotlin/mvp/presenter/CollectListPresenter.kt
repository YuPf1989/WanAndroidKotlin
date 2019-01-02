package com.rain.wanandroidkotlin.mvp.presenter

import com.rain.wanandroidkotlin.base.PresenterImpl
import com.rain.wanandroidkotlin.mvp.contract.CollectListContract
import com.rain.wanandroidkotlin.mvp.model.api.ApiService
import com.rain.wanandroidkotlin.net.RetrofitHelper
import com.rain.wanandroidkotlin.net.exception.ExceptionHandle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Author:rain
 * Date:2019/1/2 10:55
 * Description:
 */
class CollectListPresenter:PresenterImpl<CollectListContract.View>(),CollectListContract.Presenter {
    private lateinit var view: CollectListContract.View
    private var currentPage:Int = -1
    override fun attachView(view: CollectListContract.View) {
        super.attachView(view)
        this.view = view
    }
    override fun onRefresh() {
        getCollectionList(0)
    }

    override fun onLoadMore() {
        if (currentPage != -1) {
            currentPage++
            RetrofitHelper.creatApi(ApiService::class.java)
                    .getCollectionList(currentPage)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if (it.pageCount >= currentPage) {
                            view.setLoadMoreData(it)
                            view.loadComplete()
                        } else {
                            view.loadEnd()
                        }
                    },{
                        view.getCollectionListErr(ExceptionHandle.handleException(it))
                        view.loadFail()
                    })
        }
    }

    override fun getCollectionList(page: Int) {
        currentPage = page
        RetrofitHelper.creatApi(ApiService::class.java)
                .getCollectionList(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.getCollectionListOK(it)
                },{
                    view.getCollectionListErr(ExceptionHandle.handleException(it))
                })
    }
}