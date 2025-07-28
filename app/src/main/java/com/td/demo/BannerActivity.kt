package com.td.demo

import com.td.core.TDError
import com.td.demo.base.ViewBindingActivity
import com.td.demo.databinding.ActivityBannerBinding
import com.td.out.TDBanner
import com.td.out.TDBannerAdListener
import com.td.out.TDBannerAdView
import com.td.out.TDBannerConfig

class BannerActivity : ViewBindingActivity<ActivityBannerBinding>(), TDBannerAdListener {
    private var bannerAd: TDBannerAdView? = null

    override fun initView(binding: ActivityBannerBinding) {
        binding.btnBack.setOnClickListener { finish() }
        binding.btn32050.setOnClickListener {
            loadAd(DemoActivity.BANNER_320_50_UNIT_ID)
        }

        binding.btn300250.setOnClickListener {
            loadAd(DemoActivity.BANNER_300_250_UNIT_ID)
        }
        binding.btn32090.setOnClickListener {
            loadAd(DemoActivity.BANNER_320_90_UNIT_ID)
        }
        binding.btn72890.setOnClickListener {
            loadAd(DemoActivity.BANNER_728_90_UNIT_ID)
        }
        binding.btn800600.setOnClickListener {
            loadAd(DemoActivity.BANNER_800_600_UNIT_ID)
        }
    }


    private fun loadAd(unitId: String) {
        bannerAd?.destroy()
        binding.containerBanner.removeAllViews()

        val adView = TDBannerAdView(this, unitId, TDBannerConfig())
        bannerAd = adView
        adView.setListener(this)
        adView.load()
        binding.containerBanner.addView(adView)
    }

    override fun onAdLoaded(ad: TDBanner) {
        Logger.dt(this@BannerActivity, "on banner load success")
    }

    override fun onAdShowed() {
        Logger.dt(this@BannerActivity, "on banner show")
    }

    override fun onAdDismissed() {
        Logger.dt(this@BannerActivity, "on banner dismissed")
    }

    override fun onAdClicked() {
        Logger.dt(this@BannerActivity, "on banner clicked")
    }

    override fun onError(error: TDError) {
        Logger.dt(this@BannerActivity, "on banner load fail: ${error.msg}")
    }

    override fun onDestroy() {
        super.onDestroy()
        bannerAd?.destroy()
    }
}