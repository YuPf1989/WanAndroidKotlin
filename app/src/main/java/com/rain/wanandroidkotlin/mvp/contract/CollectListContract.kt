package com.rain.wanandroidkotlin.mvp.contract

import com.rain.wanandroidkotlin.base.IPresenter
import com.rain.wanandroidkotlin.base.IloadMoreView
import com.rain.wanandroidkotlin.mvp.model.entity.CollectBean

/**
 * Author:rain
 * Date:2019/1/2 10:52
 * Description:
 */
interface CollectListContract {
    interface View : IloadMoreView {
        fun getCollectionListOK(collectBean: CollectBean)

        fun getCollectionListErr(err: String)
    }

    interface Presenter : IPresenter<View> {
        fun onRefresh()

        fun onLoadMore()

        fun getCollectionList(page: Int)
    }
}