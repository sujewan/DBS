package com.sujewan.dbs.di

import com.sujewan.dbs.view.ui.home.ArticleListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    internal abstract fun contributeArticleListFragment() : ArticleListFragment
}