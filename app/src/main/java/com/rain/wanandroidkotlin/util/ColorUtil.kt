package com.rain.wanandroidkotlin.util

import android.graphics.Color

import java.util.Random


object ColorUtil {

    val randomColor: Int
        get() {
            val random = Random()
            val red = random.nextInt(160)
            val green = random.nextInt(160)
            val blue = random.nextInt(160)
            return Color.rgb(red, green, blue)
        }

}
