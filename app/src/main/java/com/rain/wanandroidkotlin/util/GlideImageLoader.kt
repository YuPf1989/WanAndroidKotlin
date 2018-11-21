package com.rain.wanandroidkotlin.util

import android.content.Context
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.youth.banner.loader.ImageLoader

/**
 * 主要用于 结合 banner 加载图片使用
 *
 */

class GlideImageLoader : ImageLoader() {
    override fun displayImage(context: Context, path: Any, imageView: ImageView) {
        Glide.with(context.applicationContext).load(path).into(imageView)
    }
}
