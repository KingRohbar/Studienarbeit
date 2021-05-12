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