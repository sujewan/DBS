package com.sujewan.dbs.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sujewan.dbs.factory.AppViewModelFactory
import com.sujewan.dbs.view.ui.article.ArticleDetailViewModel
import com.sujewan.dbs.view.ui.home.HomeActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(HomeActivityViewModel::class)
    internal abstract fun bindHomeActivityViewModel(homeActivityViewModel: HomeActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ArticleDetailViewModel::class)
    internal abstract fun bindArticleDetailViewModel(articleDetailViewModel: ArticleDetailViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(appViewModelFactory: AppViewModelFactory) : ViewModelProvider.Factory
}