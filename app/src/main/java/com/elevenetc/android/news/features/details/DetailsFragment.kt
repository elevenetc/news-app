package com.elevenetc.android.news.features.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.elevenetc.android.news.R
import com.elevenetc.android.news.core.BaseFragment
import com.elevenetc.android.news.features.details.DetailsViewModel.State
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : BaseFragment(R.layout.fragment_details) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val vmFactory = appComponent.details().viewModelFactory()
        val vm = ViewModelProvider(this, vmFactory).get(DetailsViewModelImpl::class.java)

        vm.state.observe(viewLifecycleOwner, Observer { state -> handleState(state) })
        vm.onAction(DetailsViewModel.Action.GetArticle(arguments!!.getString(ARG_ARTICLE_ID)!!))
    }

    private fun handleState(state: State) {
        if (state is State.Loading) {
            contentGroup.visibility = View.INVISIBLE
            retryProgressView.visibility = View.VISIBLE
            retryProgressView.showProgress()
        } else if (state is State.CachedResult) {
            retryProgressView.visibility = View.INVISIBLE
            contentGroup.visibility = View.VISIBLE
            val article = state.article
            textTitle.text = article.title
                .ifEmpty { getString(R.string.details_empty_title) }
            textDescription.text = article.description
                .ifEmpty { getString(R.string.details_empty_description) }

            appComponent.images().loadArticleItem(article, imageViewHead)

        } else if (state is State.NoResult) {
            AlertDialog.Builder(activity!!)
                .setCancelable(false)
                .setMessage(R.string.details_no_article_found)
                .setPositiveButton(R.string.ok)
                { _, _ -> goBack() }
                .show()
        } else if (state is State.ErrorResult) {
            AlertDialog.Builder(activity!!)
                .setCancelable(false)
                .setMessage(R.string.details_error_article_loading)
                .setPositiveButton(R.string.ok)
                { _, _ -> goBack() }
                .show()
        }
    }

    private fun goBack() {
        fragmentManager?.popBackStack()
    }

    companion object {

        const val ARG_ARTICLE_ID = "arg-article-id"

        fun create(articleId: String): DetailsFragment {
            return DetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_ARTICLE_ID, articleId)
                }
            }
        }
    }
}