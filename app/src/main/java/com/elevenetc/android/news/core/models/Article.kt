package com.elevenetc.android.news.core.models

import java.util.*

data class Article(
    val id: String,
    val title: String = "",
    val description: String = "",
    val image: String = "",
    val url: String = "",
    val author: String = "",
    val content: String = "",
    val date: Date? = null
)