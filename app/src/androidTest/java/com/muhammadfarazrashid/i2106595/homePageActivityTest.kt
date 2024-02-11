package com.muhammadfarazrashid.i2106595

import android.widget.ImageView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.junit.Assert.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import java.util.concurrent.CompletableFuture.allOf
import java.util.function.Predicate.not

class homePageActivityTest{

    @get:Rule
    val activityRule = activityScenarioRule<homePageActivity>()

    @Before
    fun setUp() {
        // Initialize Espresso Intents
        Intents.init()
    }

    @After
    fun tearDown() {
        // Release Espresso Intents
        Intents.release()
    }

    private val expectedMentorNames = listOf("Faraz Rashid", "John Doe", "Jane Doe", "John Smith", "Jane Smith")

    @Test
    fun testClickingAddMentorButtonLaunchesAddAMentorActivity() {
        onView(withId(R.id.addMentorButton)).perform(click())
        intended(hasComponent(AddAMentor::class.java.name))
    }

    @Test
    fun testClickingNotificationsButtonLaunchesNotificationsActivity() {
        onView(withId(R.id.bellIcon)).perform(click())
        intended(hasComponent(NotificationsActivity::class.java.name))

    }

}
