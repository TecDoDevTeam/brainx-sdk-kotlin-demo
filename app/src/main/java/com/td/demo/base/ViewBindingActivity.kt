package com.td.demo.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

abstract class ViewBindingActivity<VB : ViewBinding> : AppCompatActivity() {
    protected val binding: VB by lazy {
        inflateViewBinding()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView(binding)
    }

    protected open fun initView(binding: VB) {

    }

    @Suppress("UNCHECKED_CAST", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    private fun inflateViewBinding(): VB {
        var parent: Class<*> = this::class.java
        while (parent.superclass != null && parent.superclass != ViewBindingActivity::class.java) {
            parent = parent.superclass
        }
        val clazz =
            (parent.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<*>
        val method = clazz.getDeclaredMethod("inflate", LayoutInflater::class.java)
        return method.invoke(null, layoutInflater) as VB
    }
}