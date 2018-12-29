package com.rain.wanandroidkotlin.ui.activity

import android.os.Bundle
import com.rain.wanandroidkotlin.R
import com.rain.wanandroidkotlin.base.BaseActivity
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Author:rain
 * Date:2018/11/22 10:27
 * Description:
 */
class RegisterActivity:BaseActivity() {
    override fun initView(savedInstanceState: Bundle?) {
        initToolbar(toolbar,"注册",true)


    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }
}