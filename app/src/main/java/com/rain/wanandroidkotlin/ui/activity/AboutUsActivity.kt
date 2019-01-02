package com.rain.wanandroidkotlin.ui.activity

import android.os.Bundle
import com.rain.wanandroidkotlin.R
import com.rain.wanandroidkotlin.base.BaseActivity
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Author:rain
 * Date:2019/1/2 15:17
 * Description:
 */
class AboutUsActivity:BaseActivity() {
    override fun initView(savedInstanceState: Bundle?) {
        initToolbar(toolbar,getString(R.string.about_us),true)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_about_me
    }
}