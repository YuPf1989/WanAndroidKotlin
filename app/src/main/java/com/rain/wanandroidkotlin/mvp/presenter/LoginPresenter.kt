package com.rain.wanandroidkotlin.mvp.presenter

import com.rain.wanandroidkotlin.base.PresenterImpl
import com.rain.wanandroidkotlin.mvp.contract.LoginContract
import com.rain.wanandroidkotlin.mvp.model.api.ApiService
import com.rain.wanandroidkotlin.mvp.model.entity.UserInfo
import com.rain.wanandroidkotlin.net.RetrofitHelper
import com.rain.wanandroidkotlin.net.exception.ExceptionHandle
import com.rain.wanandroidkotlin.net.myobserver.MyObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Author:rain
 * Date:2018/11/22 10:48
 * Description:
 */
class LoginPresenter( val v: LoginContract.View) : PresenterImpl<LoginContract.View>(),LoginContract.Presenter {

    override fun login(userName:String,pwd:String) {
        v.showLoading()
        RetrofitHelper.creatApi(ApiService::class.java)
                .login(userName,pwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object :MyObserver<UserInfo>(){
                    override fun onNext(t: UserInfo) {
                        v.loginOk(t)
                    }

                    override fun onSubscribe(d: Disposable) {
                        super.onSubscribe(d)
                        compositeDisposable?.add(d)
                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)
                        v.loginErr(ExceptionHandle.errorCode,ExceptionHandle.errorMsg)
                    }
                })
    }
}