package com.td.demo

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.td.core.TDError
import com.td.out.TDInterstitial
import com.td.out.TDInterstitialConfig
import com.td.out.TDInterstitialEventListener
import com.td.out.TDInterstitialLoadListener

class InterActivity: AppCompatActivity(), TDInterstitialLoadListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inter)
        initView()
    }

    private fun initView() {
        findViewById<ImageView>(R.id.btn_back).apply {
            setOnClickListener { finish() }
        }
        findViewById<TextView>(R.id.btn_load).apply {
            setOnClickListener {
                TDInterstitial.load(DemoActivity.INTER_UNIT_ID, TDInterstitialConfig(), this@InterActivity)
            }
        }
        findViewById<TextView>(R.id.btn_show).apply {
            setOnClickListener {
                inter?.show()
            }
        }
    }

    private var inter: TDInterstitial? = null

    override fun onAdLoaded(ad: TDInterstitial) {
        Logger.dt(this, "on inter load success")
        inter = ad.apply {
            setEventListener(object : TDInterstitialEventListener {
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
            })
        }
    }

    override fun onError(error: TDError) {
        Logger.dt(this, "on inter load error: ${error.msg}")
    }

}