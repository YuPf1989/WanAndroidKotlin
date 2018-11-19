package com.rain.wanandroidkotlin.util

import android.annotation.SuppressLint
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * 通过反射 修改 bottomnavigation 的 shiftMode
 * ( ShiftingMode，通过查看源码发现，只要 Item 的数量达到 4 个的时候，就会开启 ShiftingMode。)
 */

object BottomNavigationViewHelper {
    @SuppressLint("RestrictedApi")
    fun disableShiftMode(view: BottomNavigationView) {
        val menuView = view.getChildAt(0) as BottomNavigationMenuView
        try {
            val shiftingMode = menuView.javaClass.getDeclaredField("mShiftingMode")
            shiftingMode.setAccessible(true)
            shiftingMode.setBoolean(menuView, false)
            shiftingMode.setAccessible(false)
            for (i in 0 until menuView.getChildCount()) {
                val item = menuView.getChildAt(i) as BottomNavigationItemView

                item.setShifting(false)
                // set once again checked value, so view will be updated
                item.setChecked(item.getItemData().isChecked())
            }
        } catch (e: NoSuchFieldException) {
        } catch (e: IllegalAccessException) {
        }

    }
}
