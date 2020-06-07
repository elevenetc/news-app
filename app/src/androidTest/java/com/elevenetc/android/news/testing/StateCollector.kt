package com.elevenetc.android.news.testing

import androidx.lifecycle.MutableLiveData
import org.junit.Assert.assertEquals

class StateCollector<T>(val ld: MutableLiveData<T>) {
    val states = mutableListOf<T>()

    init {
        ld.observeForever { states.add(it) }
    }

    fun assert(expected: List<T>) {
        assertEquals(states, expected)
    }
}