package com.rain.wanandroidkotlin.mvp.presenter

import com.rain.wanandroidkotlin.base.PresenterImpl
import com.rain.wanandroidkotlin.mvp.contract.HomeDetailContract
import com.rain.wanandroidkotlin.mvp.model.api.ApiService
import com.rain.wanandroidkotlin.net.RetrofitHelper
import com.rain.wanandroidkotlin.net.exception.ExceptionHandle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Author:rain
 * Date:2019/1/2 9:13
 * Description:
 */
class HomeDetailPresenter:PresenterImpl<HomeDetailContract.View>(),HomeDetailContract.Presenter {
    private lateinit var view:HomeDetailContract.View
    override fun attachView(view: HomeDetailContract.View) {
        super.attachView(view)
        this.view = view
    }
    override fun collectArticle(id:Int) {
        val subscribe = RetrofitHelper.creatApi(ApiService::class.java)
                .collectArticle(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.collectArticleOK("收藏成功")
                }, {
                    view.collectArticleErr(ExceptionHandle.handleException(it))
                })

        addSubscription(subscribe)


    }

    override fun cancelCollectArticle(id:Int) {
        val subscribe = RetrofitHelper.creatApi(ApiService::class.java)
                .collectArticle(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.cancelCollectArticleOK("取消收藏成功")
                }, {
                    view.cancelCollectArticleErr(ExceptionHandle.handleException(it))
                })

        addSubscription(subscribe)
    }
}