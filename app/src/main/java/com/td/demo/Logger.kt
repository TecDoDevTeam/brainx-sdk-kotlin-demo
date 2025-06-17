package com.td.demo

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast

object Logger {

    private val mainHandler = Handler(Looper.getMainLooper())
    private var lastToast: Toast? = null
    const val TAG = "BrainX_Demo"

    private fun postShowToast(context: Context, msg: String) {
        mainHandler.post {
            lastToast?.cancel()
            lastToast = Toast.makeText(context.applicationContext, msg, Toast.LENGTH_SHORT)
            lastToast?.show()
        }
    }

    fun dt(context: Context, msg: String) {
        postShowToast(context, msg)
        d(msg)
    }

    fun et(context: Context, msg: String) {
        postShowToast(context, msg)
        e(msg)
    }

    fun wt(context: Context, msg: String) {
        postShowToast(context, msg)
        w(msg)
    }

    fun d(msg: String) {
        Log.d(TAG, msg)
    }

    fun e(msg: String) {
        Log.e(TAG, msg)
    }

    fun w(msg: String) {
        Log.w(TAG, msg)
    }

}