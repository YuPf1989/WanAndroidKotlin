package com.rain.wanandroidkotlin.ui.adapter

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.rain.wanandroidkotlin.ui.fragment.SystemListLayoutFragment

/**
 * Author:rain
 * Date:2018/12/26 11:04
 * Description:
 */
class SystemDetailFragmentAdapter(fm: FragmentManager, var titles:ArrayList<String>, var fragments: ArrayList<SystemListLayoutFragment>) : FragmentPagerAdapter(fm) {
     lateinit var currentFragment:SystemListLayoutFragment
    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

    /**
     * 获取当前显示的fragment
     */
    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
        currentFragment = `object` as SystemListLayoutFragment
        super.setPrimaryItem(container, position, `object`)
    }
}