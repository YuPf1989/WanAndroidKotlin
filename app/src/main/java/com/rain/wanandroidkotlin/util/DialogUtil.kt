package com.rain.wanandroidkotlin.util

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar

/**
 * Author:rain
 * Date:2018/3/29 17:33
 * Description:
 */

object DialogUtil {
    fun createCenterProgressBar(mContext: Activity): ProgressBar {
        //整个Activity布局的最终父布局,参见参考资料
        val rootFrameLayout = mContext.findViewById<View>(android.R.id.content) as FrameLayout
        val layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.gravity = Gravity.CENTER
        val mProgressBar = ProgressBar(mContext)
        mProgressBar.layoutParams = layoutParams
        mProgressBar.visibility = View.VISIBLE
        rootFrameLayout.addView(mProgressBar)
        return mProgressBar
    }

    fun createProgressDialog(mContext: Context, msg: String): ProgressDialog {
        val mProgressDialog = ProgressDialog(mContext)
        mProgressDialog.setMessage(msg)
        mProgressDialog.show()
        return mProgressDialog
    }
}
