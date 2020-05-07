package com.sujewan.dbs.view.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sujewan.dbs.api.Resource
import com.sujewan.dbs.base.BaseTest
import com.sujewan.dbs.model.Article
import com.sujewan.dbs.repository.ArticleRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Spy
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest= Config.NONE)
class HomeActivityViewModelTest: BaseTest() {
    private lateinit var viewModel: HomeActivityViewModel

    @Spy
    private val articlesLiveData: LiveData<Resource<List<Article>>> = MutableLiveData()

    @Mock
    private lateinit var repository: ArticleRepository

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setUp() {
        Mockito.`when`(repository.getArticles()).thenReturn(articlesLiveData)
        viewModel = HomeActivityViewModel(repository)
    }

    @Test
    fun `call get all articles if not present locally`() = runBlocking {
        viewModel.articlesLiveData.observeForever {}
        Mockito
            .verify(repository)
            .getArticles()

        return@runBlocking
    }
}