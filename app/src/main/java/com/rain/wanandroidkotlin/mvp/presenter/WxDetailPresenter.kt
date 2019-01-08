package com.rain.wanandroidkotlin.mvp.presenter

import com.rain.wanandroidkotlin.base.PresenterImpl
import com.rain.wanandroidkotlin.mvp.contract.WxDetailContract
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
class WxDetailPresenter:PresenterImpl<WxDetailContract.View>(),WxDetailContract.Presenter {
    var id = -1
    var page = 1
    var view:WxDetailContract.View? = null

    override fun attachView(view: WxDetailContract.View) {
        super.attachView(view)
        this.view = view
    }
    override fun autoRefresh() {
        if (id!=-1){
            getWxPublicListResult(id,1)
        }
    }

    override fun getWxPublicListResult(id: Int, page: Int) {
        this.id = id
        this.page = page
        val subscribe = RetrofitHelper.createApi(ApiService::class.java)
                .getWXDetailList(page, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view!!.getWxPublicListOk(it)
                }, {
                    view!!.getWxPublicErr(ExceptionHandle.handleException(it))
                })

        addSubscription(subscribe)
    }

    override fun loadMore() {
        page++
        val subscribe = RetrofitHelper.createApi(ApiService::class.java)
                .getWXDetailList(page, id)
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
                    view!!.getWxPublicErr(ExceptionHandle.handleException(it))
                    view!!.loadFail()
                })

        addSubscription(subscribe)
    }
}