package com.rain.wanandroidkotlin.ui.fragment

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.rain.wanandroidkotlin.R
import com.rain.wanandroidkotlin.base.LazyLoadFragment
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Author:rain
 * Date:2018/11/19 11:18
 * Description:
 */
class HomeFragment :LazyLoadFragment() {

    companion object {
        fun getInstance() = HomeFragment()
    }

    override fun fetchData() {

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initView(savedInstanceState: Bundle?) {
        initRecycler()
    }

    private fun initRecycler() {
        recycyler.layoutManager = LinearLayoutManager(mContext)

    }
}