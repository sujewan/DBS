package com.sujewan.dbs.view.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.sujewan.dbs.R
import com.sujewan.dbs.databinding.ActivityHomeBinding
import com.sujewan.dbs.model.Article
import com.sujewan.dbs.model.ArticleDescription
import com.sujewan.dbs.util.CommonUtil
import com.sujewan.dbs.util.Constants.Companion.ARTICLE
import com.sujewan.dbs.util.Constants.Companion.ARTICLE_DES
import com.sujewan.dbs.view.adapter.ArticlesAdapter
import com.sujewan.dbs.view.ui.article.ArticleDetailFragment
import com.sujewan.dbs.view.ui.edit.ArticleEditActivity
import com.sujewan.dbs.view.ui.general.BaseAppCompatActivity
import dagger.android.AndroidInjection

class HomeActivity : BaseAppCompatActivity(),
    ArticlesAdapter.ArticlesAdapterDelegate, ArticleDetailFragment.ArticleDetailsDelegate {
    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityHomeBinding>(this, R.layout.activity_home) }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.root_layout, ArticleListFragment.newInstance(), "articleList")
                .commit()
        }

        if (!CommonUtil.isNetworkAvailable(this@HomeActivity)) {
            showSnackBar(binding.rootLayout, getString(R.string.no_internet))
        }
    }

    override fun onArticleSelected(article: Article) {
        val detailsFragment =
            ArticleDetailFragment.newInstance(article)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.root_layout, detailsFragment, "articleDetail")
            .addToBackStack(null)
            .commit()
    }

    override fun onEditButtonPressed(article: Article, articleDes: ArticleDescription) {
        val intent = Intent(this@HomeActivity, ArticleEditActivity::class.java)
        intent.putExtra(ARTICLE, article)
        intent.putExtra(ARTICLE_DES, articleDes)
        startActivity(intent)
    }

    override fun onBackButtonPressed() {
        onBackPressed()
    }
}
