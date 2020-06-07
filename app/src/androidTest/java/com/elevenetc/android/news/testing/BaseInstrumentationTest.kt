package com.elevenetc.android.news.testing

import androidx.test.platform.app.InstrumentationRegistry

open class BaseInstrumentationTest {
    fun runOnUi(call: () -> Unit) {
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            call()
        }
    }
}