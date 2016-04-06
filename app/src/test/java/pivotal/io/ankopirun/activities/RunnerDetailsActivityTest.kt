package pivotal.io.ankopirun.activities

import android.content.ComponentName
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.isEmptyString
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.robolectric.Robolectric
import org.robolectric.internal.ShadowExtractor
import org.robolectric.shadows.ShadowActivity
import pivotal.io.ankopirun.RUNNER_NAME
import pivotal.io.ankopirun.RobolectricTest
import pivotal.io.ankopirun.activities.RunnerDetailsActivity
import pivotal.io.ankopirun.activities.RunnerLocationActivity

class RunnerDetailsActivityTest : RobolectricTest() {
    lateinit var activity: RunnerDetailsActivity

    @Before
    fun setUp() {
        activity = Robolectric.setupActivity(RunnerDetailsActivity::class.java)
    }

    @Test
    fun clickingOnOkButtonStartsRunnerLocationActivityWithName() {
        activity.nameField.setText("Herp Derp")

        activity.submitNameButton.performClick()

        val shadowActivity = ShadowExtractor.extract(activity) as ShadowActivity
        val actualIntent = shadowActivity.nextStartedActivity

        assertThat(actualIntent.component, equalTo(ComponentName(activity, RunnerLocationActivity::class.java)))
        assertThat(actualIntent.getStringExtra(RUNNER_NAME), equalTo("Herp Derp"))
    }

    @Test
    fun submitNameButtonIsDisabledIfNameIsEmpty() {
        assertThat(activity.nameField.text, isEmptyString)

        assertFalse(activity.submitNameButton.isEnabled)
    }

    @Test
    fun submitNameButtonIsEnabledIfNameIsNotEmpty() {
        activity.nameField.setText("Herp derp")

        assertTrue(activity.submitNameButton.isEnabled)
    }
}