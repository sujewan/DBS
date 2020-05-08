package com.sujewan.dbs.view.ui.edit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sujewan.dbs.R
import com.sujewan.dbs.model.Article
import com.sujewan.dbs.model.ArticleDescription
import com.sujewan.dbs.util.Constants.Companion.ARTICLE
import com.sujewan.dbs.util.Constants.Companion.ARTICLE_DES
import dagger.android.AndroidInjection

class ArticleEditActivity : AppCompatActivity(), ArticleEditFragment.ArticleEditDelegate {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val article: Article = intent.getParcelableExtra(ARTICLE)
        val articleDesc: ArticleDescription = intent.getParcelableExtra(ARTICLE_DES)

        if (savedInstanceState == null) {
            val articleEditFragment =
                ArticleEditFragment.newInstance(article, articleDesc)

            supportFragmentManager
                .beginTransaction()
                .add(R.id.root_layout, articleEditFragment, "articleEdit")
                .commit()
        }
    }

    override fun onBackButtonPressed() {
        onBackPressed()
    }

    override fun onCancelButtonPressed() {
        onBackPressed()
    }

    override fun onSaveButtonPressed() {
        onBackPressed()
    }
}
