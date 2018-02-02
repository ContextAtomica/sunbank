package com.pronab.sunbank

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by pronabpal on 1/2/18.
 */
@RunWith(AndroidJUnit4::class)
class TestSunActivity {
    @Rule
    var sunActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun TestTransactinComplete() {
        onView(withId(R.id.bal)).check(matches(isDisplayed()))

    }

}
