package com.rain.wanandroidkotlin.base

/**
 * Author:rain
 * Date:2018/11/16 10:22
 * Description:
 */
interface IView {
    /**
     * 显示正常布局
     */
    fun showNormal()

    /**
     * 加载中
     */
    fun showLoading()

    /**
     * 网络错误
     */
    fun showError(code:Int,msg:String)

    /**
     * 没有数据
     */
    fun showEmpty()

    /**
     * 重新加载
     */
    fun reload()
}