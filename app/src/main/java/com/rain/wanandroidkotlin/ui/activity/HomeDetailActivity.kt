package com.rain.wanandroidkotlin.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.webkit.WebSettings
import com.just.agentweb.AgentWeb
import com.rain.wanandroidkotlin.R
import com.rain.wanandroidkotlin.base.BaseActivity
import com.rain.wanandroidkotlin.mvp.contract.HomeDetailContract
import com.rain.wanandroidkotlin.mvp.presenter.HomeDetailPresenter
import com.rain.wanandroidkotlin.util.Constant
import com.rain.wanandroidkotlin.util.JumpUtil
import com.rain.wanandroidkotlin.util.SharedPreferenceUtil
import com.rain.wanandroidkotlin.util.ToastUtil
import kotlinx.android.synthetic.main.activity_home_detail.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Author:rain
 * Date:2018/11/21 14:23
 * Description:
 */
class HomeDetailActivity : BaseActivity(), HomeDetailContract.View {
    var homeTitle: String? = null
    var homeArtistPath: String? = null
    var detailId: Int = -1
    var isCollect: Boolean = false
    lateinit var p: HomeDetailContract.Presenter
    var collectState: Int = 0// 收藏状态
    @SuppressLint("SetJavaScriptEnabled")
    override fun initView(savedInstanceState: Bundle?) {
        getIntentData()
        initToolbar(toolbar, homeTitle.toString(), true)
        val agentWeb = AgentWeb.with(this)
                .setAgentWebParent(container_webview, ViewGroup.LayoutParams(-1, -1)) // -1表示match_parent
                .useDefaultIndicator() // 使用默认的进度条
                .createAgentWeb()
                .ready()
                .go(homeArtistPath)
        val mSettings = agentWeb.webCreator.webView.settings
        mSettings.setJavaScriptEnabled(true)
        mSettings.setSupportZoom(true)
        mSettings.setBuiltInZoomControls(true)
        //不显示缩放按钮
        mSettings.setDisplayZoomControls(false)
        //设置自适应屏幕，两者合用
        //将图片调整到适合WebView的大小
        mSettings.setUseWideViewPort(true)
        //缩放至屏幕的大小
        mSettings.setLoadWithOverviewMode(true)
        //自适应屏幕
        mSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN)

        p = HomeDetailPresenter()
        p.attachView(this)

    }

    private fun getIntentData() {
        val bundle = intent.extras
        bundle?.let {
            // 从banner/首页条目传过来的数据
            homeTitle = bundle.getString(Constant.HOME_DETAIL_TITLE)
            homeArtistPath = bundle.getString(Constant.HOME_DETAIL_PATH)
            detailId = bundle.getInt(Constant.HOME_DETAIL_ID, Constant.REQUEST_ERROR)
            isCollect = bundle.getBoolean(Constant.HOME_DETAIL_IS_COLLECT)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_home_detail
    }

    override fun onDestroy() {
        super.onDestroy()
        p.detachView()
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        when (collectState) {
            1, 4 -> {
                isCollect = true
            }
            2, 3 -> {
                isCollect = false
            }
        }
        menu.findItem(R.id.menu_article_collect).setIcon(if (isCollect)
            R.drawable.icon_collect else R.drawable.icon_no_collect)
        menu.findItem(R.id.menu_article_collect).title = if (isCollect)
            getString(R.string.already_collect_title) else getString(R.string.like_title)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // 分享
            R.id.menu_article_share -> {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_TEXT, "来自WanAndroid 【${homeTitle}】$homeArtistPath")
                startActivity(Intent.createChooser(shareIntent, "分享"))
            }
            // 收藏
            R.id.menu_article_collect -> {
                val isLogin = SharedPreferenceUtil.get(this, Constant.ISLOGIN, false) as Boolean
                if (isLogin) {
                    // 请求网络，如果收藏，取消，否则收藏
                    if (detailId != -1) {
                        if (isCollect) {
                            p.cancelCollectArticle(detailId)
                        } else {
                            p.collectArticle(detailId)
                        }
                    }
                } else {
                    ToastUtil.showToast(getString(R.string.please_login))
                    JumpUtil.overlay(this, LoginActivity::class.java)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }


    // Menu菜单同时显示文字和图标
    override fun onMenuOpened(featureId: Int, menu: Menu?): Boolean {
        if (menu != null) {
            if (menu.javaClass.simpleName.equals("MenuBuilder", ignoreCase = true)) {
                try {
                    val method = menu.javaClass.getDeclaredMethod("setOptionalIconsVisible", java.lang.Boolean.TYPE)
                    method.isAccessible = true
                    method.invoke(menu, true)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        return super.onMenuOpened(featureId, menu)
    }

    override fun collectArticleOK(info: String) {
        collectState = 1
        ToastUtil.showToast(getString(R.string.collect_success))
    }

    override fun collectArticleErr(info: String) {
        collectState = 2
        if (info.contains(getString(R.string.please_login))) {
            ToastUtil.showToast(getString(R.string.please_login))
            JumpUtil.overlay(this, LoginActivity::class.java)
        } else {
            ToastUtil.showToast(getString(R.string.cancel_collect_fail)+info)
        }
    }

    override fun cancelCollectArticleOK(info: String) {
        collectState = 3
        ToastUtil.showToast(getString(R.string.cancel_collect_success))
    }

    override fun cancelCollectArticleErr(info: String) {
        collectState = 4
        if (info.contains(getString(R.string.please_login))) {
            ToastUtil.showToast(getString(R.string.please_login))
            JumpUtil.overlay(this, LoginActivity::class.java)
        } else {
            ToastUtil.showToast(getString(R.string.cancel_collect_fail)+info)
        }
    }
}