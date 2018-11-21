package com.rain.wanandroidkotlin.net.exception

import android.util.Log

import com.google.gson.JsonParseException
import com.rain.wanandroidkotlin.util.ToastUtil

import org.json.JSONException

import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException

/**
 * Author:rain
 * Date:2018/6/5 16:32
 * Description:
 * 网络异常处理类
 */
object ExceptionHandle {
    private val TAG = "ExceptionHandle"
    var errorCode = ErrorStatus.UNKNOWN_ERROR
    var errorMsg = "请求失败，请稍后重试"
    fun handleException(e: Throwable): String {
        e.printStackTrace()
        if (e is SocketTimeoutException) {
            errorMsg = "网络连接异常"
            errorCode = ErrorStatus.NETWORK_ERROR
        } else if (e is ConnectException) {
            errorMsg = "网络连接异常"
            errorCode = ErrorStatus.NETWORK_ERROR
        } else if (e !is JsonParseException && e !is JSONException && e !is ParseException) {
            if (e is ApiException) {
                errorMsg = e.message.toString()
                errorCode = ErrorStatus.SERVER_ERROR
            } else if (e is UnknownHostException) {
                errorMsg = "网络连接异常"
                errorCode = ErrorStatus.NETWORK_ERROR
            } else if (e is IllegalArgumentException) {
                errorMsg = "参数错误"
                errorCode = ErrorStatus.SERVER_ERROR
            } else {
                try {
                    Log.e(TAG, "错误: " + e.message)
                } catch (var3: Exception) {
                    Log.e(TAG, "未知错误Debug调试 ")
                }

                errorMsg = "未知错误，可能抛锚了吧~"
                errorCode = ErrorStatus.UNKNOWN_ERROR
            }
        } else {
            errorMsg = "数据解析异常"
            errorCode = ErrorStatus.SERVER_ERROR
        }
        ToastUtil.showToast(errorMsg)
        return errorMsg
    }
}

