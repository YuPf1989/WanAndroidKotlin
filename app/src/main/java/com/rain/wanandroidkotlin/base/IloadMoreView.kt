package com.rain.wanandroidkotlin.base

/**
 * Author:rain
 * Date:2018/11/16 10:31
 * Description:
 */
interface IloadMoreView {
    /**
     * 全部数据加载完毕
     */
    fun loadEnd()

    /**
     * 本次数据加载完成
     */
    fun loadComplete()

    /**
     * 本次数据加载失败
     */
    fun loadFail()

    /**
     * 设置加载更多数据
     */
    fun setLoadMoreData(any: Any)
}