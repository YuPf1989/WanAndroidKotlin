package com.rain.wanandroidkotlin

import android.app.Application

/**
 * Author:rain
 * Date:2018/11/15 17:38
 * Description:
 */
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        private lateinit var instance: Application
        fun getApplication() = instance
    }
}