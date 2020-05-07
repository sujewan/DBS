package com.sujewan.dbs.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sujewan.dbs.base.BaseTest
import okhttp3.mockwebserver.RecordedRequest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.net.HttpURLConnection

@RunWith(JUnit4::class)
class ArticleApiTest: BaseTest() {

    @Rule
    @JvmField val instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun getAllArticles_Success() {
        enqueueResponse("get_articles.json")
        val articles = LiveDataTestUtil.getValue(service.getAllArticles()).body

        val request: RecordedRequest = mockWebServer.takeRequest()
        MatcherAssert.assertThat(request.path, CoreMatchers.`is`("/article"))
        MatcherAssert.assertThat(articles, CoreMatchers.notNullValue())

        MatcherAssert.assertThat(articles?.get(0)?.id, CoreMatchers.`is`(1))
        MatcherAssert.assertThat(articles?.get(3)?.id, CoreMatchers.`is`(4))

        MatcherAssert.assertThat(articles?.get(1)?.title, CoreMatchers.`is`("article 2 title"))
        MatcherAssert.assertThat(articles?.get(3)?.title, CoreMatchers.`is`("article 4 title"))

        MatcherAssert.assertThat(articles?.get(1)?.shortDes,
            CoreMatchers.`is`("This is article 2 short description. She wholly fat who window extent either formal. Removing welcomed civility or hastened is."))
        MatcherAssert.assertThat(articles?.get(2)?.shortDes,
            CoreMatchers.`is`("This is article 3 short description. She wholly fat who window extent either formal. Removing welcomed civility or hastened is."))

        MatcherAssert.assertThat(articles?.get(0)?.avatar, CoreMatchers.`is`("https://minotar.net/avatar/user2"))
        MatcherAssert.assertThat(articles?.get(1)?.avatar, CoreMatchers.`is`("https://minotar.net/avatar/user3"))
    }

    @Test
    fun getAllArticles_Failure() {
        enqueueResponse("get_articles_empty.json")
        val articles = LiveDataTestUtil.getValue(service.getAllArticles()).body

        val request: RecordedRequest = mockWebServer.takeRequest()
        MatcherAssert.assertThat(request.path, CoreMatchers.`is`("/article"))
        MatcherAssert.assertThat(articles, CoreMatchers.notNullValue())
    }

    @Test
    fun getAllArticles_Empty_Records() {
        enqueueResponse("get_articles_empty.json")
        val articles = LiveDataTestUtil.getValue(service.getAllArticles()).body

        val request: RecordedRequest = mockWebServer.takeRequest()
        MatcherAssert.assertThat(request.path, CoreMatchers.`is`("/article"))
        MatcherAssert.assertThat(articles, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(articles?.size, CoreMatchers.`is`(0))
    }

    @Test
    fun getAllArticles_Internal_Error() {
        enqueueResponse("get_articles.json", HttpURLConnection.HTTP_INTERNAL_ERROR)
        val articles = LiveDataTestUtil.getValue(service.getAllArticles()).body

        val request: RecordedRequest = mockWebServer.takeRequest()
        MatcherAssert.assertThat(request.path, CoreMatchers.`is`("/article"))
        MatcherAssert.assertThat(articles, CoreMatchers.nullValue())
        MatcherAssert.assertThat(articles?.get(0), CoreMatchers.nullValue())
    }

    @Test
    fun getAllArticles_Network_Failure() {
        enqueueResponse("get_articles.json", HttpURLConnection.HTTP_GATEWAY_TIMEOUT)
        val articles = LiveDataTestUtil.getValue(service.getAllArticles()).body

        val request: RecordedRequest = mockWebServer.takeRequest()
        MatcherAssert.assertThat(request.path, CoreMatchers.`is`("/article"))
        MatcherAssert.assertThat(articles, CoreMatchers.nullValue())
        MatcherAssert.assertThat(articles?.get(0), CoreMatchers.nullValue())
    }

    @Test
    fun getArticleById_Success() {
        enqueueResponse("get_article_data_1.json")
        val articleDes = LiveDataTestUtil.getValue(service.getArticleById(1)).body

        val request: RecordedRequest = mockWebServer.takeRequest()
        MatcherAssert.assertThat(request.path, CoreMatchers.`is`("/article/?id=1"))
        MatcherAssert.assertThat(articleDes, CoreMatchers.notNullValue())

        MatcherAssert.assertThat(articleDes?.id, CoreMatchers.`is`(1))
        MatcherAssert.assertThat(articleDes?.desc,
            CoreMatchers.`is`("this is article 1 long text. Sitting mistake towards his few country ask. "))
    }
}