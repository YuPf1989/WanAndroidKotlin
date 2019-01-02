package com.rain.wanandroidkotlin.base

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hankkin.pagelayout.PageLayout

/**
 * Author:rain
 * Date:2018/11/16 10:58
 * Description:
 */
abstract class BaseFragment : Fragment(), ILayoutView {
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

    protected var mContext: Activity? = null
    private lateinit var mPageLayout: PageLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    abstract fun getLayoutId(): Int

    abstract fun initView(savedInstanceState: Bundle?)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mContext = activity
        mPageLayout = PageLayout.Builder(mContext!!)
                .initPage(setTargetView())
                .setOnRetryListener(object : PageLayout.OnRetryClickListener {
                    override fun onRetry() {
                        reload()
                    }
                })
                .create()
        initView(savedInstanceState)
        super.onViewCreated(view, savedInstanceState)
    }

    /**
     * 重写此方法将mPageLayout绑定到不同的布局上
     * 默认为当前的根view
     */
    open fun setTargetView():View{
        return view!!
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}