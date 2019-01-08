package com.rain.wanandroidkotlin.mvp.presenter

import com.rain.wanandroidkotlin.base.PresenterImpl
import com.rain.wanandroidkotlin.mvp.contract.DemoDetailContract
import com.rain.wanandroidkotlin.mvp.model.api.ApiService
import com.rain.wanandroidkotlin.net.RetrofitHelper
import com.rain.wanandroidkotlin.net.exception.ExceptionHandle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Author:rain
 * Date:2018/12/26 14:56
 * Description:
 */
class DemoDetailPresenter:PresenterImpl<DemoDetailContract.View>(),DemoDetailContract.Presenter {
    var id = -1
    var page = 1
    var view:DemoDetailContract.View? = null

    override fun attachView(view: DemoDetailContract.View) {
        super.attachView(view)
        this.view = view
    }
    override fun autoRefresh() {
        if (id!=-1){
            getDemoListResult(id,1)
        }
    }

    override fun getDemoListResult(id: Int, page: Int) {
        this.id = id
        this.page = page
        val subscribe = RetrofitHelper.createApi(ApiService::class.java)
                .getDemoDetailList(page, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view!!.getDemoListOk(it)
                }, {
                    view!!.getDemoListErr(ExceptionHandle.handleException(it))
                })

        addSubscription(subscribe)
    }

    override fun loadMore() {
        page++
        val subscribe = RetrofitHelper.createApi(ApiService::class.java)
                .getDemoDetailList(page, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (page>it.pageCount){
                        view!!.loadEnd()
                    }else{
                        view!!.setLoadMoreData(it)
                        view!!.loadComplete()
                    }
                }, {
                    view!!.getDemoListErr(ExceptionHandle.handleException(it))
                    view!!.loadFail()
                })

        addSubscription(subscribe)
    }
}