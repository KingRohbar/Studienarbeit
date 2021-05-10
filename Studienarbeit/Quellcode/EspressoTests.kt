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