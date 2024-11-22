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
import com.td.out.TDNativeConfig
import com.td.out.TDNativeEventListener
import com.td.out.TDNativeLoadListener

class NativeActivity: AppCompatActivity(), TDNativeLoadListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_native)
        initView()
    }

    private var needSelfRendering = false
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var container: FrameLayout

    private fun initView() {
        container = findViewById(R.id.container_native)
        findViewById<ImageView>(R.id.btn_back).apply {
            setOnClickListener { finish() }
        }
        findViewById<TextView>(R.id.btn_load_template_rendering).apply {
            setOnClickListener {
                needSelfRendering = false
                TDNative.load(DemoActivity.NATIVE_UNIT_ID, TDNativeConfig(TDNativeConfig.NativeType.TEMPLATE_RENDERING), this@NativeActivity)
            }
        }
        findViewById<TextView>(R.id.btn_load_self_rendering).apply {
            setOnClickListener {
                needSelfRendering = true
                TDNative.load(DemoActivity.NATIVE_UNIT_ID, TDNativeConfig(TDNativeConfig.NativeType.SELF_RENDERING), this@NativeActivity)
            }
        }
    }

    override fun onAdLoaded(tdNative: TDNative) {
        container.removeAllViews()
        Logger.dt(this@NativeActivity, "on native load success")
        tdNative.setEventListener(object : TDNativeEventListener {
            override fun onRenderSuccess(view: TDNativeView) {
                Logger.dt(this@NativeActivity, "on native template render success")
                container.addView(view)
            }

            override fun onRenderFail() {
                Logger.dt(this@NativeActivity, "on native template render fail")
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
        })
        if (needSelfRendering) {
            selfRenderNative(tdNative)
        } else {
            tdNative.renderForTemplate(this@NativeActivity)
        }
    }

    override fun onError(error: TDError) {
        Logger.dt(this@NativeActivity, "on native load fail: ${error.msg}")
    }

    private fun selfRenderNative(nativeAd: TDNative) {
        handler.post {
            formSelfRenderingView(nativeAd) { container, creativeViews, dislikeView ->
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

}