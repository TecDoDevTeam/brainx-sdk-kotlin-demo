package com.td.demo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.td.core.TDError
import com.td.nativead.TDNativeView
import com.td.out.TDNative
import com.td.out.TDNativeAd
import com.td.out.TDNativeAdListener
import com.td.out.TDNativeConfig

class NativeActivity: AppCompatActivity(), TDNativeAdListener {

    private var nativeAd: TDNativeAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_native)
        initView()
    }

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var container: FrameLayout

    private fun initView() {
        container = findViewById(R.id.container_native)
        findViewById<ImageView>(R.id.btn_back).apply {
            setOnClickListener { finish() }
        }
        findViewById<TextView>(R.id.btn_load_template_rendering).apply {
            setOnClickListener {
                loadNativeAd(TDNativeConfig.NativeType.TEMPLATE_RENDERING)
            }
        }
        findViewById<TextView>(R.id.btn_load_self_rendering).apply {
            setOnClickListener {
                loadNativeAd(TDNativeConfig.NativeType.SELF_RENDERING)
            }
        }
    }

    private fun loadNativeAd(type: TDNativeConfig.NativeType) {
        container.removeAllViews()
        nativeAd?.destroy()

        val ad = TDNativeAd(DemoActivity.NATIVE_UNIT_ID, TDNativeConfig(type))
        nativeAd = ad
        ad.setListener(this)
        ad.load()
    }

    override fun onRenderSuccess(view: TDNativeView) {
        Logger.dt(this@NativeActivity, "on native template render success")
        container.addView(view)
    }

    override fun onRenderFail(error: TDError) {
        Logger.dt(this@NativeActivity, "on native template render fail $error")
    }

    override fun onAdShowed() {
        Logger.dt(this@NativeActivity, "on native show")
    }

    override fun onAdDismissed() {
        Logger.dt(this@NativeActivity, "on native dismissed")
    }

    override fun onAdClicked() {
        Logger.dt(this@NativeActivity, "on native clicked")
    }

    override fun onAdLoaded(ad: TDNative) {
        when (nativeAd?.renderType) {
            TDNativeConfig.NativeType.TEMPLATE_RENDERING -> nativeAd!!.renderForTemplate(this)
            TDNativeConfig.NativeType.SELF_RENDERING -> selfRenderNative(nativeAd!!, ad)
            else -> { /* should not happened */ }
        }
        Logger.dt(this@NativeActivity, "on native load success")
    }

    override fun onError(error: TDError) {
        Logger.dt(this@NativeActivity, "on native load fail: ${error.msg}")
    }

    private fun selfRenderNative(nativeAd: TDNativeAd, ad: TDNative) {
        handler.post {
            formSelfRenderingView(ad) { container, creativeViews, dislikeView ->
                val result = nativeAd.bindViewsForInteraction(container, creativeViews, dislikeView)
                Logger.dt(this@NativeActivity, "on native self render bind ${if (result) "success" else "fail"}")
                if (result) {
                    this@NativeActivity.container.addView(container, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
                }
            }
        }
    }

    private fun formSelfRenderingView(nativeAd: TDNative, formCallback: (container: ViewGroup, creativeViews: List<View>, dislikeView: View) -> Unit) {
        val container = LayoutInflater.from(this).inflate(R.layout.native_template, this.container, false) as ViewGroup
        val creativeViews = mutableListOf<View>()
        container.findViewById<ImageView>(R.id.iv_icon_native_template).apply {
            if (nativeAd.getIcon().isEmpty()) {
                visibility = View.GONE
                return@apply
            }
            Glide.with(this).load(nativeAd.getIcon()).into(this)
            creativeViews.add(this)
        }
        container.findViewById<TextView>(R.id.tv_title_native_template).apply {
            text = nativeAd.getTitle()
            creativeViews.add(this)
        }
        container.findViewById<TextView>(R.id.tv_desc_native_template).apply {
            if (nativeAd.getDescription().isEmpty()) {
                visibility = View.GONE
                return@apply
            }
            text = nativeAd.getDescription()
            creativeViews.add(this)
        }
        container.findViewById<ViewGroup>(R.id.container_media_native_template).apply {
            addView(nativeAd.getMediaView(this@NativeActivity).apply {
                creativeViews.add(this)
            }, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        }
        container.findViewById<TextView>(R.id.btn_cta_native_template).apply {
            creativeViews.add(this)
            text = nativeAd.getCTAText()
        }
        container.addView(nativeAd.getAdLogoView(this@NativeActivity).apply {
            creativeViews.add(this)
        })
        val dislikeView = container.findViewById<ImageView>(R.id.btn_close_native_template)

        formCallback.invoke(container, creativeViews, dislikeView)
    }

    override fun onDestroy() {
        super.onDestroy()
        nativeAd?.destroy()
    }
}