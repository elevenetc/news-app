package com.elevenetc.android.news.core.utils

import kotlin.math.min

fun <T> updateRange(target: MutableList<T>, source: List<T>, page: Int, pageSize: Int) {
    val offset = page * pageSize
    val limit = min(target.size, offset + source.size)
    for ((idx, i) in (offset until limit).withIndex()) {
        target[i] = source[idx]
    }
}