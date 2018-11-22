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
class HomeDetailActivity : BaseActivity() {
    var homeTitle: String? = null
    var homeArtistPath: String? = null
    var detailId: Int? = null
    var isCollect: Boolean? = null
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_article_share -> {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_TEXT, "来自WanAndroid 【${homeTitle}】$homeArtistPath")
                startActivity(Intent.createChooser(shareIntent, "分享"))
            }
            R.id.menu_article_collect -> {
                val isLogin = SharedPreferenceUtil.get(this, Constant.ISLOGIN, false) as Boolean
                if (isLogin) {
                    // 请求网络，如果收藏，取消，否则收藏 todo
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


}