package com.elevenetc.android.news.core.scheduling

import io.reactivex.Scheduler

interface Schedulers {
    fun background(): Scheduler
    fun ui(): Scheduler
}