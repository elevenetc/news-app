package com.elevenetc.android.news.testing

import com.elevenetc.android.news.core.scheduling.Schedulers
import io.reactivex.Scheduler

class CurrentThreadSchedulers: Schedulers {
    override fun background(): Scheduler {
        return io.reactivex.schedulers.Schedulers.from {
            it.run()
        }
    }

    override fun ui(): Scheduler {
        return io.reactivex.schedulers.Schedulers.from {
            it.run()
        }
    }
}