package com.elevenetc.android.news.core.scheduling

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SchedulersImpl @Inject constructor() : com.elevenetc.android.news.core.scheduling.Schedulers {
    override fun background(): Scheduler {
        return Schedulers.io()
    }

    override fun ui(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}