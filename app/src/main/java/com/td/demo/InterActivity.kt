package com.td.demo

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.td.core.TDError
import com.td.out.TDInterstitial
import com.td.out.TDInterstitialAd
import com.td.out.TDInterstitialAdListener
import com.td.out.TDInterstitialConfig

class InterActivity : AppCompatActivity(), TDInterstitialAdListener {

    private val interstitialAd = TDInterstitialAd(DemoActivity.INTER_UNIT_ID, TDInterstitialConfig())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inter)
        interstitialAd.setListener(this)
        initView()
    }

    private fun initView() {
        findViewById<ImageView>(R.id.btn_back).apply {
            setOnClickListener { finish() }
        }
        findViewById<TextView>(R.id.btn_load).apply {
            setOnClickListener {
                interstitialAd.load()
            }
        }
        findViewById<TextView>(R.id.btn_show).apply {
            setOnClickListener {
                if (interstitialAd.isReady) {
                    interstitialAd.show()
                }
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