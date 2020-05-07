package com.sujewan.dbs.view.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sujewan.dbs.R
import dagger.android.AndroidInjection

class HomeActivity : AppCompatActivity() {

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
}
