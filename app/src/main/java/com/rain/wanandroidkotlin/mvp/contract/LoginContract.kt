package com.rain.wanandroidkotlin.mvp.contract

import com.rain.wanandroidkotlin.base.IPresenter
import com.rain.wanandroidkotlin.base.ILayoutView
import com.rain.wanandroidkotlin.base.IView
import com.rain.wanandroidkotlin.mvp.model.entity.UserInfo

/**
 * Author:rain
 * Date:2018/11/22 10:43
 * Description:
 */
interface LoginContract {
    interface View : IView {
        fun showLoading()
        fun loginOk(info: UserInfo)
        fun loginErr(code: Int, msg: String)
    }

    interface Presenter : IPresenter<LoginContract.View> {
        fun login(userName: String, pwd: String)
    }
}