package com.elevenetc.android.news.core.logging

import android.util.Log
import javax.inject.Inject

class LoggerImpl @Inject constructor() : Logger {


    override fun log(throwable: Throwable) {
        throwable.printStackTrace()
    }

    override fun logD(message: String) {
        Log.d(COMMON_TAG, message)
    }

    companion object {
        const val COMMON_TAG = "common"
    }
}