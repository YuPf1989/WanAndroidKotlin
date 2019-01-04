package com.rain.wanandroidkotlin.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.rain.wanandroidkotlin.R
import com.rain.wanandroidkotlin.base.BaseLayoutActivity
import com.rain.wanandroidkotlin.mvp.contract.HotContract
import com.rain.wanandroidkotlin.mvp.model.entity.HotBean
import com.rain.wanandroidkotlin.mvp.presenter.HotPresenter
import com.rain.wanandroidkotlin.util.ColorUtil
import com.rain.wanandroidkotlin.util.Constant
import com.rain.wanandroidkotlin.util.JumpUtil
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.activity_hot.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Author:rain
 * Date:2019/1/2 16:34
 * Description:
 */
class HotWebsiteActivity:BaseLayoutActivity(),HotContract.View {

    private lateinit var adapter:TagAdapter<HotBean>
    private lateinit var p:HotContract.Presenter
    override fun initView(savedInstanceState: Bundle?) {
        p  = HotPresenter()
        p.attachView(this)
        initToolbar(toolbar,getString(R.string.hot_title),true)
        p.getHotList()
    }

    override fun onDestroy() {
        super.onDestroy()
        p.detachView()
    }

    override fun setTargetView(): View {
        return scrollView
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_hot
    }

    override fun showLoading() {
        super.showLoading()
        p.getHotList()
    }

    override fun reload() {
        showLoading()
    }

    override fun getHotResultOK(hotBeans: List<HotBean>) {
        showNormal()

        adapter = object : TagAdapter<HotBean>(hotBeans) {
            override fun getView(parent: FlowLayout, position: Int, hotBean: HotBean): View {
                val textView = layoutInflater.inflate(R.layout.textview_item_hot, null) as TextView
                textView.text = hotBean.name
                textView.setTextColor(ColorUtil.randomColor)
                return textView
            }
        }
        flowlayout.adapter = adapter
        flowlayout.setOnTagClickListener { view, position, parent ->
            Bundle().apply {
                putString(Constant.HOME_DETAIL_TITLE, hotBeans.get(position).getName())
                putString(Constant.HOME_DETAIL_PATH, hotBeans.get(position).getLink())
            }.let {
                JumpUtil.overlay(this, HomeDetailActivity::class.java, it)
            }
            return@setOnTagClickListener true
        }

    }

    override fun getHotResultErr(err: String) {
        showError()
    }
}