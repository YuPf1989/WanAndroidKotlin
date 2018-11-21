package com.rain.wanandroidkotlin.mvp.model

/**
 * Author:rain
 * Date:2018/11/15 17:31
 * Description:
 * 基本响应数据类
 */
data class BaseResp<T>(val data: T, val code: Int, val msg: String) {
    fun isOK() = code == 0
}