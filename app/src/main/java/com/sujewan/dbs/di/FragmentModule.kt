package com.sujewan.dbs.di

import com.sujewan.dbs.view.ui.article.ArticleDetailFragment
import com.sujewan.dbs.view.ui.edit.ArticleEditFragment
import com.sujewan.dbs.view.ui.home.ArticleListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    internal abstract fun contributeArticleListFragment() : ArticleListFragment

    @ContributesAndroidInjector
    internal abstract fun contributeArticleDetailFragment() : ArticleDetailFragment

    @ContributesAndroidInjector
    internal abstract fun contributeArticleEditFragment() : ArticleEditFragment
}