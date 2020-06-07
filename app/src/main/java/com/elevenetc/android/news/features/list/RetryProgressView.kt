package com.elevenetc.android.news.features.list

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.elevenetc.android.news.R
import kotlinx.android.synthetic.main.view_retry.view.*

class RetryProgressView(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        LayoutInflater.from(context).inflate(R.layout.view_retry, this)
        btnRetry.setOnClickListener { retryHandler() }
        hide()
    }

    var retryHandler: () -> Unit = {}

    fun showError() {
        progressView.visibility = View.INVISIBLE
        errorGroup.visibility = View.VISIBLE
    }

    fun showProgress() {
        progressView.visibility = View.VISIBLE
        errorGroup.visibility = View.INVISIBLE
    }

    fun hide() {
        progressView.visibility = View.INVISIBLE
        errorGroup.visibility = View.INVISIBLE
    }
}