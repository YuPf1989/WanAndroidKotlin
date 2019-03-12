package com.rain.wanandroidkotlin

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import com.rain.wanandroidkotlin.base.BaseActivity
import com.rain.wanandroidkotlin.util.DensityUtil
import com.rain.wanandroidkotlin.util.JumpUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Author:rain
 * Date:2019/1/7 16:58
 * Description:
 */
class SplashActivity:BaseActivity() {
    private val mImageList = listOf(R.drawable.splash_image01, R.drawable.splash_image02,
            R.drawable.splash_image03, R.drawable.splash_image04, R.drawable.splash_image05)

    private val disposable by lazy {
        CompositeDisposable()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

    override fun initView(savedInstanceState: Bundle?) {
        setTextAnimation()
        setTimeTvParams()
        splashIv.setImageResource(mImageList[Random().nextInt(mImageList.size)])
        disposable.add(intervalDisposable())
        timerTv.setOnClickListener {
            startNewActivity()
        }
    }

    private fun intervalDisposable(): Disposable {
        return Observable.intervalRange(0, 3, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    timerTv.text = String.format("%d s", 3 - it)
                }
                .doOnComplete {
                    startNewActivity()
                }
                .subscribe()
    }

    private fun startNewActivity(){
        JumpUtil.overlay(this,MainActivity::class.java)
        finish()
    }

    private fun setTimeTvParams() {
        val layoutParams = timerTv.layoutParams as ConstraintLayout.LayoutParams
        layoutParams.topMargin = DensityUtil.getStatusBarHeight(this) + DensityUtil.dip2px(24)
    }

    private fun setTextAnimation() {
        val halfScreenWidth = DensityUtil.getScreenWidth() * 1.0f / 2
        val textPaint = playTv.paint
        val playTextWidth = textPaint.measureText(playTv.text.toString()) ?: .0f
        val androidTextWidth = textPaint?.measureText(androidTv.text.toString())
                ?: .0f
        val playDistance = halfScreenWidth + playTextWidth / 2
        val androidDistance = halfScreenWidth + androidTextWidth / 2
        val playTranslationX = ObjectAnimator.ofFloat(playTv, "translationX", 0f, playDistance)
        val androidTranslationX = ObjectAnimator.ofFloat(androidTv, "translationX", 0f, -androidDistance)
        val playScaleX = ObjectAnimator.ofFloat(playTv, "scaleX", .5f, 1.0f)
        val playScaleY = ObjectAnimator.ofFloat(playTv, "scaleY", .5f, 1.0f)
        val androidScaleX = ObjectAnimator.ofFloat(playTv, "scaleX", .5f, 1.0f)
        val androidSlayScaleY = ObjectAnimator.ofFloat(androidTv, "scaleY", .5f, 1.0f)
        val set = AnimatorSet()
        set.playTogether(playTranslationX, androidTranslationX, playScaleX, playScaleY, androidScaleX, androidSlayScaleY)
        set.duration = 1200
        set.interpolator = OvershootInterpolator()
        androidTv.postDelayed({ set.start() }, 500)
    }

}