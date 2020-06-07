package com.elevenetc.android.news.core.images

import android.widget.ImageView
import com.elevenetc.android.news.core.models.Article

interface ImagesLoader {
    fun loadArticleItem(article: Article, imageView: ImageView)
    fun loadArticleDetailsBackground(article: Article, imageView: ImageView)
}