package com.rain.wanandroidkotlin.ui.fragment

import android.os.Bundle
import com.rain.wanandroidkotlin.R
import com.rain.wanandroidkotlin.base.BaseFragment
import com.rain.wanandroidkotlin.mvp.contract.DemoContract
import com.rain.wanandroidkotlin.mvp.model.entity.DemoTitleBean
import com.rain.wanandroidkotlin.mvp.presenter.DemoPresenter
import com.rain.wanandroidkotlin.net.exception.ExceptionHandle
import com.rain.wanandroidkotlin.ui.adapter.DemoFragmentAdapter
import com.rain.wanandroidkotlin.util.ToastUtil
import kotlinx.android.synthetic.main.fragment_wx.*

/**
 * Author:rain
 * Date:2018/11/19 11:18
 * Description:
 */
class DemoFragment : BaseFragment(),DemoContract.View {
    lateinit var p: DemoPresenter
    lateinit var titles: ArrayList<String>
    lateinit var fragments: ArrayList<DemoDetailFragment>
    lateinit var adapter: DemoFragmentAdapter
    override fun reload() {
        showLoading()
    }

    companion object {
        fun getInstance() = DemoFragment()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_wx
    }

    override fun onDestroy() {
        super.onDestroy()
        p.detachView()
    }

    override fun showLoading() {
        super.showLoading()
        p.getDemoListData()
    }

    override fun initView(savedInstanceState: Bundle?) {
        p = DemoPresenter()
        p.attachView(this)
        titles = ArrayList()
        fragments = ArrayList()
        showLoading()
    }

    fun scrollChildToTop() {
        val currentFragment = adapter.currentFragment
        currentFragment!!.scrollToTop()
    }

    override fun getDemoResultOk(data: List<DemoTitleBean>) {
        titles.clear()
        fragments.clear()
        if (data.size > 0) {
            for (bean in data) {
                titles.add(bean.name)
                fragments.add(DemoDetailFragment.getInstance(bean.id))
            }
            titles.forEach {
                tabLayout.addTab(tabLayout.newTab().setText(it))
            }
            adapter = DemoFragmentAdapter(childFragmentManager,titles,fragments)
            viewPager.adapter = adapter
            viewPager.offscreenPageLimit = data.size
            tabLayout.setupWithViewPager(viewPager)
        }
        showNormal()
    }

    override fun getDemoResultErr(err: String) {
        showError(ExceptionHandle.errorCode,err)
        ToastUtil.showToast(err)
    }
}