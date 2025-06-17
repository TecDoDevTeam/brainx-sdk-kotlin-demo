package com.td.demo

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.td.core.TDError
import com.td.out.TDRewardItem
import com.td.out.TDRewardVideo
import com.td.out.TDRewardVideoAd
import com.td.out.TDRewardVideoAdListener
import com.td.out.TDRewardVideoConfig

class RewardActivity : AppCompatActivity(), TDRewardVideoAdListener {
    private val rewardAd = TDRewardVideoAd(DemoActivity.REWARD_VIDEO_UNIT_ID, TDRewardVideoConfig("user:tecdo"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reward)
        rewardAd.setListener(this)
        initView()
    }

    private fun initView() {
        findViewById<ImageView>(R.id.btn_back).apply {
            setOnClickListener { finish() }
        }
        findViewById<TextView>(R.id.btn_load).apply {
            setOnClickListener {
                rewardAd.load()
            }
        }
        findViewById<TextView>(R.id.btn_show).apply {
            setOnClickListener {
                if (rewardAd.isReady) {
                    rewardAd.show()
                }
            }
        }
    }

    override fun onAdLoaded(ad: TDRewardVideo) {
        Logger.dt(this, "on reward video load success")
    }

    override fun onError(error: TDError) {
        Logger.dt(this, "on reward video load error: ${error.msg}")
    }

    override fun onAdShowedFail(error: TDError) {
        Logger.dt(this@RewardActivity, "on reward video on ad showed fail $error")
    }

    override fun onRewardedSuccess(rewardItem: TDRewardItem) {
        Logger.dt(this@RewardActivity, "on reward video on rewarded success $rewardItem")
    }

    override fun onRewardedFail() {
        Logger.dt(this@RewardActivity, "on reward video on rewarded fail")
    }

    override fun onAdShowed() {
        Logger.dt(this@RewardActivity, "on reward video on ad showed")
    }

    override fun onAdDismissed() {
        Logger.dt(this@RewardActivity, "on reward video on ad dismissed")
    }

    override fun onAdClicked() {
        Logger.dt(this@RewardActivity, "on reward video on ad clicked")
    }

    override fun onDestroy() {
        super.onDestroy()
        rewardAd.destroy()
    }
}