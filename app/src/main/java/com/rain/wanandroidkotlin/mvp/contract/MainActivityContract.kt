package com.rain.wanandroidkotlin.mvp.contract

import com.rain.wanandroidkotlin.base.IPresenter
import com.rain.wanandroidkotlin.base.IView

/**
 * Author:rain
 * Date:2018/11/22 15:50
 * Description:
 */
interface MainActivityContract {
    interface View:IView{
        fun loginOutOk()
        fun loginOutErr(code:Int,msg:String)
    }

    interface Presenter:IPresenter<MainActivityContract.View>{
        fun loginOut()
    }
}