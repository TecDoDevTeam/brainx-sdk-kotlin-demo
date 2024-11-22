package com.td.demo

import android.content.Intent
import android.os.Bundle
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatCheckBox
import com.td.core.TDConfig
import com.td.core.TDError
import com.td.core.TDSDK

class DemoActivity : AppCompatActivity() {

    companion object {
        const val APP_ID = 1000019
        const val BANNER_320_50_UNIT_ID = "1000000178"
        const val BANNER_300_250_UNIT_ID = "1000000003"
        const val BANNER_320_90_UNIT_ID = "1000000004"
        const val BANNER_728_90_UNIT_ID = "1000000005"
        const val BANNER_800_600_UNIT_ID = "1000000006"
        const val SPLASH_UNIT_ID = "1000000001"
        const val INTER_UNIT_ID = "1000000176"
        const val REWARD_VIDEO_UNIT_ID = "1000000019"
        const val NATIVE_UNIT_ID = "1000000019"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)
        initView()
    }

    private lateinit var gdprCheckbox: CheckBox
    private lateinit var ccpaCheckbox: CheckBox
    private lateinit var coppaCheckbox: CheckBox

    private fun initView() {
        gdprCheckbox = findViewById<AppCompatCheckBox>(R.id.checkbox_gdpr)
        ccpaCheckbox = findViewById<AppCompatCheckBox>(R.id.checkbox_ccpa)
        coppaCheckbox = findViewById<AppCompatCheckBox>(R.id.checkbox_coppa)
        findViewById<TextView>(R.id.btn_init).apply {
            setOnClickListener {
                TDSDK.init (
                    this@DemoActivity,
                    TDConfig.Builder().setAppId(APP_ID).setCOPPA(coppaCheckbox.isChecked).setGDPR(gdprCheckbox.isChecked).setCCPA(ccpaCheckbox.isChecked).build(),
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
        }
        findViewById<TextView>(R.id.btn_banner).apply {
            setOnClickListener {
                val intent = Intent(this@DemoActivity, BannerActivity::class.java)
                startActivity(intent)
            }
        }
        findViewById<TextView>(R.id.btn_splash).apply {
            setOnClickListener {
                val intent = Intent(this@DemoActivity, SplashActivity::class.java)
                startActivity(intent)
            }
        }
        findViewById<TextView>(R.id.btn_interstitial).apply {
            setOnClickListener {
                val intent = Intent(this@DemoActivity, InterActivity::class.java)
                startActivity(intent)
            }
        }
        findViewById<TextView>(R.id.btn_rewardvideo).apply {
            setOnClickListener {
                val intent = Intent(this@DemoActivity, RewardActivity::class.java)
                startActivity(intent)
            }
        }
        findViewById<TextView>(R.id.btn_native).apply {
            setOnClickListener {
                val intent = Intent(this@DemoActivity, NativeActivity::class.java)
                startActivity(intent)
            }
        }
    }

}