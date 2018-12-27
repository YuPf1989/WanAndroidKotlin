package com.rain.wanandroidkotlin.mvp.contract

import com.rain.wanandroidkotlin.base.IPresenter
import com.rain.wanandroidkotlin.base.IView
import com.rain.wanandroidkotlin.mvp.model.entity.WxListBean

/**
 * Author:rain
 * Date:2018/12/26 9:40
 * Description:
 */
interface WxContract {
    interface View:IView{
        fun getWxResultOk(data:List<WxListBean>)
        fun getWxResultErr(err:String)
    }

    interface Presenter:IPresenter<WxContract.View>{
        fun getWxListData()
    }
}