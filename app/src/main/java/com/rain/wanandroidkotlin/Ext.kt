package com.rain.wanandroidkotlin

import android.util.Log
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.rain.wanandroidkotlin.util.Constant

/**
 * Author:rain
 * Date:2019/1/8 14:51
 * Description:
 */

/**
 * Log.e在kotlin语法上的扩展,[tag]tag,[content]输出内容
 */
fun loge(tag: String, content: String?) {
    Log.e(tag, content ?: tag)
}


fun getCookieJar(): PersistentCookieJar {
    return Constant.cookiejar
            ?: PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(MyApp.instance)).apply {
                Constant.cookiejar = this
            }
}

fun clearCookies() {
    Constant.cookiejar?.clear()
}