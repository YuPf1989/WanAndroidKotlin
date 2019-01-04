package com.rain.wanandroidkotlin.ui.activity

import android.app.ActivityOptions
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.hankkin.pagelayout.PageLayout
import com.rain.wanandroidkotlin.R
import com.rain.wanandroidkotlin.base.BaseActivity
import com.rain.wanandroidkotlin.base.BaseLayoutActivity
import com.rain.wanandroidkotlin.mvp.contract.CollectListContract
import com.rain.wanandroidkotlin.mvp.model.entity.CollectBean
import com.rain.wanandroidkotlin.mvp.presenter.CollectListPresenter
import com.rain.wanandroidkotlin.net.exception.ExceptionHandle
import com.rain.wanandroidkotlin.ui.adapter.CollectListAdapter
import com.rain.wanandroidkotlin.util.Constant
import com.rain.wanandroidkotlin.util.JumpUtil
import com.rain.wanandroidkotlin.util.ToastUtil
import kotlinx.android.synthetic.main.activity_collect_list.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Author:rain
 * Date:2019/1/2 10:45
 * Description:
 */
class CollectionListActivity: BaseLayoutActivity(),CollectListContract.View, BaseQuickAdapter.OnItemClickListener {
    private lateinit var p:CollectListContract.Presenter
    private lateinit var adapter:CollectListAdapter

    override fun initView(savedInstanceState: Bundle?) {
        initToolbar(toolbar,"我的收藏",true)
        p = CollectListPresenter()
        p.attachView(this)
        refresh_layout.setOnRefreshListener {
            p.onRefresh()
            it.finishRefresh(1000)
        }
        refresh_layout.setOnLoadMoreListener {
            p.onLoadMore()
            it.finishRefresh(1000)
        }
        adapter = CollectListAdapter(R.layout.item_homepage,null)
        adapter.onItemClickListener = this
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter
        p.getCollectionList(0)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View?, position: Int) {
        val bean = adapter.data[position] as CollectBean.DatasBean
        Bundle().apply {
            putInt(Constant.HOME_DETAIL_ID, bean.id)
            putString(Constant.HOME_DETAIL_PATH, bean.link)
            putString(Constant.HOME_DETAIL_TITLE, bean.title)
            putBoolean(Constant.HOME_DETAIL_IS_COLLECT, true)
        }.let {
            val options = ActivityOptions.makeSceneTransitionAnimation(this, view, getString(R.string.web_view))
            JumpUtil.overlay(this, HomeDetailActivity::class.java,it,options.toBundle())
        }
    }

    override fun setTargetView(): View {
        return refresh_layout
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_collect_list
    }

    override fun onDestroy() {
        super.onDestroy()
        p.detachView()
    }

    override fun showLoading() {
        super.showLoading()
        p.onRefresh()
    }

    override fun reload() {
        showLoading()
    }

    override fun getCollectionListOK(collectBean: CollectBean) {
        showNormal()
        adapter.setNewData(collectBean.datas)
    }

    override fun getCollectionListErr(err: String) {
        if (err.contains(getString(R.string.please_login))) {
            JumpUtil.overlay(this, LoginActivity::class.java)
        }
        showError()
    }

    override fun loadEnd() {
        refresh_layout.finishLoadMoreWithNoMoreData()
        ToastUtil.showToast(getString(R.string.no_more_data))
    }

    override fun loadComplete() {
        refresh_layout.finishLoadMore(true)
    }

    override fun loadFail() {
        refresh_layout.finishLoadMore(false)
    }

    override fun setLoadMoreData(any: Any) {
        adapter.addData((any as CollectBean).datas)
    }

}