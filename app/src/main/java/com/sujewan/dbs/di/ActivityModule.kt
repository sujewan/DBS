package com.sujewan.dbs.di

import com.sujewan.dbs.view.ui.edit.ArticleEditActivity
import com.sujewan.dbs.view.ui.home.HomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    internal abstract fun contributeHomeActivity(): HomeActivity

    @ContributesAndroidInjector
    internal abstract fun contributeArticleEditActivity(): ArticleEditActivity
}