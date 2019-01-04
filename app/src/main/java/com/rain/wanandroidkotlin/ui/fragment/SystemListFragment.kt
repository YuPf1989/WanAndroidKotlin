package com.rain.wanandroidkotlin.ui.fragment

import android.app.ActivityOptions
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.rain.wanandroidkotlin.R
import com.rain.wanandroidkotlin.base.LazyLoadLayoutFragment
import com.rain.wanandroidkotlin.mvp.contract.SystemDetailListContract
import com.rain.wanandroidkotlin.mvp.model.entity.SystemDetailListBean
import com.rain.wanandroidkotlin.mvp.presenter.SystemDetailListPresenter
import com.rain.wanandroidkotlin.ui.activity.HomeDetailActivity
import com.rain.wanandroidkotlin.ui.adapter.SystemDetailListAdapter
import com.rain.wanandroidkotlin.util.Constant
import com.rain.wanandroidkotlin.util.JumpUtil
import com.rain.wanandroidkotlin.util.ToastUtil
import kotlinx.android.synthetic.main.fragment_system_detail.*


private const val TAG = "SystemListLayoutFragment"
/**
 * Author:rain
 * Date:2018/12/29 11:46
 * Description:
 * 注意这里的page参数，page是从0开始的
 */
class SystemListLayoutFragment:LazyLoadLayoutFragment(),SystemDetailListContract.View {

    lateinit var p:SystemDetailListContract.Presenter
    lateinit var adapter:SystemDetailListAdapter
    var sysId = 0

    companion object {
        fun getInstance(id: Int): SystemListLayoutFragment {
            val fragment = SystemListLayoutFragment()
            val bundle = Bundle()
            bundle.putInt(Constant.SYSTEM_FRAGMENT_ID, id)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onDestroy() {
        p.detachView()
        super.onDestroy()
    }

    override fun setTargetView(): View {
        return refresh_layout
    }

    fun scrollToTop(){
        rv.smoothScrollToPosition(0)
    }

    override fun fetchData() {
        showLoading()
    }

    override fun showLoading() {
        super.showLoading()
        p.getSystemDetailList(0,sysId)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_system_detail
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initView(savedInstanceState: Bundle?) {

        p= SystemDetailListPresenter()
        p.attachView(this)
        sysId = arguments!![Constant.SYSTEM_FRAGMENT_ID] as Int
        refresh_layout.setOnRefreshListener {
            p.autoRefresh()
            it.finishRefresh(1000)
        }
        refresh_layout.setOnLoadMoreListener {
            p.loadMore()
            it.finishRefresh(1000)
        }

        adapter = SystemDetailListAdapter(R.layout.item_homepage, null)
        adapter.setOnItemClickListener { adapter, view, position ->
            val bean = adapter.data[position] as SystemDetailListBean.DatasBean
            Bundle().apply {
                putInt(Constant.HOME_DETAIL_ID, bean.getId())
                putString(Constant.HOME_DETAIL_PATH, bean.getLink())
                putString(Constant.HOME_DETAIL_TITLE, bean.getTitle())
                putBoolean(Constant.HOME_DETAIL_IS_COLLECT, bean.isCollect())
            }.let {
                // webview 和跳转的界面布局 transitionName 一定要相同
                val options = ActivityOptions.makeSceneTransitionAnimation(activity, view, getString(R.string.web_view))
                JumpUtil.overlay(activity!!, HomeDetailActivity::class.java,it,options.toBundle())
            }
        }
        rv.layoutManager = LinearLayoutManager(mContext)
        rv.adapter = adapter

    }

    override fun reload() {
        showLoading()
    }

    override fun getSystemDetailListResultOK(bean: SystemDetailListBean) {
        showNormal()
        adapter.setNewData(bean.datas)
    }

    override fun getSystemDetailListResultErr(err: String) {
        showError()
        ToastUtil.showToast(err)
    }

    override fun loadEnd() {
        ToastUtil.showToast("没有更多数据了！")
        refresh_layout.finishLoadMoreWithNoMoreData()
    }

    override fun loadComplete() {
        refresh_layout.finishLoadMore(true)
    }

    override fun loadFail() {
        refresh_layout.finishLoadMore(true)
    }

    override fun setLoadMoreData(any: Any) {
        adapter.addData((any as SystemDetailListBean).datas)
    }
}