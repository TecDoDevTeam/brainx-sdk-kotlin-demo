package com.td.demo

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.td.core.TDError
import com.td.out.TDSplash
import com.td.out.TDSplashAd
import com.td.out.TDSplashAdListener
import com.td.out.TDSplashConfig

class SplashContainerActivity : AppCompatActivity(), TDSplashAdListener {

    private val splashAd = TDSplashAd(DemoActivity.SPLASH_UNIT_ID, TDSplashConfig())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_container)
        splashAd.setListener(this)
        initView()
        loadSplash()
    }

    private lateinit var container: FrameLayout

    private fun initView() {
        container = findViewById(R.id.container_splash)
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
        splashAd.show(container)
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