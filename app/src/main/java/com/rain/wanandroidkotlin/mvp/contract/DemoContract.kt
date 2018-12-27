package com.rain.wanandroidkotlin.mvp.contract

import com.rain.wanandroidkotlin.base.IPresenter
import com.rain.wanandroidkotlin.base.IView
import com.rain.wanandroidkotlin.mvp.model.entity.DemoTitleBean
import com.rain.wanandroidkotlin.mvp.model.entity.WxListBean

/**
 * Author:rain
 * Date:2018/12/26 9:40
 * Description:
 */
interface DemoContract {
    interface View:IView{
        fun getDemoResultOk(data:List<DemoTitleBean>)
        fun getDemoResultErr(err:String)
    }

    interface Presenter:IPresenter<DemoContract.View>{
        fun getDemoListData()
    }
}