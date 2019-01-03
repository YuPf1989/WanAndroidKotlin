package com.rain.wanandroidkotlin.ui.activity

import android.annotation.TargetApi
import android.app.ActivityOptions
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.rain.wanandroidkotlin.R
import com.rain.wanandroidkotlin.base.BaseLayoutActivity
import com.rain.wanandroidkotlin.mvp.contract.SearchDetailContract
import com.rain.wanandroidkotlin.mvp.model.entity.HomePageArticleBean
import com.rain.wanandroidkotlin.mvp.presenter.SearchDetailPresenter
import com.rain.wanandroidkotlin.ui.adapter.HomeAdapter
import com.rain.wanandroidkotlin.util.Constant
import com.rain.wanandroidkotlin.util.JumpUtil
import com.rain.wanandroidkotlin.util.ToastUtil
import kotlinx.android.synthetic.main.activity_searech_detail.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Author:rain
 * Date:2019/1/3 11:51
 * Description:
 */
class SearchDetailActivity:BaseLayoutActivity(),SearchDetailContract.View, BaseQuickAdapter.OnItemClickListener {
    private lateinit var adapter:HomeAdapter
    private lateinit var key:String
    private lateinit var p:SearchDetailPresenter
    override fun setTargetView(): View {
        return normal_view
    }

    override fun initView(savedInstanceState: Bundle?) {
        intent.extras?.getString(Constant.SEARCH_RESULT_KEY)?.let {
            key  = it
        }
        initToolbar(toolbar,key,true)
        p = SearchDetailPresenter()
        p.attachView(this)
        adapter = HomeAdapter(R.layout.item_homepage,null)
        adapter.setOnItemClickListener(this)
        rv_search_result.layoutManager = LinearLayoutManager(this)
        rv_search_result.adapter = adapter
        normal_view.setOnRefreshListener {
            p.autoRefresh()
            it.finishRefresh(1000)
        }
        normal_view.setOnLoadMoreListener {
            p.loadMore()
            it.finishRefresh(1000)
        }
        showLoading()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_searech_detail
    }

    override fun showLoading() {
        super.showLoading()
        p.getSearechResult(0,key)
    }

    override fun reload() {
        showLoading()
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View?, position: Int) {
        val bean = adapter.data[position] as HomePageArticleBean.DatasBean
        Bundle().apply {
            putInt(Constant.HOME_DETAIL_ID, bean.getId())
            putString(Constant.HOME_DETAIL_PATH, bean.getLink())
            putString(Constant.HOME_DETAIL_TITLE, bean.getTitle())
        }.let {
            val options = ActivityOptions.makeSceneTransitionAnimation(this, view, getString(R.string.web_view))
            JumpUtil.overlay(this, HomeDetailActivity::class.java,it,options.toBundle())
        }
    }

    override fun getSearechResultOk(bean: HomePageArticleBean) {
        adapter.setNewData(bean.datas)
        showNormal()
    }

    override fun getSearechResultErr(err: String) {

    }

    override fun loadEnd() {
        ToastUtil.showToast(getString(R.string.no_more_data))
        normal_view.finishLoadMoreWithNoMoreData()
    }

    override fun loadComplete() {
        normal_view.finishLoadMore(true)
    }

    override fun loadFail() {
        ToastUtil.showToast(getString(R.string.data_load_error))
        normal_view.finishLoadMore(false)
    }

    override fun setLoadMoreData(any: Any) {
        adapter.addData((any as HomePageArticleBean).datas)
    }
}