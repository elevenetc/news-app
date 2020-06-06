package com.elevenetc.android.news.features.list

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.elevenetc.android.news.R
import com.elevenetc.android.news.core.models.Article
import com.elevenetc.android.news.core.utils.updateRange
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_article.view.*

class Adapter : RecyclerView.Adapter<Adapter.VH>() {

    private val data = mutableListOf<Article>()
    private val tag = "tagz"

    private var loadingDone = false
    private val typeItem = 0
    private val typeProgress = 1

    fun setLoading(loading: Boolean) {
        if (data.isEmpty()) return
        val added = !this.loadingDone && loading
        val removed = this.loadingDone && !loading
        this.loadingDone = loading
        if (added) {
            notifyItemInserted(data.size - 1)
        } else if (removed) {
            notifyItemRemoved(data.size - 1)
        }
    }

    fun setLoadingDone() {
        loadingDone = true
        notifyItemRemoved(data.size)
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

        Log.d("tagz", "append")

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

        val inflater = LayoutInflater.from(parent.context)

        return if (viewType == 0) {
            val view = inflater.inflate(R.layout.item_article, parent, false)
            ItemVH(view)
        } else {
            val view = TextView(parent.context)
            view.text = "LOADING"
            view.textSize = 100f
            ProgressVH(view)
        }
    }

    override fun getItemCount(): Int {
        if (data.isEmpty()) return 0
        return data.size + if (loadingDone) 0 else 1
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        if (holder is ItemVH) {
            holder.bind(data[position], position)
        }
    }


    class ItemVH(view: View) : VH(view) {
        fun bind(article: Article, pos: Int) {

            if (article.image.isNotEmpty()) {
                Picasso.get()
                    .load(article.image)
                    .into(itemView.imageView)
            }

            itemView.textTitle.text = (pos.toString() + ":" + article.title)
        }
    }

    class ProgressVH(view: View) : VH(view) {

    }

    open class VH(view: View) : RecyclerView.ViewHolder(view) {

    }

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