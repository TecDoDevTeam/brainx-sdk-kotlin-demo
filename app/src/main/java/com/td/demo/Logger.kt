package com.td.demo

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast

object Logger {

    private val mainHandler = Handler(Looper.getMainLooper())
    const val TAG = "BrainX_Demo"

    fun dt(context: Context, msg: String) {
        mainHandler.post { Toast.makeText(context, msg, Toast.LENGTH_SHORT).show() }
        d(msg)
    }

    fun et(context: Context, msg: String) {
        mainHandler.post { Toast.makeText(context, msg, Toast.LENGTH_SHORT).show() }
        e(msg)
    }

    fun wt(context: Context, msg: String) {
        mainHandler.post { Toast.makeText(context, msg, Toast.LENGTH_SHORT).show() }
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