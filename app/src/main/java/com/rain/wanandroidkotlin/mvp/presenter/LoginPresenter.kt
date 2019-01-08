package com.rain.wanandroidkotlin.mvp.presenter

import com.rain.wanandroidkotlin.base.PresenterImpl
import com.rain.wanandroidkotlin.mvp.contract.LoginContract
import com.rain.wanandroidkotlin.mvp.model.api.ApiService
import com.rain.wanandroidkotlin.net.RetrofitHelper
import com.rain.wanandroidkotlin.net.exception.ExceptionHandle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Author:rain
 * Date:2018/11/22 10:48
 * Description:
 */
class LoginPresenter : PresenterImpl<LoginContract.View>(),LoginContract.Presenter {
    private lateinit var v: LoginContract.View

    override fun attachView(view: LoginContract.View) {
        super.attachView(view)
        v = view
    }

    override fun login(userName:String,pwd:String) {
        v.showLoading()
        val subscribe = RetrofitHelper.createApi(ApiService::class.java)
                .login(userName, pwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    v.loginOk(it)
                }, {
                    v.loginErr(ExceptionHandle.errorCode, ExceptionHandle.errorMsg)
                })

        addSubscription(subscribe)
    }
}