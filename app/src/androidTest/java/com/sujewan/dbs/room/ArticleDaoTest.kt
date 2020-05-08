package com.sujewan.dbs.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.sujewan.dbs.model.Article
import com.sujewan.dbs.model.ArticleDescription
import com.sujewan.dbs.util.LiveDataUtil.getValue
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArticleDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var articleDao: ArticleDao
    private lateinit var articleA: Article
    private lateinit var articleB: Article
    private lateinit var articleC: Article

    private lateinit var articleDesA: ArticleDescription
    private lateinit var articleDesB: ArticleDescription
    private lateinit var articleDesC: ArticleDescription

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDB() {
        val context = InstrumentationRegistry.getTargetContext()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        articleDao = database.articleDao()

        articleDao.insertAllArticles(createMockData())
        articleDao.insertArticleDescription(articleDesA)
        articleDao.insertArticleDescription(articleDesB)
        articleDao.insertArticleDescription(articleDesC)
    }

    private fun createMockData(): ArrayList<Article> {
        articleA = Article(1, "Article 1 Title", 1586404611,
            "This is article 1 short description.", "https://minotar.net/avatar/user2")
        articleB = Article(2, "Article 2 Title", 1586214612,
            "This is article 2 short description.", "https://minotar.net/avatar/user3")
        articleC = Article(3, "Article 3 Title", 1586704643,
            "This is article 3 short description.", "https://minotar.net/avatar/user4")

        articleDesA = ArticleDescription(1, "This is article 1 Long description.")
        articleDesB = ArticleDescription(2, "This is article 2 Long description.")
        articleDesC = ArticleDescription(3, "This is article 3 Long description.")

        return arrayListOf(articleA, articleB, articleC)
    }

    @After
    fun closeDB() {
        database.close()
    }

    @Test
    fun getAllArticlesTest() {
        val articleList = getValue(articleDao.getAllArticles())
        assertThat(articleList.size, equalTo(3))

        assertThat(articleList[0], equalTo(articleC))
        assertThat(articleList[1], equalTo(articleA))
        assertThat(articleList[2], equalTo(articleB))
    }

    @Test
    fun getArticleByIdTest() {
        val articleDesTestA = getValue(articleDao.getArticleById(articleA.id))
        val articleDesTestB = getValue(articleDao.getArticleById(articleB.id))
        val articleDesTestC = getValue(articleDao.getArticleById(articleC.id))

        assertThat(articleDesTestA.id, equalTo(articleA.id))
        assertThat(articleDesTestB.id, equalTo(articleB.id))
        assertThat(articleDesTestC.id, equalTo(articleC.id))

        assertThat(articleDesTestA.desc, equalTo(articleDesTestA.desc))
        assertThat(articleDesTestB.desc, equalTo(articleDesTestB.desc))
        assertThat(articleDesTestC.desc, equalTo(articleDesTestC.desc))
    }

    @Test
    fun updateLastUpdateTest() {
        val lastUpdateA: Long  = 1486404611
        val lastUpdateB: Long  = 1562404698
        val lastUpdateC: Long  = 1584604632
        articleDao.updateArticleLastUpdate(articleA.id, lastUpdateA)
        articleDao.updateArticleLastUpdate(articleB.id, lastUpdateB)
        articleDao.updateArticleLastUpdate(articleC.id, lastUpdateC)

        val articleList = getValue(articleDao.getAllArticles())
        assertThat(articleList[0].lastUpdate, equalTo(lastUpdateC))
        assertThat(articleList[1].lastUpdate, equalTo(lastUpdateB))
        assertThat(articleList[2].lastUpdate, equalTo(lastUpdateA))
    }


}