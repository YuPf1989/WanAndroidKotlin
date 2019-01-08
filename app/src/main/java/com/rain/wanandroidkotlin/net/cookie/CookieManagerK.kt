package com.rain.wanandroidkotlin.net.cookie

import android.annotation.SuppressLint
import android.content.Context
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.rain.wanandroidkotlin.MyApp

/**
 * Author:rain
 * Date:2019/1/8 14:37
 * Description:
 */
@SuppressLint("StaticFieldLeak")
object CookieManagerK {
    val context:Context = MyApp.getApplication()
    private lateinit var cookieJar: PersistentCookieJar

    fun clearCookies(){
        cookieJar.clear()
    }

    fun getCookieJar():PersistentCookieJar{
        cookieJar =  PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(context))
        return cookieJar
    }
}