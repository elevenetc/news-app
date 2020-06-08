package com.elevenetc.android.news.core.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import kotlin.math.min

fun <T> updateRange(target: MutableList<T>, source: List<T>, page: Int, pageSize: Int) {
    val offset = page * pageSize
    val limit = min(target.size, offset + source.size)
    for ((idx, i) in (offset until limit).withIndex()) {
        target[i] = source[idx]
    }
}

fun String?.asUri(): Uri? {
    try {
        return Uri.parse(this)
    } catch (e: Exception) {}
    return null
}

fun Uri?.openInBrowser(context: Context) {
    this ?: return // Do nothing if uri is null

    val browserIntent = Intent(Intent.ACTION_VIEW, this)
    ContextCompat.startActivity(context, browserIntent, null)
}