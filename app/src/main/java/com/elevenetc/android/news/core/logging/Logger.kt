package com.elevenetc.android.news.core.logging

interface Logger {
    fun log(throwable: Throwable)
    fun logD(message: String)
}