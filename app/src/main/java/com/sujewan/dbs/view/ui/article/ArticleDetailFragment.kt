package com.sujewan.dbs.view.ui.article

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sujewan.dbs.R
import com.sujewan.dbs.api.Resource
import com.sujewan.dbs.api.Status
import com.sujewan.dbs.databinding.FragmentArticleDetailBinding
import com.sujewan.dbs.factory.AppViewModelFactory
import com.sujewan.dbs.model.Article
import com.sujewan.dbs.model.ArticleDescription
import com.sujewan.dbs.util.Constants.Companion.ARTICLE
import com.sujewan.dbs.view.ui.home.ItemArticleViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class ArticleDetailFragment : Fragment() {
    private val TAG = ArticleDetailFragment::class.java.simpleName
    private lateinit var binding : FragmentArticleDetailBinding
    private lateinit var delegate: ArticleDetailsDelegate
    private lateinit var articleDesModel: ArticleDescription

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    private val viewModel by lazy { ViewModelProviders.of(this,
        viewModelFactory).get(ArticleDetailViewModel::class.java) }

    companion object {
        fun newInstance(article: Article): ArticleDetailFragment {
            val args = Bundle()
            args.putParcelable(ARTICLE, article)
            val fragment =
                ArticleDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        if (context is ArticleDetailsDelegate) {
            delegate = context
        } else {
            throw ClassCastException("$context must implement ArticlesAdapterDelegate.")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentArticleDetailBinding.inflate(inflater, container, false)

        initView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val article: Article = arguments?.getParcelable(ARTICLE)!!
        val articleDescFromDB = viewModel.getArticleByIdFromDB(article.id)
        if (articleDescFromDB == null) {
            observeViewModel()
        } else {
            renderDataState(articleDescFromDB)
        }
    }

    private fun initView() {
        val article: Article = arguments?.getParcelable(ARTICLE)!!

        viewModel.articleId = article.id
        binding.layoutToolBar.lblTitle.text = article.title.capitalize()
        binding.vModel = ItemArticleViewModel(article)

        binding.layoutToolBar.imgBack.setOnClickListener {
            delegate.onBackButtonPressed()
        }

        binding.layoutToolBar.btnToolbarAction.setOnClickListener {
            delegate.onEditButtonPressed(article, articleDesModel)
        }
    }

    private fun observeViewModel() {
        viewModel.getArticleById(viewModel.articleId).observe(viewLifecycleOwner,
            Observer { it?.let { processResponse(it) } })
    }

    private fun processResponse(response: Resource<ArticleDescription>) {
        when (response.status) {
            Status.LOADING -> renderLoadingState()
            Status.SUCCESS -> renderDataState(response.data!!)
            Status.ERROR -> renderErrorState(response.error!!)
        }
    }

    private fun renderLoadingState() {
        binding.loadingView.visibility = View.VISIBLE
        binding.loadingView.playAnimation()
        binding.placeHolder.layoutPlaceHolder.visibility = View.GONE
        binding.imgAvatar.visibility = View.GONE
        binding.layoutToolBar.btnToolbarAction.visibility = View.GONE
    }

    private fun renderDataState(articleDesc: ArticleDescription) {
        this.articleDesModel = articleDesc
        binding.loadingView.visibility = View.GONE
        binding.loadingView.pauseAnimation()
        binding.placeHolder.layoutPlaceHolder.visibility = View.GONE
        binding.imgAvatar.visibility = View.VISIBLE
        binding.layoutToolBar.btnToolbarAction.visibility = View.VISIBLE

        binding.lblArticleDesc.text = articleDesc.desc.capitalize()
    }

    private fun renderErrorState(throwable: Throwable) {
        Log.e(TAG, throwable.localizedMessage ?: "Error happened in renderErrorState")
        binding.loadingView.visibility = View.GONE
        binding.loadingView.pauseAnimation()
        binding.imgAvatar.visibility = View.GONE
        binding.layoutToolBar.btnToolbarAction.visibility = View.GONE

        binding.placeHolder.lblPlaceHolder.text = getString(R.string.server_error)
        binding.placeHolder.layoutPlaceHolder.visibility = View.VISIBLE
    }

    interface ArticleDetailsDelegate {
        fun onBackButtonPressed()
        fun onEditButtonPressed(article: Article, articleDes: ArticleDescription)
    }

}