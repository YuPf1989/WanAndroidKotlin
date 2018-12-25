package com.rain.wanandroidkotlin.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.rain.wanandroidkotlin.R
import com.rain.wanandroidkotlin.mvp.model.entity.SystemBean

/**
 * Author:rain
 * Date:2018/12/25 16:36
 * Description:
 */
class SystemAdapter(layoutResId: Int, data: List<SystemBean>) : BaseQuickAdapter<SystemBean, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder, item: SystemBean?) {
        helper.setText(R.id.tv_knowledge_title, item?.getName())
        val sb = StringBuilder()
        for (childrenBean in item!!.getChildren()) {
            sb.append(childrenBean.name).append("      ")
        }
        helper.setText(R.id.tv_knowledge_content, sb.toString())
    }
}