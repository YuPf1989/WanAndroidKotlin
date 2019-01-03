package com.rain.wanandroidkotlin.mvp.contract

import android.content.Context
import com.rain.wanandroidkotlin.base.IPresenter
import com.rain.wanandroidkotlin.base.IView
import com.rain.wanandroidkotlin.mvp.model.entity.HotKeyBean

/**
 * Author:rain
 * Date:2019/1/3 10:08
 * Description:
 */
interface SearchContract {
    interface View:IView{
         fun getHotListOk(beanList: List<HotKeyBean>)

         fun getHotListErr(err: String)
    }
    interface Presenter:IPresenter<View>{
         fun getHotListResult()

         fun saveHistory(context: Context, historyList: List<String>)

         fun getHistoryList(context: Context):List<String>
    }
}