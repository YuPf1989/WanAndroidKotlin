package com.rain.wanandroidkotlin.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.rain.wanandroidkotlin.R
import com.rain.wanandroidkotlin.base.BaseFragment
import com.rain.wanandroidkotlin.mvp.contract.WxContract
import com.rain.wanandroidkotlin.mvp.model.entity.WxListBean
import com.rain.wanandroidkotlin.mvp.presenter.WxPresenter
import com.rain.wanandroidkotlin.net.exception.ExceptionHandle
import com.rain.wanandroidkotlin.ui.adapter.WxFragmentAdapter
import com.rain.wanandroidkotlin.util.ToastUtil
import kotlinx.android.synthetic.main.fragment_wx.*

/**
 * Author:rain
 * Date:2018/11/19 11:18
 * Description:
 */
class WxFragment : BaseFragment(), WxContract.View {
    lateinit var p: WxPresenter
    lateinit var titles: ArrayList<String>
    lateinit var fragments: ArrayList<WxDetailFragment>
    lateinit var adapter: WxFragmentAdapter

    override fun reload() {
        showLoading()
    }

    companion object {
        fun getInstance() = WxFragment()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_wx
    }

    override fun onDestroy() {
        super.onDestroy()
        p.detachView()
    }

    override fun initView(savedInstanceState: Bundle?) {
        p = WxPresenter()
        p.attachView(this)
        titles = ArrayList()
        fragments = ArrayList()
        showLoading()
    }

    fun scrollChildToTop() {
        val currentFragment = adapter.currentFragment
        currentFragment!!.scrollToTop()
    }

    override fun showLoading() {
        super.showLoading()
        p.getWxListData()
    }

    override fun getWxResultOk(data: List<WxListBean>) {
        titles.clear()
        fragments.clear()
        if (data.size > 0) {
            for (bean in data) {
                titles.add(bean.name)
                fragments.add(WxDetailFragment.getInstance(bean.id))
            }
            titles.forEach {
                tabLayout.addTab(tabLayout.newTab().setText(it))
            }
            adapter = WxFragmentAdapter(childFragmentManager,titles,fragments)
            viewPager.adapter = adapter
            viewPager.offscreenPageLimit = data.size
            tabLayout.setupWithViewPager(viewPager)
        }
        showNormal()
    }

    override fun getWxResultErr(err: String) {
        showError(ExceptionHandle.errorCode,err)
        ToastUtil.showToast(err)
    }
}