package com.td.demo

import com.td.core.TDError
import com.td.demo.base.ViewBindingActivity
import com.td.demo.databinding.ActivitySplashContainerBinding
import com.td.out.TDSplash
import com.td.out.TDSplashAd
import com.td.out.TDSplashAdListener
import com.td.out.TDSplashConfig

class SplashContainerActivity : ViewBindingActivity<ActivitySplashContainerBinding>(), TDSplashAdListener {

    private val splashAd = TDSplashAd(DemoActivity.SPLASH_UNIT_ID, TDSplashConfig())

    override fun initView(binding: ActivitySplashContainerBinding) {
        splashAd.setListener(this)
        loadSplash()
    }

    private fun loadSplash() {
        splashAd.load()
    }

    override fun onAdShowed() {
        Logger.dt(this@SplashContainerActivity, "splash on showed")
    }

    override fun onAdDismissed() {
        Logger.dt(this@SplashContainerActivity, "splash on dismissed")
        finish()
    }

    override fun onAdClicked() {
        Logger.dt(this@SplashContainerActivity, "splash on clicked")
    }

    override fun onAdShowedFail(error: TDError) {
        Logger.et(this@SplashContainerActivity, "on splash showed fail")
    }

    override fun onAdLoaded(ad: TDSplash) {
        Logger.dt(this, "on splash load success")
        splashAd.show(binding.containerSplash)
    }

    override fun onError(error: TDError) {
        Logger.dt(this, "on splash load error: ${error.msg}")
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        splashAd.destroy()
    }
}