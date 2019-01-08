package com.rain.wanandroidkotlin.net

import android.annotation.SuppressLint
import com.rain.wanandroidkotlin.getCookieJar
import com.rain.wanandroidkotlin.net.my_gsonconvert.MyGsonConverterFactory
import com.rain.wanandroidkotlin.util.Constant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager


/**
 * Author:rain
 * Date:2018/11/15 17:19
 * Description:
 * 默认单例模式
 */
object RetrofitHelper {
    private val retrofit: Retrofit

    fun <T> createApi(service: Class<T>): T {
        return retrofit.create(service)
    }

    init {
        // 这里没有对客户端的证书进行校验，生产环境是不允许的
        val x509TrustManager = object : X509TrustManager {
            @SuppressLint("TrustAllX509TrustManager")
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            }

            @SuppressLint("TrustAllX509TrustManager")
            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            }
            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        }
        val trustManagerArray = arrayOf(x509TrustManager)
        val sslContext = SSLContext.getInstance("SSL").apply {
            init(null, trustManagerArray, SecureRandom())
        }

        val client = OkHttpClient.Builder()
                .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
                .cookieJar(getCookieJar())
                .followRedirects(true)
                .retryOnConnectionFailure(true)
                .followSslRedirects(true)
                .sslSocketFactory(sslContext.socketFactory, trustManagerArray[0])
                .build()

        retrofit = Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MyGsonConverterFactory.create())
                .client(client)
                .build()
    }
}