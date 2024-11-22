package com.td.demo

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.td.core.TDError
import com.td.demo.DemoActivity
import com.td.demo.Logger
import com.td.demo.R
import com.td.out.TDSplash
import com.td.out.TDSplashConfig
import com.td.out.TDSplashEventListener
import com.td.out.TDSplashLoadListener

class SplashContainerActivity : AppCompatActivity(), TDSplashLoadListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_container)
        initView()
        loadSplash()
    }
    
    private lateinit var container: FrameLayout

    private fun initView() {
        container = findViewById(R.id.container_splash)
    }

    private fun loadSplash() {
        TDSplash.load(DemoActivity.SPLASH_UNIT_ID, TDSplashConfig(), this@SplashContainerActivity)
    }

    override fun onAdLoaded(ad: TDSplash) {
        Logger.dt(this, "on splash load success")
        ad.setEventListener(object : TDSplashEventListener {
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
        })
        container.addView(ad.getAdView())
    }

    override fun onError(error: TDError) {
        Logger.dt(this, "on splash load error: ${error.msg}")
        finish()
    }

}