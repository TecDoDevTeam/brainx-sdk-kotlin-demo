package com.td.demo

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.td.core.TDError
import com.td.out.TDSplash
import com.td.out.TDSplashAd
import com.td.out.TDSplashAdListener
import com.td.out.TDSplashConfig

class SplashActivity : AppCompatActivity(), TDSplashAdListener {

    private val splashAd = TDSplashAd(DemoActivity.SPLASH_UNIT_ID, TDSplashConfig())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        splashAd.setListener(this)
        initView()
    }

    private lateinit var container: FrameLayout

    private fun initView() {
        container = findViewById(R.id.container_splash)
        findViewById<ImageView>(R.id.btn_back).apply {
            setOnClickListener { finish() }
        }
        findViewById<TextView>(R.id.btn_load).apply {
            setOnClickListener { splashAd.load() }
        }
        findViewById<TextView>(R.id.btn_load_in_activity).apply {
            setOnClickListener {
                val intent = Intent(this@SplashActivity, SplashContainerActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onAdShowed() {
        Logger.dt(this@SplashActivity, "splash on showed")
    }

    override fun onAdDismissed() {
        Logger.dt(this@SplashActivity, "splash on dismissed")
        finish()
    }

    override fun onAdClicked() {
        Logger.dt(this@SplashActivity, "splash on clicked")
    }

    override fun onAdShowedFail(error: TDError) {
        Logger.et(this@SplashActivity, "on splash showed fail")
    }

    override fun onAdLoaded(ad: TDSplash) {
        Logger.dt(this, "on splash load success")
        splashAd.show(container)
    }

    override fun onError(error: TDError) {
        Logger.dt(this, "on splash load error: ${error.msg}")
    }

    override fun onDestroy() {
        super.onDestroy()
        splashAd.destroy()
    }
}