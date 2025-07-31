package com.td.demo

import com.td.core.TDError
import com.td.demo.base.ViewBindingActivity
import com.td.demo.databinding.ActivityInterBinding
import com.td.out.TDInterstitial
import com.td.out.TDInterstitialAd
import com.td.out.TDInterstitialAdListener
import com.td.out.TDInterstitialConfig

class InterActivity : ViewBindingActivity<ActivityInterBinding>(), TDInterstitialAdListener {

    private val interstitialAd = TDInterstitialAd(DemoActivity.INTER_UNIT_ID, TDInterstitialConfig())

    override fun initView(binding: ActivityInterBinding) {
        interstitialAd.setListener(this)

        binding.btnBack.setOnClickListener { finish() }
        binding.btnLoad.setOnClickListener {
            interstitialAd.load()
        }
        binding.btnShow.setOnClickListener {
            if (interstitialAd.isReady) {
                interstitialAd.show()
            }
        }
    }

    override fun onAdLoaded(ad: TDInterstitial) {
        Logger.dt(this, "on inter load success")
        // interstitialAd.show()
    }

    override fun onError(error: TDError) {
        Logger.dt(this, "on inter load error: ${error.msg}")
    }

    override fun onAdShowedFail(error: TDError) {
        Logger.dt(this@InterActivity, "on inter on ad showed fail $error")
    }

    override fun onAdShowed() {
        Logger.dt(this@InterActivity, "on inter on ad showed")
    }

    override fun onAdDismissed() {
        Logger.dt(this@InterActivity, "on inter on ad dismissed")
    }

    override fun onAdClicked() {
        Logger.dt(this@InterActivity, "on inter on ad clicked")
    }

    override fun onDestroy() {
        super.onDestroy()
        interstitialAd.destroy()
    }
}