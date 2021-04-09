package de.kingrohbar.leavethehouse

import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import org.hamcrest.CoreMatchers.*
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters


@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@LargeTest
class EspressoTests {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun a_createChecklist() {

        onView(withId(R.id.add_checklist)).perform(click())
        onView(withId(R.id.titleInput)).perform(typeText("Espresso Checklist Title"))
        onView(withId(R.id.descriptionInput)).perform(
            typeText("Espresso Checklist Description"),
            ViewActions.closeSoftKeyboard()
        )
        onView(withId(R.id.createChecklistFab)).perform(click())

        onView(withText("Espresso Checklist Title")).check(matches(isDisplayed()))
    }

    @Test
    fun b_editChecklist() {
        val recyclerView: RecyclerView = activityRule.activity.findViewById(R.id.checklistRecyclerView)
        var itemCount: Int? = recyclerView.adapter?.itemCount

        onView(withId(R.id.edit)).perform(click())
        onView(ViewMatchers.withId(R.id.checklistRecyclerView))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    itemCount?.minus(1)!!,
                    click()
                )
            )
        onView(withId(R.id.titleInput)).perform(
            clearText(),
            typeText("Espresso Checklist edited Title")
        )
        onView(withId(R.id.descriptionInput)).perform(
            clearText(),
            typeText("Espresso Checklist edited Description"),
            ViewActions.closeSoftKeyboard()
        )
        onView(withId(R.id.editChecklistFab)).perform(click())

        onView(withText("Espresso Checklist edited Title")).check(matches(isDisplayed()))
    }

    @Test
    fun c_createTask(){
        val recyclerView: RecyclerView = activityRule.activity.findViewById(R.id.checklistRecyclerView)
        var itemCount: Int? = recyclerView.adapter?.itemCount

        onView(ViewMatchers.withId(R.id.checklistRecyclerView))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    itemCount?.minus(1)!!,
                    click()
                )
            )
        onView(withId(R.id.add_task)).perform(click())
        onView(withId(R.id.titleInputTask)).perform(typeText("Espresso Task Title"))
        onView(withId(R.id.descriptionInputTask)).perform(
            typeText("Espresso Task Description"),
            ViewActions.closeSoftKeyboard()
        )
        onView(withId(R.id.createTaskFab)).perform(click())

        onView(withText("Espresso Task Title")).check(matches(isDisplayed()))
        pressBack()
    }

    @Test
    fun d_checkTask(){
        val recyclerView: RecyclerView = activityRule.activity.findViewById(R.id.checklistRecyclerView)
        var itemCount: Int? = recyclerView.adapter?.itemCount

        onView(ViewMatchers.withId(R.id.checklistRecyclerView))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    itemCount?.minus(1)!!,
                    click()
                )
            )


        val activity = getActivityInstance()
        val recyclerViewTask: RecyclerView = activity!!.findViewById(R.id.taskRecyclerView)
        var itemCountTask: Int? = recyclerViewTask.adapter?.itemCount
        onView(ViewMatchers.withId(R.id.taskRecyclerView))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    itemCountTask?.minus(1)!!,
                    click()
                )
            )
        pressBack()
    }

    @Test
    fun e_editTask(){
        val recyclerView: RecyclerView = activityRule.activity.findViewById(R.id.checklistRecyclerView)
        var itemCount: Int? = recyclerView.adapter?.itemCount

        onView(ViewMatchers.withId(R.id.checklistRecyclerView))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    itemCount?.minus(1)!!,
                    click()
                )
            )


        val activity = getActivityInstance()
        val recyclerViewTask: RecyclerView = activity!!.findViewById(R.id.taskRecyclerView)
        var itemCountTask: Int? = recyclerViewTask.adapter?.itemCount
        onView(withId(R.id.editTasks)).perform(click())
        onView(ViewMatchers.withId(R.id.taskRecyclerView))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    itemCountTask?.minus(1)!!,
                    click()
                )
            )
        onView(withId(R.id.titleInputTask)).perform(
            clearText(),
            typeText("Espresso Task edited Title")
        )
        onView(withId(R.id.descriptionInputTask)).perform(
            clearText(),
            typeText("Espresso Task edited Description"),
            ViewActions.closeSoftKeyboard()
        )
        onView(withId(R.id.editTaskFab)).perform(click())

        onView(withText("Espresso Task edited Title")).check(matches(isDisplayed()))
        pressBack()
    }

    @Test
    fun f_deleteTask(){
        val recyclerView: RecyclerView = activityRule.activity.findViewById(R.id.checklistRecyclerView)
        var itemCount: Int? = recyclerView.adapter?.itemCount

        onView(ViewMatchers.withId(R.id.checklistRecyclerView))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    itemCount?.minus(1)!!,
                    click()
                )
            )


        val activity = getActivityInstance()
        val recyclerViewTask: RecyclerView = activity!!.findViewById(R.id.taskRecyclerView)
        var itemCountTask: Int? = recyclerViewTask.adapter?.itemCount
        onView(withId(R.id.editTasks)).perform(click())
        onView(ViewMatchers.withId(R.id.taskRecyclerView))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    itemCountTask?.minus(1)!!,
                    click()
                )
            )
        onView(withId(R.id.deleteTask)).perform(click())
        onView(withText("YES")).perform(click())

        onView(withText("Espresso Task edited Title")).check(doesNotExist())
        pressBack()
    }

    private fun getActivityInstance(): Activity? {
        val activity = arrayOfNulls<Activity>(1)
        InstrumentationRegistry.getInstrumentation().runOnMainSync(Runnable {
            var currentActivity: Activity? = null
            val resumedActivities: Collection<*> =
                ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED)
            if (resumedActivities.iterator().hasNext()) {
                currentActivity = resumedActivities.iterator().next() as Activity?
                activity[0] = currentActivity
            }
        })
        return activity[0]
    }

    @Test
    fun z_deleteChecklist() {

        val recyclerView: RecyclerView = activityRule.activity.findViewById(R.id.checklistRecyclerView)
        var itemCount: Int? = recyclerView.adapter?.itemCount

        onView(withId(R.id.edit)).perform(click())
        onView(ViewMatchers.withId(R.id.checklistRecyclerView))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    itemCount?.minus(1)!!,
                    click()
                )
            )
        onView(withId(R.id.deleteChecklist)).perform(click())
        onView(withText("YES")).perform(click())

        onView(withText("Espresso Checklist edited Title")).check(doesNotExist())
    }
}