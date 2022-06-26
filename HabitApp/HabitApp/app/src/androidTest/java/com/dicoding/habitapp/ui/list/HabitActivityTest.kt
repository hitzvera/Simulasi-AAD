package com.dicoding.habitapp.ui.list

import android.app.Instrumentation
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.dicoding.habitapp.ui.add.AddHabitActivity
import com.dicoding.habitapp.R
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

//TODO 16 : Write UI test to validate when user tap Add Habit (+), the AddHabitActivity displayed
@RunWith(AndroidJUnit4ClassRunner::class)
class HabitActivityTest {


    private lateinit var monitor: Instrumentation.ActivityMonitor

    @Before
    fun setup(){
        ActivityScenario.launch(HabitListActivity::class.java)
        monitor = InstrumentationRegistry.getInstrumentation()
            .addMonitor(AddHabitActivity::class.java.name, null, false)
    }

    @Test
    fun moveToAddTask(){
        Espresso.onView(withId(R.id.fab)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.fab)).perform(ViewActions.click())


        // tunggu 5 detik, jika lebih Timeout or false
        val secondActivity = InstrumentationRegistry.getInstrumentation()
            .waitForMonitorWithTimeout(monitor, 5000)
        assertNotNull(secondActivity)
    }

}