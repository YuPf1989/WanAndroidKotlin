package com.rain.wanandroidkotlin.ui.fragment

import android.app.ActivityOptions
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.rain.wanandroidkotlin.R
import com.rain.wanandroidkotlin.base.LazyLoadLayoutFragment
import com.rain.wanandroidkotlin.mvp.contract.DemoDetailContract
import com.rain.wanandroidkotlin.mvp.model.entity.DemoDetailListBean
import com.rain.wanandroidkotlin.mvp.presenter.DemoDetailPresenter
import com.rain.wanandroidkotlin.ui.activity.HomeDetailActivity
import com.rain.wanandroidkotlin.ui.adapter.DemoDetailAdapter
import com.rain.wanandroidkotlin.util.Constant
import com.rain.wanandroidkotlin.util.JumpUtil
import com.rain.wanandroidkotlin.util.ToastUtil
import kotlinx.android.synthetic.main.fragment_system_detail.*

/**
 * Author:rain
 * Date:2018/12/26 9:39
 * Description:
 */
class DemoDetailFragment : LazyLoadLayoutFragment(), DemoDetailContract.View {
    var demoId = -1
    lateinit var p: DemoDetailPresenter
    lateinit var adapter: DemoDetailAdapter

    companion object {
        fun getInstance(id: Int): DemoDetailFragment {
            val fragment = DemoDetailFragment()
            val bundle = Bundle()
            bundle.putInt(Constant.DEMO_FRAGMENT_ID, id)
            fragment.arguments = bundle
            return fragment
        }
    }

    fun scrollToTop() {
        rv.smoothScrollToPosition(0)
    }

    override fun onDestroy() {
        p.detachView()
        super.onDestroy()
    }

    override fun setTargetView(): View {
        return refresh_layout
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_system_detail
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initView(savedInstanceState: Bundle?) {
        demoId = arguments!![Constant.DEMO_FRAGMENT_ID] as Int
        p = DemoDetailPresenter()
        p.attachView(this)

        refresh_layout.setOnRefreshListener {
            p.autoRefresh()
            it.finishRefresh(1000)
        }
        refresh_layout.setOnLoadMoreListener {
            p.loadMore()
            it.finishRefresh(1000)
        }

        adapter = DemoDetailAdapter(R.layout.item_demo_list, null)
        adapter.setOnItemClickListener { adapter, view, position ->
            val bean = adapter.data[position] as DemoDetailListBean.DatasBean
            Bundle().apply {
                putInt(Constant.HOME_DETAIL_ID, bean.getId())
                putString(Constant.HOME_DETAIL_PATH, bean.getLink())
                putString(Constant.HOME_DETAIL_TITLE, bean.getTitle())
                putBoolean(Constant.HOME_DETAIL_IS_COLLECT, bean.isCollect())
            }.let {
                val options = ActivityOptions.makeSceneTransitionAnimation(activity, view, getString(R.string.web_view))
                JumpUtil.overlay(activity!!, HomeDetailActivity::class.java,it,options.toBundle())
            }
        }
        rv.layoutManager = LinearLayoutManager(mContext)
        rv.adapter = adapter
    }

    override fun fetchData() {
        showLoading()
    }

    override fun showLoading() {
        super.showLoading()
        p.getDemoListResult(demoId, 1)
    }

    override fun reload() {
        showLoading()
    }

    override fun getDemoListOk(bean: DemoDetailListBean) {
        showNormal()
        adapter.setNewData(bean.datas)
    }

    override fun getDemoListErr(err: String) {
        showError()
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
        adapter.addData((any as DemoDetailListBean).datas)
    }
}