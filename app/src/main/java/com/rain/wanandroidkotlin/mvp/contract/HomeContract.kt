package com.rain.wanandroidkotlin.mvp.contract

import com.rain.wanandroidkotlin.base.IPresenter
import com.rain.wanandroidkotlin.base.ILayoutView
import com.rain.wanandroidkotlin.base.IloadMoreView
import com.rain.wanandroidkotlin.mvp.model.entity.BenarBean
import com.rain.wanandroidkotlin.mvp.model.entity.HomePageArticleBean

/**
 * Author:rain
 * Date:2018/11/19 15:45
 * Description:
 *
 */
interface HomeContract {
     interface LayoutView : ILayoutView,IloadMoreView {
        fun getBannerOk(bannerBean:List<BenarBean> )

        fun getBannerErr(info:String)

        fun getHomePageListOk(homeList: HomePageArticleBean)

        fun getHomePageListErr(info:String)

    }

    interface Presenter:IPresenter<HomeContract.LayoutView>{
        fun autoRefresh()

        fun getBanner()

        fun getHomePageListData(page:Int)

        fun loadMore()
    }
}