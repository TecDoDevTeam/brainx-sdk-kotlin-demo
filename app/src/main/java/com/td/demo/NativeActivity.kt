package com.td.demo

import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
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
            TDNativeConfig.NativeType.SELF_RENDERING -> selfRenderNativeAd(nativeAd!!, ad)
            else -> { /* should not happened */
            }
        }
        Logger.dt(this@NativeActivity, "on native load success")
    }

    override fun onError(error: TDError) {
        Logger.dt(this@NativeActivity, "on native load fail: ${error.msg}")
    }

    private fun selfRenderNativeAd(tdNativeAd: TDNativeAd, ad: TDNative) {
        val selfRenderingBinding =
            NativeTemplateBinding.inflate(layoutInflater, binding.containerNative, false)
        val container = selfRenderingBinding.root
        val creativeViews = mutableListOf<View>()

        if (ad.icon.isEmpty()) {
            selfRenderingBinding.adIcon.visibility = View.GONE
        } else {
            selfRenderingBinding.adIcon.visibility = View.VISIBLE
            Glide.with(selfRenderingBinding.adIcon)
                .load(ad.icon)
                .into(selfRenderingBinding.adIcon)
        }
        creativeViews.add(selfRenderingBinding.adIcon)

        selfRenderingBinding.adTitle.text = ad.title
        creativeViews.add(selfRenderingBinding.adTitle)

        if (ad.description.isEmpty()) {
            selfRenderingBinding.adDesc.visibility = View.GONE
        } else {
            selfRenderingBinding.adDesc.visibility = View.VISIBLE
            selfRenderingBinding.adDesc.text = ad.description
            creativeViews.add(selfRenderingBinding.adDesc)
        }

        val mediaView = ad.getMediaView(this@NativeActivity)
        val lp = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        selfRenderingBinding.mediaContainer.addView(mediaView, 0, lp)
        creativeViews.add(mediaView)
        if (mediaView.mediaContent.hasVideoContent()) {
            val vc = mediaView.mediaContent.videoController
            // you can use the videoController to control video
        }

        selfRenderingBinding.btnCtaNativeTemplate.text = ad.ctaText
        creativeViews.add(selfRenderingBinding.btnCtaNativeTemplate)

        val adLogoView = ad.getAdLogoView(this@NativeActivity)
        selfRenderingBinding.adLogoContainer.addView(adLogoView)
        creativeViews.add(adLogoView)

        val dislikeView = selfRenderingBinding.btnCloseNativeTemplate

        val result = tdNativeAd.bindViewsForInteraction(container, creativeViews, dislikeView)
        Logger.dt(this, "on native self render bind ${if (result) "success" else "fail"}")
        if (result) {
            binding.containerNative.addView(container, MATCH_PARENT, MATCH_PARENT)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        nativeAd?.destroy()
    }
}