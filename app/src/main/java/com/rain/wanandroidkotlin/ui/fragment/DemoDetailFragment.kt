package com.rain.wanandroidkotlin.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.rain.wanandroidkotlin.R
import com.rain.wanandroidkotlin.base.LazyLoadFragment
import com.rain.wanandroidkotlin.mvp.contract.DemoDetailContract
import com.rain.wanandroidkotlin.mvp.model.entity.DemoDetailListBean
import com.rain.wanandroidkotlin.mvp.model.entity.WxPublicListBean
import com.rain.wanandroidkotlin.mvp.presenter.DemoDetailPresenter
import com.rain.wanandroidkotlin.net.exception.ExceptionHandle
import com.rain.wanandroidkotlin.ui.adapter.DemoDetailAdapter
import com.rain.wanandroidkotlin.ui.adapter.WxDetailAdapter
import com.rain.wanandroidkotlin.util.Constant
import com.rain.wanandroidkotlin.util.ToastUtil
import kotlinx.android.synthetic.main.fragment_system_detail.*

/**
 * Author:rain
 * Date:2018/12/26 9:39
 * Description:
 */
class DemoDetailFragment : LazyLoadFragment(), DemoDetailContract.View {
    var demoId = -1
    var p: DemoDetailPresenter? = null
    var adapter: DemoDetailAdapter? = null

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
        p!!.detachView()
        super.onDestroy()
    }

    override fun setTargetView(): View {
        return refresh_layout
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_system_detail
    }

    override fun initView(savedInstanceState: Bundle?) {
        demoId = arguments!![Constant.DEMO_FRAGMENT_ID] as Int
        p = DemoDetailPresenter()
        p!!.attachView(this)

        refresh_layout.setOnRefreshListener {
            p!!.autoRefresh()
            it.finishRefresh(1000)
        }
        refresh_layout.setOnLoadMoreListener {
            p!!.loadMore()
            it.finishRefresh(1000)
        }

        adapter = DemoDetailAdapter(R.layout.item_demo_list, null)
        rv.layoutManager = LinearLayoutManager(mContext)
        rv.adapter = adapter

    }

    override fun fetchData() {
        showLoading()
    }

    override fun showLoading() {
        super.showLoading()
        p!!.getDemoListResult(demoId, 1)
    }

    override fun reload() {
        showLoading()
    }

    override fun getDemoListOk(bean: DemoDetailListBean) {
        showNormal()
        adapter!!.setNewData(bean.datas)
    }

    override fun getDemoListErr(err: String) {
        showError(ExceptionHandle.errorCode, err)
        ToastUtil.showToast(err)
    }

    override fun loadEnd() {
        refresh_layout.finishLoadMoreWithNoMoreData()
    }

    override fun loadComplete() {
        refresh_layout.finishLoadMore(true)
    }

    override fun loadFail() {
        refresh_layout.finishLoadMore(true)
    }

    override fun setLoadMoreData(any: Any) {
        adapter!!.addData((any as DemoDetailListBean).datas)
    }
}