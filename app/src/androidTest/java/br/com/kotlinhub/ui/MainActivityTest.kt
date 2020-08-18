package br.com.kotlinhub.ui

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import br.com.kotlinhub.R
import br.com.kotlinhub.RecyclerViewTestHelper.atPosition
import br.com.kotlinhub.adapter.RepoListAdapter
import br.com.kotlinhub.util.EspressoIdlingResource
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    val intentRule = IntentsTestRule(MainActivity::class.java)

    @Test
    fun a_whenAppLaunches_shouldShowInitialProgressBar() {
        onView(withId(R.id.pbEmptyList)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.pbReposList)).check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    @Test
    fun b_whenAppLaunches_shouldDisplayRecyclerView() {
        onView(withId(R.id.rvRepos)).check(matches(isDisplayed()))
    }

    @Test
    fun c_whenClickOnRecyclerViewItem_shouldCallIntentWithActionView() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)

        val itemPosition = 0
        onView(withId(R.id.rvRepos))
            .perform(
                actionOnItemAtPosition<RepoListAdapter.RepoListViewHolder>(
                    itemPosition,
                    click()
                )
            )
        intended(hasAction(Intent.ACTION_VIEW))

        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun d_whenShowsRecyclerViewList_shouldHaveAllDescendantViewsForEachListItem() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        val pageSize = 30

        for (itemPosition in 0 until pageSize) {
            onView(withId(R.id.rvRepos))
                .perform(scrollToPosition<RepoListAdapter.RepoListViewHolder>(itemPosition))
                .check(matches(atPosition(itemPosition, hasDescendant(withId(R.id.ivOwnerPhoto)))))
                .check(matches(atPosition(itemPosition, hasDescendant(withId(R.id.tvOwnerName)))))
                .check(matches(atPosition(itemPosition, hasDescendant(withId(R.id.tvRepoName)))))
                .check(
                    matches(
                        atPosition(
                            itemPosition,
                            hasDescendant(withId(R.id.tvRepoDescription))
                        )
                    )
                )
                .check(matches(atPosition(itemPosition, hasDescendant(withId(R.id.ivFork)))))
                .check(matches(atPosition(itemPosition, hasDescendant(withId(R.id.ivStar)))))
                .check(matches(atPosition(itemPosition, hasDescendant(withId(R.id.tvForkCount)))))
                .check(matches(atPosition(itemPosition, hasDescendant(withId(R.id.tvStarCount)))))
        }

        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

//    @Test
//    fun e_whenScrollsRecyclerViewListUponEndOfPage_shouldDisplayProgressBar() {
//        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
//        val pageSize = 30
//        val itemPosition = pageSize - 1
//
//            onView(withId(R.id.rvRepos))
//                .perform(scrollToPosition<RepoListAdapter.RepoListViewHolder>(itemPosition))
//            // Not working
//            onView(withId(R.id.pbReposList)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
//
//        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
//    }

}
