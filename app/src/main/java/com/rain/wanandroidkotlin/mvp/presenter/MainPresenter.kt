package com.rain.wanandroidkotlin.mvp.presenter

import com.rain.wanandroidkotlin.base.PresenterImpl
import com.rain.wanandroidkotlin.mvp.contract.MainActivityContract

/**
 * Author:rain
 * Date:2018/11/22 16:16
 * Description:
 */
class MainPresenter(val v: MainActivityContract.View) : PresenterImpl<MainActivityContract.View>(),MainActivityContract.Presenter {
    override fun loginOut() {
        
    }
}