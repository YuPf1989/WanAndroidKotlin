package com.rain.wanandroidkotlin.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import com.rain.wanandroidkotlin.MainActivity
import com.rain.wanandroidkotlin.R
import com.rain.wanandroidkotlin.base.BaseActivity
import com.rain.wanandroidkotlin.eventbus.UpdateUserInfo
import com.rain.wanandroidkotlin.mvp.contract.LoginContract
import com.rain.wanandroidkotlin.mvp.model.entity.UserInfo
import com.rain.wanandroidkotlin.mvp.presenter.LoginPresenter
import com.rain.wanandroidkotlin.util.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.toolbar.*
import org.greenrobot.eventbus.EventBus

/**
 * Author:rain
 * Date:2018/11/21 16:19
 * Description:
 */
class LoginActivity : BaseActivity(), LoginContract.View {
    private var pwd: String? = null
    private var userName: String? = null
    private lateinit var presenter: LoginPresenter
    private lateinit var pb: ProgressBar

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun initView(savedInstanceState: Bundle?) {
        presenter = LoginPresenter(this)
        presenter.attachView(this)
        pb = DialogUtil.createCenterProgressBar(this)
        pb.visibility = View.GONE
        initToolbar(toolbar, getString(R.string.login), true)
        tv_register.setOnClickListener {
            JumpUtil.overlay(this, RegisterActivity::class.java)
        }
        btn_login.setOnClickListener {
            if (check()) {
                presenter.login(userName!!, pwd!!)
            }
        }
    }

    private fun check(): Boolean {
        pwd = et_ensure_password.text.toString().trim()
        userName = et_ensure_username.text.toString().trim()
        if (pwd!!.length > 6 && userName!!.length > 6) {
            return true
        } else {
            ToastUtil.showToast(getString(R.string.username_incorrect))
            return false
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun loginOk(info: UserInfo) {
        pb.visibility = View.GONE
        ToastUtil.showToast(getString(R.string.login_ok))
        SharedPreferenceUtil.put(this, Constant.USERNAME, info.getUsername())
        SharedPreferenceUtil.put(this, Constant.PASSWORD, info.getPassword())
        SharedPreferenceUtil.put(this, Constant.ISLOGIN, true)
        EventBus.getDefault().post(UpdateUserInfo())
        finish()
    }

    override fun loginErr(code: Int, msg: String) {
        pb.visibility = View.GONE
    }

    override fun showLoading() {
        pb.visibility = View.VISIBLE
    }


}