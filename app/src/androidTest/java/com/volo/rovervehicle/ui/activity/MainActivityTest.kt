package com.volo.rovervehicle.ui.activity

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.volo.rovervehicle.data.model.VehicleType
import com.volo.rovervehicle.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testTabLayoutDisplayed() {
        // Verify that the TabLayout is displayed
        onView(withId(com.volo.voloandroidtask.R.id.tab_layout))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testViewPagerDisplayed() {
        // Verify that the ViewPager is displayed
        onView(withId(com.volo.voloandroidtask.R.id.pager))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testTabLayoutTitles() {
        // Set up the expected tab titles
        // Verify that the TabLayout has the expected titles
        for (index in VehicleType.values()) {
            onView(withText(index.typeName))
                .check(matches(isDisplayed()))
        }
    }

    // Add more test cases as needed

}
