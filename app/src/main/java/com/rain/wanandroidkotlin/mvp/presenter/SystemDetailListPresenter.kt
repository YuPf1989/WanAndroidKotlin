package com.rain.wanandroidkotlin.mvp.presenter

import com.rain.wanandroidkotlin.base.PresenterImpl
import com.rain.wanandroidkotlin.mvp.contract.SystemDetailListContract
import com.rain.wanandroidkotlin.mvp.model.api.ApiService
import com.rain.wanandroidkotlin.net.RetrofitHelper
import com.rain.wanandroidkotlin.net.exception.ExceptionHandle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Author:rain
 * Date:2018/12/29 13:02
 * Description:
 * 注意page页数从0开始
 */
class SystemDetailListPresenter : PresenterImpl<SystemDetailListContract.View>(), SystemDetailListContract.Presenter {
    lateinit var view: SystemDetailListContract.View
    var currentPage:Int = -1
    var id:Int = -1
    override fun attachView(view: SystemDetailListContract.View) {
        super.attachView(view)
        this.view = view
    }

    override fun autoRefresh() {
        if (id!=-1){
            getSystemDetailList(0,id)
        }
    }

    override fun loadMore() {
        currentPage++
        val subscribe = RetrofitHelper.creatApi(ApiService::class.java)
                .getSystemDetailList(currentPage, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.pageCount >= currentPage) {
                        view.setLoadMoreData(it)
                        view.loadComplete()
                    } else {
                        view.loadEnd()
                    }
                }, {
                    view.getSystemDetailListResultErr(ExceptionHandle.handleException(it))
                    view.loadFail()
                })
        addSubscription(subscribe)
    }

    override fun getSystemDetailList(page: Int, id: Int) {
        currentPage = page
        this.id = id
        val subscribe = RetrofitHelper.creatApi(ApiService::class.java)
                .getSystemDetailList(currentPage, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.getSystemDetailListResultOK(it)
                }, {
                    view.getSystemDetailListResultErr(ExceptionHandle.handleException(it))
                })
        addSubscription(subscribe)
    }
}