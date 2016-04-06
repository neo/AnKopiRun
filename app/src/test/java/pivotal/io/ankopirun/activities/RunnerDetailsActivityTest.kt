package pivotal.io.ankopirun.activities

import android.content.Intent
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.isEmptyString
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.robolectric.Robolectric
import org.robolectric.internal.ShadowExtractor
import org.robolectric.shadows.ShadowActivity
import pivotal.io.ankopirun.RunnerDetailsActivity
import pivotal.io.ankopirun.RunnerLocationActivity

class RunnerDetailsActivityTest : RobolectricTest() {
    lateinit var activity: RunnerDetailsActivity

    @Before
    fun setUp() {
        activity = Robolectric.setupActivity(RunnerDetailsActivity::class.java)
    }

    @Test
    fun clickingOnOkButtonStartsRunnerLocationActivity() {
        activity.submitNameButton.performClick()

        val shadowActivity = ShadowExtractor.extract(activity) as ShadowActivity
        val actualIntent = shadowActivity.nextStartedActivity
        val expectedIntent = Intent(activity, RunnerLocationActivity::class.java)

        assertEquals(expectedIntent, actualIntent)
    }

    @Test
    fun submitNameButtonIsDisabledIfNameIsEmpty() {
        assertThat(activity.nameField.text, isEmptyString)

        assertFalse(activity.submitNameButton.isEnabled)
    }

    @Test
    fun submitNameButtonIsEnabledIfNameIsNotEmpty() {
        activity.nameField.setText("Asd")

        assertTrue(activity.submitNameButton.isEnabled)
    }
}