package com.td.demo

import android.content.Intent
import com.td.core.TDConfig
import com.td.core.TDError
import com.td.core.TDSDK
import com.td.demo.base.ViewBindingActivity
import com.td.demo.databinding.ActivityDemoBinding

class DemoActivity : ViewBindingActivity<ActivityDemoBinding>() {

    companion object {
        const val APP_ID = 1000001
        const val BANNER_320_50_UNIT_ID = "1000000001"
        const val BANNER_300_250_UNIT_ID = "1000000002"
        const val BANNER_320_90_UNIT_ID = "1000000003"
        const val BANNER_728_90_UNIT_ID = "1000000004"
        const val BANNER_800_600_UNIT_ID = "1000000005"
        const val SPLASH_UNIT_ID = "1000000006"
        const val INTER_UNIT_ID = "1000000008"
        const val REWARD_VIDEO_UNIT_ID = "1000000019"
        const val NATIVE_UNIT_ID = "1000000194"
    }

    override fun initView(binding: ActivityDemoBinding) {
        val gdprCheckbox = binding.checkboxGdpr
        val ccpaCheckbox = binding.checkboxCcpa
        val coppaCheckbox = binding.checkboxCoppa
        binding.btnInit.setOnClickListener {
            TDSDK.init(
                this@DemoActivity,
                TDConfig.Builder().setAppId(APP_ID).setCOPPA(coppaCheckbox.isChecked)
                    .setGDPR(gdprCheckbox.isChecked).setCCPA(ccpaCheckbox.isChecked).build(),
                object : TDSDK.InitCallback {
                    override fun onSDKInitSuccess() {
                        Logger.dt(this@DemoActivity, "on sdk init success")
                    }

                    override fun onSDKInitFail(error: TDError) {
                        Logger.dt(this@DemoActivity, "on sdk init fail ${error.msg}")
                    }
                }
            )
        }
        binding.btnBanner.setOnClickListener {
            val intent = Intent(this@DemoActivity, BannerActivity::class.java)
            startActivity(intent)
        }
        binding.btnSplash.setOnClickListener {
            val intent = Intent(this@DemoActivity, SplashActivity::class.java)
            startActivity(intent)
        }
        binding.btnInterstitial.setOnClickListener {
            val intent = Intent(this@DemoActivity, InterActivity::class.java)
            startActivity(intent)
        }
        binding.btnRewardvideo.setOnClickListener {
            val intent = Intent(this@DemoActivity, RewardActivity::class.java)
            startActivity(intent)
        }
        binding.btnNative.setOnClickListener {
            val intent = Intent(this@DemoActivity, NativeActivity::class.java)
            startActivity(intent)
        }
    }
}