package com.rain.wanandroidkotlin.mvp.contract

import com.rain.wanandroidkotlin.base.ILayoutView
import com.rain.wanandroidkotlin.base.IPresenter
import com.rain.wanandroidkotlin.base.IloadMoreView
import com.rain.wanandroidkotlin.mvp.model.entity.DemoDetailListBean
import com.rain.wanandroidkotlin.mvp.model.entity.WxPublicListBean

/**
 * Author:rain
 * Date:2018/12/26 14:38
 * Description:
 */
interface DemoDetailContract {
    interface View:IloadMoreView,ILayoutView{
         fun getDemoListOk(bean: DemoDetailListBean)

         fun getDemoListErr(err: String)
    }

    interface Presenter:IPresenter<DemoDetailContract.View>{
        /**
         * 加载第一页数据
         */
        fun autoRefresh()

        fun getDemoListResult(id:Int,page:Int)

        fun loadMore()

    }
}