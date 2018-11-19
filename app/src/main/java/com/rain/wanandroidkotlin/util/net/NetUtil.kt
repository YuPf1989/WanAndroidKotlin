package com.rain.wanandroidkotlin.util.net

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import androidx.annotation.RequiresApi

/**
 * Author:rain
 * Date:2018/11/16 16:44
 * Description:
 */
class NetUtil {
    /**
     * 没有连接网络
     */
    val NETWORK_NONE = -1
    /**
     * 移动网络
     */
    val NETWORK_MOBILE = 0
    /**
     * 无线网络
     */
    val NETWORK_WIFI = 1

    var mContext: Context? = null

    fun init(context: Context) {
        mContext = context
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    fun getNetWorkState(): Int {
        if (mContext == null) {
            throw UnsupportedOperationException("please use NetUtils before init it")
        }
        // 得到连接管理器对象
        val connMgr = mContext!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        //获取所有网络连接的信息
        val networks = connMgr.allNetworks
        //通过循环将网络信息逐个取出来
        for (network in networks) {
            //获取ConnectivityManager对象对应的NetworkInfo对象
            val networkInfo = connMgr.getNetworkInfo(network)
            if (networkInfo.isConnected) {
                return if (networkInfo.type == ConnectivityManager.TYPE_MOBILE) {
                    NETWORK_MOBILE
                } else {
                    NETWORK_WIFI
                }
            }
        }
        return NETWORK_NONE
    }
}