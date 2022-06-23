import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import org.junit.Test
import org.junit.runner.RunWith
import com.dicoding.courseschedule.R
import android.app.Instrumentation.ActivityMonitor
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.dicoding.courseschedule.ui.add.AddCourseActivity
import com.dicoding.courseschedule.ui.list.ListActivity
import junit.framework.Assert.assertNotNull
import org.junit.Before


@RunWith(AndroidJUnit4ClassRunner::class)
class ListActivityTest {


    private lateinit var monitor: ActivityMonitor

    @Before
    fun setup(){
        ActivityScenario.launch(ListActivity::class.java)
        monitor = getInstrumentation()
            .addMonitor(AddCourseActivity::class.java.name, null, false)
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