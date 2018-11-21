package com.rain.wanandroidkotlin.base

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

/**
 * Author:rain
 * Date:2018/11/16 9:58
 * Description:
 */
abstract class BaseActivity :AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())

        initView(savedInstanceState)

    }

    abstract fun initView(savedInstanceState: Bundle?)

    abstract fun getLayoutId(): Int

    protected fun initToolbar(toobar:Toolbar,title:String,homeAsUpEnabled:Boolean) {
        toobar.title = title
        setSupportActionBar(toobar)
        // 设置toolbar是否有返回按钮
        supportActionBar?.setDisplayHomeAsUpEnabled(homeAsUpEnabled)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }


}