package com.rain.wanandroidkotlin.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.rain.wanandroidkotlin.R

/**
 * Author:rain
 * Date:2019/1/3 11:07
 * Description:
 */
class SearchAdapter(layoutResId: Int, data: List<String>?):BaseQuickAdapter<String,BaseViewHolder>(layoutResId,data) {
    override fun convert(helper: BaseViewHolder, item: String?) {
        helper.setText(R.id.tv_history, item)
        helper.addOnClickListener(R.id.image_close)
    }
}