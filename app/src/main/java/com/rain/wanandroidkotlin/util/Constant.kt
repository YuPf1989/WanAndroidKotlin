package com.rain.wanandroidkotlin.util

import com.franmontiel.persistentcookiejar.PersistentCookieJar

/**
 * Author:rain
 * Date:2018/11/16 11:47
 * Description:
 * 存储一些常量值
 * const val:相当于java中的public static  final
 * val:相当于java中的private static  final,并提供public static  final getXX方法
 */
object Constant {
    const val BASE_URL = "https://www.wanandroid.com/"
    /**
     * 首页 banner key
     */
    const val HOME_DETAIL_TITLE = "banner_title"
    const val HOME_DETAIL_PATH = "banner_path"
    const val HOME_DETAIL_ID = "banner_id"
    const val HOME_DETAIL_IS_COLLECT = "banner_iscollect"

    /**
     * 用户相关 sp key
     */
    const val USERNAME = "username"
    const val PASSWORD = "password"
    const val ISLOGIN = "is_login"

    /**
     * int 网络返回值
     */
    const val REQUEST_ERROR = -1
    const val REQUEST_SUCCESS = 0

    const val WX_FRAGMENT_ID = "wx_fragment_id";
    const val DEMO_FRAGMENT_ID = "demo_fragment_id";
    const val SYSTEM = "system"
    const val SYSTEM_FRAGMENT_ID = "system_fragment_id"

    /**
     * 保存 搜索历史 sp key
     *
     */
    const val DEFAULT = "default"
    const val SEARCH_HISTORY = "search_history"

    /**
     * 搜索界面 跳转 搜索界面界面 key
     */
    const val SEARCH_RESULT_KEY = "search_result_key"


     var cookiejar:PersistentCookieJar? = null
}