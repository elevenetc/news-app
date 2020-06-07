package com.elevenetc.android.news.testing

import com.elevenetc.android.news.core.logging.Logger

class FakeLogger: Logger {
    override fun log(throwable: Throwable) {

    }

    override fun logD(message: String) {

    }
}