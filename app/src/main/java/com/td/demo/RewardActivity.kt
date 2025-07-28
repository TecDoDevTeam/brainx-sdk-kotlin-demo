package com.td.demo

import com.td.core.TDError
import com.td.demo.base.ViewBindingActivity
import com.td.demo.databinding.ActivityRewardBinding
import com.td.out.TDRewardItem
import com.td.out.TDRewardVideo
import com.td.out.TDRewardVideoAd
import com.td.out.TDRewardVideoAdListener
import com.td.out.TDRewardVideoConfig

class RewardActivity : ViewBindingActivity<ActivityRewardBinding>(), TDRewardVideoAdListener {
    private val rewardAd = TDRewardVideoAd(DemoActivity.REWARD_VIDEO_UNIT_ID, TDRewardVideoConfig("user:tecdo"))

    override fun initView(binding: ActivityRewardBinding) {
        super.initView(binding)
        rewardAd.setListener(this)

        binding.btnBack.setOnClickListener { finish() }
        binding.btnLoad.setOnClickListener { rewardAd.load() }
        binding.btnShow.setOnClickListener {
            if (rewardAd.isReady) {
                rewardAd.show()
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