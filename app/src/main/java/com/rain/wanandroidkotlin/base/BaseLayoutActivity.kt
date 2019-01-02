package com.rain.wanandroidkotlin.base

import android.os.Bundle
import android.view.View
import com.hankkin.pagelayout.PageLayout

/**
 * Author:rain
 * Date:2019/1/2 14:31
 * Description:
 * 带有不同状态布局的activity
 */
abstract class BaseLayoutActivity : BaseActivity(), ILayoutView {
    lateinit var mPageLayout: PageLayout
    lateinit var normalView:View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPageLayout = PageLayout.Builder(this)
                .initPage(setTargetView())
                .setOnRetryListener(object : PageLayout.OnRetryClickListener {
                    override fun onRetry() {
                        reload()
                    }
                })
                .create()

    }

    override fun showNormal() {
        mPageLayout.hide()
    }

    override fun showLoading() {
        mPageLayout.showLoading()
    }

    override fun showError(code: Int, msg: String) {
        mPageLayout.showError()
    }

    override fun showEmpty() {
        mPageLayout.showEmpty()
    }

    open fun setTargetView(): View {
        return normalView
    }
}