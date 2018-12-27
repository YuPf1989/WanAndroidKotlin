package com.rain.wanandroidkotlin.ui.adapter

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.rain.wanandroidkotlin.R
import com.rain.wanandroidkotlin.mvp.model.entity.DemoDetailListBean
import com.rain.wanandroidkotlin.mvp.model.entity.WxPublicListBean

/**
 * Author:rain
 * Date:2018/12/26 15:31
 * Description:
 */
class DemoDetailAdapter(layoutResId: Int, data: List<DemoDetailListBean.DatasBean>?) : BaseQuickAdapter<DemoDetailListBean.DatasBean, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder, item: DemoDetailListBean.DatasBean) {
        if (!TextUtils.isEmpty(item.title)) {
            helper.setText(R.id.tv_title, item.title)
        }
        if (!TextUtils.isEmpty(item.desc)) {
            helper.setText(R.id.tv_content, item.desc)
        }
        if (!TextUtils.isEmpty(item.niceDate)) {
            helper.setText(R.id.tv_time, item.niceDate)
        }
        if (!TextUtils.isEmpty(item.author)) {
            helper.setText(R.id.tv_author, item.author)
        }
        Glide.with(mContext).load(item.envelopePic).into(helper.getView(R.id.image_simple) as ImageView)
    }
}