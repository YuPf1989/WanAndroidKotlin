package com.rain.wanandroidkotlin.mvp.contract

import com.rain.wanandroidkotlin.base.IPresenter
import com.rain.wanandroidkotlin.base.IView

/**
 * Author:rain
 * Date:2019/1/2 9:10
 * Description:
 */
interface HomeDetailContract {
    interface View : IView {
        fun collectArticleOK(info: String)

        fun collectArticleErr(info: String)

        fun cancelCollectArticleOK(info: String)

        fun cancelCollectArticleErr(info: String)
    }

    interface Presenter : IPresenter<View> {
        fun collectArticle(id:Int)
        fun cancelCollectArticle(id:Int)
    }
}