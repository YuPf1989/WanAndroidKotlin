package com.rain.wanandroidkotlin.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.rain.wanandroidkotlin.R
import com.rain.wanandroidkotlin.base.BaseFragment
import com.rain.wanandroidkotlin.base.LazyLoadFragment
import com.rain.wanandroidkotlin.mvp.contract.HomeContract
import com.rain.wanandroidkotlin.mvp.model.entity.BenarBean
import com.rain.wanandroidkotlin.mvp.model.entity.HomePageArticleBean
import com.rain.wanandroidkotlin.mvp.presenter.HomePresenter
import com.rain.wanandroidkotlin.net.exception.ExceptionHandle
import com.rain.wanandroidkotlin.ui.activity.HomeDetailActivity
import com.rain.wanandroidkotlin.ui.adapter.HomeAdapter
import com.rain.wanandroidkotlin.util.Constant
import com.rain.wanandroidkotlin.util.GlideImageLoader
import com.rain.wanandroidkotlin.util.JumpUtil
import com.rain.wanandroidkotlin.util.ToastUtil
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.view_banner.*
import java.util.*

/**
 * Author:rain
 * Date:2018/11/19 11:18
 * Description:
 */
class HomeFragment : BaseFragment(), HomeContract.View {
    var p: HomePresenter? = null
    var adapter: HomeAdapter? = null
    var bannerView: LinearLayout? = null
    var linkList: ArrayList<String>? = null
    var imageList: ArrayList<String>? = null
    var titleList: ArrayList<String>? = null

    companion object {
        fun getInstance() = HomeFragment()
    }

    override fun showLoading() {
        super.showLoading()
        p!!.autoRefresh()
    }

    private fun initRefreshLayout() {
        refresh_layout.setOnRefreshListener {
            p!!.autoRefresh()
            refresh_layout.finishRefresh(1000)
        }
        refresh_layout.setOnLoadMoreListener {
            p!!.loadMore()
            refresh_layout.finishLoadMore(1000)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initView(savedInstanceState: Bundle?) {
        p = HomePresenter()
        p!!.attachView(this)
        initBanner()
        initRecycler()
        initRefreshLayout()
        initData()
        showLoading()
    }

    private fun initData() {
        linkList = ArrayList<String>()
        imageList = ArrayList<String>()
        titleList = ArrayList<String>()
    }

    private fun initBanner() {
        bannerView = layoutInflater.inflate(R.layout.view_banner, null) as LinearLayout
    }

    private fun initRecycler() {
        recycyler.layoutManager = LinearLayoutManager(mContext)
        adapter = HomeAdapter(R.layout.item_homepage, null)
        adapter!!.setOnItemClickListener { adapter, view, position ->

        }
        adapter!!.setOnItemChildClickListener { adapter, view, position ->

        }
        adapter!!.addHeaderView(bannerView)
        recycyler.adapter = adapter
    }

    override fun onDestroy() {
        p!!.detachView()
        super.onDestroy()
    }

    override fun getBannerOk(bannerBean: List<BenarBean>) {
        linkList!!.clear()
        titleList!!.clear()
        imageList!!.clear()
        for (benarBean in bannerBean) {
            imageList!!.add(benarBean.imagePath)
            titleList!!.add(benarBean.title)
            linkList!!.add(benarBean.url)
        }
        // banner git 地址 https://github.com/youth5201314/banner
        banner.setImageLoader(GlideImageLoader())
                .setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
                .setImages(imageList)
                .setBannerAnimation(Transformer.Accordion)
                .setBannerTitles(titleList)
                .isAutoPlay(true)
                .setDelayTime(5000)
                .setIndicatorGravity(BannerConfig.RIGHT)
                .start()
        banner.setOnBannerListener { position ->
            if (!TextUtils.isEmpty(linkList!!.get(position))) {
                val bundle = Bundle()
                bundle.putString(Constant.HOME_DETAIL_TITLE, titleList!!.get(position))
                bundle.putString(Constant.HOME_DETAIL_PATH, linkList!!.get(position))
                JumpUtil.overlay(context!!, HomeDetailActivity::class.java, bundle)
            }
        }
    }

    override fun getBannerErr(info: String) {
    }

    override fun getHomePageListOk(homeList: HomePageArticleBean) {
        showNormal()
        adapter!!.setNewData(homeList.datas)
    }

    override fun getHomePageListErr(info: String) {
        showError(ExceptionHandle.errorCode,info)
        ToastUtil.showToast(info)
    }

    override fun reload() {
        showLoading()
    }

    override fun loadEnd() {
        refresh_layout.finishLoadMoreWithNoMoreData()
    }

    override fun loadComplete() {
        refresh_layout.finishLoadMore(true)
    }

    override fun loadFail() {
        refresh_layout.finishLoadMore(false)
    }

    override fun setLoadMoreData(any: Any) {
        adapter!!.addData((any as HomePageArticleBean).datas)
    }

    fun scrollToTop() {
        recycyler.smoothScrollToPosition(0)
    }
}