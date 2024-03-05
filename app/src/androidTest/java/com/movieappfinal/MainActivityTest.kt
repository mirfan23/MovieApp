package com.movieappfinal

import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {
    private val dummyEmail = "email@email.com"
    private val dummyPassword = "12121212"
    private val delayTimeMs = 2000L
    private val idlingResources = DelayIdlingResources(delayTimeMs)

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        IdlingRegistry.getInstance().register(DelayIdlingResources(delayTimeMs))
    }

    @Test
    fun testLoginSuccess() {
        IdlingRegistry.getInstance().register(idlingResources)
        Espresso.onView(withId(R.id.btn_login_onBoarding))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        IdlingRegistry.getInstance().unregister(idlingResources)
        Espresso.onView(withId(R.id.btn_login_onBoarding)).perform(ViewActions.click())

        IdlingRegistry.getInstance().register(idlingResources)
        Espresso.onView(withId(R.id.tiet_email_login))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        IdlingRegistry.getInstance().unregister(idlingResources)
        Espresso.onView(withId(R.id.tiet_email_login)).perform(ViewActions.typeText(dummyEmail))

        IdlingRegistry.getInstance().register(idlingResources)
        Espresso.onView(withId(R.id.tiet_password_login))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        IdlingRegistry.getInstance().unregister(idlingResources)
        Espresso.onView(withId(R.id.tiet_password_login))
            .perform(ViewActions.typeText(dummyPassword))

        IdlingRegistry.getInstance().register(idlingResources)
        Espresso.onView(withId(R.id.btn_login))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        IdlingRegistry.getInstance().unregister(idlingResources)
        Espresso.onView(withId(R.id.btn_login)).perform(ViewActions.click())
    }
}

class DelayIdlingResources(private val delayTimeMs: Long) : IdlingResource {
    private var resourcesCallback: IdlingResource.ResourceCallback? = null
    private val startTime = System.currentTimeMillis()
    override fun getName(): String {
        return DelayIdlingResources::class.java.simpleName
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        resourcesCallback = callback
    }

    override fun isIdleNow(): Boolean {
        val elapseTime = System .currentTimeMillis() - startTime
        val isIdle = elapseTime >= delayTimeMs
        if (isIdle && resourcesCallback != null) {
            resourcesCallback?.onTransitionToIdle()
        }
        return isIdle
    }
}