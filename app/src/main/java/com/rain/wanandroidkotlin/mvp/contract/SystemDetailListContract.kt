package com.rain.wanandroidkotlin.mvp.contract

import com.rain.wanandroidkotlin.base.IPresenter
import com.rain.wanandroidkotlin.base.IloadMoreView
import com.rain.wanandroidkotlin.mvp.model.entity.SystemDetailListBean

/**
 * Author:rain
 * Date:2018/12/29 12:59
 * Description:
 */
interface SystemDetailListContract {
    interface View:IloadMoreView{
         fun getSystemDetailListResultOK(bean: SystemDetailListBean)

         fun getSystemDetailListResultErr(info: String)
    }
    interface Presenter:IPresenter<View>{
         fun autoRefresh()

         fun loadMore()

         fun getSystemDetailList(page: Int, id: Int)
    }
}