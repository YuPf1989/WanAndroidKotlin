package com.rain.wanandroidkotlin.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.hankkin.pagelayout.PageLayout
import com.rain.wanandroidkotlin.R

/**
 * Author:rain
 * Date:2019/1/2 14:31
 * Description:
 * 带有不同状态布局的activity
 */
abstract class BaseLayoutActivity : AppCompatActivity(), ILayoutView {
    private lateinit var mPageLayout: PageLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        mPageLayout = PageLayout.Builder(this)
                .initPage(setTargetView())
                .setLoading(R.layout.layout_loading_view,R.id.tv_loading)
                .setOnRetryListener(object : PageLayout.OnRetryClickListener {
                    override fun onRetry() {
                        reload()
                    }
                })
                .create()
        initView(savedInstanceState)
    }

    abstract fun initView(savedInstanceState: Bundle?)

    abstract fun getLayoutId(): Int

    abstract fun setTargetView(): View

    override fun showNormal() {
        mPageLayout.hide()
    }

    override fun showLoading() {
        mPageLayout.showLoading()
    }

    override fun showError() {
        mPageLayout.showError()
    }

    override fun showEmpty() {
        mPageLayout.showEmpty()
    }

    protected fun initToolbar(toobar: Toolbar, title:String, homeAsUpEnabled:Boolean) {
        toobar.title = title
        setSupportActionBar(toobar)
        // 设置toolbar是否有返回按钮
        supportActionBar?.setDisplayHomeAsUpEnabled(homeAsUpEnabled)
        toobar.setNavigationOnClickListener { finish() }
    }

}