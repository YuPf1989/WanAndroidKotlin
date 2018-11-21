package com.rain.wanandroidkotlin.ui.fragment

import android.os.Bundle
import com.rain.wanandroidkotlin.R
import com.rain.wanandroidkotlin.base.LazyLoadFragment

/**
 * Author:rain
 * Date:2018/11/19 11:18
 * Description:
 */
class SystemFragment :LazyLoadFragment() {
    override fun reload() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        fun getInstance() = SystemFragment()
    }
    override fun fetchData() {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLayoutId(): Int {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return R.layout.fragment_test
    }

    override fun initView(savedInstanceState: Bundle?) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}