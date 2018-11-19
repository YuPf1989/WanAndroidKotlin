package com.rain.wanandroidkotlin.base

/**
 * Author:rain
 * Date:2018/11/16 10:20
 * Description:
 */
interface IPresenter<V :IView> {
    /**
     * presenter与view视图绑定
     */
    fun attachView(view:V)

    /**
     * presenter与view视图解绑
     */
    fun detachView()

    /**
     * 检查当前的view是否与视图绑定
     */
    fun checkViewAttached():Boolean
}