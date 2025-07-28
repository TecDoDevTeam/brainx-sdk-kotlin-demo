package com.td.demo.utils

import android.app.Activity
import android.content.Intent
import android.os.Bundle

inline fun <reified T> Activity.startActivity(
    receiver: Intent.() -> Unit = {},
    options: Bundle? = null
) {
    val intent = Intent(this, T::class.java)
    receiver(intent)
    startActivity(intent, options)
}