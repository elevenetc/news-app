package com.elevenetc.android.news.core.images

import android.widget.ImageView
import com.elevenetc.android.news.R
import com.elevenetc.android.news.core.models.Article
import com.squareup.picasso.Picasso
import javax.inject.Inject

class ImagesLoaderImpl @Inject constructor() : ImagesLoader {

    override fun loadArticleItem(article: Article, imageView: ImageView) {
        if (article.image.isNotEmpty()) {
            Picasso.get()
                .load(article.image)
                .placeholder(R.drawable.round_photo_camera_black_24)
                .fit().centerCrop().noFade()
                .into(imageView)
        } else {
            Picasso.get()
                .load(R.drawable.round_photo_camera_black_24)
                .into(imageView)
        }
    }

    override fun loadArticleDetailsBackground(article: Article, imageView: ImageView) {
        if (article.image.isNotEmpty()) {
            Picasso.get()
                .load(article.image)
                .into(imageView)
        }
    }

}