package com.rain.wanandroidkotlin

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.rain.wanandroidkotlin.base.BaseActivity
import com.rain.wanandroidkotlin.ui.fragment.DemoFragment
import com.rain.wanandroidkotlin.ui.fragment.HomeFragment
import com.rain.wanandroidkotlin.ui.fragment.SystemFragment
import com.rain.wanandroidkotlin.ui.fragment.WxFragment
import com.rain.wanandroidkotlin.util.BottomNavigationViewHelper
import com.rain.wanandroidkotlin.util.ToastUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    var fragmentList:ArrayList<Fragment>? = null
    var lastSelect = 0

    private val naviListener = BottomNavigationView.OnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.navigation_home -> {
                selectFragment(0)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_system -> {
                selectFragment(1)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_demo -> {
                selectFragment(2)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_wx -> {
                selectFragment(3)
                return@OnNavigationItemSelectedListener true
            }
            else -> return@OnNavigationItemSelectedListener false

        }
    }

    private fun selectFragment(current: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        val lastFragment = fragmentList!!.get(lastSelect)
        val currentFragment = fragmentList!!.get(current)
        lastSelect = current
        transaction.hide(lastFragment)
        if (!currentFragment.isAdded) {
            // todo 与原作者不太一样
            transaction.add(R.id.content_main,currentFragment)
        }
        transaction.show(currentFragment).commitAllowingStateLoss()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView(savedInstanceState: Bundle?) {
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        BottomNavigationViewHelper.disableShiftMode(bottom_navigation)
        bottom_navigation.setOnNavigationItemSelectedListener(naviListener)

        initData()

    }

    private fun initData() {
        initFragment()
        selectFragment(0)
    }

    private fun initFragment() {
        fragmentList = ArrayList()
        fragmentList!!.add( HomeFragment.getInstance())
        fragmentList!!.add( SystemFragment.getInstance())
        fragmentList!!.add( DemoFragment.getInstance())
        fragmentList!!.add( WxFragment.getInstance())

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_hot -> {
                ToastUtil.showToast("action_hot")
                return true
            }
            R.id.action_search -> {
                ToastUtil.showToast("action_search")
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
