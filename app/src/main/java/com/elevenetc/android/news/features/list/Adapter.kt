package com.elevenetc.android.news.features.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.elevenetc.android.news.R
import com.elevenetc.android.news.core.images.ImagesLoader
import com.elevenetc.android.news.core.models.Article
import com.elevenetc.android.news.core.utils.updateRange
import kotlinx.android.synthetic.main.item_article.view.*

class Adapter(val imagesLoader: ImagesLoader) : RecyclerView.Adapter<Adapter.VH>() {

    private val data = mutableListOf<Article>()

    private var loadingDone = false
    private var errorState = false
    private val typeItem = 0
    private val typeProgress = 1

    var retryHandler: () -> Unit = {}
    var itemClickHandler: (Article) -> Unit = {}

    fun setErrorState(value: Boolean) {
        errorState = value
        notifyItemChanged(data.size)
    }

    fun isEmpty(): Boolean {
        return data.isEmpty()
    }

    override fun getItemViewType(position: Int): Int {
        return if (data.size > 0 && !loadingDone && position == data.size) {
            typeProgress
        } else {
            typeItem
        }
    }

    fun append(articles: List<Article>) {
        if (articles.isEmpty()) return
        insertNewDiff(data + articles)
    }

    fun appendOrRefresh(articles: List<Article>, page: Int, pageSize: Int) {

        if (articles.size < pageSize) {
            loadingDone = true
            notifyItemRemoved(data.size)
        }

        if (articles.isEmpty()) return

        if (data.isEmpty()) {
            insertNewDiff(articles)
        } else {
            val offset = page * pageSize
            if (offset < data.size) {
                val newData = data.toMutableList()
                updateRange(newData, articles, page, pageSize)
                insertNewDiff(newData)
            } else {
                insertNewDiff(data + articles)
            }
        }
    }

    private fun insertNewDiff(newData: List<Article>) {
        val diffCallback = DiffCallback(newData, data)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        data.clear()
        data.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {


        return if (viewType == typeItem) {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.item_article, parent, false)
            ItemVH(view, imagesLoader)
        } else {
            FooterVH(RetryProgressView(parent.context))
        }
    }

    override fun getItemCount(): Int {
        if (data.isEmpty()) return 0
        return data.size + if (loadingDone) 0 else 1
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        if (holder is ItemVH) {
            holder.bind(data[position], itemClickHandler)
        } else if (holder is FooterVH) {
            holder.bind(errorState, this)
        }
    }

    class ItemVH(view: View, val imagesLoader: ImagesLoader) : VH(view) {
        fun bind(
            article: Article,
            itemClickHandler: (Article) -> Unit
        ) {

            imagesLoader.loadArticleItem(article, itemView.imageView)

            itemView.textTitle.text = article.title
                .ifEmpty { itemView.context.getString(R.string.list_empty_title) }
            itemView.setOnClickListener { itemClickHandler(article) }
        }
    }

    class FooterVH(private val retryProgress: RetryProgressView) : VH(retryProgress) {
        fun bind(
            errorState: Boolean,
            adapter: Adapter
        ) {
            retryProgress.retryHandler = { adapter.retryHandler() }
            if (errorState) {
                retryProgress.showError()
            } else {
                retryProgress.showProgress()
            }
        }
    }

    open class VH(view: View) : RecyclerView.ViewHolder(view)

    private class DiffCallback(val newList: List<Article>, val oldList: List<Article>) :
        DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val newArticle = newList[newItemPosition]
            val oldArticle = newList[oldItemPosition]

            return newArticle == oldArticle
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val newArticle = newList[newItemPosition]
            val oldArticle = newList[oldItemPosition]

            return newArticle.id == oldArticle.id
        }

    }
}