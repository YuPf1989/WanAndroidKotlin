package com.rain.wanandroidkotlin

import android.app.Application
//import com.didichuxing.doraemonkit.DoraemonKit

/**
 * Author:rain
 * Date:2018/11/15 17:38
 * Description:
 */
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return
//        }
//        LeakCanary.install(this);
        instance = this
//        DoraemonKit.install(this)
    }

    companion object {
        lateinit var instance: Application
            private set
//        fun getApplication() = instance
    }
}