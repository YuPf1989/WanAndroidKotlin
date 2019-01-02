package com.rain.wanandroidkotlin.mvp.contract

import com.rain.wanandroidkotlin.base.IPresenter
import com.rain.wanandroidkotlin.base.IView
import com.rain.wanandroidkotlin.mvp.model.entity.HotBean

/**
 * Author:rain
 * Date:2019/1/2 16:52
 * Description:
 */
interface HotContract {
    interface View:IView{
         fun getHotResultOK(hotBeans: List<HotBean>)

         fun getHotResultErr(err: String)
    }
    interface Presenter:IPresenter<View>{
         fun getHotList()
    }
}