package com.rain.wanandroidkotlin.ui.adapter

import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.rain.wanandroidkotlin.R
import com.rain.wanandroidkotlin.mvp.model.entity.WxPublicListBean

/**
 * Author:rain
 * Date:2018/12/26 15:31
 * Description:
 */
class WxDetailAdapter(layoutResId: Int, data: List<WxPublicListBean.DatasBean>?) : BaseQuickAdapter<WxPublicListBean.DatasBean, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder, item: WxPublicListBean.DatasBean) {
        helper.getView<TextView>(R.id.tv_tag).setVisibility(View.GONE)
        if (!TextUtils.isEmpty(item.getTitle())) {
            helper.setText(R.id.tv_content, item.getTitle())
        }
        if (!TextUtils.isEmpty(item.getAuthor())) {
            helper.setText(R.id.tv_author, item.getAuthor())
        }
        if (!TextUtils.isEmpty(item.getNiceDate())) {
            helper.setText(R.id.tv_time, item.getNiceDate())
        }
        if (!TextUtils.isEmpty(item.getChapterName())) {
            helper.setText(R.id.tv_type, item.getSuperChapterName())
        }
        helper.setImageResource(R.id.image_collect, if (item.isCollect()) R.drawable.icon_collect else R.drawable.icon_no_collect)
    }
}