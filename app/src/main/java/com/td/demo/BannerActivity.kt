package com.td.demo

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.td.core.TDError
import com.td.out.TDBanner
import com.td.out.TDBannerConfig
import com.td.out.TDBannerEventListener
import com.td.out.TDBannerLoadListener

class BannerActivity : AppCompatActivity(), TDBannerLoadListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_banner)
        initView()
    }

    private lateinit var container: FrameLayout

    private fun initView() {
        container = findViewById(R.id.container_banner)
        findViewById<ImageView>(R.id.btn_back).apply {
            setOnClickListener { finish() }
        }
        findViewById<TextView>(R.id.btn_320_50).apply {
            setOnClickListener {
                TDBanner.load(DemoActivity.BANNER_320_50_UNIT_ID, TDBannerConfig(), this@BannerActivity)
            }
        }
        findViewById<TextView>(R.id.btn_300_250).apply {
            setOnClickListener {
                TDBanner.load(DemoActivity.BANNER_300_250_UNIT_ID, TDBannerConfig(), this@BannerActivity)
            }
        }
        findViewById<TextView>(R.id.btn_320_90).apply {
            setOnClickListener {
                TDBanner.load(DemoActivity.BANNER_320_90_UNIT_ID, TDBannerConfig(), this@BannerActivity)
            }
        }
        findViewById<TextView>(R.id.btn_728_90).apply {
            setOnClickListener {
                TDBanner.load(DemoActivity.BANNER_728_90_UNIT_ID, TDBannerConfig(), this@BannerActivity)
            }
        }
        findViewById<TextView>(R.id.btn_800_600).apply {
            setOnClickListener {
                TDBanner.load(DemoActivity.BANNER_800_600_UNIT_ID, TDBannerConfig(), this@BannerActivity)
            }
        }
    }

    override fun onAdLoaded(ad: TDBanner) {
        container.removeAllViews()
        Logger.dt(this@BannerActivity, "on banner load success")
        ad.setEventListener(object : TDBannerEventListener {
            override fun onAdShowed() {
                Logger.dt(this@BannerActivity, "on banner showed")
            }

            override fun onAdDismissed() {
                Logger.dt(this@BannerActivity, "on banner dismissed")
            }

            override fun onAdClicked() {
                Logger.dt(this@BannerActivity, "on banner clicked")
            }
        })
        container.addView(ad.getAdView())
    }

    override fun onError(error: TDError) {
        Logger.dt(this@BannerActivity, "on banner load fail: ${error.msg}")
    }

}