package com.td.demo

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.td.core.TDError
import com.td.out.TDBanner
import com.td.out.TDBannerAdListener
import com.td.out.TDBannerAdView
import com.td.out.TDBannerConfig

class BannerActivity : AppCompatActivity(), TDBannerAdListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_banner)
        initView()
    }

    private lateinit var container: FrameLayout

    private var bannerAd: TDBannerAdView? = null

    private fun initView() {
        container = findViewById(R.id.container_banner)
        findViewById<ImageView>(R.id.btn_back).apply {
            setOnClickListener { finish() }
        }
        findViewById<TextView>(R.id.btn_320_50).apply {
            setOnClickListener {
                loadAd(DemoActivity.BANNER_320_50_UNIT_ID)
            }
        }
        findViewById<TextView>(R.id.btn_300_250).apply {
            setOnClickListener {
                loadAd(DemoActivity.BANNER_300_250_UNIT_ID)
            }
        }
        findViewById<TextView>(R.id.btn_320_90).apply {
            setOnClickListener {
                loadAd(DemoActivity.BANNER_320_90_UNIT_ID)
            }
        }
        findViewById<TextView>(R.id.btn_728_90).apply {
            setOnClickListener {
                loadAd(DemoActivity.BANNER_728_90_UNIT_ID)
            }
        }
        findViewById<TextView>(R.id.btn_800_600).apply {
            setOnClickListener {
                loadAd(DemoActivity.BANNER_800_600_UNIT_ID)
            }
        }
    }

    private fun loadAd(unitId: String) {
        bannerAd?.destroy()
        container.removeAllViews()

        val adView = TDBannerAdView(this, unitId, TDBannerConfig())
        bannerAd = adView
        adView.setListener(this)
        adView.load()
        container.addView(adView)
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