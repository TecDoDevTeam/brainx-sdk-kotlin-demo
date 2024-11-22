package com.td.demo

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.td.core.TDError
import com.td.out.TDSplash
import com.td.out.TDSplashConfig
import com.td.out.TDSplashEventListener
import com.td.out.TDSplashLoadListener

class SplashActivity : AppCompatActivity(), TDSplashLoadListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initView()
    }

    private lateinit var container: FrameLayout

    private fun initView() {
        container = findViewById(R.id.container_splash)
        findViewById<ImageView>(R.id.btn_back).apply {
            setOnClickListener { finish() }
        }
        findViewById<TextView>(R.id.btn_load).apply {
            setOnClickListener { TDSplash.load(DemoActivity.SPLASH_UNIT_ID, TDSplashConfig(), this@SplashActivity) }
        }
        findViewById<TextView>(R.id.btn_load_in_activity).apply {
            setOnClickListener {
                val intent = Intent(this@SplashActivity, SplashContainerActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onAdLoaded(ad: TDSplash) {
        Logger.dt(this, "on splash load success")
        ad.setEventListener(object : TDSplashEventListener {
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
        })
        container.addView(ad.getAdView())
    }

    override fun onError(error: TDError) {
        Logger.dt(this, "on splash load error: ${error.msg}")
    }

}