package com.sujewan.dbs.view.ui.edit

import android.os.Bundle
import com.sujewan.dbs.R
import com.sujewan.dbs.model.Article
import com.sujewan.dbs.model.ArticleDescription
import com.sujewan.dbs.util.Constants
import com.sujewan.dbs.util.Constants.Companion.ARTICLE
import com.sujewan.dbs.util.Constants.Companion.ARTICLE_DES
import com.sujewan.dbs.view.ui.general.BaseAppCompatActivity
import dagger.android.AndroidInjection
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ArticleEditActivity : BaseAppCompatActivity(), ArticleEditFragment.ArticleEditDelegate {

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

    override fun onShowErrorDialog(errorMessage: String) {
        showWarningDialog(errorMessage)
    }

    override fun onShowSuccessDialog(message: String) {
        showSuccessDialog(message)
        GlobalScope.launch {
            delay(Constants.LOADING_SUCCESS_DELAY)
            finish()
        }
    }
}
