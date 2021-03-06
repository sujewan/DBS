package com.sujewan.dbs.view.ui.edit

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.sujewan.dbs.R
import com.sujewan.dbs.databinding.FragmentArticleEditBinding
import com.sujewan.dbs.factory.AppViewModelFactory
import com.sujewan.dbs.model.Article
import com.sujewan.dbs.model.ArticleDescription
import com.sujewan.dbs.util.Constants.Companion.ARTICLE
import com.sujewan.dbs.util.Constants.Companion.ARTICLE_DES
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class ArticleEditFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    private val viewModel by lazy { ViewModelProviders.of(this,
        viewModelFactory).get(ArticleEditViewModel::class.java) }

    private lateinit var binding : FragmentArticleEditBinding
    private lateinit var delegate: ArticleEditDelegate
    companion object {

        fun newInstance(article: Article, articleDesc: ArticleDescription): ArticleEditFragment {
            val args = Bundle()
            args.putParcelable(ARTICLE, article)
            args.putParcelable(ARTICLE_DES, articleDesc)
            val fragment = ArticleEditFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)

        if (context is ArticleEditDelegate) {
            delegate = context
        } else {
            throw ClassCastException("$context must implement ArticleEditDelegate.")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentArticleEditBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {
        val article: Article = arguments?.getParcelable(ARTICLE)!!
        val articleDesc: ArticleDescription = arguments?.getParcelable(ARTICLE_DES)!!

        binding.layoutToolBar.lblTitle.text = getString(R.string.edit_label).plus(article.title.capitalize())
        binding.layoutToolBar.btnToolbarAction.text = getString(R.string.cancel)
        binding.txtDescription.setText(articleDesc.desc.capitalize())

        binding.layoutToolBar.imgBack.setOnClickListener {
            delegate.onBackButtonPressed()
        }

        binding.layoutToolBar.btnToolbarAction.setOnClickListener {
            delegate.onCancelButtonPressed()
        }

        binding.btnSave.setOnClickListener {
            val newDesc = binding.txtDescription.text.toString().trim()
            if (isValidData(newDesc)) {
                viewModel.updateArticleDescription(
                    ArticleDescription(articleDesc.id, newDesc)
                )
                delegate.onShowSuccessDialog("Successfully saved : ${article.title}")
            } else {
                delegate.onShowErrorDialog(getString(R.string.error_msg_empty_desc))
            }
        }
    }

    private fun isValidData(newDesc: String) : Boolean {
        if (newDesc.isEmpty()) {
            return false
        }
        return true
    }

    interface ArticleEditDelegate {
        fun onBackButtonPressed()
        fun onCancelButtonPressed()
        fun onShowErrorDialog(errorMessage: String)
        fun onShowSuccessDialog(message: String)
    }
}