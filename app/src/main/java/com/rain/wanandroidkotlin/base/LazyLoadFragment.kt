package com.rain.wanandroidkotlin.base

import android.os.Bundle

/**
 * Author:rain
 * Date:2018/11/16 11:08
 * Description:
 * 懒加载的fragment
 * 只有当前fragment初始化过，并且对用户可见,没有加载过数据的时候才进行数据加载
 */
abstract class LazyLoadFragment : BaseFragment() {
    // 界面是否初始化过
    private var isViewInitiated = false
    // 界面对用户是否可见
    private var isVisibleToUser = false
    // 是否初始化过数据
    private var isDataInitiated = false


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isViewInitiated = true
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
        preFetchData()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    // 重写此方法,是否强制刷新数据
    protected fun isForceUpdate(forceUpdate: Boolean) = forceUpdate

    private fun preFetchData() {
        if (isViewInitiated && isVisibleToUser && !isDataInitiated || isForceUpdate(false)) {
            fetchData()
            isDataInitiated = true
        }
    }

    abstract fun fetchData()

}