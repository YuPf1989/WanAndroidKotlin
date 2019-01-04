package com.rain.wanandroidkotlin.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.rain.wanandroidkotlin.R
import com.rain.wanandroidkotlin.base.BaseLayoutActivity
import com.rain.wanandroidkotlin.mvp.contract.SearchContract
import com.rain.wanandroidkotlin.mvp.model.entity.HotKeyBean
import com.rain.wanandroidkotlin.mvp.presenter.SearchPresenter
import com.rain.wanandroidkotlin.ui.adapter.SearchAdapter
import com.rain.wanandroidkotlin.util.ColorUtil
import com.rain.wanandroidkotlin.util.Constant
import com.rain.wanandroidkotlin.util.JumpUtil
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.activity_searech.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Author:rain
 * Date:2019/1/2 15:40
 * Description:
 */
class SearchActivity:BaseLayoutActivity(),SearchContract.View, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener, View.OnClickListener {
    private lateinit var historyList:ArrayList<String>// 本地储存的搜索历史
    private lateinit var adapter:SearchAdapter
    private lateinit var p:SearchPresenter
    override fun initView(savedInstanceState: Bundle?) {
        initToolbar(toolbar,getString(R.string.search_title),true)
        p = SearchPresenter()
        p.attachView(this)
        tv_clear.setOnClickListener(this)
        rv_history.layoutManager = LinearLayoutManager(this)
        adapter = SearchAdapter(R.layout.item_search_history,null)
        adapter.onItemClickListener = this
        adapter.onItemChildClickListener = this
        rv_history.adapter = adapter
        p.getHotListResult()
        historyList = p.getHistoryList(this) as ArrayList<String>
    }

    override fun setTargetView(): View {
        return normal_view
    }

    override fun onDestroy() {
        super.onDestroy()
        p.detachView()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_searech
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search,menu)
        val itemSearch = menu.findItem(R.id.menu_search).actionView as SearchView
        itemSearch.queryHint = getString(R.string.input_search_content)
        itemSearch.isIconified = false
        itemSearch.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                if (!historyList.contains(query)) {
                    historyList.add(query)
                    p.saveHistory(this@SearchActivity, historyList)
                }
                Bundle().apply {
                    putString(Constant.SEARCH_RESULT_KEY, query)
                }.let {
                    JumpUtil.overlay(this@SearchActivity, SearchDetailActivity::class.java, it)
                }
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun showLoading() {
        super.showLoading()
        p.getHotListResult()
        historyList = p.getHistoryList(this) as ArrayList<String>
    }

    override fun reload() {
        showLoading()
    }

    override fun getHotListOk(beanList: List<HotKeyBean>) {
        adapter.setNewData(historyList)
        initFlowLayout(beanList)
        showNormal()
    }

    private fun initFlowLayout(beanList: List<HotKeyBean>) {
        val tagAdapter = object : TagAdapter<HotKeyBean>(beanList) {
            override fun getView(parent: FlowLayout?, position: Int, searchHot: HotKeyBean): View {
                val text = layoutInflater.inflate(R.layout.textview_item_hot, null) as TextView
                val name = searchHot.name
                text.text = name
                text.setTextColor(ColorUtil.randomColor)
                return text
            }
        }
        flow_search.adapter = tagAdapter
        flow_search.setOnTagClickListener { _, position, _ ->
            val name = beanList[position].name
            if (!historyList.contains(name)) {
                historyList.add(name)
                p.saveHistory(this, historyList)
            }
            Bundle().apply {
                putString(Constant.SEARCH_RESULT_KEY, name)
            }.let {
                JumpUtil.overlay(this, SearchDetailActivity::class.java, it)
            }
            return@setOnTagClickListener true
        }
    }

    override fun getHotListErr(err: String) {
        showError()
    }

    /**
     * 点击搜索历史进行搜索
     */
    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View?, position: Int) {
        val name = adapter.data[position] as String
        Bundle().apply {
            putString(Constant.SEARCH_RESULT_KEY, name)
        }.let {
            JumpUtil.overlay(this, SearchDetailActivity::class.java, it)
        }
    }

    /**
     * 清空单个搜索历史
     */
    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View?, position: Int) {
        val name = adapter.data[position] as String
        historyList.remove(name)
        adapter.notifyDataSetChanged()
        p.saveHistory(this,historyList)
    }

    /**
     * 清空所有的搜索历史
     */
    override fun onClick(v: View?) {
        historyList.clear()
        adapter.notifyDataSetChanged()
        p.saveHistory(this,historyList)
    }
}