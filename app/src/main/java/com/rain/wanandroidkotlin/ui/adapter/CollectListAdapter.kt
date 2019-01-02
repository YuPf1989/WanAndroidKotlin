package com.rain.wanandroidkotlin.ui.adapter

import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.rain.wanandroidkotlin.R
import com.rain.wanandroidkotlin.mvp.model.entity.CollectBean
import com.rain.wanandroidkotlin.mvp.model.entity.SystemDetailListBean
import com.rain.wanandroidkotlin.mvp.model.entity.WxPublicListBean

/**
 * Author:rain
 * Date:2018/12/26 15:31
 * Description:
 */
class CollectListAdapter(layoutResId: Int, data: List<CollectBean.DatasBean>?) : BaseQuickAdapter<CollectBean.DatasBean, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder, item: CollectBean.DatasBean) {
        helper.getView<TextView>(R.id.tv_tag).visibility = View.GONE
        if (!TextUtils.isEmpty(item.title)) {
            helper.setText(R.id.tv_content, item.title)
        }
        if (!TextUtils.isEmpty(item.author)) {
            helper.setText(R.id.tv_author, item.author)
        }
        if (!TextUtils.isEmpty(item.niceDate)) {
            helper.setText(R.id.tv_time, item.niceDate)
        }
        if (!TextUtils.isEmpty(item.chapterName)) {
            helper.setText(R.id.tv_type, item.chapterName)
        }
        helper.setImageResource(R.id.image_collect, R.drawable.icon_collect)
    }
}