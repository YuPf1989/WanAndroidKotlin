package com.rain.wanandroidkotlin.mvp.model

import android.content.Context
import com.rain.wanandroidkotlin.util.Constant
import com.rain.wanandroidkotlin.util.SharedPreferenceUtil
import java.util.*
import kotlin.collections.ArrayList

/**
 * Author:rain
 * Date:2019/1/3 10:46
 * Description:
 * 从本地sp获取历史记录
 */
class SearchModel {
    fun saveHistory(context: Context, historyList: List<String>) {
        //保存之前先清空之前的存储
        SharedPreferenceUtil.remove(context, Constant.SEARCH_HISTORY)
        //存储
        val sb = StringBuilder()
        if (historyList.isNotEmpty()) {
            for (s in historyList) {
                sb.append(s).append(",")
            }
            sb.delete(sb.length - 1, sb.length)
            SharedPreferenceUtil.put(context, Constant.SEARCH_HISTORY, sb.toString().trim { it <= ' ' })
        }
    }

    fun getHistoryList(context: Context): List<String> {
        val historyList:ArrayList<String> by lazy {
            return@lazy ArrayList<String>()
        }
        val histories = SharedPreferenceUtil[context, Constant.SEARCH_HISTORY, Constant.DEFAULT] as String
        if (histories != Constant.DEFAULT) {
            val history = histories.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            historyList.addAll(Arrays.asList(*history))
        }
        return historyList
    }
}