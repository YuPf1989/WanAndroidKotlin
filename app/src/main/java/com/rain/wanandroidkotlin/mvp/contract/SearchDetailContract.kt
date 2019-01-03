package com.rain.wanandroidkotlin.mvp.contract

import com.rain.wanandroidkotlin.base.IPresenter
import com.rain.wanandroidkotlin.base.IView
import com.rain.wanandroidkotlin.base.IloadMoreView
import com.rain.wanandroidkotlin.mvp.model.entity.HomePageArticleBean

/**
 * Author:rain
 * Date:2019/1/3 14:27
 * Description:
 */
interface SearchDetailContract {
    interface View:IloadMoreView{
         fun getSearechResultOk(bean: HomePageArticleBean)

         fun getSearechResultErr(err: String)
    }

    interface Presenter : IPresenter<View> {
         fun getSearechResult(page: Int, key: String)

         fun autoRefresh()

         fun loadMore()
    }
}