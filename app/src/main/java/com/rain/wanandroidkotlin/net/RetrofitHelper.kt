package com.rain.wanandroidkotlin.net

import com.rain.wanandroidkotlin.net.cookie.CookiesManager
import com.rain.wanandroidkotlin.net.my_gsonconvert.MyGsonConverterFactory
import com.rain.wanandroidkotlin.util.Constant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

/**
 * Author:rain
 * Date:2018/11/15 17:19
 * Description:
 * 默认单例模式
 */
object RetrofitHelper {
    val retrofit:Retrofit

    fun <T>creatApi(service: Class<T>):T{
        return retrofit.create(service)
    }
    init {
        val client = OkHttpClient.Builder()
                .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .cookieJar(CookiesManager())
                .build()

        retrofit = Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MyGsonConverterFactory.create())
                .client(client)
                .build()
    }
}