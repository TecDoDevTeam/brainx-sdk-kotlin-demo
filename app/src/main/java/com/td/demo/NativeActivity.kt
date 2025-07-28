package com.td.demo

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.td.core.TDError
import com.td.demo.base.ViewBindingActivity
import com.td.demo.databinding.ActivityNativeBinding
import com.td.demo.databinding.NativeTemplateBinding
import com.td.nativead.TDNativeView
import com.td.out.TDNative
import com.td.out.TDNativeAd
import com.td.out.TDNativeAdListener
import com.td.out.TDNativeConfig

class NativeActivity : ViewBindingActivity<ActivityNativeBinding>(), TDNativeAdListener {

    private var nativeAd: TDNativeAd? = null

    private val handler = Handler(Looper.getMainLooper())

    override fun initView(binding: ActivityNativeBinding) {
        super.initView(binding)
        binding.btnBack.setOnClickListener { finish() }
        binding.btnLoadTemplateRendering.setOnClickListener {
            loadNativeAd(TDNativeConfig.NativeType.TEMPLATE_RENDERING)
        }
        binding.btnLoadSelfRendering.setOnClickListener {
            loadNativeAd(TDNativeConfig.NativeType.SELF_RENDERING)
        }
    }

    private fun loadNativeAd(type: TDNativeConfig.NativeType) {
        binding.containerNative.removeAllViews()
        nativeAd?.destroy()

        val ad = TDNativeAd(DemoActivity.NATIVE_UNIT_ID, TDNativeConfig(type))
        nativeAd = ad
        ad.setListener(this)
        ad.load()
    }

    override fun onRenderSuccess(view: TDNativeView) {
        Logger.dt(this@NativeActivity, "on native template render success")
        binding.containerNative.addView(view)
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
            else -> { /* should not happened */
            }
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
                Logger.dt(
                    this@NativeActivity,
                    "on native self render bind ${if (result) "success" else "fail"}"
                )
                if (result) {
                    binding.containerNative.addView(
                        container,
                        LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
                    )
                }
            }
        }
    }

    private fun formSelfRenderingView(
        nativeAd: TDNative,
        formCallback: (container: ViewGroup, creativeViews: List<View>, dislikeView: View) -> Unit
    ) {
        val selfRenderingBinding = NativeTemplateBinding.inflate(layoutInflater, binding.containerNative, false)
        val container = selfRenderingBinding.root
        val creativeViews = mutableListOf<View>()
        selfRenderingBinding.ivIconNativeTemplate.apply {
            if (nativeAd.getIcon().isEmpty()) {
                visibility = View.GONE
                return@apply
            }
            Glide.with(this).load(nativeAd.getIcon()).into(this)
            creativeViews.add(this)
        }
        selfRenderingBinding.tvTitleNativeTemplate.apply {
            text = nativeAd.getTitle()
            creativeViews.add(this)
        }
        selfRenderingBinding.tvDescNativeTemplate.apply {
            if (nativeAd.getDescription().isEmpty()) {
                visibility = View.GONE
                return@apply
            }
            text = nativeAd.getDescription()
            creativeViews.add(this)
        }
        selfRenderingBinding.containerMediaNativeTemplate.apply {
            addView(nativeAd.getMediaView(this@NativeActivity).apply {
                creativeViews.add(this)
            }, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        }
        selfRenderingBinding.btnCtaNativeTemplate.apply {
            creativeViews.add(this)
            text = nativeAd.getCTAText()
        }
        container.addView(nativeAd.getAdLogoView(this@NativeActivity).apply {
            creativeViews.add(this)
        })
        val dislikeView = selfRenderingBinding.btnCloseNativeTemplate

        formCallback.invoke(container, creativeViews, dislikeView)
    }

    override fun onDestroy() {
        super.onDestroy()
        nativeAd?.destroy()
    }
}