package pivotal.io.ankopirun.activities

import android.content.Intent
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.isEmptyString
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.robolectric.Robolectric
import pivotal.io.ankopirun.RUNNER_NAME
import pivotal.io.ankopirun.RobolectricTest
import pivotal.io.ankopirun.models.Run
import pivotal.io.ankopirun.repositories.RunRepository

class RunnerLocationActivityTest : RobolectricTest() {
    lateinit var activity: RunnerLocationActivity

    @Before
    fun setUp() {
        activity = Robolectric.buildActivity(RunnerLocationActivity::class.java)
                .withIntent(Intent().putExtra(RUNNER_NAME, "Herp derp"))
                .create()
                .get()
    }

    @Test
    fun submitLocationButtonIsDisabledIfLocationIsEmpty() {
        assertThat(activity.locationField.text, isEmptyString)

        assertFalse(activity.submitLocationButton.isEnabled)
    }

    @Test
    fun submitLocationButtonIsEnabledIfLocationIsNotEmpty() {
        activity.locationField.setText("The Plain")

        assertTrue(activity.submitLocationButton.isEnabled)
    }

    @Test
    fun clickingOnSubmitLocationButtonCreatesRun() {
        val runRepository = mock(RunRepository::class.java)

        activity.runRepository = runRepository
        activity.locationField.setText("The Plain")

        activity.submitLocationButton.performClick()

        verify(runRepository).create(Run("Herp derp", "The Plain"))
    }
}