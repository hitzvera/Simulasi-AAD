package com.dicoding.todoapp.ui.list


import android.app.Instrumentation.ActivityMonitor
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.dicoding.todoapp.R
import com.dicoding.todoapp.ui.add.AddTaskActivity
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


//TODO 16 : Write UI test to validate when user tap Add Task (+), the AddTaskActivity displayed
@RunWith(AndroidJUnit4ClassRunner::class)
class TaskActivityTest {


    private lateinit var monitor: ActivityMonitor

    @Before
    fun setup(){
        ActivityScenario.launch(TaskActivity::class.java)
        monitor = getInstrumentation()
            .addMonitor(AddTaskActivity::class.java.name, null, false)
    }

    @Test
    fun moveToAddTask(){
        onView(withId(R.id.fab)).check(matches(isDisplayed()))
        onView(withId(R.id.fab)).perform(click())


        // tunggu 5 detik, jika lebih Timeout or false
        val secondActivity = getInstrumentation()
            .waitForMonitorWithTimeout(monitor, 5000)
        assertNotNull(secondActivity)
    }

}