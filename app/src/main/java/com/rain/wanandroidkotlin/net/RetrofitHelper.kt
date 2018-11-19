package com.rain.wanandroidkotlin.net

import com.rain.wanandroidkotlin.util.Constant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Author:rain
 * Date:2018/11/15 17:19
 * Description:
 * 默认单例模式
 */
object RetrofitHelper {
    val retrofit:Retrofit

    fun <T>creatApi(service: Class<T>){
        retrofit.create(service)
    }
    init {
        val client = OkHttpClient.Builder()
                .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
                .build()

        retrofit = Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
    }
}