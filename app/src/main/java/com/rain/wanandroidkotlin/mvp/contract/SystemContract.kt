package com.rain.wanandroidkotlin.mvp.contract

import com.rain.wanandroidkotlin.base.ILayoutView
import com.rain.wanandroidkotlin.base.IPresenter
import com.rain.wanandroidkotlin.mvp.model.entity.SystemBean

/**
 * Author:rain
 * Date:2018/12/25 16:00
 * Description:
 */
interface SystemContract {
    interface View:ILayoutView{
        fun getSystemListOk(dataBean: List<SystemBean>)

        fun getSystemListErr(info: String)
    }

    interface Presenter:IPresenter<SystemContract.View>{
         fun autoRefresh()

         fun getSystemList()
    }
}