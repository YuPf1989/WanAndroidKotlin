package com.rain.wanandroidkotlin.ui.activity

import android.os.Bundle
import android.util.Log
import com.rain.wanandroidkotlin.R
import com.rain.wanandroidkotlin.base.BaseActivity
import com.rain.wanandroidkotlin.mvp.model.entity.SystemBean
import com.rain.wanandroidkotlin.ui.adapter.SystemDetailFragmentAdapter
import com.rain.wanandroidkotlin.ui.fragment.SystemListFragment
import com.rain.wanandroidkotlin.util.Constant
import kotlinx.android.synthetic.main.activity_system_detail.*
import kotlinx.android.synthetic.main.toolbar.*

private const val TAG = "SystemDetailActivity"
/**
 * Author:rain
 * Date:2018/12/29 10:54
 * Description:
 */
class SystemDetailActivity:BaseActivity() {
    lateinit var toolbarTitle:String
    lateinit var tabTitles:ArrayList<String>
    lateinit var fragments:ArrayList<SystemListFragment>
    lateinit var adapter: SystemDetailFragmentAdapter

    override fun initView(savedInstanceState: Bundle?) {
        getIntentData()
        adapter = SystemDetailFragmentAdapter(supportFragmentManager,tabTitles,fragments)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = fragments.size
        tab.setupWithViewPager(viewPager)
        fab.setOnClickListener {
            adapter.currentFragment.scrollToTop()
        }
        initToolbar(toolbar,toolbarTitle,true)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_system_detail
    }

    private fun getIntentData() {
        (intent.getSerializableExtra(Constant.SYSTEM) as SystemBean).let { it ->
            toolbarTitle = it.name
            tabTitles = ArrayList()
            fragments = ArrayList()
            it.children.forEach {
                tab.addTab(tab.newTab().setText(it.name))
                tabTitles.add(it.name)
                fragments.add(SystemListFragment.getInstance(it.id))
            }
        }
    }
}