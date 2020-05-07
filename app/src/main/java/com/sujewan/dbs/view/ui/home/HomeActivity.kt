package com.sujewan.dbs.view.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sujewan.dbs.R
import com.sujewan.dbs.model.Article
import com.sujewan.dbs.view.adapter.ArticlesAdapter
import com.sujewan.dbs.view.ui.article.ArticleDetailFragment
import dagger.android.AndroidInjection

class HomeActivity : AppCompatActivity(),
    ArticlesAdapter.ArticlesAdapterDelegate, ArticleDetailFragment.ArticleDetailsDelegate {

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

    override fun onEditButtonPressed(article: Article) {

    }

    override fun onBackButtonPressed() {
        onBackPressed()
    }
}
