package com.rain.wanandroidkotlin.ui.adapter

import android.text.Html
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.rain.wanandroidkotlin.R
import com.rain.wanandroidkotlin.mvp.model.entity.HomePageArticleBean

/**
 * Author:rain
 * Date:2018/11/19 16:22
 * Description:
 */
class HomeAdapter(layoutResId: Int, data: List<HomePageArticleBean.DatasBean>?) : BaseQuickAdapter<HomePageArticleBean.DatasBean, BaseViewHolder>(layoutResId,data) {
    override fun convert(helper: BaseViewHolder, item: HomePageArticleBean.DatasBean) {
        helper.getView<View>(R.id.tv_tag).setVisibility(View.GONE)
        if (!TextUtils.isEmpty(item.getTitle())) {
            helper.setText(R.id.tv_content, Html.fromHtml(item.getTitle()))
        }
        if (!TextUtils.isEmpty(item.getAuthor())) {
            helper.setText(R.id.tv_author, item.getAuthor())
        }
        if (!TextUtils.isEmpty(item.getNiceDate())) {
            helper.setText(R.id.tv_time, item.getNiceDate())
        }
        if (!TextUtils.isEmpty(item.getChapterName())) {
            val classifyName = item.getSuperChapterName() + " / " + item.getChapterName()
            helper.setText(R.id.tv_type, classifyName)
        }
        if (item.getSuperChapterName().contains(mContext.getString(R.string.project))) {
            helper.getView<View>(R.id.tv_tag).setVisibility(View.VISIBLE)
            helper.setText(R.id.tv_tag, mContext.getString(R.string.project))
            helper.setTextColor(R.id.tv_tag, mContext.resources.getColor(R.color.green))
            helper.setBackgroundRes(R.id.tv_tag, R.drawable.drawable_shape_green)
        } else if (item.getSuperChapterName().contains(mContext.getString(R.string.hot))) {
            helper.getView<View>(R.id.tv_tag).setVisibility(View.VISIBLE)
            helper.setText(R.id.tv_tag, mContext.getString(R.string.hot))
            helper.setTextColor(R.id.tv_tag, mContext.resources.getColor(R.color.red))
            helper.setBackgroundRes(R.id.tv_tag, R.drawable.drawable_shape_red)
        }
        helper.addOnClickListener(R.id.image_collect)
        helper.setImageResource(R.id.image_collect, if (item.isCollect()) R.drawable.icon_collect else R.drawable.icon_no_collect)
    }
}