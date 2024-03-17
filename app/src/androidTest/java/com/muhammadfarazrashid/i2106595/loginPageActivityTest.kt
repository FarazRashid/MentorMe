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
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.toPackage
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtras
import androidx.test.espresso.intent.matcher.IntentMatchers.hasType
import androidx.test.espresso.intent.matcher.IntentMatchers.filterEquals
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
import androidx.test.espresso.matcher.ViewMatchers.hasFocus
import androidx.test.espresso.matcher.ViewMatchers.withText
import java.util.concurrent.CompletableFuture.allOf
import java.util.function.Predicate.not

class loginPageActivityTest{

    @get:Rule
    val activityRule = activityScenarioRule<loginActivity>()

    val email: String ="ahmadhassan@gmail.com"
    val password:String = "faraz123456"

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



    //check if the login button works
    @Test
    fun testClickingLoginButtonLaunchesHomePageActivity() {
        onView(withId(R.id.signupButton)).perform(click())
        //the email field should show focus and error
        onView(withId(R.id.userEmail)).check(matches(hasFocus()))
    }

    //check if the email field is empty
    @Test
    fun testEmptyEmailField() {
        onView(withId(R.id.signupButton)).perform(click())
        onView(withId(R.id.userEmail)).check(matches(hasErrorText("Please enter your email")))
    }

    @Test
    fun testTypingEmail() {
        onView(withId(R.id.userEmail)).perform(typeText("Email"), closeSoftKeyboard())
        onView(withId(R.id.emailText)).check(matches(withText("Email")))
    }

    @Test
    fun testTypingPassword() {
        onView(withId(R.id.userPassword)).perform(typeText("Password"), closeSoftKeyboard())
        onView(withId(R.id.passwordText)).check(matches(withText("Password")))
    }

    //check if the password field is empty
    @Test
    fun testEmptyPasswordField() {

        onView(withId(R.id.userEmail)).perform(typeText(email), closeSoftKeyboard())
        onView(withId(R.id.signupButton)).perform(click())
        onView(withId(R.id.userPassword)).check(matches(hasErrorText("Please enter your password")))
    }


    @Test
    fun testLogin() {
        onView(withId(R.id.userEmail)).perform(typeText(email), closeSoftKeyboard())
        onView(withId(R.id.userPassword)).perform(typeText(password), closeSoftKeyboard())
        onView(withId(R.id.signupButton)).perform(click())
        //wait for the activity to launch
        Thread.sleep(5000)
        intended(hasComponent(homePageActivity::class.java.name))

    }




}
