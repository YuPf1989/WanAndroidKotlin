package com.rain.wanandroidkotlin.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.rain.wanandroidkotlin.R
import com.rain.wanandroidkotlin.base.BaseFragment
import com.rain.wanandroidkotlin.base.LazyLoadFragment
import com.rain.wanandroidkotlin.mvp.contract.SystemContract
import com.rain.wanandroidkotlin.mvp.model.entity.SystemBean
import com.rain.wanandroidkotlin.mvp.presenter.SystemPresenter
import com.rain.wanandroidkotlin.ui.adapter.SystemAdapter
import com.rain.wanandroidkotlin.util.ToastUtil
import kotlinx.android.synthetic.main.fragment_system.*

/**
 * Author:rain
 * Date:2018/11/19 11:18
 * Description:
 */
class SystemFragment : BaseFragment(),SystemContract.View {

    var listSystem: List<SystemBean> = ArrayList()
    var adapter:SystemAdapter? = null
    var p:SystemPresenter? = null
    override fun reload() {
        showLoading()
    }

    companion object {
        fun getInstance() = SystemFragment()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_system
    }

    override fun initView(savedInstanceState: Bundle?) {
        p = SystemPresenter()
        p!!.attachView(this)
        rv_system.layoutManager = LinearLayoutManager(mContext)
        adapter = SystemAdapter(R.layout.item_system,listSystem)
        rv_system.adapter = adapter
        refresh_layout.setOnRefreshListener {
            p!!.autoRefresh()
            it.finishRefresh(1000)
        }
        showLoading()
    }

    override fun onDestroy() {
        p!!.detachView()
        super.onDestroy()
    }

    fun scrollToTop(){
        rv_system.smoothScrollToPosition(0)
    }

    override fun showLoading() {
        super.showLoading()
        p!!.autoRefresh()
    }

    override fun getSystemListOk(dataBean: List<SystemBean>) {
        showNormal()
        adapter!!.replaceData(dataBean)
    }

    override fun getSystemListErr(info: String) {
        ToastUtil.showToast(info)
    }
}