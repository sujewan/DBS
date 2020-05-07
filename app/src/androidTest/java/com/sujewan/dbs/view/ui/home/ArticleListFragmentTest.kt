package com.sujewan.dbs.view.ui.home

import android.content.res.Resources
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.sujewan.dbs.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class ArticleListFragmentTest {
    @get:Rule
    val activityRule = ActivityTestRule(HomeActivity::class.java)

    var recyclerView: RecyclerView? = null
    private var recyclerViewMatcher: RecyclerViewMatcher? = null

    companion object {
        private const val ITEM_FIRST_ITEM = "Article 1 title"
        private const val ITEM_LAST_ITEM = "Article 4 title"
    }

    @Before
    fun initActivity() {
        recyclerView = activityRule.activity.findViewById(R.id.rv_articles)
        recyclerViewMatcher = RecyclerViewMatcher(R.id.rv_articles)
    }

    @Test
    fun testRecyclerViewScroll() {
        // Get total item of RecyclerView
        val recyclerView =
            activityRule.activity.findViewById<RecyclerView>(R.id.rv_articles)
        val itemCount = recyclerView.adapter!!.itemCount

        // Scroll to end of page with position
        Espresso.onView(withId(R.id.rv_articles))
            .inRoot(
                RootMatchers.withDecorView(
                    Matchers.`is`(activityRule.activity.window.decorView)
                )
            )
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(itemCount - 1))
    }

    @Test
    fun verifyFirstItem() {
        try {
            Thread.sleep(6000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        // Verify the first element
        Espresso
            .onView(recyclerViewMatcher!!.atPosition(0))
            .check(
                ViewAssertions.matches(
                    ViewMatchers.hasDescendant(
                        ViewMatchers.withText(
                            ITEM_FIRST_ITEM
                        )
                    )
                )
            )
    }

    @Test
    fun verifyLastItem() {
        try {
            Thread.sleep(6000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        // Scroll to bottom of recyclerview
        Espresso
            .onView(withId(R.id.rv_articles))
            .perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                    recyclerView!!.adapter!!.itemCount - 1
                )
            )

        // Verify the last element
        Espresso
            .onView(recyclerViewMatcher!!.atPosition(recyclerView!!.adapter!!.itemCount - 1))
            .check(
                ViewAssertions.matches(
                    ViewMatchers.hasDescendant(
                        ViewMatchers.withText(
                            ITEM_LAST_ITEM
                        )
                    )
                )
            )
    }

    inner class RecyclerViewMatcher(private val recyclerViewId: Int) {
        fun atPosition(position: Int): Matcher<View> {
            return atPositionOnView(position, -1)
        }

        fun atPositionOnView(
            position: Int,
            targetViewId: Int
        ): Matcher<View> {
            return object : TypeSafeMatcher<View>() {
                var resources: Resources? = null
                var childView: View? = null
                override fun describeTo(description: Description) {
                    var idDescription = Integer.toString(recyclerViewId)
                    if (resources != null) {
                        idDescription = try {
                            resources!!.getResourceName(recyclerViewId)
                        } catch (var4: Resources.NotFoundException) {
                            String.format(
                                "%s (resource name not found)",
                                Integer.valueOf(recyclerViewId)
                            )
                        }
                    }
                    description.appendText("with id: $idDescription")
                }

                public override fun matchesSafely(view: View): Boolean {
                    resources = view.resources
                    if (childView == null) {
                        val recyclerView: RecyclerView =
                            view.rootView.findViewById(recyclerViewId)
                        childView = if (recyclerView.id == recyclerViewId) {
                            recyclerView.findViewHolderForAdapterPosition(position)!!.itemView
                        } else {
                            return false
                        }
                    }
                    return if (targetViewId == -1) {
                        view === childView
                    } else {
                        val targetView =
                            childView!!.findViewById<View>(targetViewId)
                        view === targetView
                    }
                }
            }
        }
    }
}
