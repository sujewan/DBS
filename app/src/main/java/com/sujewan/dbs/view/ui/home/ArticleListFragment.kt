package com.sujewan.dbs.view.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.sujewan.dbs.R
import com.sujewan.dbs.api.Resource
import com.sujewan.dbs.api.Status
import com.sujewan.dbs.databinding.FragmentArticleBinding
import com.sujewan.dbs.factory.AppViewModelFactory
import com.sujewan.dbs.model.Article
import com.sujewan.dbs.view.adapter.ArticlesAdapter
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class ArticleListFragment: Fragment() {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    private val viewModel by lazy { ViewModelProviders.of(this,
        viewModelFactory).get(HomeActivityViewModel::class.java) }

    lateinit var binding : FragmentArticleBinding

    companion object {
        fun newInstance() : ArticleListFragment {
            val args = Bundle()
            val fragment = ArticleListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_article, container, false)
        if ( savedInstanceState == null ) {
            viewModel.firstTime = true
        }

        initView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeViewModel()
    }

    private fun initView() {
        val linearLayout = LinearLayoutManager(context)
        binding.rvArticles.layoutManager = linearLayout
        binding.rvArticles.setHasFixedSize(true)
        viewModel.adapter = ArticlesAdapter()
        binding.rvArticles.adapter = viewModel.adapter
    }

    private fun observeViewModel() {
        viewModel.articlesLiveData.observe(viewLifecycleOwner, Observer { it?.let { processResponse(it) } })
    }

    private fun processResponse(response: Resource<List<Article>>) {
        when (response.status) {
            Status.LOADING -> renderLoadingState()
            Status.SUCCESS -> renderDataState(response.data!!)
            Status.ERROR -> renderErrorState(response.error!!)
        }
    }

    private fun renderLoadingState() {
        binding.loadingView.visibility = View.VISIBLE
        binding.loadingView.playAnimation()
    }

    private fun renderDataState(items : List<Article>) {
        viewModel.adapter.updateList(items as ArrayList<Article>)

        binding.loadingView.visibility = View.GONE
        binding.loadingView.pauseAnimation()

        if(viewModel.firstTime) {
            binding.rvArticles.scheduleLayoutAnimation()
            viewModel.firstTime = false
        }
    }

    private fun renderErrorState(throwable: Throwable) {
        binding.loadingView.visibility = View.GONE
        binding.loadingView.pauseAnimation()
    }
}
