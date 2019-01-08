package com.rain.wanandroidkotlin.util

import com.franmontiel.persistentcookiejar.PersistentCookieJar

/**
 * Author:rain
 * Date:2018/11/16 11:47
 * Description:
 * 存储一些常量值
 */
object Constant {
    val BASE_URL = "http://www.wanandroid.com/"
    /**
     * 首页 banner key
     */
    val HOME_DETAIL_TITLE = "banner_title"
    val HOME_DETAIL_PATH = "banner_path"
    val HOME_DETAIL_ID = "banner_id"
    val HOME_DETAIL_IS_COLLECT = "banner_iscollect"

    /**
     * 用户相关 sp key
     */
    val USERNAME = "username"
    val PASSWORD = "password"
    val ISLOGIN = "is_login"

    /**
     * int 网络返回值
     */
    val REQUEST_ERROR = -1
    val REQUEST_SUCCESS = 0

    val WX_FRAGMENT_ID = "wx_fragment_id";
    val DEMO_FRAGMENT_ID = "demo_fragment_id";
    val SYSTEM = "system"
    val SYSTEM_FRAGMENT_ID = "system_fragment_id"

    /**
     * 保存 搜索历史 sp key
     *
     */
    val DEFAULT = "default"
    val SEARCH_HISTORY = "search_history"

    /**
     * 搜索界面 跳转 搜索界面界面 key
     */
    val SEARCH_RESULT_KEY = "search_result_key"


    var cookiejar:PersistentCookieJar? = null
}